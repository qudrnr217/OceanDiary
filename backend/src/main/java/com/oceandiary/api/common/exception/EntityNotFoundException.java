package com.oceandiary.api.common.exception;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
