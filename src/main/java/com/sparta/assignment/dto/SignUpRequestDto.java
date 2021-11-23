package com.sparta.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequestDto {
    private String nickname;
    private String password;
    private String passwordCheck;
}
