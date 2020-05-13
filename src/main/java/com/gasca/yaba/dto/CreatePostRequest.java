package com.gasca.yaba.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreatePostRequest {
    @NotBlank(message = "Title can't be empty")
    private String title;

    @NotBlank(message = "Description can't be blank")
    private String description;

    @NotBlank(message = "Content can't be blank")
    private String content;
}
