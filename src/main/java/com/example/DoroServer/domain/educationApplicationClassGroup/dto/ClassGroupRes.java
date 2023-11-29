package com.example.DoroServer.domain.educationApplicationClassGroup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClassGroupRes {

    private Long id; // PK
    private String className; // 학급 이름
    private String educationConcept; // 교육 컨셉
    private int numberOfStudents; // 학생 수
    private List<LocalDate> educationDates; // 교육 날짜
    private String remark; // 희망 교육 시간
    private boolean unfixed; // 교육 시간 미정 여부
}
