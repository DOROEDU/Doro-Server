package com.example.DoroServer.domain.educationApplication.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.example.DoroServer.global.exception.SessionIdException;
import com.example.DoroServer.global.jwt.RedisService;

@ExtendWith(MockitoExtension.class)
public class SessionFilterTest {

    @Mock
    private RedisService redisService;

    @InjectMocks
    private SessionFilter sessionFilter;

    @Test
    @DisplayName("doFilter 메서드 테스트 - 세션 ID가 유효한 경우")
    public void testDoFilter_ValidSessionId_Success() throws Exception {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/education-application");
        request.addHeader("Session-Id", "valid-session-id");
        String phoneNumber = "01012345678"; // valid-session-id: 01012345678
        MockHttpServletResponse response = new MockHttpServletResponse();

        given(redisService.getValues("valid-session-id")).willReturn(phoneNumber);

        // when
        MockFilterChain filterChain = new MockFilterChain();
        sessionFilter.doFilter(request, response, filterChain);

        // then
        verify(redisService, times(1)).getValues("valid-session-id");
        assertEquals(phoneNumber, request.getAttribute("phoneNumber"));
    }

    @Test
    @DisplayName("doFilter 메서드 테스트 - 세션 ID가 없이 요청한 경우")
    public void testDoFilter_InvalidSessionId_ExceptionThrown() throws Exception {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/education-application");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        MockFilterChain filterChain = new MockFilterChain();

        // then
        assertThrows(SessionIdException.class, () -> sessionFilter.doFilter(request, response, filterChain));
    }

    @Test
    @DisplayName("doFilter 메서드 테스트 - 세션 ID가 유효하지 않은 경우")
    public void testDoFilter_InvalidSessionId_ExceptionThrown2() throws Exception {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/education-application");
        request.addHeader("Session-Id", "invalid-session-id");

        MockHttpServletResponse response = new MockHttpServletResponse();

        given(redisService.getValues("invalid-session-id")).willReturn(null);

        // when
        MockFilterChain filterChain = new MockFilterChain();

        // then
        assertThrows(SessionIdException.class, () -> sessionFilter.doFilter(request, response, filterChain));
    }
}
