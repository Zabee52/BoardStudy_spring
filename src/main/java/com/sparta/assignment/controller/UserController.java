package com.sparta.assignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.assignment.dto.SignUpRequestDto;
import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.service.KakaoUserService;
import com.sparta.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @GetMapping("/user/login")
    public String loginPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // userDetails가 null이 아니다 = 로그인한 상태라는 뜻.
        if(userDetails != null){
            return "redirect:/board?login";
        }else{
            return "login";
        }
    }

    @PostMapping("/user/login")
    public String loginProc() {
        return "redirect:/board";
    }

    @GetMapping("/user/logout")
    public String logout(){
        return "board";
    }

    @GetMapping("/user/signup")
    public String signUpPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails!=null){
            return "redirect:/board?login";
        }else{
            return "signup";
        }
    }

    @PostMapping("/user/signup")
    @ResponseBody
    public String signUpProc(@RequestBody SignUpRequestDto signUpRequestDto){
        String str = "";
        try{
            str = userService.signUp(signUpRequestDto);
        }catch(Exception e){
            str = e.getMessage();
        }
        return str;
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);

        return "redirect:/";
    }
}
