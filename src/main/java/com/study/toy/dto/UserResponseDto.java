package com.study.toy.dto;

import com.study.toy.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String profileImageUrl;

    @Builder
    public UserResponseDto(Long id, String name, String email, String profileImageUrl){
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

    //Entity -> DTO로 변환하는 메서드
    public static UserResponseDto from(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
