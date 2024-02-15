package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class FestivalNotIncludeRouteException extends GeneralException {

    public FestivalNotIncludeRouteException() {
        super(Error.FESTIVAL_NOT_INCLUDE_ROUTE_ERROR);
    }

    public FestivalNotIncludeRouteException(Error error) {
        super(error);
    }
}
