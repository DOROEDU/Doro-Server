package com.example.DoroServer.domain.educationApplication.dto;

import java.util.List;

import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupRes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EducationApplicationRes {

    private Long id;
    private String name;
    private String institutionName;
    private String position;
    private String phoneNumber;
    private String email;
    private int numberOfStudents;
    private String studentRank;
    private int budget;
    private String overallRemark;
    private List<ClassGroupRes> ClassGroups;
}