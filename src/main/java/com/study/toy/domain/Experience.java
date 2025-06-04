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
@Table(name="experiences")
public class Experience extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 양방향 연관관계 주인
    @JoinColumn(name = "profile_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) // 실제 FK 컬럼 이름
    private Profile profile;

    @Column(name="company")
    private String company;

    @Column(name="position")
    private String position;

    @Column(name="description")
    private String description;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @Builder
    public Experience(Long id, Profile profile, String company, String position, String description, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.profile = profile;
        this.company =company;
        this.position = position;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
