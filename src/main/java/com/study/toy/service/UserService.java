package com.study.toy.service;

import com.study.toy.domain.User;
import com.study.toy.dto.LoginDto;
import com.study.toy.dto.RegisterDto;
import com.study.toy.dto.TokenDto;
import com.study.toy.dto.TokenResponseDto;
import com.study.toy.global.Exception.CustomException;
import com.study.toy.global.Exception.ErrorCode;
import com.study.toy.global.JWT.TokenContext;
import com.study.toy.global.JWT.TokenContextHolder;
import com.study.toy.global.JWT.TokenManager;
import com.study.toy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;
    private final UserRepository userRepository;

    /*
    * 회원가입
    */
    @Transactional
    public TokenResponseDto register(RegisterDto registerDto) {
        // email validation 체크
        if (userRepository.existsByEmail(registerDto.getEmail())) {
           throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        RegisterDto encodedDto = RegisterDto.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .password(encodedPassword)  //암호화된 비밀번호
                .build();

        User user = userRepository.save(encodedDto.toEntity());
        return toTokenResponseDto(user);
    }

    /*
     * 로그인 체크
     */
    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginDto loginDto) {
        //동일한 이메일이 있는지 확인
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_EMAIL)
        );

        //있으면 패스워드 체크
        user.checkPassword(loginDto.getPassword(), passwordEncoder);
        return toTokenResponseDto(user);
    }

    //로그인 시, 토큰 반환
    public TokenResponseDto toTokenResponseDto(User user) {
        TokenDto tokenDto = TokenDto.builder().userId(user.getId()).build();


        return tokenManager.generateToken(tokenDto);
    }

    /*
    * 로그인한 사용자의 userId 가져오기 
    */
    @Transactional(readOnly = true)
    public User getCurrentUser(){
        //로그인한 사용자 번호 가져오기
        Long userId = TokenContextHolder.getContext().getUserId();
        System.out.println("================="+userId+"================");

        return userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_EMAIL));
    }

}