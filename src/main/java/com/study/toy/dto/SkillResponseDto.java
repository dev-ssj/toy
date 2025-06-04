package com.study.toy.dto;

import com.study.toy.domain.Skill;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SkillResponseDto {
    private Long id;
    private String name;

    @Builder
    public SkillResponseDto(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static SkillResponseDto from(Skill skill){
        return SkillResponseDto.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
