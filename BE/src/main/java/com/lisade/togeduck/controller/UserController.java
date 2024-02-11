package com.lisade.togeduck.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.lisade.togeduck.annotation.ValidateUserId;
import com.lisade.togeduck.dto.request.LoginDto;
import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.exception.InvalidSignUpInfoException;
import com.lisade.togeduck.global.response.ApiResponse;
import com.lisade.togeduck.mapper.UserMapper;
import com.lisade.togeduck.service.UserService;
import com.lisade.togeduck.validator.SignUpValidator.CheckEmailValidator;
import com.lisade.togeduck.validator.SignUpValidator.CheckNicknameValidator;
import com.lisade.togeduck.validator.SignUpValidator.CheckUserIdValidator;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;
    private final CheckUserIdValidator checkUserIdValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckEmailValidator checkEmailValidator;

    @InitBinder // UserController 요청에 대한 커스텀 validator 추가
    public void validatorBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof SignUpDto) {
            binder.addValidators(checkUserIdValidator, checkNicknameValidator, checkEmailValidator);
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<Object> checkUserId(
        @PathVariable(name = "user_id") @ValidateUserId String userId) {
        return ResponseEntity.ok(
            ApiResponse.onSuccess(UserMapper.toValidateUserIdDto("사용가능한 아이디입니다.")));
    }

    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpDto signUpDto, Errors errors) {

        if (errors.hasErrors()) {
            SignUpFailureDto signUpFailureDto = userService.validateSignUp(errors);
            throw new InvalidSignUpInfoException(BAD_REQUEST, signUpFailureDto);
        }
        Long id = userService.join(signUpDto);
        return new ResponseEntity<>(ApiResponse.of(CREATED.value(), CREATED.name(), id), CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDto loginDto, Errors errors) {
        if (errors.hasErrors()) {
            Map<String, String> validationResult = new HashMap<>();

            for (FieldError fieldError : errors.getFieldErrors()) {
                String field = fieldError.getField();
                validationResult.put(field, fieldError.getDefaultMessage());
            }
            for (Entry<String, String> entry : validationResult.entrySet()) {
                System.out.println("entry.getValue() = " + entry.getValue());
            }
        }
        return null;
    }
}
