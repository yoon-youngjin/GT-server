package dev.yoon.gridgetest.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 인증
    ALREADY_REGISTERED_USER(400, "이미 가입된 회원 입니다."),
    MISMATCHED_PASSWORD(401, "패스워드가 일치하지 않습니다."),
    LOGIN_ERROR(401, "비밀번호를 잘못 입력하셨습니다."),
    QUIT_USER(401,"탈퇴한 회원입니다."),
    DUPLICATE_PHONE_NUMBER(400, "이미 사용 중인 전화번호입니다."),
    DUPLICATE_NICKNAME(400, "이미 사용 중인 닉네임입니다."),
    DUPLICATE_KEYWORD(400, "중복된 키워드가 포함되어 있습니다."),

    INVALID_FILTER_TYPE(401, "잘못된 필터 타입 입니다.(ex. orderBy : VIEW_COUNT)"),
    INVALID_USER_TYPE(401, "잘못된 회원 타입 입니다.(ex. userType : KAKAO)"),
    INVALID_MBTI_TYPE(401, "잘못된 MBTI 타입 입니다.(ex. mbti : INFP)"),

    NOT_EXISTS_AUTHORIZATION(401, "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(401, "인증 타입이 Bearer 타입이 아닙니다."),
    NOT_VALID_TOKEN(401, "유효하지 않은 토큰 입니다."),
    ACCESS_TOKEN_EXPIRED(401, "해당 access token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(401, "tokenType이 access token이 아닙니다."),
    REFRESH_TOKEN_EXPIRED(401, "해당 refresh token은 만료됐습니다."),
    REFRESH_TOKEN_NOT_FOUND(400, "해당 refresh token은 존재하지 않습니다."),

    // 회원
    USER_NOT_FOUND(400, "해당 회원은 존재하지 않습니다."),
    NOT_MATCH_USER_TYPE(400, "소셜 타입이 일치하지 않습니다."),
    IS_QUIT_USER(400, "탈퇴한 유저입니다."),
    NOT_EQUALS_PHONE(400, "회원의 전화번호와 일치하지 않습니다."),
    NOT_EQUALS_CHECK_PASSWORD(400, "비밀번호가 서로 일치하지 않습니다"),

    // 인증 코드
    AUTH_CODE_NOT_FOUND(400, "해당 인증 코드를 찾을 수 없습니다."),
    AUTH_CODE_NOT_EQUAL(400, "인증 코드가 일치하지 않습니다. "),

    // 답변
    ANSWER_NOT_FOUND(400, "해당 답변을 찾을 수 없습니다."),
    REPLY_USER_NOT_WRITER(403, "해당 답변의 댓글에 대한 작성자가 아닙니다."),
    USER_NOT_WRITER(403, "해당 글에 대한 작성자가 아닙니다." ),

    PROCEED_WITH_SIGNUP(300, "회원가입을 진행해주세요."),

    // 피드
    BOARD_NOT_FOUND(401, "해당 글을 찾을 수 없습니다."),
    BOARD_USER_NOT_WRITER(403, "해당 글에 대한 작성자가 아닙니다." ),
    LIKE_NOT_FOUND(401, "해당 좋아요를 찾을 수 없습니다."),

    // 신고
    CANT_REPORT_MYSELF(400, "자신의 게시물은 신고할 수 없습니다."),
    REPORT_NOT_FOUND(401, "해당 신고를 찾을 수 없습니다."),

    // 메시지
    CANT_SEND_ME_MESSAGE(400, "자신에게는 메일을 보낼 수 없습니다."),

    // 변경
    CANT_CHANGE_2_WEEKS(400, "2주 이내에 비밀번호를 2번까지 변경할 수 있습니다."),

    FAILED_TO_SEND_SMS(400, "문자 전송에 실패했습니다."),

    // 팔로우
    FOLLOW_NOT_FOUND(400, "해당 팔로우를 찾을 수 없습니다."),
    EXISTS_FOLLOW(400, "이미 팔로우가 존재합니다."),

    // 인가
    FORBIDDEN_ADMIN(403, "관리자 권한이 없습니다."),

    // 파일
    FILE_IO(400, "파일 처리를 실패했습니다.");



    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;

}

