package com.example.DoroServer.domain.lectureContentImage.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class LectureContentImageDto {
    private Long id;

    @NotNull
    private String url;
}
