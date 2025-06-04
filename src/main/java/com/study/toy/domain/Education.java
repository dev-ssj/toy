package com.study.toy.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="educations")
public class Education extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 양방향 연관관계 주인
    @JoinColumn(name = "profile_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))   // name:실제 연관된 컬럼 이름, 양방향 연관관계 설정하되, FK는 생성안되도록 지정
    private Profile profile;

    @Column(name="school")
    private String school;

    @Column(name="degree")
    private String degree;

    @Column(name="field_of_study")
    private String fieldOfStudy;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @Builder
    public Education(Long id, Profile profile, String school, String degree, String fieldOfStudy, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.profile = profile;
        this.school =school;
        this.degree = degree;
        this.fieldOfStudy =fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
