package dev.yoon.gridgetest.domain.board.application;


import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.domain.Like;
import dev.yoon.gridgetest.domain.board.repository.LikeRepository;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;

    public Boolean existsUser(Board board, User user) {
        return likeRepository.existsByBoardAndUser(board, user);

    }


    public void deleteLike(Board board, User user) {
        Like like = likeRepository.findByBoardAndUser(board, user)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.LIKE_NOT_FOUND));

        likeRepository.delete(like);

    }

    public void addLike(Like like) {
        likeRepository.save(like);
    }
}
