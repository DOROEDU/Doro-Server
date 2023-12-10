package com.example.DoroServer.domain.lectureContent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;
import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;
import com.example.DoroServer.global.s3.AwsS3ServiceImpl;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContentImageS3ServiceImpl implements ContentImageS3Service {
    private final AwsS3ServiceImpl awsS3Service;

    void validate(MultipartFile[] files) {
        if (files == null) {
            // Multipart가 1개라도 배열에 넣어서 요청해야함
            throw new BaseException(Code.JSON_SYNTAX_ERROR);
        }
        if (files.length > 100 || files.length < 1) {
            throw new BaseException(Code.LECTURE_CONTENT_INVAILD_IMAGE_COUNT);
        }
        for (MultipartFile file : files) {
            if (file.getSize() > 10485760) { // 10MB
                throw new BaseException(Code.LECTURE_CONTENT_IMAGE_SIZE_OVER);
            }
        }
    }

    String getFileNameFrom(String url) {
        // UUID(36자) + .jpg(4자) = 파일명(40자)
        return url.substring(url.length() - 40);
    }

    @Override
    public List<String> uploadS3Images(MultipartFile[] files) {
        validate(files);

        // S3 BUCKET/lecture-content에 파일 업로드 후, 업로드된 파일들의 URL 반환
        return Arrays.stream(files)
                .map(file -> awsS3Service.upload(file, "lecture-content"))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteS3Image(LectureContentImage lectureContentImage) {
        String uploadedUrl = lectureContentImage.getUrl();
        String fileName = getFileNameFrom(uploadedUrl);
        awsS3Service.deleteImage2(fileName);
    }

}