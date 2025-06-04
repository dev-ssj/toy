package com.study.toy.service;

import com.study.toy.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    
    //기본 메시지 반환
    public <T> CommonResponse<T> success (T data){
        return CommonResponse.<T> builder()
                .status(HttpStatus.OK.value())
                .message("요청에 성공했습니다.")
                .data(data)
                .build();
    }

    public <T> CommonResponse<T> success(String message, T data){
        return CommonResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    //에러반환
    public CommonResponse<Void> error(int status, String message) {
        return CommonResponse.<Void>builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }
}
