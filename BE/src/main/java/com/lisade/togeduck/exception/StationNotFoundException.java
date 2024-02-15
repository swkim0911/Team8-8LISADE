package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class StationNotFoundException extends GeneralException {

    public StationNotFoundException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
