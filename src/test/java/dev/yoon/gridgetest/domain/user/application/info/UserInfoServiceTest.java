package dev.yoon.gridgetest.domain.user.application.info;

import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.Birth;
import dev.yoon.gridgetest.domain.user.model.Email;
import dev.yoon.gridgetest.domain.user.model.Password;
import dev.yoon.gridgetest.domain.user.model.UserType;
import dev.yoon.gridgetest.domain.user.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class UserInfoServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user1 = User.createUser(User.builder()
                .userType(UserType.GENERAL)
                .birth(Birth.toBirth("1997년 12월 15일"))
                .email(Email.from("dudwls143@gmail.com"))
                .nickname("yoon")
                .password(Password.from("password"))
                .phoneNumber("01064145948")
                .socialId(null)
                .name("yoon")
                .build());

        User user2 = User.createUser(User.builder()
                .userType(UserType.GENERAL)
                .birth(Birth.toBirth("1997년 12월 15일"))
                .email(Email.from("dudwls14@gmail.com"))
                .nickname("yoon1")
                .password(Password.from("password"))
                .phoneNumber("0106414598")
                .socialId(null)
                .name("yoon")
                .build());

        user1.setCreateTime(LocalDateTime.now().minusMonths(10));
        user2.setCreateTime(LocalDateTime.now().minusYears(2));

        userRepository.save(user1);
        userRepository.save(user2);

    }

    @Test
    public void test() {
        userRepository.updateAcceptTerm(false, LocalDate.now().minusYears(1));
    }
}