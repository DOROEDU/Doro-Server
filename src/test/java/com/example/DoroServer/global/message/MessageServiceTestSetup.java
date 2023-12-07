package com.example.DoroServer.global.message;

import com.example.DoroServer.global.message.dto.SendAuthNumReq.MessageType;

import org.springframework.http.HttpHeaders;

import com.example.DoroServer.global.message.dto.VerifyAuthNumReq;
import com.example.DoroServer.global.message.dto.VerifyAuthNumRes;

public class MessageServiceTestSetup {

    public static VerifyAuthNumReq getVerifyAuthNumReq() {
        return VerifyAuthNumReq.builder()
                .phone("01012345678")
                .authNum("123456")
                .messageType(MessageType.TEMP)
                .build();

    }

    public static VerifyAuthNumRes getVerifyAuthNumRes() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Session-Id", "some-session-id");
        return VerifyAuthNumRes.builder()
                .sessionId(headers)
                .build();
    }

}