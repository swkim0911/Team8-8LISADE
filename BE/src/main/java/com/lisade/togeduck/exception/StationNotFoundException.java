package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class StationNotFoundException extends GeneralException {

    public StationNotFoundException() {
        super(Error.STATION_NOT_FOUND_ERROR);
    }

    public StationNotFoundException(Error error) {
        super(error);
    }

}
