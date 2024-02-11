package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class InvalidLoginException extends GeneralException {

    public InvalidLoginException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
