package dev.yoon.gridgetest.domain.user.application.info;

import dev.yoon.gridgetest.domain.admin.dto.users.GetUserInfoDto;
import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.follow.application.FollowService;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.dto.info.*;
import dev.yoon.gridgetest.domain.user.exception.NotEqualsCheckPasswordException;
import dev.yoon.gridgetest.domain.user.exception.NotEqualsPhoneException;
import dev.yoon.gridgetest.domain.user.model.Nickname;
import dev.yoon.gridgetest.domain.user.repository.UserRepository;
import dev.yoon.gridgetest.domain.user.validator.UserValidator;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import dev.yoon.gridgetest.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {

    @Value("${s3.users.path}")
    private String PATH;

    private final UserService userService;
    private final BoardService boardService;
    private final UserValidator userValidator;
    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;
    private final FollowService followService;

    public GetUserInfoRes getUserInfo(String phone) {

        User user = userService.getUserByPhoneNumber(phone);

        // 작성한 글 개수
        Long boardCnt = boardService.getBoardCountByUser(user);

        // 팔로워 개수
        Long followerCnt = followService.getFollowerCount(user);

        // 팔로잉 개수
        Long followingCnt = followService.getFollowingCount(user);

        return GetUserInfoRes.of(user, boardCnt, followerCnt, followingCnt);

    }

    @Transactional
    public void updatePassword(UpdatePasswordReq request, String phone) {

        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new NotEqualsCheckPasswordException(ErrorCode.NOT_EQUALS_CHECK_PASSWORD);
        }
        User user = userService.getUserByPhoneNumber(phone);
        user.updatePassword(request.getPassword());

        log.info("[유저 비밀번호 변경]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());

    }

    // TODO 테스트
    @Scheduled(cron = "0 0 0 * * *") // 매일 0시
    @Transactional
    public void updateClosedDebateStatus() {
        userRepository.updateAcceptTerm(false, LocalDate.now().minusYears(1));

    }


    public void checkUser(CheckUserReq request, String phone) {

        if (!request.getPhone().equals(phone)) {
            throw new NotEqualsPhoneException(ErrorCode.NOT_EQUALS_PHONE);
        }

    }

    public void checkUpdateName(String phone) {
        User user = userService.getUserByPhoneNumber(phone);
        user.getName().isValidUpdate();
    }

    public void checkUpdateNickname(CheckUpdateNicknameReq request, String phone) {
        User user = userService.getUserByPhoneNumber(phone);

        // 2주 이내에 2번 닉네임을 변경하였는지 검사
        user.getNickname().isValidUpdate();

        // 닉네임 중복 검사
        userValidator.validateDuplicateNickname(request.getNickname());

    }


    @Transactional
    public void updateUserInfo(UpdateUserInfoReq request, String phone) {
        String url = "";

        if (request.getProfileImg() != null) {
            String imageName = s3Uploader.uploadFile(request.getProfileImg(), PATH);
            url = s3Uploader.getUrl(PATH, imageName);
        }

        User user = userService.getUserByPhoneNumber(phone);
        User updateUser = request.toEntity(url);

        user.updateUserInfo(updateUser);

        log.info("[유저 정보 변경]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());


    }

    @Transactional
    public void updatePrivate(String phone) {
        User user = userService.getUserByPhoneNumber(phone);
        user.updatePrivate();

        log.info("[유저 비활성화]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());


    }

    @Transactional
    public void quitUser(String phone) {
        User user = userService.getUserByPhoneNumber(phone);
        userService.quitUser(user);

        log.info("[유저 탈퇴]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());

    }



    public Page<User> getAllUsersByQuery(Pageable pageable, GetUserInfoDto.Request request) {
        return userRepository.findAllUsersByQuery(pageable, request);

    }


}
