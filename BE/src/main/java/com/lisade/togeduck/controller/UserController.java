package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public SignUpFailureDto signUp(@RequestBody @Valid SignUpDto signUpDto, Errors errors) {

        if (errors.hasErrors()) {
            return userService.validateSignUp(errors);
        }
        userService.checkDuplication(signUpDto);
        userService.join(signUpDto);
        return null;
    }
}
