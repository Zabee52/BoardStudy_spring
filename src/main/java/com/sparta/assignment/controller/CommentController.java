package com.sparta.assignment.controller;

import com.sparta.assignment.dto.CommentDto;
import com.sparta.assignment.dto.UpdateDto;
import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Secured("ROLE_USER")
    @PostMapping("/board/comment")
    public String writeComment(@RequestBody CommentDto commentDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        String str;
        try{
            str = commentService.writeComment(commentDto, userDetails);
        }catch(Exception e){
            str = e.getMessage();
        }
        return str;
    }

    @Secured("ROLE_USER")
    @PutMapping("/board/comment/{id}")
    public String modifyComment(@PathVariable Long id,
                                @RequestBody UpdateDto updateDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        String str;
        try{
            str = commentService.modifyComment(id, updateDto, userDetails);
        }catch(Exception e){
            str = e.getMessage();
        }

        return str;
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/board/comment/{id}")
    public String deleteComment(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        String str;

        try{
            str = commentService.deleteComment(id, userDetails);
        }catch(Exception e){
            str = e.getMessage();
        }

        return str;
    }
}
