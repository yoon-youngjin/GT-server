package dev.yoon.gridgetest.domain.user.domain;

import dev.yoon.gridgetest.domain.base.BaseTimeEntity;
import dev.yoon.gridgetest.domain.user.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
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

    private String profileUrl;

    @Column(length = 20, unique = true)
    private String socialId;

    @Embedded
    private Password password;

    @Embedded
    private Email email;

    @Column(length = 20, unique = true)
    private String phoneNumber;

    @Embedded
    private Birth birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(30) default 'ROLE_USER'")
    private Role role;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    // 1년 마다 갱신
    private Boolean isAcceptTerms;

    private String webSite;

    private String description;

    private Boolean isPrivateUser;

    @Builder
    public User(UserType userType, Email email, Password password, String socialId, String webSite, String description,
                String profileUrl, Nickname nickname, Name name, String phoneNumber, Birth birth) {

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

    }


    public static User createUser(User user) {
        return User.builder()
                .userType(user.getUserType())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .name(user.getName())
                .phoneNumber(user.phoneNumber)
                .birth(user.birth)
                .build();
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

    public void quit() {
        this.isDeleted = true;
    }
}
