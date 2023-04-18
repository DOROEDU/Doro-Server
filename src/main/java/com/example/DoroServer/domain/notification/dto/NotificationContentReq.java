package com.example.DoroServer.domain.notification.dto;

import com.example.DoroServer.domain.notification.entity.Notification;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationContentReq {   // 알림이 생성될 때, 토큰 없이 title과 body만 전달받는 객체

    @NotBlank(message = "알림 제목을 입력하세요.")
    private String title;

    @NotBlank(message = "알림 내용을 입력하세요.")
    private String body;
    public Notification toEntity() {
        return Notification.builder()
                .title(title)
                .body(body)
                .isRead(false)
                .build();
    }
}