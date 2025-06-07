package com.study.toy.controller;

import com.study.toy.domain.Profile;
import com.study.toy.dto.CommonResponse;
import com.study.toy.dto.ProfileDetailResponseDto;
import com.study.toy.dto.ProfileResponseDto;
import com.study.toy.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileApiController {
    private final ProfileService profileService;

    //데이터만 리턴하는 메서드
//    @GetMapping("/api/profiles")
//    public ResponseEntity<List<ProfileResponseDto>> findAllProfiles(){
//        List<ProfileResponseDto> profile = profileService.getAllProfiles();
//
//        return ResponseEntity.ok().body(profile);
//    }

//    @GetMapping("/api/profiles/{id}")
//    public ResponseEntity<ProfileDetailResponseDto> findProfile(@PathVariable long id){
//        Profile profile = profileService.getProfiles(id);
//
//        return ResponseEntity.ok()
//                .body(ProfileDetailResponseDto.from(profile));
//    }


    @GetMapping("/api/profiles")
    public ResponseEntity<?> findAllProriles(){
        List<ProfileResponseDto> profiles = profileService.getAllProfiles();
        return CommonResponse.setResponse(new CommonResponse<>(profiles));
    }

    @GetMapping("/api/profiles/{id}")
    public ResponseEntity<?> findProfile(@PathVariable long id){

        ProfileResponseDto profiles = ProfileResponseDto.from(profileService.getProfiles(id));
        return CommonResponse.setResponse(new CommonResponse<>(profiles));
    }
}
