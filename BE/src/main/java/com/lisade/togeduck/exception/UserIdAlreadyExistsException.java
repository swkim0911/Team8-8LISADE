package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class UserIdAlreadyExistsException extends GeneralException {

    public UserIdAlreadyExistsException() {
        super(Error.USER_ID_ALREADY_REGISTER_ERROR);
    }

    public UserIdAlreadyExistsException(Error error) {
        super(error);
    }
}
