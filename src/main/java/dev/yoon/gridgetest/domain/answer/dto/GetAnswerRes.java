package dev.yoon.gridgetest.domain.answer.dto;

import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class GetAnswerRes {

    private Long userId;

    private String userNickname;

    private String userProfileUrl;

    private Long boardId;

    private String content;

    private String createTime;

    private Slice<GetAnswerHistRes> commentList;

    @Builder
    public GetAnswerRes(Long userId, String userNickname, String userProfileUrl, Long boardId,
                        String content, String createTime, Slice<GetAnswerHistRes> commentList) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileUrl = userProfileUrl;
        this.boardId = boardId;
        this.content = content;
        this.createTime = createTime;
        this.commentList = commentList;
    }

    public static GetAnswerRes of(Board board, Slice<Answer> answers, User me, Pageable pageable) {

        List<GetAnswerHistRes> answerHistDtos = answers.stream().map(
                answer -> GetAnswerHistRes.of(answer, me)
        ).collect(Collectors.toList());

        return GetAnswerRes.builder()
                .userId(board.getUser().getId())
                .userNickname(board.getUser().getNickname())
                .userProfileUrl(board.getUser().getProfileUrl())
                .boardId(board.getId())
                .content(board.getContent())
                .createTime(DateTimeUtils.convertToLocalDatetimeToTime(board.getCreateTime()))
                .commentList(new SliceImpl<>(answerHistDtos, pageable, answers.hasNext()))
                .build();
    }


    @Getter
    @Setter
    public static class GetAnswerHistRes {

        private Long userId;

        private String userProfileUrl;

        private String userNickname;

        private Long answerId;

        private String comment;

        private String createTime;

        private Integer replyCnt;

        private Boolean likeUser;

        @Builder
        public GetAnswerHistRes(Long userId, String userProfileUrl, String userNickname, Long answerId,
                                String comment, String createTime, Integer replyCnt, Boolean likeUser) {
            this.userId = userId;
            this.userProfileUrl = userProfileUrl;
            this.userNickname = userNickname;
            this.answerId = answerId;
            this.comment = comment;
            this.createTime = createTime;
            this.replyCnt = replyCnt;
            this.likeUser = likeUser;
        }

        private static GetAnswerHistRes of(Answer answer, User me) {

            return GetAnswerHistRes.builder()
                    .userId(answer.getUser().getId())
                    .userProfileUrl(answer.getUser().getProfileUrl())
                    .userNickname(answer.getUser().getNickname())
                    .answerId(answer.getId())
                    .comment(answer.getComment())
                    .replyCnt(answer.getReplies().size())
                    .createTime(DateTimeUtils.convertToLocalDatetimeToTime(answer.getCreateTime()))
                    .likeUser(answer.getLikeList().stream().filter(like ->
                            like.getUser() == me).count() != 0)
                    .build();
        }
    }


}
