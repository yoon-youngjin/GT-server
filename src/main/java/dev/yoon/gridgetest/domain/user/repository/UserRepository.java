package dev.yoon.gridgetest.domain.user.repository;


import dev.yoon.gridgetest.domain.user.model.Email;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialId(String socialId);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByNickname(String nickname);

    Optional<User> findByNicknameOrPhoneNumber(String nickname, String phone);

}
