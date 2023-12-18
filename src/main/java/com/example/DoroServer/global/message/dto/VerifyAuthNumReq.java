package com.example.DoroServer.global.message.dto;

import com.example.DoroServer.global.message.dto.SendAuthNumReq.MessageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyAuthNumReq {
    @NotBlank
    @Pattern(regexp = "^01([016789])([0-9]{3,4})([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
    private String phone;

    @NotBlank
    private String authNum;

    @NotNull
    private MessageType messageType;
}
