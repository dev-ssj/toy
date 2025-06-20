package com.study.toy.service;

import com.study.toy.domain.*;
import com.study.toy.dto.*;
import com.study.toy.global.Exception.CustomException;
import com.study.toy.global.Exception.ErrorCode;
import com.study.toy.global.TextParser;
import com.study.toy.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;


    /*
     * 모든 프로필 조회
     */
    public List<ProfileResponseDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        //dto를 반환해줘야하기때문에 stream을 사용해야한다.
        return profiles.stream()
                .map(ProfileResponseDto::from)
                .collect(Collectors.toList());
    }

    /*
     * 특정 프로필 조회
     */
    public Profile getProfiles(long id){
        return profileRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.INVALID_EMAIL));
    }

    /*
     * 프로필 생성 
     */
    @Transactional
    public Long createProfile(Long userId, CreateProfileRequestDto requestDto) {
        // 유저 직접 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL));

        // 기존 프로필 존재 여부 확인
        if (profileRepository.existsByUser(user)) {
            throw new CustomException(ErrorCode.ALREADY_PROFILE_EXISTS);
        }

        // 프로필 생성 및 저장
        Profile profile = requestDto.toEntity(user);
        Profile savedProfile = profileRepository.save(profile);

        // skillSet 파싱 및 저장
        List<String> skillNames = TextParser.doSplitCode(requestDto.getSkillSet());
        List<Skill> skills = skillNames.stream()
                .map(name -> Skill.builder()
                        .name(name)
                        .profile(savedProfile)
                        .build())
                .toList();

        skillRepository.saveAll(skills);

        return savedProfile.getId();
    }

    /*
     * 프로필 삭제
     */
    @Transactional
    public void updateProfile(Long userId, Long profileId, UpdateProfileRequestDto requestDto) {
        // 1. 프로필 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        // 2. 사용자 권한 체크 (직접 받은 userId와 프로필 소유자 비교)
        if (!profile.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        // 3. 프로필 필드 업데이트
        requestDto.updateEntity(profile);

        // 4. 기존 스킬 삭제
        skillRepository.deleteAll(profile.getSkills());

        // 5. 새로운 스킬 파싱 및 저장
        List<String> skillNames = TextParser.doSplitCode(requestDto.getSkillSet());
        List<Skill> newSkills = skillNames.stream()
                .map(name -> Skill.builder()
                        .name(name)
                        .profile(profile)
                        .build())
                .collect(Collectors.toList());

        skillRepository.saveAll(newSkills);
    }


    /*
    * 경력 추가
    */
    @Transactional
    public void addExperience(Long profileId, ExperienceRequestDto requestDto) {
        // 1. 프로필 존재 여부 확인
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        // 2.Experience 생성 + 프로필 연관관계 설정
        Experience experience = requestDto.toEntity(profile);
        experienceRepository.save(experience);// 연관관계 설정

    }

    /*
     * 경력 추가
     */
    @Transactional
    public void deleteExperience(Long profileId, Long experienceId) {
        // 1. 프로필 존재 여부 확인
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        // 2. 삭제 대상 경력 조회
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new CustomException(ErrorCode.EXPERIENCE_NOT_FOUND));

        // 3. 경력이 해당 프로필에 속해 있는지 검증
        if (!experience.getProfile().getId().equals(profileId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        // 4. 삭제
        experienceRepository.delete(experience);
    }

    /*
     * 학력 추가
     */
    @Transactional
    public void addEducation(Long profileId, EducationRequestDto dto) {
        
        //프로필 유무 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        Education education = dto.toEntity(profile);
        educationRepository.save(education);
    }


    /*
     * 학력 삭제
     */
    @Transactional
    public void deleteEducation(Long profileId, Long educationId) {
        
        //프로필 유무 
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        //해당 학력이 없으면 에러
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new CustomException(ErrorCode.EDUCATION_NOT_FOUND));

        //프로필 id와 해당 id가 맞는지
        if (!education.getProfile().getId().equals(profileId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        educationRepository.delete(education);
    }

}

