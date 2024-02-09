package com.lisade.togeduck.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class UserService {

    public Map<String, String> validateSignUp(Errors errors) {
        Map<String, String> validationResult = new HashMap<>();

        for (FieldError fieldError : errors.getFieldErrors()) {
            String field = fieldError.getField();
            validationResult.put(field, fieldError.getDefaultMessage());
        }
        return validationResult;
    }
}
