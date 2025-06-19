package com.study.toy.dto;

import com.study.toy.domain.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProfileRequestDto {
    private String location;
    private String bio;
    private String website;
    private String gitName;
    private String skillSet; // 수정할 스킬 문자열도 포함

    public static UpdateProfileRequestDto of(String location, String bio, String website, String gitName, String skillSet) {
        UpdateProfileRequestDto dto = new UpdateProfileRequestDto();
        dto.location = location;
        dto.bio = bio;
        dto.website = website;
        dto.gitName = gitName;
        dto.skillSet = skillSet;
        return dto;
    }

    public void updateEntity(Profile profile) {
        profile.update(location, bio, website, gitName);
    }
}