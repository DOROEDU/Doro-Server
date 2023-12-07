package com.example.DoroServer.global.message;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DoroServer.global.jwt.RedisService;
import com.example.DoroServer.global.message.dto.VerifyAuthNumReq;
import com.example.DoroServer.global.message.dto.VerifyAuthNumRes;

import static com.example.DoroServer.global.message.MessageServiceTestSetup.getVerifyAuthNumReq;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {

    private MessageServiceImpl messageService;

    @Mock
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        messageService = new MessageServiceImpl("apiKey", "apiSecret", "fromNumber", "http://example.com", "pfid",
                "templateId", redisService);
    }

    @Test
    @DisplayName("verifyAuthNum2 테스트")
    void verifyAuthNum2Test() {
        // given
        VerifyAuthNumReq verifyAuthNumReq = getVerifyAuthNumReq();

        given(redisService.getValues(verifyAuthNumReq.getMessageType() + verifyAuthNumReq.getPhone()))
                .willReturn(verifyAuthNumReq.getAuthNum()); // redisService는 모두 정상적으로 작동한다고 가정

        // when
        VerifyAuthNumRes result = messageService.verifyAuthNum2(verifyAuthNumReq);

        // then
        verify(redisService, Mockito.times(1))
                .getValues(verifyAuthNumReq.getMessageType() + verifyAuthNumReq.getPhone());
        assert (result.getSessionId().get("Session-Id") != null);
    }
}
