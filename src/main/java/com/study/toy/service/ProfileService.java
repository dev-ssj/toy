package com.study.toy.service;

import com.study.toy.domain.Profile;
import com.study.toy.dto.ProfileResponseDto;
import com.study.toy.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public List<ProfileResponseDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        //dto를 반환해줘야하기때문에 stream을 사용해야한다.
        return profiles.stream()
                .map(ProfileResponseDto::from)
                .collect(Collectors.toList());
    }

    public Profile getProfiles(long id){
        return profileRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 계정이 없습니다 : "+id));
    }

}

