package com.example.DoroServer.domain.lecture.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue
    @Column(name = "lecture_id")
    private Long id; // PK

    @NotNull
    private String institution; // 강의 기관

    @NotNull
    private String city; // 강의 도시

    private String studentGrade; // 청강 학생 학년

    private String studentNumber; // 청강 학생 수

    @NotNull
    private Long mainTutor; // 강의 메인 강사

    @NotNull
    private Long subTutor; // 강의 서브 강사

    private Long staff; // 강의 스태프

    @NotNull
    @Enumerated(EnumType.STRING)
    private LectureStatus status; // 강의 상태 [RECRUITING, ALLOCATION_COMP,FINISH]

    private LocalDateTime lectureDate; // 강의 날짜

    private LocalDateTime enrollStateDate; // 강의 등록 시작 날짜

    private LocalDateTime enrollEndDate; // 강의 등록 종료 날짜

//    private Long lectureContentId; // 강의 내용

//    private Long chatId; // 강의 채팅

}