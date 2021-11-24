package com.sparta.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String nickname;
    private String password;
    private Long kakaoId;

    public UserDto(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.kakaoId = null;
    }
}
