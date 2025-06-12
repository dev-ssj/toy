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
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        return CommonResponse.setResponse(CommonResponse.of(ex.getErrorCode()));
    }

    // 잘못된 파라미터나 값이 들어왔을 때 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return CommonResponse.setResponse(CommonResponse.of(ErrorCode.BAD_REQUEST, ex.getMessage()));
    }

    //모든 예상치 못한 에러 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnhandled(Exception ex){
        return CommonResponse.setResponse(CommonResponse.of(ErrorCode.INTERNAL_SERVER_ERROR,"알 수 없는 에러가 발생했습니다."));
    }
}