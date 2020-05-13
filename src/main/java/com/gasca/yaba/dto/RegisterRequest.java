package com.gasca.yaba.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Username can't be blank")
    private String username;

    @NotBlank(message = "Password can't be blank")
    private String password;

    @NotBlank(message = "Name can't be blank")
    private String name;

    private String bio;
}
