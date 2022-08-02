package dev.yoon.gridgetest.domain.user.repository;


import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialId(String socialId);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByNickname(Nickname nickname);

    Optional<User> findByNicknameOrPhoneNumber(String nickname, String phone);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isAcceptTerms =:isAcceptTerms WHERE u.createTime <:date")
    void updateAcceptTerm(@Param("isAcceptTerms") boolean isAcceptTerms, @Param("date") LocalDate date);

}
