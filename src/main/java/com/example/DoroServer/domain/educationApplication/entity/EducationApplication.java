package com.example.DoroServer.domain.educationApplication.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.DoroServer.domain.educationApplicationClassGroup.entity.ClassGroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EducationApplication { // extends BaseEntity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id; // PK

    private String name; // 신청자 성함
    private String institutionName; // 신청자 소속 기관명
    private String position; // 신청자 직위
    private String phoneNumber; // 신청자 전화번호
    private String email; // 신청자 이메일

    private int numberOfStudents; // 교육 학생 수
    private String studentRank; // 학생 정보
    private int budget; // 교육 예산

    private String overallRemark; // 교육 특이사항

    // == 연관관계 매핑 ==//

    // EducationApplication과 ClassGroup은 일대다(One-to-Many) 관계
    @Builder.Default
    @OneToMany(mappedBy = "educationApplication", cascade = CascadeType.REMOVE)
    private List<ClassGroup> classGroups = new ArrayList<>(); // 교육 학급 정보

}