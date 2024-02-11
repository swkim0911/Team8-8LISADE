package com.lisade.togeduck.validator.SignUpValidator;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//todo 애노테이션으로 변경 가능함
public abstract class AbstractValidator<T> implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        doValidate((T) target, errors);
    }

    protected abstract void doValidate(final T dto, final Errors errors);
}
