package com.sparta.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
    private Long postId;
    private Long userId;
    private String nickname;
    private String content;
}
