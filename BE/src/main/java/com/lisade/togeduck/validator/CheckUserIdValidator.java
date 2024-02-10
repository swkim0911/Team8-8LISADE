package com.lisade.togeduck.validator;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckUserIdValidator extends AbstractValidator<SignUpDto> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(SignUpDto dto, Errors errors) {
        if (userRepository.existsByUserId(dto.getUserId())) {
            errors.rejectValue("userId", "ID duplication", "이미 존재하는 아이디입니다.");
        }
    }
}
