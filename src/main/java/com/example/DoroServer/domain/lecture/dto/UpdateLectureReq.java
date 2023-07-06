package com.example.DoroServer.domain.lecture.dto;

import com.example.DoroServer.domain.lecture.entity.LectureDate;
import com.example.DoroServer.domain.lecture.entity.LectureStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLectureReq {
    private String mainTitle; // 강의 제목
    private String subTitle; // 강의 제목
    private String institution; // 강의 기관
    private String city; // 강의 도시
    private String place; // 강의 도시
    private String studentGrade; // 강의 대상
    private String studentNumber; // 인원수
    private String  mainTutor; // 강의 메인 강사 수
    private String  subTutor; // 강의 서브 강사 수
    private String  staff; // 강의 스태프 수
    private String mainPayment; //강사 급여
    private String subPayment;
    private String staffPayment;
    private String transportCost;
    private String time; // 시간
    private List<LocalDate> lectureDates = new ArrayList<>(); // 강의 날짜
    private LectureDate lectureDate;
    private LectureStatus status;

}
