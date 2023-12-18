package com.example.DoroServer.domain.lectureContent.dto;

import java.util.List;

import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageRes;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LectureContentRes {

    private Long id; // PK
    private String kit; // 강의 사용 키트
    private String detail; // 강의 세부 구성
    private String requirement; // 강의 자격 요건
    private String content; // 강의 컨텐츠
    private List<LectureContentImageRes> images; // 강의 컨텐츠 이미지 파일들
}
