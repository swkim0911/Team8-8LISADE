package com.lisade.togeduck.validator.annotation;

import com.lisade.togeduck.validator.UserIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIdValidator.class)
public @interface ValidateUserId {

    String message() default "이미 존재하는 아이디입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
