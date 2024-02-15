package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class UnAuthenticationException extends GeneralException {

    public UnAuthenticationException() {
        super(Error.UN_AUTHENTICATION_ERROR);
    }

    public UnAuthenticationException(Error error) {
        super(error);
    }
}
