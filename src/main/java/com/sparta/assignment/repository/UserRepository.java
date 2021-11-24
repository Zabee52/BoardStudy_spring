package com.sparta.assignment.repository;

import com.sparta.assignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByKakaoId(Long kakaoId);
}
