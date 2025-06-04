package com.study.toy.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="skills")
public class Skill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 양방향 연관관계 주인
    @JoinColumn(name="profile_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) // 실제 FK 컬럼 이름
    private Profile profile;

    @Column(name="name")
    private String name;

    @Builder
    public Skill(Long id, Profile profile, String name){
        this.id = id;
        this.profile = profile;
        this.name = name;
    }
}
