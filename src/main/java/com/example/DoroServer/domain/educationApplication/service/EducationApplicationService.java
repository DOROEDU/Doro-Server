package com.example.DoroServer.domain.educationApplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationMapper;
import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationReq;
import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationRes;
import com.example.DoroServer.domain.educationApplication.dto.RetrieveApplicationReq;
import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;
import com.example.DoroServer.domain.educationApplication.repository.EducationApplicationRepository;
import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationApplicationService {

    private final EducationApplicationRepository repository;
    private final EducationApplicationMapper mapper;

    /* Education Application */

    // create
    public EducationApplicationRes save(EducationApplicationReq applicationReq) {
        EducationApplication educationApplication = mapper.toEntity(applicationReq);
        EducationApplication savedEducationApplication = repository.save(educationApplication);

        return mapper.toDTO(savedEducationApplication);
    }

    // read
    public List<EducationApplicationRes> findByPhoneNumber(RetrieveApplicationReq retrieveApplicationReq) {
        List<EducationApplication> educationApplications = repository
                .findByPhoneNumber(retrieveApplicationReq.getPhoneNumber());

        return mapper.toDTO(educationApplications);
    }

    // update
    public EducationApplicationRes update(Long id, EducationApplicationReq applicationReq) {
        EducationApplication educationApplication = repository.findById(id)
                .orElseThrow(() -> new BaseException(Code.EDUCATION_APPLICATION_NOT_FOUND));

        mapper.toEntity(applicationReq, educationApplication);
        EducationApplication updatedEducationApplication = repository.save(educationApplication);

        return mapper.toDTO(updatedEducationApplication);
    }

    // delete
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
