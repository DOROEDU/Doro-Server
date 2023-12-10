package com.example.DoroServer.domain.lectureContentImage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.DoroServer.domain.lectureContent.dto.LectureContentRes;
import com.example.DoroServer.domain.lectureContent.entity.LectureContent;
import com.example.DoroServer.domain.lectureContent.repository.LectureContentRepository;
import com.example.DoroServer.domain.lectureContent.service.ContentImageS3ServiceImpl;
import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageMapper;
import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageReq;
import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageRes;
import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;
import com.example.DoroServer.domain.lectureContentImage.repository.LectureContentImageRepository;
import com.example.DoroServer.global.exception.BaseException;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LectureContentImageServiceTest {

    @InjectMocks
    private LectureContentImageService lectureContentImageService;

    @Mock
    private LectureContentImageRepository lectureContentImageRepository;

    @Mock
    private LectureContentRepository lectureContentRepository;

    @Mock
    private ContentImageS3ServiceImpl contentImageS3Service;

    @Spy
    private LectureContentImageMapper lectureContentImageMapper = Mappers.getMapper(LectureContentImageMapper.class);

    LectureContentImageReq setUplectureContentImageReq() {
        MockMultipartFile mockFile = new MockMultipartFile("file", "Hello, World!".getBytes());
        return LectureContentImageReq.builder()
                .files(new MultipartFile[] { mockFile })
                .build();
    }

    String UPLOADED_URL = "url";

    LectureContent setUplectureContent() {
        return LectureContent.builder()
                .id(1L)
                .kit("kit")
                .detail("detail")
                .requirement("requirement")
                .content("content")
                .build();
    }

    LectureContentImage setUplectureContentImage() {
        return LectureContentImage.builder()
                .id(1L)
                .url(UPLOADED_URL)
                .build();
    }

    LectureContentImageRes setUplectureContentImageRes() {
        return LectureContentImageRes.builder()
                .id(1L)
                .url(UPLOADED_URL)
                .build();
    }

    LectureContentRes setUplectureContentRes() {
        return LectureContentRes.builder()
                .id(1L)
                .kit("kit")
                .detail("detail")
                .requirement("requirement")
                .content("content")
                .images(List.of(setUplectureContentImageRes()))
                .build();
    }

    @Test
    @DisplayName("강의 컨텐츠 이미지 추가 테스트")
    void addLectureContentImages() {
        // given
        LectureContentImageReq lectureContentImageReq = setUplectureContentImageReq();
        Long id = 1L;
        LectureContent lectureContent = setUplectureContent();
        List<LectureContentImageRes> lectureContentImageResList = List.of(setUplectureContentImageRes());

        given(lectureContentRepository.findById(id)).willReturn(Optional.of(lectureContent));
        given(contentImageS3Service.uploadS3Images(lectureContentImageReq.getFiles()))
                .willReturn(List.of(UPLOADED_URL));
        given(lectureContentImageRepository.saveAll(anyList()))
                .willReturn(List.of(setUplectureContentImage()));
        given(lectureContentImageMapper.toLectureContentImageResList(lectureContent.getImages()))
                .willReturn(lectureContentImageResList);

        // when
        List<LectureContentImageRes> result = lectureContentImageService.addLectureContentImages(id,
                lectureContentImageReq);

        // then
        assertThat(result).isEqualTo(lectureContentImageResList);
        verify(lectureContentRepository, times(1)).findById(id);
        verify(contentImageS3Service, times(1)).uploadS3Images(lectureContentImageReq.getFiles());
        verify(lectureContentImageRepository, times(1)).saveAll(anyList());
        verify(lectureContentImageMapper, times(1)).toLectureContentImageResList(lectureContent.getImages());

    }

    @Test
    @DisplayName("강의 컨텐츠 이미지 삭제 테스트")
    void deleteLectureContentImage() {
        // given
        Long id = 1L;
        Long imageId = 1L;
        LectureContent lectureContent = setUplectureContent();
        LectureContentImage lectureContentImage = setUplectureContentImage();
        lectureContent.getImages().add(lectureContentImage);

        given(lectureContentRepository.findById(id)).willReturn(Optional.of(lectureContent));
        doNothing().when(contentImageS3Service).deleteS3Image(lectureContentImage);
        willDoNothing().given(lectureContentImageRepository).delete(lectureContentImage);

        // when
        lectureContentImageService.deleteLectureContentImage(id, imageId);

        // then
        verify(lectureContentRepository, times(1)).findById(id);
        verify(contentImageS3Service, times(1)).deleteS3Image(lectureContentImage);
        verify(lectureContentImageRepository, times(1)).delete(lectureContentImage);
    }

    @Test
    @DisplayName("유효하지 않은 강의 컨텐츠 이미지 삭제 예외 발생 테스트")
    void deleteLectureContentImageWithInvalidId() {
        // given
        Long id = -1L;
        Long imageId = 1L;

        given(lectureContentRepository.findById(id)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> lectureContentImageService.deleteLectureContentImage(id, imageId))
                .isInstanceOf(BaseException.class);
    }

}
