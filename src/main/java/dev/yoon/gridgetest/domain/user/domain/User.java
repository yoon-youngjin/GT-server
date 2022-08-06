package dev.yoon.gridgetest.domain.user.domain;

import dev.yoon.gridgetest.domain.base.BaseTimeEntity;
import dev.yoon.gridgetest.domain.user.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET is_deleted = true, user_state = 'INACTIVE' WHERE user_id=?")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long Id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserType userType;

    @Embedded
    private Nickname nickname;

    @Embedded
    private Name name;

    @Column(nullable = false)
    private String profileUrl;

    @Column(length = 20, unique = true)
    private String socialId;

    @Embedded
    private Password password;

    @Embedded
    private Email email;

    @Column(unique = true, length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 10, nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(30) default 'ROLE_USER'")
    private Role role;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Boolean isDeleted;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Boolean isAcceptTerms;

    private String webSite;

    private String description;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Boolean isPrivateUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserState userState;

    private LocalDateTime logInTime;

    @Builder
    public User(UserType userType, Email email, Password password, String socialId, String webSite, String description,
                String profileUrl, Nickname nickname, Name name, String phoneNumber, LocalDate birth) {

        this.userType = userType;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.socialId = socialId;
        this.role = Role.ROLE_USER;
        this.isDeleted = false;
        this.isAcceptTerms = true;
        this.isPrivateUser = false;
        this.webSite = webSite;
        this.description = description;
        this.profileUrl = profileUrl;
        this.userState = UserState.ACTIVE;
        this.logInTime = LocalDateTime.now();

    }

    public void updatePassword(String password) {
        this.password.changePassword(password);
    }

    public void updateUserInfo(User user) {
        this.name.changeName(user.getName().getValue());
        this.nickname.changeNickname(user.getNickname().getValue());
        this.webSite = user.getWebSite();
        this.description = user.getDescription();
    }

    public void updatePrivate() {
        this.isPrivateUser = !this.isPrivateUser;
    }

    public void updateLoginTime() {
        logInTime = LocalDateTime.now();
    }

    public void updateUserState(UserState state) {
        userState = state;
    }
}
