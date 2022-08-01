package dev.yoon.gridgetest.domain.board.application;


import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.domain.BoardImage;
import dev.yoon.gridgetest.domain.board.domain.Like;
import dev.yoon.gridgetest.domain.board.dto.CreateBoardReq;
import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.board.repository.BoardRepository;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import dev.yoon.gridgetest.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    }

    public Slice<GetMainBoardRes> getMainBoardList(Pageable pageable, String phone) {

        // 유저 조회
        User user = userService.getUserByPhoneNumber(phone);

        return boardRepository.findMainBoard(pageable, user);
    }


    @Transactional
    public void addOrDeleteLike(Long boardId, String phone) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BOARD_NOT_FOUND));
        User user = userService.getUserByPhoneNumber(phone);

        if (likeService.existsUser(board, user)) {
            likeService.deleteLike(board, user);
        }else {
            Like like = Like.createLike(board, user);
            board.addLike(like);

            likeService.addLike(like);
        }
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BOARD_NOT_FOUND));

    }

    public Long getBoardCountByUser(User user) {

        return boardRepository.countBoardByUser(user);
    }

    public List<Board> getBoardByUser(User user) {
        return boardRepository.findAllByUser(user);

    }
}
