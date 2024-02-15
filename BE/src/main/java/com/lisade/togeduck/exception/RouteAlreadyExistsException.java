package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class RouteAlreadyExistsException extends GeneralException {

    public RouteAlreadyExistsException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
