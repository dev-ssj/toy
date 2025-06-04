package com.study.toy.dto;

import com.study.toy.domain.Education;
import com.study.toy.domain.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EducationResponseDto {
    private Long id;
    private String school;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public EducationResponseDto(Long id, String school, String degree, String fieldOfStudy, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.school = school;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static EducationResponseDto from(Education education){
        return EducationResponseDto.builder()
                .id(education.getId())
                .school(education.getSchool())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .build();
    }
}
