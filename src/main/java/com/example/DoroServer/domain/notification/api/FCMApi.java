package com.example.DoroServer.domain.notification.api;

import com.example.DoroServer.domain.notification.dto.FCMMessageReq;
import com.example.DoroServer.domain.notification.service.FCMService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FCMApi {

    private final FCMService fcmService;

    // FCM 서버에 알림 전송요청
    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody FCMMessageReq dto) {
        try {
            // FCM 서버에 메시지 전송
            fcmService.sendMessageTo(
                    dto.getTargetToken(), // 메시지를 받을 대상 기기의 FCM 토큰
                    dto.getTitle(),       // 메시지의 제목
                    dto.getBody());       // 메시지의 내용

            // 성공적으로 메시지를 전송한 경우 200 OK 응답 반환
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            log.info("error = ",e);

            //요청 실패 시 400 Bad Request 응답 반환
            return ResponseEntity.badRequest().build();
        }
    }
}
