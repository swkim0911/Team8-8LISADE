package com.lisade.togeduck.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;

    private String message;

    private T result;

    public static <T> ApiResponse<T> onSuccess(T result) {
        return of(HttpStatus.OK.value(), HttpStatus.OK.name(), result);
    }

    public static <T> ApiResponse<T> of(int status, String message, T result) {
        return new ApiResponse<>(status, message, result);
    }
}
