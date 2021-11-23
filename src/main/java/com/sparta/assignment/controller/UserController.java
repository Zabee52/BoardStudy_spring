package com.sparta.assignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.assignment.dto.SignUpRequestDto;
import com.sparta.assignment.service.KakaoUserService;
import com.sparta.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @GetMapping("/user/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/user/login")
    public String loginProc() {
        return "redirect:/";
    }

    @GetMapping("/user/signup")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping("/user/signup")
    @ResponseBody
    public String signUpProc(@RequestBody SignUpRequestDto signUpRequestDto){
        return userService.signUp(signUpRequestDto);
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);

        return "redirect:/";
    }
}
