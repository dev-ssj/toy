package com.study.toy.dto;

import com.study.toy.global.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class CommonResponse<T> {

    public static final int SUCCESS_CODE = 200;

    private int code;
    private String message;
    private T response;

    public CommonResponse(T response) {
        this.code = SUCCESS_CODE;
        this.message = "success";
        this.response = response;
    }

    public CommonResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.response = null;
    }

    public CommonResponse(final ErrorCode code, final String message) {
        this.message = message;
        this.response = null;
    }

    @Builder
    public CommonResponse(int code, String message, T response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public static CommonResponse of(final ErrorCode code) {
        return new CommonResponse(code);
    }

    public static CommonResponse of(final ErrorCode code, final String message) {
        return new CommonResponse(code, message);
    }

    public static ResponseEntity<?> setResponse(Object data) {
        return ResponseEntity.ok()
                .body(data);
    }
    public static ResponseEntity<?> setResponse() {
        return ResponseEntity.ok()
                .body("ok");
    }
}