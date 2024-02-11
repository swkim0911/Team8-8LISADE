package com.lisade.togeduck.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.lisade.togeduck.dto.request.LoginDto;
import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.LoginEmptyFieldDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.InvalidSignUpException;
import com.lisade.togeduck.exception.LoginEmptyFieldException;
import com.lisade.togeduck.global.response.ApiResponse;
import com.lisade.togeduck.service.UserService;
import com.lisade.togeduck.validator.SignUpValidator.CheckEmailValidator;
import com.lisade.togeduck.validator.SignUpValidator.CheckNicknameValidator;
import com.lisade.togeduck.validator.SignUpValidator.CheckUserIdValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
public class UserController {

    private final UserService userService;
    private final CheckUserIdValidator checkUserIdValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckEmailValidator checkEmailValidator;

    @InitBinder // UserController 요청에 대한 커U스텀 validator 추가
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkUserIdValidator, checkNicknameValidator, checkEmailValidator);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<Object> checkUserId(@PathVariable(name = "user_id") String userId) {
        return userService.checkUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpDto signUpDto, Errors errors) {

        if (errors.hasErrors()) {
            SignUpFailureDto signUpFailureDto = userService.validateSignUp(errors);
            throw new InvalidSignUpException(BAD_REQUEST, signUpFailureDto);
        }
        Long id = userService.join(signUpDto);
        return new ResponseEntity<>(ApiResponse.of(CREATED.value(), CREATED.name(), id), CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto, Errors errors,
        HttpServletRequest request) {

        if (errors.hasErrors()) {
            LoginEmptyFieldDto loginEmptyFieldDto = userService.validateLogin(errors);
            throw new LoginEmptyFieldException(BAD_REQUEST, loginEmptyFieldDto);
        }
        //로그인 정보가 맞지 않은 경우
        User user = userService.login(loginDto);
        HttpSession session = request.getSession();
        session.setAttribute("hello", user);
        return ResponseEntity.ok(ApiResponse.onSuccess(user.getUserId()));
    }
}
