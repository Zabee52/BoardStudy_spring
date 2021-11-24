package com.sparta.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CommentDto {
    @NotBlank
    private Long postId;

    @NotBlank
    private Long userId;

    @NotBlank
    private String nickname;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
