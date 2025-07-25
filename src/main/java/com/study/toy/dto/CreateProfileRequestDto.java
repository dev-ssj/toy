package com.study.toy.dto;

import com.study.toy.domain.Profile;
import com.study.toy.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateProfileRequestDto {
    private Long id;
    private String location;
    private String bio;
    private String website;
    private String gitName;
    private String skillSet; // 콤마로 구분된 스킬 문자열

    public static CreateProfileRequestDto of(String location, String bio, String website, String gitName, String skillSet) {
        CreateProfileRequestDto dto = new CreateProfileRequestDto();
        dto.location = location;
        dto.bio = bio;
        dto.website = website;
        dto.gitName = gitName;
        dto.skillSet = skillSet;
        return dto;
    }

    public Profile toEntity(User user) {
        return Profile.builder()
                .user(user)
                .location(location)
                .bio(bio)
                .webSite(website)
                .gitName(gitName)
                .build();
    }

    @Builder
    public CreateProfileRequestDto(Long id, String location, String bio, String website, String gitName, String skillSet){
        this.id = id;
        this.location = location;
        this.bio = bio;
        this.website = website;
        this.gitName = gitName;
        this.skillSet = skillSet;
    }
}