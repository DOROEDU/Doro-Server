package com.example.DoroServer.domain.lectureContent.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LectureContentDto {

    private Long id;

    @NotBlank
    private String kit; // 강의 사용 키트

    @NotBlank
    private String detail; // 강의 세부 구성

    @NotNull
    private String requirement; // 강의 자격 요건

    @NotNull
    private String content; // 강의 내용

    private List<LectureContentImageDto> images; // 강의 자료 이미지
}
