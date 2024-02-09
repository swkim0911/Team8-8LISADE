package com.lisade.togeduck.global.exception;

import com.lisade.togeduck.global.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GeneralException extends RuntimeException {

    private final StatusCode statusCode;
}
