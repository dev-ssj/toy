package com.study.toy.controller;

import com.study.toy.dto.RegisterDto;
import com.study.toy.dto.TokenResponseDto;
import com.study.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<TokenResponseDto> register(@RequestBody RegisterDto registerDto) {
        TokenResponseDto response = userService.register(registerDto);
        return ResponseEntity.ok(response);
    }

}
