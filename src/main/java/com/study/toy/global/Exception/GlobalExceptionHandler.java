package com.study.toy.global.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.study.toy.dto.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        return CommonResponse.setResponse(CommonResponse.of(ex.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnhandled(Exception ex) {
        return CommonResponse.setResponse(CommonResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}