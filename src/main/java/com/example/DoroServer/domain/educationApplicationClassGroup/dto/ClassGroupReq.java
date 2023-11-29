package com.example.DoroServer.domain.educationApplicationClassGroup.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.google.firebase.database.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassGroupReq {

    @NotBlank
    private String className; // 학급 이름

    @NotBlank
    private String educationConcept; // 교육 컨셉

    @Min(0)
    private int numberOfStudents; // 학생 수

    @NotEmpty
    private List<LocalDate> educationDates; // 교육 날짜

    private String remark; // 희망 교육 시간

    @NotNull
    private boolean unfixed; // 교육 시간 미정 여부
}
