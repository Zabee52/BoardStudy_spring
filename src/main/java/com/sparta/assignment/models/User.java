package com.sparta.assignment.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String permission;

    @Column(unique = true)
    private Long kakaoId;

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.permission = "ROLE_USER";
        this.kakaoId = null;
    }

    public User(String nickname, String password, Long kakaoId) {
        this.nickname = nickname;
        this.password = password;
        this.permission = "ROLE_USER";
        this.kakaoId = kakaoId;
    }

    public void setKakaoId(Long kakaoId){
        this.kakaoId = kakaoId;
    }
}
