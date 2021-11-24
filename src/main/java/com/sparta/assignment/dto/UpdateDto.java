package com.sparta.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
