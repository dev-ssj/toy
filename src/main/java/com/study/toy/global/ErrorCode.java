package com.study.toy.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PROFILE_NOT_FOUND(404, "프로필을 찾을 수 없습니다."),
    UNAUTHORIZED(401, "인증이 필요합니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int code;
    private final String message;
}
