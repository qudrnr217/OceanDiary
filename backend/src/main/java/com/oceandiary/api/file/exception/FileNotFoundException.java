package com.oceandiary.api.file.exception;

import com.oceandiary.api.common.exception.EntityNotFoundException;

public class FileNotFoundException extends EntityNotFoundException {
    public FileNotFoundException() {
        super("존재하지 않는 파일입니다.");
    }
}
