package com.example.DoroServer.domain.lectureContent.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.example.DoroServer.global.s3.MultipartFileUtils;

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
public class CreateLectureContentReq {

    @NotBlank
    private String kit; // 강의 사용 키트

    @NotBlank
    private String detail; // 강의 세부 구성

    @NotNull
    private String requirement; // 강의 자격 요건

    @NotNull
    private String content; // 강의 컨텐츠

    @NotNull
    private MultipartFile[] files; // 강의 컨텐츠 이미지 파일들

    public void validateFiles() {
        MultipartFileUtils.validateMultipartFiles(this.files);
    }

}
