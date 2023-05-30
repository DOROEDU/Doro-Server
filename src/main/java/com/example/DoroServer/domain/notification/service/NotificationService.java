package com.example.DoroServer.domain.notification.service;

import com.example.DoroServer.domain.notification.dto.NotificationContentReq;
import com.example.DoroServer.domain.notification.dto.NotificationRes;
import com.example.DoroServer.domain.notification.dto.NotificationReq;
import com.example.DoroServer.domain.notification.dto.NotificationDto;
import com.example.DoroServer.domain.notification.entity.Notification;
import com.example.DoroServer.domain.notification.repository.NotificationRepository;
import com.example.DoroServer.domain.token.repository.TokenRepository;
import com.example.DoroServer.domain.user.entity.User;
import com.example.DoroServer.domain.user.repository.UserRepository;
import com.example.DoroServer.domain.userNotification.entity.UserNotification;
import com.example.DoroServer.domain.userNotification.service.UserNotificationService;
import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final TokenRepository tokenRepository;

    private final UserRepository userRepository;

    private final UserNotificationService userNotificationService;

    private final ObjectMapper objectMapper;

    private final String API_URL;       // FCM 전송 Api URL

    private final String PROJECT_ID;    // 생성한 FCM Project Id

    public NotificationService(
            NotificationRepository notificationRepository,
            TokenRepository tokenRepository,
            ObjectMapper objectMapper,
            UserRepository userRepository,
            UserNotificationService userNotificationService,
            @Value("${api.url}") String apiUrl,
            @Value("${project.id}") String projectId) {
        this.notificationRepository = notificationRepository;
        this.tokenRepository = tokenRepository;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.userNotificationService = userNotificationService;
        this.API_URL = apiUrl;
        this.PROJECT_ID = projectId;
    }

    // 유저 개인의 알림 조회하는 메소드
    public List<NotificationRes> findUserNotifications(Long userId, Pageable pageable) {
        List<UserNotification> userNotifications = userNotificationService
                .findUserNotificationsByUserId(userId, pageable);

        return userNotifications.stream()
                .map(un -> un.getNotification().toRes())
                .collect(Collectors.toList());
    }

    // id에 해당하는 알림을 조회하는 메소드
    public NotificationRes findNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElseThrow(() -> {
            log.info("Notification을 찾을 수 없습니다. id = {}", id);
            throw new BaseException(Code.NOTIFICATION_NOT_FOUND);
        }).toRes();
    }

    // 전달받은 title과 body로 알림을 저장하는 메소드
    @Transactional
    public Long saveNotification(NotificationContentReq notificationContentReq) {
        Notification notification = notificationContentReq.toEntity();
        notificationRepository.save(notification);
        return notification.getId();
    }

    // 모든 유저에게 푸쉬알림 발송 후 저장
    @Transactional
    public void sendNotificationToAll(NotificationContentReq notificationContentReq) {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            users.stream().forEach(user -> {
                // 유저별로 알림 수신 동의 여부 체크
                if (user.getNotificationAgreement()) {
                    // 동의했을 경우 보유한 모든 토큰에 알림 발송
                    user.getTokens().stream().forEach(token -> {
                        NotificationReq notificationReq = NotificationReq.builder()
                                .targetToken(token.getToken())
                                .title(notificationContentReq.getTitle())
                                .body(notificationContentReq.getBody())
                                .build();
                        sendMessageTo(notificationReq);
                    });

                    // 알림 저장
                    Long notificationId = saveNotification(notificationContentReq);
                    userNotificationService.saveUserNotification(user.getId(), notificationId);
                }
            });
        }
    }

    // 선택한 유저에게 알림 전송
    @Transactional
    public void sendNotificationsToSelectedUsers(NotificationContentReq notificationContentReq) {
        notificationContentReq.getUserIds().forEach(id ->
        {
            User user = userRepository.findById(id).orElseThrow(() -> {
                        log.info("유저를 찾을 수 없습니다. id = {}", id);
                        throw new BaseException(Code.USER_NOT_FOUND);
                    }
            );
            if (user.getNotificationAgreement()) {
                user.getTokens().stream()
                        .forEach(token -> {
                            NotificationReq notificationReq = NotificationReq.builder()
                                    .targetToken(token.getToken())
                                    .title(notificationContentReq.getTitle())
                                    .body(notificationContentReq.getBody())
                                    .build();
                            sendMessageTo(notificationReq);
                        });
                // 알림 저장
                Long notificationId = saveNotification(notificationContentReq);
                userNotificationService.saveUserNotification(id, notificationId);
            }
        });
    }

    public void sendFixedMessageToUser(User user, String title, String body) {
        if (user.getNotificationAgreement()) {
            user.getTokens().stream()
                    .forEach(token -> {
                        NotificationReq notificationReq = NotificationReq.builder()
                                .targetToken(token.getToken())
                                .title(title)
                                .body(body)
                                .build();
                        sendMessageTo(notificationReq);
                    });
        }
    }


    // FCM 메시지를 보내는 메소드
    public void sendMessageTo(NotificationReq notificationReq) {
        // FCM 메시지 생성
        String message = makeMessage(notificationReq);
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

        try {
            // HTTP 요청 생성
            Request request = new Request.Builder()
                    .url(API_URL.replace("{project-id}", PROJECT_ID))
                    .post(requestBody)
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();

            // HTTP 요청 실행
            httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new BaseException(Code.NOTIFICATION_PUSH_FAIL);
        }
    }


    // FCM 메시지를 생성하는 메소드
    private String makeMessage(NotificationReq notificationReq) {
        NotificationDto fcmMessage =
                NotificationDto.builder()
                        .message(
                                NotificationDto.Message.builder()
                                        .token(notificationReq.getTargetToken())
                                        .notification(
                                                NotificationDto.Notification.builder()
                                                        .title(notificationReq.getTitle())
                                                        .body(notificationReq.getBody())
                                                        .image(null)
                                                        .build())
                                        .apns(
                                                NotificationDto.Apns.builder()
                                                        .payload(
                                                                NotificationDto.Payload.builder()
                                                                        .aps(NotificationDto.Aps.builder()
                                                                                .sound("default")
                                                                                .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .validateOnly(false)
                        .build();

        String fcmMessageString = "";

        try {
            fcmMessageString = objectMapper.writeValueAsString(fcmMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return fcmMessageString;
    }

    // GoogleApi 사용하기 위해 Firebase 에서부터 AccessToken 발급받는 메소드
    private String getAccessToken() throws IOException {
        // 서비스 키 파일이 위치한 경로
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        // 서비스 키 파일로 인증정보 획득
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(
                                new ClassPathResource(firebaseConfigPath).getInputStream())
                        .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        // 토큰 만료 확인
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    // 확인한 알림 읽음 처리
    @Transactional
    public void readNotification(Long id) {
        Optional<Notification> findNotification = notificationRepository.findById(id);
        Notification notification = findNotification.orElseThrow(() -> {
            log.info("Notification을 찾을 수 없습니다. id = {}", id);
            throw new BaseException(Code.NOTIFICATION_NOT_FOUND);
        });
        notification.isReadTrue();
    }

}

