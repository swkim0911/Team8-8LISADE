package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class RouteAlreadyExistsException extends GeneralException {

    public RouteAlreadyExistsException() {
        super(Error.ROUTE_ALREADY_EXISTS_ERROR);
    }

    public RouteAlreadyExistsException(Error error) {
        super(error);
    }
}
