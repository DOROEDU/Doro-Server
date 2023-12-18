package com.example.DoroServer.domain.lectureContent.entity;

import javax.persistence.GenerationType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LectureContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_content_id")
    private Long id; // PK

    private String kit; // 강의 사용 키트

    private String detail; // 강의 세부 구성

    private String requirement; // 강의 자격 요건

    private String content; // 강의 컨텐츠

    // == 연관관계 매핑 ==//

    // LectureContent과 LectureContentImage는 일대다(One-to-Many) 관계
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<LectureContentImage> images = new ArrayList<>();

}
