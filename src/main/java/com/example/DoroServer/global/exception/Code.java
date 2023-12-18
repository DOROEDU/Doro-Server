package com.example.DoroServer.global.exception;

import com.google.api.Http;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum Code {

        SUCCESS(OK, "SUCCESS", "OK"),
        // Common
        BAD_REQUEST(HttpStatus.BAD_REQUEST, "AUTH001", "잘못된 요청입니다."),
        // 인증 관련 오류 AUTH001,AUTH002...
        FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH001", "접근 권한이 없습니다."),
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH002", "인증정보가 유효하지 않습니다."),
        JWT_BAD_REQUEST(HttpStatus.UNAUTHORIZED, "AUTH003", "잘못된 JWT 서명입니다."),
        JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH004", "토큰이 만료되었습니다."),
        JWT_UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH006", "지원하지 않는 JWT 토큰입니다."),
        JWT_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH007", "유효한 JWT 토큰이 없습니다."),
        UNAUTHORIZED_PHONE_NUMBER(HttpStatus.UNAUTHORIZED, "AUTH008", "인증되지 않은 전화번호입니다."),
        EXIST_ACCOUNT(HttpStatus.CONFLICT, "AUTH009", "이미 존재하는 아이디입니다."),
        PASSWORD_DID_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH010", "비밀번호가 일치하지 않습니다."),
        DORO_ADMIN_AUTH_FAILED(HttpStatus.UNAUTHORIZED, "AUTH011", "관리자 인증번호가 일치하지 않습니다."),
        DORO_USER_AUTH_FAILED(HttpStatus.UNAUTHORIZED, "AUTH012", "도로 인증번호가 일치하지 않습니다."),
        ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH013", "가입된 아이디가 없습니다."),
        EXIST_PHONE(HttpStatus.CONFLICT, "AUTH014", "이미 존재하는 휴대폰 번호입니다."),
        REFRESH_TOKEN_DID_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH015", "RefreshToken 정보가 일치하지 않습니다."),
        SESSION_ID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH016", "SessionId 정보가 없습니다."),
        SESSION_ID_NOT_VALID(HttpStatus.UNAUTHORIZED, "AUTH017", "SessionId 정보가 유효하지 않습니다."),

        WITHDRAWAL_FAILED(HttpStatus.BAD_REQUEST, "AUTH016", "회원 탈퇴에 실패했습니다."),

        // USER 관련 오류 U001,U002...
        USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER001", "사용자가 존재하지 않습니다"),

        // 강의 관련 오류 L001,L002..
        LECTURE_NOT_FOUND(HttpStatus.BAD_REQUEST, "LEC001", "강의가 존재하지 않습니다."),
        LECTURE_CONTENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "LEC002", "강의 컨텐츠가 존재하지 않습니다."),
        LECTURE_CONTENT_IMAGE_SIZE_OVER(HttpStatus.BAD_REQUEST, "LEC003", "이미지 사이즈가 10MB를 초과했습니다."),
        LECTURE_CONTENT_INVAILD_IMAGE_COUNT(HttpStatus.BAD_REQUEST, "LEC004", "이미지는 1장 이상 100장 이하로 업로드해주세요."),
        LECTURE_CONTENT_IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "LEC005", "이미지가 존재하지 않습니다."),

        // 신청 강사 관련 오류
        TUTOR_NOT_FOUND(HttpStatus.BAD_REQUEST, "TUTOR001", "신청하지 않은 강사입니다."),
        ALREADY_EXIST(HttpStatus.BAD_REQUEST, "TUTOR002", "이미 신청하셨습니다"),
        USER_LECTURE_NOT_FOUND(HttpStatus.BAD_REQUEST, "TUTOR003", "신청내역이 존재하지 않습니다"),

        // 알림톡 관련 오류 M001, M002...
        MESSAGE_SEND_FAILED(HttpStatus.BAD_REQUEST, "M001", "메시지 전송이 실패했습니다. 올바른 번호인지 확인하세요."),
        VERIFICATION_DID_NOT_MATCH(HttpStatus.BAD_REQUEST, "M002", "인증 번호가 일치하지 않습니다."),

        // Notification 관련 오류
        NOTIFICATION_PUSH_FAIL(HttpStatus.BAD_REQUEST, "NOTI001", "FCM 알림 푸쉬에 실패했습니다."),
        NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "NOTI002", "알림을 찾을 수 없습니다."),

        // 공지 관련 오류
        ANNOUNCEMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "ANNO001", "공지를 찾을 수 없습니다."),

        // S3 관련 오류 S001, S002...
        RESIZE_FAILED(HttpStatus.BAD_REQUEST, "S001", "파일 리사이징을 실패했습니다."),
        EMPTY_FILE(HttpStatus.NO_CONTENT, "S002", "업로드 파일이 없습니다."),
        UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "S003", "업로드 중 오류가 발생했습니다."),
        FILE_DELETE_FAILED(HttpStatus.BAD_REQUEST, "S004", "파일 삭제 중 오류가 발생했습니다."),

        // UserNotification 관련 오류
        USER_NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "USERNOTI001", "UserNotification을 찾을 수 없습니다."),

        // fcm 관련 오류
        FCM_UNSPECIFIED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FCM001",
                        "현재 알림을 보낼 수 없습니다. 다시 한번 시도하시고 안되면 관리자에게 문의 바랍니다."),
        FCM_CERTIFICATION_IS_NOT_VALID(HttpStatus.SERVICE_UNAVAILABLE, "FCM002",
                        "FCM 계정을 확인해주세요 apns 혹은 web 인증서에 문제가 있습니다."),
        FCM_INTERNAL_SERVER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "FCM003",
                        "현재 FCM 서버에 문제가 있습니다, 다시 한번 시도해보시고 계속 문제가 있으면 FCM 측에 문의를 해주세요"),
        FCM_INVALID_ARGUMENT(HttpStatus.INTERNAL_SERVER_ERROR, "FCM004",
                        "어플리케이션 이용이 오래되어 토큰이 삭제되었습니다, 로그아웃 혹은 앱을 지웠다가 다시 접속해주세요."),
        FCM_UNREGISTERED(HttpStatus.INTERNAL_SERVER_ERROR, "FCM005",
                        "어플리케이션 이용이 오래되어 토큰이 삭제되었습니다, 로그아웃 혹은 앱을 지웠다가 다시 접속해주세요"),

        // 강의(교육) 신청 관련 오류
        EDUCATION_APPLICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "EDU001", "교육 신청서를 찾을 수 없습니다."),
        EDUCATION_CLASS_GROUP_NOT_FOUND(HttpStatus.BAD_REQUEST, "EDU002", "교육 학급을 찾을 수 없습니다."),
        EDUCATION_APPLICATION_DIFFERENT_PHONE(HttpStatus.UNAUTHORIZED, "EDU003", "휴대폰 번호가 동일하지 않습니다."),

        METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "C-0004", "요청 인자가 유효하지 않음"),
        METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C-0002", "허용되지 않은 Request Method 호출"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C-0003", "내부 서버 오류"),
        INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", "유효하지 않은 값 타입"),
        JSON_SYNTAX_ERROR(HttpStatus.BAD_REQUEST, "C-006", "json 형식이 잘못되었습니다."),
        NOT_SUPPORTED_BODY_TYPE(HttpStatus.BAD_REQUEST, "C-007", "지원하지 않는 형식의 Request Body 형식입니다. json 형식으로 보내주세요");

        private final HttpStatus httpStatus;
        private final String code;
        private final String message;
}
