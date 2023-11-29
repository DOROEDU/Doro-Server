package com.example.DoroServer.domain.educationApplicationClassGroup.service;

import org.springframework.stereotype.Service;

import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;
import com.example.DoroServer.domain.educationApplication.repository.EducationApplicationRepository;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupMapper;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupReq;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupRes;
import com.example.DoroServer.domain.educationApplicationClassGroup.entity.ClassGroup;
import com.example.DoroServer.domain.educationApplicationClassGroup.repository.ClassGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassGroupService {

    private final EducationApplicationRepository educationApplicationRepository;
    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupMapper mapper;

    /* Class Group */

    // create
    public ClassGroupRes addClassGroupToApplication(Long applicationId, ClassGroupReq classGroupReq) {
        EducationApplication educationApplication = educationApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교육 신청서가 없습니다. applicationId=" + applicationId));

        ClassGroup classGroup = mapper.toEntity(classGroupReq);
        classGroup.setEducationApplication(educationApplication);
        ClassGroup savedClassGroup = classGroupRepository.save(classGroup);

        return mapper.toDTO(savedClassGroup);
    }

    // update
    public ClassGroupRes updateClassGroup(Long id, Long classGroupId, ClassGroupReq classGroupReq) {
        ClassGroup target = classGroupRepository.findById(classGroupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교육 학급 정보가 없습니다. id=" + classGroupId));

        mapper.toEntity(classGroupReq, target);
        ClassGroup updatedClassGroup = classGroupRepository.save(target);

        return mapper.toDTO(updatedClassGroup);
    }

    // delete
    public void deleteClassGroup(Long id, Long classGroupId) {
        ClassGroup classGroup = classGroupRepository.findById(classGroupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교육 학급 정보가 없습니다. id=" + classGroupId));
        classGroup.getEducationApplication().getId().equals(id);

        classGroupRepository.deleteById(classGroupId);
    }

}
