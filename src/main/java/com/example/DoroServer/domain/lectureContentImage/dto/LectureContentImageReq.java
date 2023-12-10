package com.example.DoroServer.domain.lectureContentImage.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class LectureContentImageReq {
    @NotNull
    private MultipartFile[] files; // 강의 컨텐츠 이미지 파일들 -> 하나만 보내면 무시됨

}
