package com.study.toy.domain;

import com.study.toy.global.Exception.CustomException;
import com.study.toy.global.Exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="profile_image_url")
    private String profileImageUrl;

    @Builder
    public User(Long id, String name, String email, String password, String profileImageUrl){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password =password;
        this.profileImageUrl = profileImageUrl;
    }

    public void checkPassword(String Password, BCryptPasswordEncoder bCryptPasswordEncoder) {
        if (!bCryptPasswordEncoder.matches(Password, this.password)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

}
