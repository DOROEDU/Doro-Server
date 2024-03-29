package com.example.DoroServer.domain.notification.dto;

import com.example.DoroServer.domain.notification.entity.Notification;
import com.example.DoroServer.domain.notification.entity.NotificationType;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;

import com.google.firebase.messaging.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationContentReq {   // 알림이 생성될 때, 토큰 없이 title과 body만 전달받는 객체

    @NotBlank(message = "알림 제목을 입력하세요.")
    private String title;

    @NotBlank(message = "알림 내용을 입력하세요.")
    private String body;

    @NotNull
    private NotificationType notificationType;


    public Notification toEntity(NotificationType notificationType, Long targetId) {
        return Notification.builder()
                .title(title)
                .body(body)
                .notificationType(notificationType)
                .targetId(targetId)
                .build();
    }

    public AndroidConfig toDefaultAndroidConfig() {
        return AndroidConfig.builder()
                .setNotification(
                        AndroidNotification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .setClickAction("NOTIFICATION_CLICK")
                                .build()
                )
                .build();
    }

    public ApnsConfig toDefaultApnsConfig() {
        return ApnsConfig.builder()
                .setAps(
                        Aps.builder()
                                .setAlert(
                                        ApsAlert.builder()
                                                .setTitle(title)
                                                .setBody(body)
                                                .build()
                                )
                                .setCategory("NOTIFICATION_CLICK")
                                .setSound("default")
                                .build()
                )
                .build();
    }
}
