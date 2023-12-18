package com.example.DoroServer.global.message;

import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;
import com.example.DoroServer.global.exception.MessageException;
import com.example.DoroServer.global.jwt.RedisService;
import com.example.DoroServer.global.message.dto.SendAuthNumReq;
import com.example.DoroServer.global.message.dto.VerifyAuthNumReq;
import com.example.DoroServer.global.message.dto.VerifyAuthNumRes;

import java.time.Duration;
import java.util.UUID;
import java.util.HashMap;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final DefaultMessageService defaultMessageService;
    private final RedisService redisService;
    private final String fromNumber;
    private final String pfId;
    private final String templateId;

    public MessageServiceImpl(@Value("${solapi.api-key}") String apiKey,
            @Value("${solapi.api-secret}") String apiSecret,
            @Value("${solapi.from-number}") String fromNumber,
            @Value("${solapi.domain}") String domain,
            @Value("${solapi.pfid}") String pfid,
            @Value("${solapi.templateId}") String templateId,
            RedisService redisService) {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, domain);
        this.fromNumber = fromNumber;
        this.pfId = pfid;
        this.templateId = templateId;
        this.redisService = redisService;
    }

    @Transactional
    @Override
    public void sendAuthNum(SendAuthNumReq sendAuthNumReq) {
        try {
            String authNum = numberGen(6);
            Message message = makeKakaoMessage(sendAuthNumReq, authNum);

            redisService.setValues(sendAuthNumReq.getMessageType() + sendAuthNumReq.getPhone(),
                    authNum, Duration.ofSeconds(300));
            log.info("Redis 저장 성공");
            defaultMessageService.sendOne(new SingleMessageSendingRequest(message));
            log.info("메시지 전송 성공");
        } catch (Exception e) {
            throw new MessageException(Code.MESSAGE_SEND_FAILED);
        }
    }

    private Message makeKakaoMessage(SendAuthNumReq sendAuthNumReq, String authNum) {
        KakaoOption kakaoOption = new KakaoOption();
        kakaoOption.setPfId(pfId);
        kakaoOption.setTemplateId(templateId);
        // 알림톡 템플릿 내 #{변수} 관리
        HashMap<String, String> variables = new HashMap<>();
        variables.put("#{인증번호}", authNum);
        kakaoOption.setVariables(variables);

        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(sendAuthNumReq.getPhone());
        message.setKakaoOptions(kakaoOption);
        return message;
    }

    @Override
    public void verifyAuthNum(VerifyAuthNumReq verifyAuthNumReq) {
        String redisAuthNum = redisService.getValues(verifyAuthNumReq.getMessageType() +
                verifyAuthNumReq.getPhone());
        if (redisAuthNum == null || !redisAuthNum.equals(verifyAuthNumReq.getAuthNum())) {
            throw new BaseException(Code.VERIFICATION_DID_NOT_MATCH);
        }
        redisService.deleteValues(verifyAuthNumReq.getPhone());
        redisService.setValues(verifyAuthNumReq.getMessageType() +
                verifyAuthNumReq.getPhone(), "Verified", Duration.ofMinutes(30));
    }

    @Override
    public VerifyAuthNumRes verifyAuthNum2(VerifyAuthNumReq verifyAuthNumReq) {
        String redisAuthNum = redisService.getValues(verifyAuthNumReq.getMessageType() +
                verifyAuthNumReq.getPhone());
        if (redisAuthNum == null || !redisAuthNum.equals(verifyAuthNumReq.getAuthNum())) {
            throw new BaseException(Code.VERIFICATION_DID_NOT_MATCH);
        }
        redisService.deleteValues(verifyAuthNumReq.getPhone());

        // Redis에 5분간 유효한 세션(세션 ID: 전화번호) 생성
        String sessionId = generateSessionId();
        redisService.setValues(sessionId, verifyAuthNumReq.getPhone(), Duration.ofMinutes(5));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Session-Id", sessionId);
        return VerifyAuthNumRes.builder().sessionId(httpHeaders).build();
    }

    /**
     * @param len : 생성할 난수의 길이
     */
    private String numberGen(int len) {
        Random rand = new Random();
        StringBuilder numStr = new StringBuilder();

        for (int i = 0; i < len; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr.append(ran);
        }
        return numStr.toString();
    }

    public static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
