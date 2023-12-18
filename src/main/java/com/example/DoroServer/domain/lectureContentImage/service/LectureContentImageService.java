package com.example.DoroServer.domain.lectureContentImage.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.DoroServer.domain.lectureContent.entity.LectureContent;
import com.example.DoroServer.domain.lectureContent.repository.LectureContentRepository;
import com.example.DoroServer.domain.lectureContent.service.ContentImageS3ServiceImpl;
import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageMapper;
import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageReq;
import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageRes;
import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;
import com.example.DoroServer.domain.lectureContentImage.repository.LectureContentImageRepository;
import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureContentImageService {
    private final LectureContentImageRepository lectureContentImageRepository;
    private final LectureContentRepository lectureContentRepository;
    private final LectureContentImageMapper lectureContentImageMapper;
    private final ContentImageS3ServiceImpl contentImageS3Service;

    public List<LectureContentImageRes> addLectureContentImages(Long id,
            LectureContentImageReq lectureContentImageReq) {
        lectureContentImageReq.validateFiles();
        LectureContent lectureContent = findLectureContentById(id);

        // lectureContentReq의 files를 S3에 업로드 후, 업로드된 파일들의 URL을 LectureContentImage로 변환
        List<String> uploadedUrls = contentImageS3Service.uploadS3Images(lectureContentImageReq.getFiles());
        List<LectureContentImage> lectureContentImages = uploadedUrls.stream()
                .map(LectureContentImage::new)
                .collect(Collectors.toList());

        lectureContentImages = lectureContentImageRepository.saveAll(lectureContentImages);
        lectureContent.getImages().addAll(lectureContentImages);

        return lectureContentImageMapper.toLectureContentImageResList(lectureContentImages);
    }

    public void deleteLectureContentImage(Long contentId, Long imageId) {
        LectureContent lectureContent = findLectureContentById(contentId);
        LectureContentImage lectureContentImage = getLectureContentImage(imageId, lectureContent);

        contentImageS3Service.deleteS3Image(lectureContentImage);
        lectureContent.getImages().remove(lectureContentImage);
        lectureContentImageRepository.delete(lectureContentImage);
    }

    /* 서비스 코드에서 사용되는 메서드 */

    private LectureContentImage getLectureContentImage(Long imageId, LectureContent lectureContent) {
        LectureContentImage lectureContentImage = lectureContent.getImages().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new BaseException(Code.LECTURE_CONTENT_IMAGE_NOT_FOUND));
        return lectureContentImage;
    }

    LectureContent findLectureContentById(Long id) {
        return lectureContentRepository.findById(id)
                .orElseThrow(() -> new BaseException(Code.LECTURE_CONTENT_NOT_FOUND));
    }

}
