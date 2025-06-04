package com.study.toy.controller;

import com.study.toy.domain.Profile;
import com.study.toy.dto.CommonResponse;
import com.study.toy.dto.ProfileDetailResponseDto;
import com.study.toy.dto.ProfileResponseDto;
import com.study.toy.service.ProfileService;
import com.study.toy.service.ResponseService;
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
    private final ResponseService responseService;

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
    public ResponseEntity<CommonResponse<?>> findAllProriles(){
        return ResponseEntity.ok(responseService.success("전체 프로필 조회 성공", profileService.getAllProfiles()));
    }

    @GetMapping("/api/profiles/{id}")
    public ResponseEntity<CommonResponse<?>> findProfile(@PathVariable long id){
        return ResponseEntity.ok
                (responseService.success("프로필 조회 성공", ProfileDetailResponseDto.from(profileService.getProfiles(id)))
        );
    }
}
