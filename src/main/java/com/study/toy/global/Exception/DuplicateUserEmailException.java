package com.study.toy.global.Exception;

public class DuplicateUserEmailException extends CustomException{
    public DuplicateUserEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
