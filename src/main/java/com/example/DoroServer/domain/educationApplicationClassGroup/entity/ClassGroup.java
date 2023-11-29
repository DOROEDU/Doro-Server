package com.example.DoroServer.domain.educationApplicationClassGroup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;

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
public class ClassGroup { // extends BaseEntity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_info_id")
    private Long id; // PK

    private String className; // 학급 이름
    private String educationConcept; // 교육 컨셉
    private int numberOfStudents; // 학생 수
    @ElementCollection
    private List<LocalDate> educationDates; // 교육 날짜
    private String remark; // 희망 교육 시간
    private boolean unfixed; // 교육 시간 미정 여부

    // == 연관관계 매핑 ==//

    // ClassGroup은 EducationApplication은 다대일(Many-to-One) 관계
    @ManyToOne
    @JoinColumn(name = "application_id")
    private EducationApplication educationApplication; // 교육 신청서

    public void setEducationApplication(EducationApplication educationApplication) {
        this.educationApplication = educationApplication;
    }
}
