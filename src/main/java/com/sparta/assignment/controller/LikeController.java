package com.sparta.assignment.controller;

import com.sparta.assignment.dto.LikeDto;
import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/board/post/like")
    public String likeProc(@RequestBody LikeDto likeDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails != null){
            likeService.likeProc(likeDto);
            return "redirect:/board/post/" + likeDto.getPostId();
        }else{
            return "redirect:/user/login";
        }
    }
}
