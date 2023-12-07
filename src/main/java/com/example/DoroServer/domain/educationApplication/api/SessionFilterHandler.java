package com.example.DoroServer.domain.educationApplication.api;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;

import com.example.DoroServer.global.common.AuthErrorResponse;
import com.example.DoroServer.global.exception.Code;
import com.example.DoroServer.global.exception.SessionIdException;

public class SessionFilterHandler extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (SessionIdException e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("utf-8");
            Code code = e.getMessage().equals("SESSION_ID_NOT_FOUND") ? Code.SESSION_ID_NOT_FOUND
                    : Code.SESSION_ID_NOT_VALID;
            httpResponse.setStatus(code.getHttpStatus().value());
            AuthErrorResponse authErrorResponse = buildAuthErrorResponse(code);
            httpResponse.getWriter().write(authErrorResponse.toString());

            return;
        }
    }

    // 리팩터링 필요 - static 메서드가 아닌 별개의 클래스에서 메서드 생성
    public static AuthErrorResponse buildAuthErrorResponse(Code errorCode) {
        return AuthErrorResponse.builder()
                .errorCode(errorCode)
                .message(errorCode.getMessage())
                .cause(SessionFilter.class.getName())
                .build();
    }
}