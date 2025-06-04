package com.study.toy.dto;

import com.study.toy.domain.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProfileDetailResponseDto {
    private Long id;
    private UserResponseDto user;
    private String location;
    private String bio;
    private String gitName;
    private String webSite;
    private List<SkillResponseDto> skill;
    private List<EducationResponseDto> education;
    private List<ExperienceResponseDto> experience;

    @Builder
    public ProfileDetailResponseDto(Long id, UserResponseDto user, String location, String bio, String gitName, String webSite, List<SkillResponseDto> skill, List<EducationResponseDto> education, List<ExperienceResponseDto> experience){
        this.id = id;
        this.user = user;
        this.location = location;
        this.bio = bio;
        this.gitName = gitName;
        this.webSite = webSite;
        this.skill = skill;
        this.experience = experience;
        this.education = education;
    }

    public static ProfileDetailResponseDto from(Profile profile){
        return ProfileDetailResponseDto.builder()
                .id(profile.getId())
                .user(UserResponseDto.from(profile.getUser()))
                .location(profile.getLocation())
                .bio(profile.getBio())
                .gitName(profile.getGitName())
                .webSite(profile.getWebSite())
                /* 1. getSkills로 모든 스킬 데이터 불러오고
                * 2. .stream()으로 1에서 가져온 skills들을 하나씩 반복처리한다
                * 3. .map() : skills 객체를 SkillResponseDto에 정의된 from 메서드에 전달하여 DTO로 변환
                * 4. .collect() : DTO로 변환된 sKills 객체들을 리스트로 모음.
                */
                .skill(profile.getSkills().stream()
                        .map(SkillResponseDto::from)
                        .collect(Collectors.toList()))
                .experience(profile.getExperiences().stream()
                        .map(ExperienceResponseDto::from)
                        .collect(Collectors.toList()))
                .education(profile.getEducations().stream()
                        .map(EducationResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }

}
