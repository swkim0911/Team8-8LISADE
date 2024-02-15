package com.lisade.togeduck.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {
    ;
    private final HttpStatus httpStatus;
    private final String Message;
}
