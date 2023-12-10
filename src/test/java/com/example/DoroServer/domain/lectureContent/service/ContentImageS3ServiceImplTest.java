package com.example.DoroServer.domain.lectureContent.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.DoroServer.domain.lectureContent.dto.CreateLectureContentReq;
import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;
import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.s3.AwsS3ServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ContentImageS3ServiceImplTest {
    @InjectMocks
    private ContentImageS3ServiceImpl contentImageS3Service;

    @Mock
    private AwsS3ServiceImpl awsS3Service;

    private String FILE_URL = "https://rodu-s3/lecture-content/052d05f0-e3aa-4088-9b6d-ed3bc42485b2.jpg";

    private CreateLectureContentReq setUpLectureContentReq0() {
        return CreateLectureContentReq.builder()
                .kit("kit")
                .detail("detail")
                .requirement("requirement")
                .content("content")
                .build();
    }

    private CreateLectureContentReq setUpLectureContentReq1() {
        MockMultipartFile mockFile = new MockMultipartFile("file", "Hello, World!".getBytes());
        return CreateLectureContentReq.builder()
                .kit("kit")
                .detail("detail")
                .requirement("requirement")
                .content("content")
                .files(new MultipartFile[] { mockFile })
                .build();
    }

    private LectureContentImage setUpLectureContentImage() {
        return LectureContentImage.builder()
                .url(FILE_URL).build();
    }

    @Test
    @DisplayName("validate 테스트 - 이미지 파일이 없는 경우 예외발생")
    void validateTest0() {
        // given
        CreateLectureContentReq lectureContentReq = setUpLectureContentReq0();

        // when & then
        assertThrows(BaseException.class, () -> contentImageS3Service.validate(lectureContentReq.getFiles()));

    }

    @Test
    @DisplayName("uploadS3Images 테스트")
    void uploadS3ImagesTest() {
        // given
        CreateLectureContentReq lectureContentReq = setUpLectureContentReq1();

        given(awsS3Service.upload(any(MultipartFile.class), eq("lecture-content")))
                .willReturn(FILE_URL);

        // when
        List<String> urls = contentImageS3Service.uploadS3Images(lectureContentReq.getFiles());

        // then
        verify(awsS3Service, times(1)).upload(any(MultipartFile.class), eq("lecture-content"));
        assertThat(urls.get(0)).isEqualTo(FILE_URL);

    }

    @Test
    @DisplayName("deleteS3Image 테스트")
    void deleteS3ImageTest() {
        // given
        LectureContentImage lectureContentImage = setUpLectureContentImage();

        // when
        contentImageS3Service.deleteS3Image(lectureContentImage);

        // then
        verify(awsS3Service, times(1)).deleteImage2(anyString());

    }

}
