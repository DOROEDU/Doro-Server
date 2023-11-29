package com.example.DoroServer.domain.educationApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;

public interface EducationApplicationRepository extends JpaRepository<EducationApplication, Long> {

    List<EducationApplication> findByPhoneNumber(String phoneNumber);

}
