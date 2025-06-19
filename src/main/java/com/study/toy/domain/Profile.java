package com.study.toy.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name="profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name="location")
    private String location;

    @Column(name="bio")
    private String bio;

    @Column(name="website")
    private String webSite;

    @Column(name="gitname")
    private String gitName;

    /* 1개의 프로필은 여러개의 스킬, 경력, 학력을 가질 수 있다. 프로필이 1, 스킬,경력,학력이 N
    * 프로필 조회 시, 학력, 경력, 스킬을 함께 보여 주기 떄문에 양방향 연관관계로 설계.
    * mappedBy로 설정했으므로 연관관계의 주인이 아님. 즉, 읽기만 가능
     */
    @OneToMany(mappedBy = "profile",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "profile",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "profile",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    @Builder
    public Profile(User user, String location, String bio, String webSite, String gitName){
        this.user = user;
        this.location = location;
        this.bio = bio;
        this.webSite =webSite;
        this.gitName = gitName;
    }

    //프로필 수정용
    public void update(String location, String bio, String website, String gitName) {
        this.location = location;
        this.bio = bio;
        this.webSite = website;
        this.gitName = gitName;
    }
}
