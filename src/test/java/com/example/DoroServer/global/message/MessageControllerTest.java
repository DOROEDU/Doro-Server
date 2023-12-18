package com.example.DoroServer.global.message;

import static com.example.DoroServer.global.message.MessageServiceTestSetup.getVerifyAuthNumReq;
import static com.example.DoroServer.global.message.MessageServiceTestSetup.getVerifyAuthNumRes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.DoroServer.global.message.dto.VerifyAuthNumReq;
import com.example.DoroServer.global.message.dto.VerifyAuthNumRes;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageServiceImpl messageService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    @DisplayName("MockMvc를 통한 휴대폰 인증 세션 테스트")
    public void verifyAuthNum2Test() throws Exception {
        // given
        VerifyAuthNumReq verifyAuthNumReq = getVerifyAuthNumReq();
        VerifyAuthNumRes verifyAuthNumRes = getVerifyAuthNumRes();

        given(messageService.verifyAuthNum2(any(VerifyAuthNumReq.class))).willReturn(verifyAuthNumRes);

        // when & then
        mockMvc.perform(post("/message/verify2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(verifyAuthNumReq)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Session-Id"));
    }
}
