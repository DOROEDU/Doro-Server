package com.example.DoroServer.domain.lecture.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.DoroServer.domain.lecture.entity.LectureStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Builder
public class FindAllLecturesCond {
    private List<String> city;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private LectureStatus lectureStatus;

}
