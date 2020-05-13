package com.gasca.yaba.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CommentRequest {
    @NotBlank(message = "Content can't be blank")
    private String content;
}
