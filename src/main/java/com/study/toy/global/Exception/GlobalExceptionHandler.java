package com.study.toy.global.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.study.toy.dto.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<?>> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(ex.getErrorCode().getCode())
                .body(CommonResponse.of(ex.getErrorCode()));
    }


    //모든 예상치 못한 에러 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleUnhandled(Exception ex) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .body(CommonResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

}