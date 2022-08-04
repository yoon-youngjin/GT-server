package dev.yoon.gridgetest.domain.admin.dto.users;


import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.Role;
import dev.yoon.gridgetest.domain.user.model.UserState;
import dev.yoon.gridgetest.domain.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetUserDetailRes {

    private Long userId;

    private UserType userType;

    private String nickname;

    private String name;

    private String socialId;

    private String password;

    private String phone;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy.MM.dd", timezone="Asia/Seoul")
    private LocalDate birth;

    private String profileImg;

    private Role role;

    private Boolean isDeleted;

    private String webSite;

    private String description;;

    private Boolean isPrivateUser;

    private UserState userState;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime logInTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime updatedTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdTime;

    @Builder
    public GetUserDetailRes(Long userId, UserType userType, String nickname, String name, String socialId, String password,
                            String phone, LocalDate birth, String profileImg, Role role, Boolean isDeleted, String webSite,
                            String description, Boolean isPrivateUser, UserState userState, LocalDateTime logInTime, LocalDateTime updatedTime, LocalDateTime createdTime) {
        this.userId = userId;
        this.userType = userType;
        this.nickname = nickname;
        this.name = name;
        this.socialId = socialId;
        this.password = password;
        this.phone = phone;
        this.birth = birth;
        this.profileImg = profileImg;
        this.role = role;
        this.isDeleted = isDeleted;
        this.webSite = webSite;
        this.description = description;
        this.isPrivateUser = isPrivateUser;
        this.userState = userState;
        this.logInTime = logInTime;
        this.updatedTime = updatedTime;
        this.createdTime = createdTime;
    }

    public static GetUserDetailRes from(User user) {

        return GetUserDetailRes.builder()
                .userId(user.getId())
                .userType(user.getUserType())
                .nickname(user.getNickname().getValue())
                .name(user.getName().getValue())
                .socialId(user.getSocialId() != null ? user.getSocialId() : null)
                .password(user.getPassword().getValue())
                .phone(user.getPhoneNumber())
                .birth(user.getBirth())
                .profileImg(user.getProfileUrl() != null ? user.getProfileUrl() : null)
                .role(user.getRole())
                .isDeleted(user.getIsDeleted())
                .webSite(user.getWebSite())
                .description(user.getDescription())
                .isPrivateUser(user.getIsPrivateUser())
                .userState(user.getUserState())
                .logInTime(user.getLogInTime())
                .updatedTime(user.getUpdateTime())
                .createdTime(user.getCreateTime())
                .build();
    }
}
