package com.study.toy;

import com.study.toy.domain.Education;
import com.study.toy.domain.Experience;
import com.study.toy.domain.Profile;
import com.study.toy.domain.User;
import com.study.toy.dto.CreateProfileRequestDto;
import com.study.toy.dto.EducationRequestDto;
import com.study.toy.dto.ExperienceRequestDto;
import com.study.toy.dto.UpdateProfileRequestDto;
import com.study.toy.repository.*;
import com.study.toy.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ProfileServiceMockTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private EducationRepository educationRepository;

    @Mock
    SkillRepository skillRepository;

    @InjectMocks
    private ProfileService profileService;

    @Captor
    private ArgumentCaptor<Profile> profileCaptor;

    @Captor
    private ArgumentCaptor<Experience> experienceCaptor;

    @Captor
    private ArgumentCaptor<Education> educationCaptor;
    @Test
    @DisplayName("프로필을 생성한다.")
    void createProfile_success() {
        // given

        //유저 정보
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name("Eric")
                .email("eric@test.com")
                .build();

        //프로필
        CreateProfileRequestDto dto = CreateProfileRequestDto.builder()
                .bio("백엔드 개발자")
                .website("https://kakao.com")
                .location("제주도")
                .skillSet("Java,Spring Boot")
                .build();

        Profile savedProfile = Profile.builder()
                .id(100L)
                .user(user)
                .bio(dto.getBio())
                .webSite(dto.getWebsite())
                .location(dto.getLocation())
                .build();

        //userID로 조회한 USER 반환
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        //프로필이 존재하는지 체크
        given(profileRepository.existsByUser(user)).willReturn(false);

        //.save()로 db에 저장하는게 아닌 가짜저장기능
        given(profileRepository.save(any(Profile.class))).willReturn(savedProfile);

        // when
        Long profileId = profileService.createProfile(userId, dto);

        // then
        verify(profileRepository).save(profileCaptor.capture());
        Profile captured = profileCaptor.getValue();

        assertAll(
                () -> assertThat(profileId).isEqualTo(savedProfile.getId()),
                () -> assertThat(captured.getUser()).isEqualTo(user),
                () -> assertThat(captured.getBio()).isEqualTo("백엔드 개발자"),
                () -> assertThat(captured.getWebSite()).isEqualTo("https://kakao.com"),
                () -> assertThat(captured.getLocation()).isEqualTo("제주도")
        );

        verify(skillRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("프로필을 수정한다.")
    void updateProfile_success() {
        // given
        Long userId = 1L;
        Long profileId = 100L;

        //유저 정보
        User user = User.builder()
                .id(userId)
                .name("Eric")
                .email("eric@example.com")
                .build();

        //기존 프로필
        Profile profile = Profile.builder()
                .id(profileId)
                .user(user)
                .bio("기존 자기소개")
                .location("Old City")
                .webSite("https://old.com")
                .gitName("oldgit")
                .build();

        //수정된 프로필
        UpdateProfileRequestDto dto = UpdateProfileRequestDto.of(
                "New City",
                "수정된 소개입니다.",
                "https://new.com",
                "newgit",
                "Docker,AWS"
        );

        //profileId로 조회한 프로필을 반환
        given(profileRepository.findById(profileId)).willReturn(Optional.of(profile));

        // when
        profileService.updateProfile(userId, profileId, dto);

        // then
        assertAll(
                () -> assertThat(profile.getLocation()).isEqualTo("New City"),
                () -> assertThat(profile.getBio()).isEqualTo("수정된 소개입니다."),
                () -> assertThat(profile.getWebSite()).isEqualTo("https://new.com"),
                () -> assertThat(profile.getGitName()).isEqualTo("newgit")
        );

        verify(skillRepository).deleteAll(profile.getSkills());
        verify(skillRepository).saveAll(anyList());
    }
    @Test
    @DisplayName("경력을 추가한다.")
    void addExperience_success() {
        // given
        Long profileId = 1L;
        Profile profile = Profile.builder().id(profileId).build();

        //경력
        ExperienceRequestDto dto = ExperienceRequestDto.builder()
                .company("카카오")
                .position("백엔드 개발자")
                .description("광고 플랫폼 개발")
                .startDate(LocalDate.of(2020, 1, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .build();

        //해당 프로필 반환
        given(profileRepository.findById(profileId)).willReturn(Optional.of(profile));

        
        //저장
        profileService.addExperience(profileId, dto);

        verify(experienceRepository).save(experienceCaptor.capture());
        Experience saved = experienceCaptor.getValue();

        //깂 검증
        assertAll(
                () -> assertThat(saved.getCompany()).isEqualTo("카카오"),
                () -> assertThat(saved.getPosition()).isEqualTo("백엔드 개발자"),
                () -> assertThat(saved.getDescription()).isEqualTo("광고 플랫폼 개발"),
                () -> assertThat(saved.getStartDate()).isEqualTo(LocalDate.of(2020, 1, 1)),
                () -> assertThat(saved.getEndDate()).isEqualTo(LocalDate.of(2022, 12, 31)),
                () -> assertThat(saved.getProfile()).isEqualTo(profile)
        );
    }

    @Test
    @DisplayName("경력을 삭제한다.")
    void deleteExperience_success() {
        // given
        Long profileId = 1L;
        Long experienceId = 10L;

        Profile profile = Profile.builder().id(profileId).build();
        Experience experience = Experience.builder().id(experienceId).profile(profile).build();

        //해당 프로필과 경력 반환
        given(profileRepository.findById(profileId)).willReturn(Optional.of(profile));
        given(experienceRepository.findById(experienceId)).willReturn(Optional.of(experience));

        // when
        profileService.deleteExperience(profileId, experienceId);

        // then
        verify(experienceRepository).delete(experience);
    }

    @Test
    @DisplayName("학력을 추가한다.")
    void addEducation_success() {
        // given
        Long profileId = 2L;
        Profile profile = Profile.builder().id(profileId).build();

        EducationRequestDto dto = EducationRequestDto.builder()
                .school("서울대학교")
                .fieldOfStudy("컴퓨터공학")
                .degree("학사")
                .startDate(LocalDate.of(2015, 3, 1))
                .endDate(LocalDate.of(2019, 2, 28))
                .build();

        //해당 프로필 반환
        given(profileRepository.findById(profileId)).willReturn(Optional.of(profile));

        // when
        profileService.addEducation(profileId, dto);

        // then
        verify(educationRepository).save(educationCaptor.capture());
        Education saved = educationCaptor.getValue();

        assertAll(
                () -> assertThat(saved.getSchool()).isEqualTo("서울대학교"),
                () -> assertThat(saved.getFieldOfStudy()).isEqualTo("컴퓨터공학"),
                () -> assertThat(saved.getDegree()).isEqualTo("학사"),
                () -> assertThat(saved.getStartDate()).isEqualTo(LocalDate.of(2015, 3, 1)),
                () -> assertThat(saved.getEndDate()).isEqualTo(LocalDate.of(2019, 2, 28)),
                () -> assertThat(saved.getProfile()).isEqualTo(profile)
        );

    }

    @Test
    @DisplayName("학력을 삭제한다.")
    void deleteEducation_success() {
        // given
        Long profileId = 2L;
        Long educationId = 20L;

        Profile profile = Profile.builder().id(profileId).build();
        Education education = Education.builder().id(educationId).profile(profile).build();

        given(profileRepository.findById(profileId)).willReturn(Optional.of(profile));
        given(educationRepository.findById(educationId)).willReturn(Optional.of(education));

        // when
        profileService.deleteEducation(profileId, educationId);

        // then
        verify(educationRepository).delete(education);
    }


}