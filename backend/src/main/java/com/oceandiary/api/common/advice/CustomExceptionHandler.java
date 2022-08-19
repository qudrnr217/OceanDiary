package com.oceandiary.api.common.advice;

import com.oceandiary.api.common.exception.BusinessException;
import com.oceandiary.api.common.exception.PermissionException;
import com.oceandiary.api.room.exception.PasswordNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException ex) {
        ErrorResponse response = ErrorResponse.build("400", ex);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ErrorResponse> permissionExceptionHandler(PermissionException ex) {
        ErrorResponse response = ErrorResponse.build("401", ex);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordNotValidException.class)
    public ResponseEntity<ErrorResponse> passwordExceptionHandler(PasswordNotValidException ex) {
        ErrorResponse response = ErrorResponse.build("401", ex);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
