package com.study.toy.dto;

import lombok.Getter;

@Getter
public class LoginDto {
    private String email;
    private String password;

    public LoginDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}
