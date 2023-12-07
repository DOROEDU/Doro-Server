package com.example.DoroServer.domain.educationApplication;

import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationReq;
import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationRes;
import com.example.DoroServer.domain.educationApplication.dto.RetrieveApplicationReq;
import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupRes;

import java.util.List;

public class EducationApplicationTestSetup {
    public static Long ID = 1L;
    public static String NAME = "홍길동";
    public static String UPDATED_NAME = "고길동";
    public static String PHONE_NUMBER = "01012345678";
    public static String INSTITUTION_NAME = "냉장고등학교";
    public static String POSITION = "교장선생님";
    public static String UPDATED_POSITION = "교감선생님";
    public static String EMAIL = "gildong@test.com";
    public static String STUDENT_RANK = "고등학교 1학년";
    public static int NUMBER_OF_STUDENTS = 30;
    public static int BUDGET = 1000000;
    public static List<ClassGroupRes> CLASS_GROUPS = List.of();
    public static String OVERALL_REMARK = "특이사항 없음";
    public static String UPDATED_OVERALL_REMARK = "불량배 둘리 조심";

    public static EducationApplicationReq getEducationApplicationReq() {
        return EducationApplicationReq.builder()
                .name(NAME)
                .phoneNumber(PHONE_NUMBER)
                .institutionName(INSTITUTION_NAME)
                .position(POSITION)
                .email(EMAIL)
                .studentRank(STUDENT_RANK)
                .numberOfStudents(NUMBER_OF_STUDENTS)
                .budget(BUDGET)
                .overallRemark(OVERALL_REMARK)
                .build();
    }

    public static EducationApplicationReq getUpdateEducationApplicationReq() {
        return EducationApplicationReq.builder()
                .name(UPDATED_NAME)
                .phoneNumber(PHONE_NUMBER)
                .institutionName(INSTITUTION_NAME)
                .position(UPDATED_POSITION)
                .email(EMAIL)
                .studentRank(STUDENT_RANK)
                .numberOfStudents(NUMBER_OF_STUDENTS)
                .budget(BUDGET)
                .overallRemark(UPDATED_OVERALL_REMARK)
                .build();
    }

    public static EducationApplicationRes getEducationApplicationRes() {
        return EducationApplicationRes.builder()
                .id(ID)
                .name(NAME)
                .phoneNumber(PHONE_NUMBER)
                .institutionName(INSTITUTION_NAME)
                .position(POSITION)
                .email(EMAIL)
                .studentRank(STUDENT_RANK)
                .numberOfStudents(NUMBER_OF_STUDENTS)
                .budget(BUDGET)
                .overallRemark(OVERALL_REMARK)
                .classGroups(CLASS_GROUPS)
                .build();
    }

    public static EducationApplicationRes getUpdateEducationApplicationRes() {
        return EducationApplicationRes.builder()
                .id(ID)
                .name(UPDATED_NAME)
                .phoneNumber(PHONE_NUMBER)
                .institutionName(INSTITUTION_NAME)
                .position(UPDATED_POSITION)
                .email(EMAIL)
                .studentRank(STUDENT_RANK)
                .numberOfStudents(NUMBER_OF_STUDENTS)
                .budget(BUDGET)
                .overallRemark(UPDATED_OVERALL_REMARK)
                .classGroups(CLASS_GROUPS)
                .build();
    }

    public static EducationApplication getEducationApplication() {
        return EducationApplication.builder()
                .id(ID)
                .name(NAME)
                .phoneNumber(PHONE_NUMBER)
                .institutionName(INSTITUTION_NAME)
                .position(POSITION)
                .email(EMAIL)
                .studentRank(STUDENT_RANK)
                .numberOfStudents(NUMBER_OF_STUDENTS)
                .budget(BUDGET)
                .overallRemark(OVERALL_REMARK)
                .build();
    }

    public static EducationApplication getUpdateEducationApplication() {
        return EducationApplication.builder()
                .id(ID)
                .name(UPDATED_NAME)
                .phoneNumber(PHONE_NUMBER)
                .institutionName(INSTITUTION_NAME)
                .position(UPDATED_POSITION)
                .email(EMAIL)
                .studentRank(STUDENT_RANK)
                .numberOfStudents(NUMBER_OF_STUDENTS)
                .budget(BUDGET)
                .overallRemark(UPDATED_OVERALL_REMARK)
                .build();
    }

    public static RetrieveApplicationReq getRetrieveApplicationReq() {
        return RetrieveApplicationReq.builder()
                .phoneNumber(PHONE_NUMBER)
                .build();
    }

    public static List<EducationApplication> getEducationApplications() {
        return List.of(getEducationApplication());
    }

    public static List<EducationApplicationRes> getEducationApplicationResList() {
        return List.of(getEducationApplicationRes());
    }

}
