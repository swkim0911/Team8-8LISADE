package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class DuplicateUserIdException extends GeneralException {

    public DuplicateUserIdException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
