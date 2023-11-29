package com.example.DoroServer.domain.educationApplication.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveApplicationReq {
    @NotBlank
    private String phoneNumber; // 신청자 전화번호
}
