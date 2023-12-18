package com.example.DoroServer.domain.educationApplicationClassGroup.service;

import org.springframework.stereotype.Service;

import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;
import com.example.DoroServer.domain.educationApplication.repository.EducationApplicationRepository;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupMapper;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupReq;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupRes;
import com.example.DoroServer.domain.educationApplicationClassGroup.entity.ClassGroup;
import com.example.DoroServer.domain.educationApplicationClassGroup.repository.ClassGroupRepository;
import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassGroupService {

    private final EducationApplicationRepository educationApplicationRepository;
    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupMapper mapper;

    /* Class Group */

    // create
    public ClassGroupRes addClassGroupToApplication(Long applicationId, ClassGroupReq classGroupReq,
            String phoneNumber) {
        EducationApplication educationApplication = educationApplicationRepository
                .findByIdAndPhoneNumber(applicationId, phoneNumber)
                .orElseThrow(() -> new BaseException(Code.EDUCATION_APPLICATION_NOT_FOUND));

        ClassGroup classGroup = mapper.toEntity(classGroupReq);
        classGroup.setEducationApplication(educationApplication);
        ClassGroup savedClassGroup = classGroupRepository.save(classGroup);

        return mapper.toDTO(savedClassGroup);
    }

    // update
    public ClassGroupRes updateClassGroup(Long applicationId, Long classGroupId, ClassGroupReq classGroupReq,
            String phoneNumber) {
        EducationApplication educationApplication = educationApplicationRepository
                .findByIdAndPhoneNumber(applicationId, phoneNumber)
                .orElseThrow(() -> new BaseException(Code.EDUCATION_APPLICATION_NOT_FOUND));

        ClassGroup classGroup = educationApplication.getClassGroups().stream()
                .filter(group -> group.getId().equals(classGroupId))
                .findFirst()
                .orElseThrow(() -> new BaseException(Code.EDUCATION_CLASS_GROUP_NOT_FOUND));

        mapper.toEntity(classGroupReq, classGroup);
        ClassGroup updatedClassGroup = classGroupRepository.save(classGroup);

        return mapper.toDTO(updatedClassGroup);
    }

    // delete
    public void deleteClassGroup(Long applicationId, Long classGroupId, String phoneNumber) {
        EducationApplication educationApplication = educationApplicationRepository
                .findByIdAndPhoneNumber(applicationId, phoneNumber)
                .orElseThrow(() -> new BaseException(Code.EDUCATION_APPLICATION_NOT_FOUND));

        educationApplication.getClassGroups().stream()
                .filter(group -> group.getId().equals(classGroupId))
                .findFirst()
                .orElseThrow(() -> new BaseException(Code.EDUCATION_CLASS_GROUP_NOT_FOUND));

        classGroupRepository.deleteById(classGroupId);
    }

}
