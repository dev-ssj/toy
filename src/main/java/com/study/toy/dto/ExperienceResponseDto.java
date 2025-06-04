package com.study.toy.dto;

import com.study.toy.domain.Experience;
import com.study.toy.domain.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExperienceResponseDto {
    private Long id;
    private String company;
    private String position;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public ExperienceResponseDto(Long id, String company, String position, String description, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.company = company;
        this.position = position;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static ExperienceResponseDto from(Experience experience){
        return ExperienceResponseDto.builder()
                .id(experience.getId())
                .company(experience.getCompany())
                .position(experience.getPosition())
                .description(experience.getDescription())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .build();
    }
}
