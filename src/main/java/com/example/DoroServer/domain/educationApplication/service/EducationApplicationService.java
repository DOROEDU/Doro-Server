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
    public EducationApplicationRes save(EducationApplicationReq applicationReq, String phoneNumber) {
        throwExceptionIfNotEqual(applicationReq.getPhoneNumber(), phoneNumber);

        EducationApplication educationApplication = mapper.toEntity(applicationReq);
        EducationApplication savedEducationApplication = repository.save(educationApplication);

        return mapper.toDTO(savedEducationApplication);
    }

    // read
    public List<EducationApplicationRes> findByPhoneNumber(RetrieveApplicationReq retrieveApplicationReq,
            String phoneNumber) {
        throwExceptionIfNotEqual(retrieveApplicationReq.getPhoneNumber(), phoneNumber);

        List<EducationApplication> educationApplications = repository.findByPhoneNumber(phoneNumber);

        return mapper.toDTO(educationApplications);
    }

    // update
    public EducationApplicationRes update(Long id, EducationApplicationReq applicationReq, String phoneNumber) {
        throwExceptionIfNotEqual(applicationReq.getPhoneNumber(), phoneNumber);

        EducationApplication educationApplication = repository.findByIdAndPhoneNumber(id, phoneNumber).orElseThrow(
                () -> new BaseException(Code.EDUCATION_APPLICATION_NOT_FOUND));

        mapper.toEntity(applicationReq, educationApplication);
        EducationApplication updatedEducationApplication = repository.save(educationApplication);

        return mapper.toDTO(updatedEducationApplication);
    }

    // delete
    public void delete(Long id, String phoneNumber) {
        EducationApplication educationApplication = repository.findByIdAndPhoneNumber(id, phoneNumber).orElseThrow(
                () -> new BaseException(Code.EDUCATION_APPLICATION_NOT_FOUND));

        repository.delete(educationApplication);
    }

    public static void throwExceptionIfNotEqual(String phoneNumber1, String phoneNumber2) {
        // 두 번호가 같지 않으면 예외 발생
        if (!phoneNumber2.equals(phoneNumber1)) {
            throw new BaseException(Code.EDUCATION_APPLICATION_DIFFERENT_PHONE);
        }
    }

}
