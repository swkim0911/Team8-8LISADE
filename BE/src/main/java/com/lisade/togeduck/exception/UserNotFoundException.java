package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GeneralException {

    public UserNotFoundException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
