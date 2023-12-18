package com.example.DoroServer.domain.lectureContent.service;

import com.example.DoroServer.domain.lectureContent.dto.LectureContentMapper;
import com.example.DoroServer.domain.lectureContent.dto.CreateLectureContentReq;
import com.example.DoroServer.domain.lectureContent.dto.LectureContentRes;
import com.example.DoroServer.domain.lectureContent.dto.UpdateLectureContentReq;
import com.example.DoroServer.domain.lectureContent.entity.LectureContent;
import com.example.DoroServer.domain.lectureContent.repository.LectureContentRepository;
import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;
import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;

import java.util.stream.Collectors;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureContentService {

    private final LectureContentRepository lectureContentRepository;
    private final LectureContentMapper lectureContentMapper;
    private final ModelMapper modelMapper;
    private final ContentImageS3ServiceImpl contentImageS3Service;

    /* 서비스 코드 */

    public List<LectureContentRes> findAllLectureContents() {
        List<LectureContent> lectureContentList = lectureContentRepository.findAll();

        return lectureContentMapper.toLectureContentResList(lectureContentList);
    }

    public LectureContentRes createLectureContent(CreateLectureContentReq lectureContentReq) {
        lectureContentReq.validateFiles();
        LectureContent lectureContent = modelMapper.map(lectureContentReq, LectureContent.class);

        // lectureContentReq의 files를 S3에 업로드 후, 업로드된 파일들의 URL을 LectureContentImage로 변환
        List<String> uploadedUrls = contentImageS3Service.uploadS3Images(lectureContentReq.getFiles());
        List<LectureContentImage> lectureContentImages = uploadedUrls.stream()
                .map(LectureContentImage::new)
                .collect(Collectors.toList());

        lectureContent.getImages().addAll(lectureContentImages);
        LectureContent savedLectureContent = lectureContentRepository.save(lectureContent);

        return lectureContentMapper.toLectureContentRes(savedLectureContent);
    }

    public LectureContentRes updateLectureContent(Long id, UpdateLectureContentReq updateLectureContentReq) {
        LectureContent lectureContent = findLectureContentById(id);

        modelMapper.map(updateLectureContentReq, lectureContent);

        LectureContent updatedLectureContent = lectureContentRepository.save(lectureContent);

        return lectureContentMapper.toLectureContentRes(updatedLectureContent);
    }

    public void deleteLectureContent(Long id) {
        LectureContent lectureContent = findLectureContentById(id);

        lectureContent.getImages().forEach(contentImageS3Service::deleteS3Image);

        lectureContentRepository.delete(lectureContent);
    }

    /* 서비스 코드에서 사용되는 메서드 */

    LectureContent findLectureContentById(Long id) {
        return lectureContentRepository.findById(id)
                .orElseThrow(() -> new BaseException(Code.LECTURE_CONTENT_NOT_FOUND));
    }

}
