package dev.yoon.gridgetest.domain.board.application;


import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardInfoDto;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.domain.BoardImage;
import dev.yoon.gridgetest.domain.board.domain.Like;
import dev.yoon.gridgetest.domain.board.dto.CreateBoardReq;
import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.board.dto.ReportBoardReq;
import dev.yoon.gridgetest.domain.board.dto.UpdateBoardReq;
import dev.yoon.gridgetest.domain.board.repository.BoardRepository;
import dev.yoon.gridgetest.domain.report.application.ReportService;
import dev.yoon.gridgetest.domain.report.entity.Report;
import dev.yoon.gridgetest.domain.report.exception.CantReportMySelfException;
import dev.yoon.gridgetest.domain.report.model.ServiceType;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.AuthenticationException;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import dev.yoon.gridgetest.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    @Value("${s3.gtc.path}")
    private String PATH;

    private final UserService userService;
    private final BoardRepository boardRepository;
    private final LikeService likeService;

    private final S3Uploader s3Uploader;


    @Transactional
    public void createBoard(CreateBoardReq request, String phone) {

        // 유저 조회
        User user = userService.getUserByPhoneNumber(phone);

        // 글 생성
        Board board = request.toEntity();
        Board saveBoard = Board.createBoard(board, user);

        // 이미지 생성 및 저장
        request.getBoardImages().forEach(multipartFile -> {

            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            BoardImage boardImage = BoardImage.createBoardImage(board, imageName, url);
            saveBoard.addBoardImage(boardImage);

        });

        // 글 저장
        boardRepository.save(saveBoard);

        log.info("[피드 생성]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());

    }

    public Slice<GetMainBoardRes> getMainBoardList(Pageable pageable, String phone) {

        // 유저 조회
        User user = userService.getUserByPhoneNumber(phone);

        log.info("[피드 조회]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());
        return boardRepository.findMainBoard(pageable, user);
    }


    @Transactional
    public void addOrDeleteLike(Long boardId, String phone) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BOARD_NOT_FOUND));
        User user = userService.getUserByPhoneNumber(phone);

        if (likeService.existsUser(board, user)) {

            likeService.deleteLike(board, user);
            log.info("[피드 좋아요 취소]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());
        } else {
            Like like = Like.createLike(board, user);
            board.addLike(like);
            likeService.addLike(like);
            log.info("[피드 좋아요 추가]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());

        }
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BOARD_NOT_FOUND));

    }

    public Long getBoardCountByUser(User user) {

        return boardRepository.countBoardByUser(user);
    }


    public Slice<Board> getBoardByUser(Pageable pageable,User user) {

        return boardRepository.findAllByUser(pageable, user);

    }

    @Transactional
    public void updateBoard(UpdateBoardReq request, String phone) {

        User user = userService.getUserByPhoneNumber(phone);
        Board board = getBoardById(request.getBoardId());

        // 유저 검증
        if (user != board.getUser()) {
            throw new AuthenticationException(ErrorCode.BOARD_USER_NOT_WRITER);
        }


        board.updateContent(request.getContent());
        log.info("[피드 수정]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());

    }



    @Transactional
    public void deleteBoard(Long boardId, String phone) {

        User user = userService.getUserByPhoneNumber(phone);
        Board board = getBoardById(boardId);

        if (user != board.getUser()) {
            throw new AuthenticationException(ErrorCode.BOARD_USER_NOT_WRITER);
        }
        board.deleteBoard();
        log.info("[피드 삭제]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());

    }

    @Transactional
    public void deleteBoardByAdmin(Long boardId) {
        Board board = getBoardById(boardId);
        board.deleteBoard();

    }

    public Page<Board> getAllBoardByQuery(Pageable pageable, GetBoardInfoDto.Request request) {
        return boardRepository.findAllBoardByQuery(pageable, request);

    }
}
