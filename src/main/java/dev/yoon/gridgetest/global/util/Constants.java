package dev.yoon.gridgetest.global.util;

public class Constants {

    private Constants() {
        throw new AssertionError();
    }

    public static final Integer SET_PAGE_ITEM_MAX_COUNT = 10;

    // 유저
    public static final String GET_USER_BOARD = "유저 피드 조회에 성공하였습니다.";
    public static final String GET_USER_INFO = "유저 조회에 성공하였습니다.";
    public static final String CHECK_USER_BY_PHONE = "유저 검증에 성공하였습니다.";
    public static final String UPDATE_PASSWORD = "유저 비밀번호 변경에 성공하였습니다.";
    public static final String CHECK_UPDATE_TIME_NAME = "유저 이름 변경 2주 검증에 성공하였습니다.";
    public static final String CHECKU_UPDATE_TIME_NICKNAME = "유저 닉네임 변경 2주 검증에 성공하였습니다.";
    public static final String UPDATE_USER_INFO = "유저 정보 변경에 성공하였습니다.";
    public static final String UPDATE_PRIVATE_USER = "비공개 또는 공개 유저 변경에 성공하였습니다.";
    public static final String QUIT_USER = "유저 탈퇴에 성공하였습니다.";
    public static final String LOGIN = "유저 로그인에 성공하였습니다.";
    public static final String OAUTH_LOGIN = "유저 소셜 로그인에 성공하였습니다.";
    public static final String OAUTH_SIGN_UP = "유저 소셜 회원가입에 성공하였습니다.";
    public static final String SIGN_UP = "유저 회원가입에 성공하였습니다.";
    public static final String SEND_SMS = "유저 인증메세지 전송 성공하였습니다.";
    public static final String CHECK_CODE = "유저 인증코드 검증에 성공하였습니다.";
    public static final String CHECK_NICKNAME = "유저 중복 닉네임 검증에 성공하였습니다.";
    public static final String CHECK_PHONE = "유저 중복 번호 검색에 성공하였습니다.";

    // 메시지
    public static final String SEND_MESSAGE = "메시지 전송에 성공하였습니다.";
    public static final String GET_NOTIFICATION = "메시지 조회에 성공하였습니다.";

    // 피드
    public static final String CREATE_BOARD = "피드 생성에 성공하였습니다.";
    public static final String GET_MAIN_BOARD = "피드 조회에 성공하였습니다.";
    public static final String ADD_OR_DELETE_LIKE = "좋아요 추가 또는 취소에 성공하였습니다.";
    public static final String UPDATE_BOARD = "피드 수정에 성공하였습니다.";
    public static final String DELETE_BOARD = "피드 삭제에 성공하였습니다.";


    // 댓글
    public static final String CREATE_ANSWER = "댓글 생성에 성공하였습니다.";
    public static final String GET_ANSWER = "댓글 조회에 성공하였습니다.";
    public static final String CREATE_REPLY = "대댓글 생성에 성공하였습니다.";
    public static final String DELETE_ANSWER = "댓글 삭제에 성공하였습니다.";

    // 팔로우
    public static final String FOLLOW = "팔로우 요청 또는 취소에 성공하였습니다.";
    public static final String GET_REQUEST_FOLLOW = "팔로우 조회에 성공하였습니다.";
    public static final String ACCEPT_FOLLOW = "팔로우 수락에 성공하였습니다.";
    public static final String REJECT_FOLLOW = "팔로우 거절에 성공하였습니다.";


    // 신고
    public static final String REPORT_BOARD = "피드 신고에 성공하였습니다.";
    public static final String REPORT_ANSWER = "댓글 신고에 성공하였습니다.";

    // 인증 - 피드
    public static final String GET_ALL_BOARD = "피드 전체 조회에 성공하였습니다.";
    public static final String GET_BOARD_DETAIL_INFO = "피드 상세 조회에 성공하였습니다.";
    public static final String FORCE_DELETE_BOARD = "피드 강제 삭제에 성공하였습니다.";

    // 인증 - 유저
    public static final String GET_ALL_USER_INFO = "유저 전체 조회에 성공하였습니다.";
    public static final String GET_USER_DETAIL_INFO = "유저 상세 조회에 성공하였습니다.";
    public static final String PAUSE_USER = "유저 정지에 성공하였습니다.";

    // 인증 - 신고
    public static final String GET_ALL_REPORT = "신고 전체 조회에 성공하였습니다.";
    public static final String DELETE_REPORT = "신고 삭제 성공하였습니다.";

    // 인증 - 로그
    public static final String GET_USER_LOG = "유저 로그 전체 조회에 성공하였습니다.";
    public static final String GET_BOARD_LOG = "피드 로그 전체 조회에 성공하였습니다.";
    public static final String GET_REPORT_LOG = "신고 로그 전체 조회에 성공하였습니다.";
    public static final String GET_ANSWER_LOG = "댓글 로그 전체 조회에 성공하였습니다.";










}
