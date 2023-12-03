package com.example.DoroServer.domain.educationApplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationMapper;
import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationReq;
import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationRes;
import com.example.DoroServer.domain.educationApplication.dto.RetrieveApplicationReq;
import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;
import com.example.DoroServer.domain.educationApplication.repository.EducationApplicationRepository;

import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getEducationApplication;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getEducationApplicationReq;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getEducationApplicationRes;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getRetrieveApplicationReq;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getUpdateEducationApplicationReq;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getUpdateEducationApplicationRes;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.ID;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.UPDATED_NAME;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.UPDATED_OVERALL_REMARK;

@ExtendWith(MockitoExtension.class)
public class EducationApplicationServiceTest {

    @InjectMocks
    private EducationApplicationService educationApplicationService;

    @Mock
    private EducationApplicationMapper mapper;

    @Mock
    private EducationApplicationRepository educationApplicationRepository;

    @DisplayName("교육 신청 생성 테스트")
    @Test
    void createEducationApplicationTest() {
        // given
        EducationApplicationReq applicationReq = getEducationApplicationReq();
        EducationApplication educationApplication = getEducationApplication();
        EducationApplicationRes applicationRes = getEducationApplicationRes();

        given(mapper.toEntity(applicationReq)).willReturn(educationApplication);
        given(educationApplicationRepository.save(educationApplication)).willReturn(educationApplication);
        given(mapper.toDTO(educationApplication)).willReturn(applicationRes);

        // when
        EducationApplicationRes result = educationApplicationService.save(applicationReq);

        // then
        verify(mapper, times(1)).toEntity(applicationReq);
        verify(educationApplicationRepository, times(1)).save(educationApplication);
        verify(mapper, times(1)).toDTO(educationApplication);
        assertThat(result.getId()).isEqualTo(ID);
    }

    @DisplayName("교육 신청 조회 테스트")
    @Test
    void retrieveEducationApplicationTest() {
        // given
        RetrieveApplicationReq retrieveApplicationReq = getRetrieveApplicationReq();
        EducationApplication educationApplication = getEducationApplication();
        EducationApplicationRes applicationRes = getEducationApplicationRes();
        List<EducationApplicationRes> applicationResList = List.of(applicationRes);
        given(educationApplicationRepository.findByPhoneNumber(educationApplication.getPhoneNumber()))
                .willReturn(List.of(educationApplication));
        given(mapper.toDTO(List.of(educationApplication))).willReturn(applicationResList);

        // when
        List<EducationApplicationRes> result = educationApplicationService
                .findByPhoneNumber(retrieveApplicationReq);

        // then
        verify(mapper, times(1)).toDTO(List.of(educationApplication));
        verify(educationApplicationRepository, times(1)).findByPhoneNumber(educationApplication.getPhoneNumber());
        verify(mapper, times(1)).toDTO(List.of(educationApplication));
        assertThat(result.get(0).getId()).isEqualTo(ID);

    }

    @DisplayName("교육 신청 수정 테스트")
    @Test
    void updateEducationApplicationTest() {
        // given
        EducationApplicationReq applicationReq = getUpdateEducationApplicationReq();
        EducationApplication educationApplication = getEducationApplication();
        EducationApplicationRes applicationRes = getUpdateEducationApplicationRes();

        given(educationApplicationRepository.findById(educationApplication.getId()))
                .willReturn(java.util.Optional.of(educationApplication));
        given(mapper.toEntity(applicationReq, educationApplication)).willReturn(educationApplication);
        given(educationApplicationRepository.save(educationApplication)).willReturn(educationApplication);
        given(mapper.toDTO(educationApplication)).willReturn(applicationRes);

        // when
        EducationApplicationRes result = educationApplicationService.update(educationApplication.getId(),
                applicationReq);

        // then
        verify(educationApplicationRepository, times(1)).findById(educationApplication.getId());
        verify(mapper, times(1)).toEntity(applicationReq, educationApplication);
        verify(educationApplicationRepository, times(1)).save(educationApplication);
        verify(mapper, times(1)).toDTO(educationApplication);
        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getName()).isEqualTo(UPDATED_NAME);
        assertThat(result.getOverallRemark()).isEqualTo(UPDATED_OVERALL_REMARK);
    }

}
