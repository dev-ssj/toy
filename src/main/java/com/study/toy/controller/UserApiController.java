package com.study.toy.controller;

import com.study.toy.domain.User;
import com.study.toy.dto.CommonResponse;
import com.study.toy.dto.LoginDto;
import com.study.toy.dto.RegisterDto;
import com.study.toy.dto.TokenResponseDto;
import com.study.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    /*
    * 회원가입
    */
    @PostMapping("/api/users")
    public ResponseEntity<TokenResponseDto> register(@RequestBody RegisterDto registerDto) {
        TokenResponseDto response = userService.register(registerDto);
        return ResponseEntity.ok(response);
    }


    /*
    * 로그인
    */
    //공통 api응답
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        TokenResponseDto tokenResponse = userService.login(loginDto);
        return CommonResponse.setResponse(new CommonResponse<>(tokenResponse));
    }
}

