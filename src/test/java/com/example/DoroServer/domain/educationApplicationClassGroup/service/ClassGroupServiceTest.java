package com.example.DoroServer.domain.educationApplicationClassGroup.service;

import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getClassGroup;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getClassGroupReq;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getClassGroupRes;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getUpdateClassGroup;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getUpdateClassGroupReq;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getUpdateClassGroupRes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getEducationApplication;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;
import com.example.DoroServer.domain.educationApplication.repository.EducationApplicationRepository;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupMapper;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupReq;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupRes;
import com.example.DoroServer.domain.educationApplicationClassGroup.entity.ClassGroup;
import com.example.DoroServer.domain.educationApplicationClassGroup.repository.ClassGroupRepository;
import com.example.DoroServer.global.exception.BaseException;

@ExtendWith(MockitoExtension.class)
public class ClassGroupServiceTest {

    @InjectMocks
    private ClassGroupService classGroupService;

    @Mock
    private EducationApplicationRepository educationApplicationRepository;

    @Mock
    private ClassGroupRepository classGroupRepository;

    @Mock
    private ClassGroupMapper mapper;

    @Test
    @DisplayName("교육 신청서에 학급 정보 추가 테스트")
    void addClassGroupToApplicationTest() {
        // given
        EducationApplication educationApplication = getEducationApplication();
        ClassGroupReq classGroupReq = getClassGroupReq();
        ClassGroup classGroup = getClassGroup();
        ClassGroup savedClassGroup = getClassGroup();
        ClassGroupRes classGroupRes = getClassGroupRes();
        Long applicationId = educationApplication.getId();
        String phoneNumber = educationApplication.getPhoneNumber();

        given(educationApplicationRepository.findByIdAndPhoneNumber(applicationId, phoneNumber))
                .willReturn(Optional.of(educationApplication));
        given(mapper.toEntity(classGroupReq)).willReturn(classGroup);
        given(classGroupRepository.save(classGroup)).willReturn(savedClassGroup);
        given(mapper.toDTO(savedClassGroup)).willReturn(classGroupRes);

        // when
        ClassGroupRes result = classGroupService.addClassGroupToApplication(applicationId, classGroupReq, phoneNumber);

        // then
        assertThat(result).isEqualTo(classGroupRes);
        verify(educationApplicationRepository, times(1)).findByIdAndPhoneNumber(applicationId, phoneNumber);
        verify(mapper, times(1)).toEntity(classGroupReq);
        verify(classGroupRepository, times(1)).save(classGroup);
        verify(mapper, times(1)).toDTO(savedClassGroup);
    }

    @Test
    @DisplayName("교육 신청서에 학급 정보 추가 테스트")
    void updateClassGroupToApplicationTest() {
        // given
        EducationApplication educationApplication = getEducationApplication();
        ClassGroupReq updateClassGroupReq = getUpdateClassGroupReq();
        ClassGroup classGroup = getClassGroup();
        ClassGroup updateClassGroup = getUpdateClassGroup();
        ClassGroupRes classGroupRes = getUpdateClassGroupRes();
        Long applicationId = educationApplication.getId();
        Long classGroupId = classGroup.getId();
        String phoneNumber = educationApplication.getPhoneNumber();

        educationApplication.getClassGroups().add(classGroup);
        given(educationApplicationRepository.findByIdAndPhoneNumber(applicationId, phoneNumber))
                .willReturn(Optional.of(educationApplication));
        given(mapper.toEntity(updateClassGroupReq, classGroup)).willReturn(updateClassGroup);
        given(classGroupRepository.save(classGroup)).willReturn(classGroup);
        given(mapper.toDTO(classGroup)).willReturn(classGroupRes);

        // when
        ClassGroupRes result = classGroupService.updateClassGroup(applicationId, classGroupId, updateClassGroupReq,
                phoneNumber);

        // then
        assertThat(result).isEqualTo(classGroupRes);
        verify(educationApplicationRepository, times(1)).findByIdAndPhoneNumber(applicationId, phoneNumber);
        verify(mapper, times(1)).toEntity(updateClassGroupReq, classGroup);
        verify(classGroupRepository, times(1)).save(classGroup);
        verify(mapper, times(1)).toDTO(classGroup);
    }

    @Test
    @DisplayName("교육 신청서에 학급 정보 업데이트 실패 테스트 - 교육 신청서가 존재하지 않음")
    void updateClassGroupErrorTest1() {
        // given
        EducationApplication educationApplication = getEducationApplication();
        ClassGroupReq updateClassGroupReq = getUpdateClassGroupReq();
        ClassGroup classGroup = getClassGroup();
        Long applicationId = educationApplication.getId();
        Long classGroupId = classGroup.getId();
        String phoneNumber = educationApplication.getPhoneNumber();

        given(educationApplicationRepository.findByIdAndPhoneNumber(applicationId, phoneNumber))
                .willReturn(Optional.of(educationApplication));

        // when & then
        assertThrows(BaseException.class, () -> classGroupService.updateClassGroup(applicationId, classGroupId,
                updateClassGroupReq, phoneNumber));
        verify(educationApplicationRepository, times(1)).findByIdAndPhoneNumber(applicationId, phoneNumber);

    }

    @Test
    @DisplayName("교육 신청서에 학급 정보 업데이트 실패 테스트 - 핸드폰 번호가 일치하지 않음")
    void updateClassGroupErrorTest2() {
        // given
        EducationApplication educationApplication = getEducationApplication();
        ClassGroupReq updateClassGroupReq = getUpdateClassGroupReq();
        ClassGroup classGroup = getClassGroup();
        Long applicationId = educationApplication.getId();
        Long classGroupId = classGroup.getId();
        String phoneNumber = "01011112222"; // 01012345678

        // when & then
        assertThrows(BaseException.class, () -> classGroupService.updateClassGroup(applicationId, classGroupId,
                updateClassGroupReq, phoneNumber));

    }

}
