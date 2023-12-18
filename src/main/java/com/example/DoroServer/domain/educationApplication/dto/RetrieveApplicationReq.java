package com.example.DoroServer.domain.educationApplication.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "^01([016789])([0-9]{3,4})([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber; // 신청자 전화번호
}
