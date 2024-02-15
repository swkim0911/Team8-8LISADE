package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class BusNotFoundException extends GeneralException {

    public BusNotFoundException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
