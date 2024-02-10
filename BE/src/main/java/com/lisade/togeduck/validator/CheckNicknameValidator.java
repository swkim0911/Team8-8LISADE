package com.lisade.togeduck.validator;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<SignUpDto> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(SignUpDto dto, Errors errors) {
        if (userRepository.existsByNickname(dto.getNickname())) {
            errors.rejectValue("nickname", "nickname duplication", "이미 존재하는 닉네임입니다.");
        }
    }
}
