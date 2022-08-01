package dev.yoon.gridgetest.domain.answer.application;

import dev.yoon.gridgetest.domain.answer.domain.AnswerLike;
import dev.yoon.gridgetest.domain.answer.dto.GetAnswerRes;
import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.answer.dto.CreateAnswerReq;
import dev.yoon.gridgetest.domain.answer.dto.CreateReplyReq;
import dev.yoon.gridgetest.domain.answer.repository.AnswerRepository;
import dev.yoon.gridgetest.domain.board.domain.Like;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final UserService userService;
    private final BoardService boardService;
    private final AnswerRepository answerRepository;
    private final AnswerLikeService answerLikeService;


    @Transactional
    public void createAnswer(Long boardId, CreateAnswerReq request, String phone) {

        Board board = boardService.getBoardById(boardId);
        User user = userService.getUserByPhoneNumber(phone);

        Answer answer = request.toEntity();
        Answer saveAnswer = Answer.createAnswer(user, board, answer);

        answerRepository.save(saveAnswer);
    }

    @Transactional
    public void createReply(Long answerId, CreateReplyReq request, String phone) {

        User user = userService.getUserByPhoneNumber(phone);
        Answer answer = getAnswerById(answerId);

        Answer reply = request.toEntity();
        Answer saveReply = Answer.createAnswer(user, answer.getBoard(), reply);
        saveReply.setParent(answer);

        answerRepository.save(saveReply);

    }




    public GetAnswerRes getAnswerByBoard(Pageable pageable, String phone, Long boardId) {
        User user = userService.getUserByPhoneNumber(phone);
        Board board = boardService.getBoardById(boardId);

        Slice<Answer> answers = answerRepository.findAnswersByBoard(pageable, user, board);

        return GetAnswerRes.of(board, answers, user, pageable);

    }

    @Transactional
    public void addOrDeleteLike(Long answerId, String phone) {

        Answer answer = getAnswerById(answerId);
        User user = userService.getUserByPhoneNumber(phone);

        if (answerLikeService.existsUser(answer, user)) {
            answerLikeService.deleteLike(answer, user);
        }else {
            AnswerLike answerLike = AnswerLike.createLike(answer, user);
            answer.addLike(answerLike);

            answerLikeService.addLike(answerLike);
        }

    }

    public Answer getAnswerById(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ANSWER_NOT_FOUND));

    }
}
