package com.study.toy.dto;

import com.study.toy.domain.Education;
import com.study.toy.domain.Profile;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EducationRequestDto {

    private String school;
    private String fieldOfStudy;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;

    public Education toEntity(Profile profile) {
        return Education.builder()
                .school(this.school)
                .fieldOfStudy(this.fieldOfStudy)
                .degree(this.degree)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .profile(profile)
                .build();
    }
}
