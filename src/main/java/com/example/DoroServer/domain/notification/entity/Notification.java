package com.example.DoroServer.domain.notification.entity;

import com.example.DoroServer.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id; // PK

    @NotBlank(message = "알림 제목을 입력하세요.")
    private String title; // 알림 제목

    @NotBlank(message = "알림 내용을 입력하세요")
    private String body; // 알림 내용

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


    private Long targetId;


}
