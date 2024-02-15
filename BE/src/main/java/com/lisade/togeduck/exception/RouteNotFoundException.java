package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class RouteNotFoundException extends GeneralException {

    public RouteNotFoundException() {
        super(Error.ROUTE_NOT_FOUND_ERROR);
    }

    public RouteNotFoundException(Error error) {
        super(error);
    }
}
