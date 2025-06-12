package com.study.toy.global.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(400, "잘못된 요청입니다."),
    INVALID_PASSWORD(401, "비밀번호가 맞지않습니다"),
    INVALID_EMAIL(401, "등록된 이메일이 없습니다."),
    DUPLICATE_EMAIL(401, "이미 등록된 이메일입니다."),
    PROFILE_NOT_FOUND(404, "프로필을 찾을 수 없습니다."),
    UNAUTHORIZED(401, "인증이 필요합니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int code;
    private final String message;
}
