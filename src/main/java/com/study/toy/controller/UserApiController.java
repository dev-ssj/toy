package com.study.toy.controller;

import com.study.toy.domain.User;
import com.study.toy.dto.CommonResponse;
import com.study.toy.dto.LoginDto;
import com.study.toy.dto.RegisterDto;
import com.study.toy.dto.TokenResponseDto;
import com.study.toy.global.Exception.InvalidUserEmailException;
import com.study.toy.global.Exception.InvalidUserPasswordException;
import com.study.toy.repository.UserRepository;
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

    @PostMapping("/api/users")
    public ResponseEntity<TokenResponseDto> register(@RequestBody RegisterDto registerDto) {
        TokenResponseDto response = userService.register(registerDto);
        return ResponseEntity.ok(response);
    }

    //강사님이 주신 api요청대로
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            TokenResponseDto tokenResponse = userService.login(loginDto);
            return ResponseEntity.ok(tokenResponse);
        } catch (InvalidUserEmailException | InvalidUserPasswordException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("errors", List.of(Map.of("msg", "Invalid Credentials"))));
        }
    }

    //공통 api응답
    @PostMapping("/login")
    public ResponseEntity<?> login2(@RequestBody LoginDto loginDto) {
        TokenResponseDto tokenResponse = userService.login(loginDto);
        return CommonResponse.setResponse(new CommonResponse<>(tokenResponse));
    }
}

