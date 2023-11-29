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
    public ClassGroupRes addClassGroupToApplication(Long id, ClassGroupReq classInfoReq) {
        EducationApplication educationApplication = educationApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 교육 신청서가 없습니다. id=" + id));

        ClassGroup classGroup = mapper.toEntity(classInfoReq);
        classGroup.setEducationApplication(educationApplication);
        ClassGroup savedClassGroup = classGroupRepository.save(classGroup);

        return mapper.toDTO(savedClassGroup);
    }

    // update
    public ClassGroupRes updateClassGroup(Long id, Long classInfoId, ClassGroupReq classInfoReq) {
        ClassGroup target = classGroupRepository.findById(classInfoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교육 학급 정보가 없습니다. id=" + classInfoId));

        mapper.toEntity(classInfoReq, target);
        ClassGroup updatedClassInfo = classGroupRepository.save(target);

        return mapper.toDTO(updatedClassInfo);
    }

    // delete
    public void deleteClassGroup(Long id, Long classInfoId) {
        ClassGroup classGroup = classGroupRepository.findById(classInfoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교육 학급 정보가 없습니다. id=" + classInfoId));
        classGroup.getEducationApplication().getId().equals(id);

        classGroupRepository.deleteById(classInfoId);
    }

}
