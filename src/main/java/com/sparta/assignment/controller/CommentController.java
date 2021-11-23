package com.sparta.assignment.controller;

import com.sparta.assignment.dto.CommentDto;
import com.sparta.assignment.dto.UpdateDto;
import com.sparta.assignment.models.Comment;
import com.sparta.assignment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/board/comment")
    public Comment writeComment(@RequestBody CommentDto commentDto){
        return commentService.writeComment(commentDto);
    }

    @PutMapping("/board/comment/{id}")
    public void modifyComment(@PathVariable Long id, @RequestBody UpdateDto updateDto){
        commentService.modifyComment(id, updateDto);
    }

    @DeleteMapping("/board/comment/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
    }
}
