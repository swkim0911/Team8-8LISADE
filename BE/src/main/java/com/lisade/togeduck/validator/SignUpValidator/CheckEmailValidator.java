package com.lisade.togeduck.validator.SignUpValidator;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<SignUpDto> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(SignUpDto dto, Errors errors) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            errors.rejectValue("email", "email duplication", "이미 존재하는 이메일입니다.");
        }
    }
}
