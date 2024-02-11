package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class InvalidSignUpInfoException extends GeneralException {

    public InvalidSignUpInfoException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
