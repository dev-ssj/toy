package com.study.toy.controller;

import com.study.toy.domain.Profile;
import com.study.toy.dto.*;
import com.study.toy.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    
    /*
    * 전체 프로필 조회
    */
    @Transactional(readOnly = true)
    @GetMapping("/api/profiles")
    public ResponseEntity<?> findAllProriles(){
        List<ProfileResponseDto> profiles = profileService.getAllProfiles();
        return CommonResponse.setResponse(new CommonResponse<>(profiles));
    }

    /*
    * 특정 id의 프로필 조회 
    */
    @Transactional(readOnly = true)
    @GetMapping("/api/profiles/{id}")
    public ResponseEntity<?> findProfile(@PathVariable long id){

        ProfileResponseDto profiles = ProfileResponseDto.from(profileService.getProfiles(id));
        return CommonResponse.setResponse(new CommonResponse<>(profiles));
    }

    /*
     * 프로필 생성
     */
    @PostMapping("/api/profiles")
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileRequestDto dto,
                                           @RequestParam Long userId) {
        Long profileId = profileService.createProfile(userId, dto);
        return CommonResponse.setResponse(new CommonResponse<>("프로필 생성 완료. profileId : " +profileId));
    }

    /*
     * 프로필 수정
     */
    @PutMapping("/api/profiles/{profileId}")
    public ResponseEntity<?> updateProfile(@RequestParam Long userId,
                                           @PathVariable Long profileId,
                                           @RequestBody UpdateProfileRequestDto dto) {
        profileService.updateProfile(userId, profileId, dto);
        return CommonResponse.setResponse(new CommonResponse<>("프로필 수정 완료. userId : "+userId));
    }

    /*
     * 경력 추가
     */
    @PostMapping("/api/profiles/{profileId}/experiences")
    public ResponseEntity<?> addExperience(@PathVariable Long profileId,
                                           @RequestBody ExperienceRequestDto dto) {
        profileService.addExperience(profileId, dto);
        return CommonResponse.setResponse(new CommonResponse<>("경력 추가 완료. profileID : " + profileId));
    }

    /*
     * 경력 삭제
     */
    @DeleteMapping("/api/profiles/{profileId}/experiences/{experienceId}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long profileId,
                                              @PathVariable Long experienceId) {
        profileService.deleteExperience(profileId, experienceId);
        return CommonResponse.setResponse(new CommonResponse<>("경력 삭제 완료. experienceId : " + experienceId ));
    }
    
    /*
     * 학력 추가
     */
    @PostMapping("/api/profiles/{profileId}/educations")
    public ResponseEntity<?> addEducation(@PathVariable Long profileId,
                                          @RequestBody EducationRequestDto dto) {
        profileService.addEducation(profileId, dto);
        return CommonResponse.setResponse(new CommonResponse<>("학력 추가 완료. profileID : " +profileId ));
    }

    /*
     * 학력 삭제
     */
    @DeleteMapping("/api/profiles/{profileId}/educations/{educationId}")
    public ResponseEntity<?> deleteEducation(@PathVariable Long profileId,
                                             @PathVariable Long educationId) {
        profileService.deleteEducation(profileId, educationId);
        return CommonResponse.setResponse(new CommonResponse<>("학력 삭제 완료. educationId : " + educationId));
    }

}
