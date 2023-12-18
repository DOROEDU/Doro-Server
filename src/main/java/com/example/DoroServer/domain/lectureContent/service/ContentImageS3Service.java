package com.example.DoroServer.domain.lectureContent.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;

public interface ContentImageS3Service {
    List<String> uploadS3Images(MultipartFile[] files);

    void deleteS3Image(LectureContentImage lectureContentImage);
}
