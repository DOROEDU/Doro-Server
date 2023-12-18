package com.example.DoroServer.domain.lectureContentImage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
public class LectureContentImageRes {
    private Long id; // PK
    private String url; // uploaded image url
}
