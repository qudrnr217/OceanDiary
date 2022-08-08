package com.oceandiary.api.room.exception;

import javax.persistence.EntityNotFoundException;

public class PasswordNotValidException extends EntityNotFoundException {
    public PasswordNotValidException() {
        super("비밀번호가 유효하지 않습니다.");
    }
}
