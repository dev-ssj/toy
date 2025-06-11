package com.study.toy.global.Exception;

public class InvalidUserPasswordException extends CustomException{
    public InvalidUserPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
