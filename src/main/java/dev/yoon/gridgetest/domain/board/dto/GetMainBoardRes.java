package dev.yoon.gridgetest.domain.board.dto;

import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GetMainBoardRes {

    private Long boardId;

    private String content;

    private List<String> imageUrls;

    private Long userId;

    private String userNickname;

    private String userProfileUrl;

    private Boolean likeUser;

    private Integer likeCnt;

    private Integer answerCnt;

    private String createTime;

    @Builder
    public GetMainBoardRes(Long boardId, String content, List<String> imageUrls, Long userId,
                           String userNickname, String userProfileUrl, Boolean likeUser, Integer likeCnt,
                           Integer answerCnt, String createTime) {
        this.boardId = boardId;
        this.content = content;
        this.imageUrls = imageUrls;
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileUrl = userProfileUrl;
        this.likeUser = likeUser;
        this.likeCnt = likeCnt;
        this.answerCnt = answerCnt;
        this.createTime = createTime;
    }



    public static GetMainBoardRes of(Board board, User user) {

        List<String> imgUrls = board.getBoardImages().stream().map(boardImage -> boardImage.getImageUrl()).collect(Collectors.toList());

        GetMainBoardRes response = GetMainBoardRes.builder()
                .boardId(board.getId())
                .content(board.getContent())
                .imageUrls(imgUrls)
                .userId(board.getUser().getId())
                .userNickname(board.getUser().getNickname())
                .userProfileUrl(board.getUser().getProfileUrl())
                .likeUser(board.getLikeList().stream().filter(like ->
                        like.getUser() == user).count() != 0)
                .likeCnt(board.getLikeList().size())
                .answerCnt(board.getAnswers().size())
                .createTime(DateTimeUtils.convertToLocalDatetimeToTime(board.getCreateTime()))
                .build();


        return response;

    }

}
