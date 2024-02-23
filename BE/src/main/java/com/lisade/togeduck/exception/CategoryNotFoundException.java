package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class CategoryNotFoundException extends GeneralException {

    public CategoryNotFoundException() {
        super(Error.CATEGORY_NOT_FOUND);
    }

    public CategoryNotFoundException(Error error) {
        super(error);
    }
}
