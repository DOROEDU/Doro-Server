package com.example.DoroServer.domain.lectureContent.service;

import com.example.DoroServer.domain.lectureContent.dto.CreateLectureContentReq;
import com.example.DoroServer.domain.lectureContent.dto.LectureContentMapper;
import com.example.DoroServer.domain.lectureContent.dto.LectureContentRes;
import com.example.DoroServer.domain.lectureContent.dto.UpdateLectureContentReq;
import com.example.DoroServer.domain.lectureContent.entity.LectureContent;
import com.example.DoroServer.domain.lectureContent.repository.LectureContentRepository;
import com.example.DoroServer.global.exception.BaseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureContentServiceTest {

    @InjectMocks
    private LectureContentService lectureContentService;

    @Mock
    private LectureContentRepository lectureContentRepository;

    @Mock
    private ContentImageS3ServiceImpl contentImageS3Service;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private LectureContentMapper lectureContentMapper = Mappers.getMapper(LectureContentMapper.class);

    private LectureContent setUpLectureContent() {
        return LectureContent.builder()
                .id(1L)
                .kit("kit")
                .detail("detail")
                .requirement("requirement")
                .content("content")
                .build();
    }

    private CreateLectureContentReq setUpLectureContentReq() {
        MockMultipartFile mockFile = new MockMultipartFile("file", "Hello, World!".getBytes());
        return CreateLectureContentReq.builder()
                .kit("kit")
                .detail("detail")
                .requirement("requirement")
                .content("content")
                .files(new MultipartFile[] { mockFile })
                .build();
    }

    private UpdateLectureContentReq setUpUpdateLectureContentReq() {
        return UpdateLectureContentReq.builder()
                .kit("updated")
                .content("updated")
                .build();
    }

    private LectureContentRes setUpLectureContentRes() {
        return LectureContentRes.builder()
                .id(1L)
                .kit("kit")
                .detail("detail")
                .requirement("requirement")
                .content("content")
                .images(new ArrayList<>())
                .build();
    }

    private LectureContentRes setUpUpdatedLectureContentRes() {
        return LectureContentRes.builder()
                .id(1L)
                .kit("updated")
                .detail("detail")
                .requirement("requirement")
                .content("updated")
                .images(new ArrayList<>())
                .build();
    }

    private List<LectureContent> setUpLectureContentList(int listSize) {
        List<LectureContent> allContents = new ArrayList<LectureContent>();
        for (int i = 0; i < listSize; i++) {
            allContents.add(setUpLectureContent());
        }
        return allContents;
    }

    private List<LectureContentRes> setUpLectureContentResList(int listSize) {
        List<LectureContentRes> allContents = new ArrayList<LectureContentRes>();
        for (int i = 0; i < listSize; i++) {
            allContents.add(setUpLectureContentRes());
        }
        return allContents;
    }

    @DisplayName("강의자료 생성 Mapper 테스트")
    @Test
    void toLectureContentMapperTest() throws IllegalAccessException {
        // given
        CreateLectureContentReq createLectureContentReq = setUpLectureContentReq();
        LectureContent lectureContent = setUpLectureContent();

        // when
        LectureContent lectureContentMapped = modelMapper.map(createLectureContentReq, LectureContent.class);

        // then
        for (Field field : lectureContentMapped.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(lectureContent);
            if (field.getName().equals("id")) {
                continue;
            }
            assertThat(value).isNotNull();
        }

    }

    @DisplayName("강의 자료 생성 테스트")
    @Test
    void createLectureContentTest() {
        // given
        CreateLectureContentReq createLectureContentReq = setUpLectureContentReq();
        LectureContent lectureContent = setUpLectureContent();
        LectureContentRes lectureContentRes = setUpLectureContentRes();

        given(modelMapper.map(createLectureContentReq, LectureContent.class)).willReturn(lectureContent);
        given(contentImageS3Service.uploadS3Images(any(MultipartFile[].class))).willReturn(new ArrayList<String>());
        given(lectureContentRepository.save(any(LectureContent.class))).willReturn(setUpLectureContent());
        given(lectureContentMapper.toLectureContentRes(any(LectureContent.class))).willReturn(lectureContentRes);

        // when
        LectureContentRes savedLectureContent = lectureContentService.createLectureContent(createLectureContentReq);

        // then
        verify(modelMapper, times(1)).map(createLectureContentReq, LectureContent.class);
        verify(contentImageS3Service, times(1)).uploadS3Images(any(MultipartFile[].class));
        verify(lectureContentRepository, times(1)).save(any(LectureContent.class));
        verify(lectureContentMapper, times(1)).toLectureContentRes(any(LectureContent.class));
        assertThat(savedLectureContent).isNotNull();

    }

    @DisplayName("강의 자료 조회 Mapper 테스트")
    @Test
    void toLectureContentResListMapperTest() throws IllegalAccessException {
        // given
        int contentCount = 5;
        List<LectureContent> lectureContentList = setUpLectureContentList(contentCount);
        List<LectureContentRes> lectureContentResList = setUpLectureContentResList(contentCount);

        // when
        List<LectureContentRes> mappedlectureContentResList = lectureContentMapper
                .toLectureContentResList(lectureContentList);

        // then
        for (int i = 0; i < contentCount; i++) {
            LectureContentRes mappedRes = mappedlectureContentResList.get(i);
            LectureContentRes originalRes = lectureContentResList.get(i);

            for (Field field : mappedRes.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object mappedValue = field.get(mappedRes);
                Object originalValue = field.get(originalRes);

                assertThat(mappedValue).isEqualTo(originalValue);
            }
        }

    }

    @Test
    @DisplayName("강의 자료 조회 테스트")
    void findAllLectureContentsTest() {
        // given
        int contentCount = 5;
        List<LectureContent> lectureContentList = setUpLectureContentList(contentCount);
        List<LectureContentRes> lectureContentResList = setUpLectureContentResList(contentCount);

        given(lectureContentRepository.findAll()).willReturn(lectureContentList);
        given(lectureContentMapper.toLectureContentResList(anyList())).willReturn(lectureContentResList);

        // when
        List<LectureContentRes> result = lectureContentService.findAllLectureContents();

        // then
        assertEquals(contentCount, result.size());
        verify(lectureContentRepository, times(1)).findAll();
        verify(lectureContentMapper, times(1)).toLectureContentResList(anyList());
    }

    @DisplayName("강의 자료 업데이트 예외 테스트")
    @Test
    void updateLectureContentExceptionTest() {
        // given
        LectureContent lectureContent = setUpLectureContent();
        UpdateLectureContentReq updateLectureContentReq = setUpUpdateLectureContentReq();

        given(lectureContentRepository.findById(any(Long.class))).willReturn(Optional.empty());

        // when & then
        assertThrows(BaseException.class, () -> {
            lectureContentService.updateLectureContent(lectureContent.getId(), updateLectureContentReq);
        });
    }

    @DisplayName("강의 자료 업데이트 테스트")
    @Test
    void updateLectureContentTest() {
        // given
        LectureContent lectureContent = setUpLectureContent();
        UpdateLectureContentReq updateLectureContentReq = setUpUpdateLectureContentReq();
        LectureContentRes updatedLectureContentRes = setUpUpdatedLectureContentRes();

        given(lectureContentRepository.findById(any(Long.class))).willReturn(Optional.of(lectureContent));
        given(lectureContentRepository.save(any(LectureContent.class))).willReturn(lectureContent);
        given(lectureContentMapper.toLectureContentRes(any(LectureContent.class)))
                .willReturn(updatedLectureContentRes);

        // when
        LectureContentRes updatedLectureContent = lectureContentService
                .updateLectureContent(lectureContent.getId(), updateLectureContentReq);

        // then
        verify(lectureContentRepository, times(1)).findById(any(Long.class));
        verify(lectureContentRepository, times(1)).save(any(LectureContent.class));
        verify(lectureContentMapper, times(1)).toLectureContentRes(any(LectureContent.class));
        assertThat(updatedLectureContent).isNotNull();
        assertThat(updatedLectureContent.getKit()).isEqualTo(updateLectureContentReq.getKit());

    }

}