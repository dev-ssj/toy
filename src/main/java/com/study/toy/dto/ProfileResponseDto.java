package com.study.toy.dto;

import com.study.toy.domain.Profile;
import com.study.toy.domain.Skill;
import com.study.toy.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProfileResponseDto {
    private Long id;
    private UserResponseDto user;
    private String location;
    private List<SkillResponseDto> skill;

    @Builder
    public ProfileResponseDto(Long id, UserResponseDto user, String location, List<SkillResponseDto> skill){
        this.id = id;
        this.user = user;
        this.location = location;
        this.skill = skill;
    }

    public static ProfileResponseDto from(Profile profile){
        return ProfileResponseDto.builder()
                .id(profile.getId())
                .user(UserResponseDto.from(profile.getUser()))
                .location(profile.getLocation())
                .skill(profile.getSkills().stream()
                        .map(SkillResponseDto::from) 
                        .collect(Collectors.toList()))
                .build();
    }

}
