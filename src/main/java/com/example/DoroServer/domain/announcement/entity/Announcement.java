package com.example.DoroServer.domain.announcement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue
    @Column(name = "announcement_id")
    private Long id; //PK

    private String title; // 공지 제목

    private String content; // 공지 내용

    @Column(columnDefinition = "BLOB")
    private byte[] picture; // 공지 첨부 사진 - 업로드 사이즈 제한 추가 필요

}