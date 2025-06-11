package com.study.toy.service;

import com.study.toy.domain.User;
import com.study.toy.dto.LoginDto;
import com.study.toy.dto.RegisterDto;
import com.study.toy.dto.TokenDto;
import com.study.toy.dto.TokenResponseDto;
import com.study.toy.global.Exception.DuplicateUserEmailException;
import com.study.toy.global.Exception.InvalidUserEmailException;
import com.study.toy.global.Exception.InvalidUserPasswordException;
import com.study.toy.global.JWT.TokenManager;
import com.study.toy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;
    private final UserRepository userRepository;

    //회원가입
    @Transactional
    public TokenResponseDto register(RegisterDto registerDto) {
        // email validation 체크
        if (userRepository.existsByEmail(registerDto.getEmail())) {
           throw new DuplicateUserEmailException();
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

    //로그인check
    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                () -> new InvalidUserEmailException()
        );

        if(!user.checkPassword(loginDto.getPassword())) {
            throw new InvalidUserPasswordException();
        }
        return toTokenResponseDto(user);
    }

    public TokenResponseDto toTokenResponseDto(User user) {
        TokenDto tokenDto = TokenDto.builder().userId(user.getId()).build();

        return tokenManager.generateToken(tokenDto);
    }

}