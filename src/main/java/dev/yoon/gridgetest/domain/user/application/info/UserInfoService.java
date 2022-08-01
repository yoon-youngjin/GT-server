package dev.yoon.gridgetest.domain.user.application.info;

import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.dto.info.GetUserInfoRes;
import dev.yoon.gridgetest.domain.user.repository.UserRepository;
import dev.yoon.gridgetest.domain.user.validator.UserValidator;
import dev.yoon.gridgetest.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

    public GetUserInfoRes getUserInfo(String phone) {

        User user = userService.getUserByPhoneNumber(phone);

        // 작성한 글 개수
        Long boardCnt = boardService.getBoardCountByUser(user);

        // 팔로우 개수

        // 팔로잉 개수

        return GetUserInfoRes.of(user, boardCnt);

    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시
    @Transactional
    public void updateClosedDebateStatus() {
        userRepository.updateAcceptTerm(false, LocalDate.now().minusYears(1));

    }

}
