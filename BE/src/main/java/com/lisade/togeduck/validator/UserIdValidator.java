package com.lisade.togeduck.validator;

import com.lisade.togeduck.repository.UserRepository;
import com.lisade.togeduck.validator.annotation.ValidateUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserIdValidator implements ConstraintValidator<ValidateUserId, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(ValidateUserId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String userId, ConstraintValidatorContext constraintValidatorContext) {
        if (userId.isBlank()) { // userId가 @Blank 에 걸리는 경우
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("아이디를 입력해주세요.")
                .addConstraintViolation();
            return false;
        }
        boolean isDuplicated = userRepository.existsByUserId(userId);
        return !isDuplicated;
    }
}
