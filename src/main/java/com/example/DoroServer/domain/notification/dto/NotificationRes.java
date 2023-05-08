package com.example.DoroServer.domain.notification.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NotificationRes {

    private Long id;

    private String title; // 알림 제목

    private String body; // 알림 내용

    private Boolean isRead; // 알림 읽음 유무

    private LocalDateTime createdAt; // 알림 생성 시간

    private LocalDateTime lastModifiedAt; // 알림 수정 시간

}