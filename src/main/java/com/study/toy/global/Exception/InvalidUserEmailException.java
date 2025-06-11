package com.study.toy.global.Exception;

public class InvalidUserEmailException extends CustomException{
    public InvalidUserEmailException() {
        super(ErrorCode.INVALID_EMAIL);
    }
}
