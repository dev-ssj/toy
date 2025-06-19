package com.study.toy.dto;

import com.study.toy.domain.Experience;
import com.study.toy.domain.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExperienceRequestDto {
    private String company;
    private String position;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public ExperienceRequestDto(String company, String position, String description, LocalDate startDate, LocalDate endDate){
        this.company = company;
        this.position = position;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Experience toEntity(Profile profile) {
        return Experience.builder()
                .company(this.company)
                .position(this.position)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .profile(profile)
                .build();
    }

}
