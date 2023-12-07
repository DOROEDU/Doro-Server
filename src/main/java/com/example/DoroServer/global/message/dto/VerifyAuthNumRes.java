package com.example.DoroServer.global.message.dto;

import org.springframework.http.HttpHeaders;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyAuthNumRes {
    HttpHeaders sessionId;
}