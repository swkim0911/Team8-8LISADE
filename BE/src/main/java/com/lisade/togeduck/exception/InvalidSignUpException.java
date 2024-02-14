package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class InvalidSignUpException extends GeneralException {

    public InvalidSignUpException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
