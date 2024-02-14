package com.lisade.togeduck.validator;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class SignUpValidator extends AbstractValidator<Object> {

    private final UserService userService;

    @Override
    protected void doValidate(Object dto, Errors errors) {
        if (dto instanceof SignUpDto signUpDto) {
            userService.exist(signUpDto, errors);
        }
    }
}
