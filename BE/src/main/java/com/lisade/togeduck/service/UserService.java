package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.mapper.UserMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
public class UserService {

    public SignUpFailureDto validateSignUp(Errors errors) {
        Map<String, String> validationResult = getSignUpErrorField(errors);
        return UserMapper.toSignUpFailureDto(validationResult);
    }

    private Map<String, String> getSignUpErrorField(Errors errors) {
        Map<String, String> validationResult = new HashMap<>();

        for (FieldError fieldError : errors.getFieldErrors()) {
            String field = fieldError.getField();
            validationResult.put(field, fieldError.getDefaultMessage());
        }

        return validationResult;
    }
}
