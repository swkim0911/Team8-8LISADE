package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class AuthenticationRequiredException extends GeneralException {

    public AuthenticationRequiredException(HttpStatus httpStatus,
        Object result) {
        super(httpStatus, result);
    }
}
