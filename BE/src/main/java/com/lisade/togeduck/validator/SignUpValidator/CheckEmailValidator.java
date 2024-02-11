package com.lisade.togeduck.validator.SignUpValidator;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<Object> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(Object dto, Errors errors) {
        if (dto instanceof SignUpDto signUpDto) {
            if (userRepository.existsByEmail(signUpDto.getEmail())) {
                errors.rejectValue("email", "email duplication", "이미 존재하는 이메일입니다.");
            }
        }
    }
}
