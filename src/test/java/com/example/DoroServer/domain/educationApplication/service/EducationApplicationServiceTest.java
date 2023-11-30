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

@ExtendWith(MockitoExtension.class)
public class EducationApplicationServiceTest {

    @InjectMocks
    private EducationApplicationService educationApplicationService;

    @Mock
    private EducationApplicationMapper mapper;

    @Mock
    private EducationApplicationRepository educationApplicationRepository;

    private EducationApplicationReq setUpApplicationReq() {
        return EducationApplicationReq.builder()
                .name("홍길동")
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교장선생님")
                .email("gildong@test.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(30)
                .budget(1000000)
                .overallRemark("특이사항 없음")
                .build();
    }

    private EducationApplication setUpEducationApplication() {
        return EducationApplication.builder()
                .id(1L)
                .name("홍길동")
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교장선생님")
                .email("gildong@test.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(30)
                .budget(1000000)
                .overallRemark("특이사항 없음")
                .build();
    }

    private EducationApplicationReq setUpUpdateApplicationReq() {
        return EducationApplicationReq.builder()
                .name("고길동") // update
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교장선생님")
                .email("gildong@test.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(30)
                .budget(1000000)
                .overallRemark("둘리가 있음") // update
                .build();
    }

    private EducationApplicationRes setUpEducationApplicationRes() {
        return EducationApplicationRes.builder()
                .id(1L)
                .name("홍길동")
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교장선생님")
                .email("gildong@test.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(30)
                .budget(1000000)
                .overallRemark("특이사항 없음")
                .build();
    }

    private EducationApplicationRes setUpUpdateEducationApplicationRes() {
        return EducationApplicationRes.builder()
                .id(1L)
                .name("고길동") // update
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교장선생님")
                .email("gildong@test.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(30)
                .budget(1000000)
                .overallRemark("둘리가 있음") // update
                .build();
    }

    private RetrieveApplicationReq setURetrieveApplicationReq() {
        return RetrieveApplicationReq.builder()
                .phoneNumber("010-1234-5678")
                .build();
    }

    @DisplayName("교육 신청 생성 테스트")
    @Test
    void createEducationApplicationTest() {
        // given
        EducationApplicationReq applicationReq = setUpApplicationReq();
        EducationApplication educationApplication = setUpEducationApplication();
        EducationApplicationRes applicationRes = setUpEducationApplicationRes();

        given(mapper.toEntity(applicationReq)).willReturn(educationApplication);
        given(educationApplicationRepository.save(educationApplication)).willReturn(educationApplication);
        given(mapper.toDTO(educationApplication)).willReturn(applicationRes);

        // when
        EducationApplicationRes result = educationApplicationService.save(applicationReq);

        // then
        verify(mapper, times(1)).toEntity(applicationReq);
        verify(educationApplicationRepository, times(1)).save(educationApplication);
        verify(mapper, times(1)).toDTO(educationApplication);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @DisplayName("교육 신청 조회 테스트")
    @Test
    void retrieveEducationApplicationTest() {
        // given
        RetrieveApplicationReq retrieveApplicationReq = setURetrieveApplicationReq();
        EducationApplication educationApplication = setUpEducationApplication();
        EducationApplicationRes applicationRes = setUpEducationApplicationRes();
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
        assertThat(result.get(0).getId()).isEqualTo(1L);

    }

    @DisplayName("교육 신청 수정 테스트")
    @Test
    void updateEducationApplicationTest() {
        // given
        EducationApplicationReq applicationReq = setUpUpdateApplicationReq();
        EducationApplication educationApplication = setUpEducationApplication();
        EducationApplicationRes applicationRes = setUpUpdateEducationApplicationRes();

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
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("고길동");
        assertThat(result.getOverallRemark()).isEqualTo("둘리가 있음");
    }

}
