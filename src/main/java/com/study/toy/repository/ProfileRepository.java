package com.study.toy.repository;

import com.study.toy.domain.Profile;
import com.study.toy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);

    boolean existsByUser(User user); //프로필이 존재하는지

    Optional<Profile> findByUserId(Long userId);
    List<Profile> findByUserIdIn(List<Long> userIds);
}
