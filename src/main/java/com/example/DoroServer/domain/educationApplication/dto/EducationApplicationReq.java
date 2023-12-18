package com.example.DoroServer.domain.educationApplication.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationApplicationReq {

    @NotBlank
    private String name; // 신청자 성함

    @NotBlank
    private String institutionName; // 신청자 소속 기관명

    @NotBlank
    private String position; // 신청자 직위

    @NotBlank
    @Pattern(regexp = "^01([016789])([0-9]{3,4})([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber; // 신청자 전화번호

    @Email
    private String email; // 신청자 이메일

    @Min(0)
    private int numberOfStudents; // 교육 학생 수

    @NotBlank
    private String studentRank; // 학생 정보

    @Min(0)
    private int budget; // 교육 예산

    private String overallRemark; // 교육 특이사항
}