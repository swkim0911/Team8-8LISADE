package com.lisade.togeduck.validator.SignUpValidator;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<Object> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(Object dto, Errors errors) {
        if (dto instanceof SignUpDto signUpDto) {
            if (userRepository.existsByNickname(signUpDto.getNickname())) {
                errors.rejectValue("nickname", "nickname duplication", "이미 존재하는 닉네임입니다.");
            }
        }
    }
}
