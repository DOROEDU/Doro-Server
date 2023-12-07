package com.example.DoroServer.domain.educationApplication.api;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import com.example.DoroServer.global.exception.Code;
import com.example.DoroServer.global.exception.SessionIdException;
import com.example.DoroServer.global.jwt.RedisService;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SessionFilter extends GenericFilterBean {
    // 해당 필터는 "/education-application"에 대한 요청에 대해서만 적용

    private final RedisService redisService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.startsWith("/education-application")) {
            String sessionId = httpRequest.getHeader("Session-Id");
            if (sessionId == null) {
                log.debug("유효한 Session-Id가 없습니다, uri: {}", requestURI);
                throw new SessionIdException(Code.SESSION_ID_NOT_FOUND);
            }
            String phoneNumber = redisService.getValues(sessionId);
            if (phoneNumber == null) {
                log.debug("유효한  Session-Id가 아닙니다, uri: {}", requestURI);
                throw new SessionIdException(Code.SESSION_ID_NOT_VALID);
            }
            httpRequest.setAttribute("phoneNumber", phoneNumber);

        }

        chain.doFilter(request, response);
    }
}
