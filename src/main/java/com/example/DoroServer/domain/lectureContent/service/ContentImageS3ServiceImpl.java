package com.example.DoroServer.domain.lectureContent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;
import com.example.DoroServer.global.s3.AwsS3ServiceImpl;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContentImageS3ServiceImpl implements ContentImageS3Service {
    private final AwsS3ServiceImpl awsS3Service;

    private static final int fileNameLength = 40; // UUID(36)+확장자(4)

    String getFileNameFrom(String url) {
        return url.substring(url.length() - fileNameLength);
    }

    @Override
    public List<String> uploadS3Images(MultipartFile[] files) {
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