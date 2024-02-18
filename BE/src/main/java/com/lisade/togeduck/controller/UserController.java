package com.lisade.togeduck.controller;

import static com.lisade.togeduck.constant.SessionConst.LOGIN_USER;
import static org.springframework.http.HttpStatus.CREATED;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.request.LoginDto;
import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDto;
import com.lisade.togeduck.dto.response.ValidateUserIdDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.UnAuthenticationException;
import com.lisade.togeduck.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/{user_id}")
    public ValidateUserIdDto checkUserId(@PathVariable(name = "user_id") String userId) {
        return userService.checkUserId(userId);
    }

    @PostMapping("")
    public ResponseEntity<Long> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        Long id = userService.join(signUpDto);
        return new ResponseEntity<>(id, CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody @Valid LoginDto loginDto,
        HttpServletRequest request) {
        User loginUser = userService.login(loginDto);
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER.getSessionName(), loginUser);
        return new ResponseEntity<>(loginUser.getId(), HttpStatus.OK);
    }

    @GetMapping("/routes")
    public Slice<UserReservedRouteDto> getRoutes(
        @Login User user,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        if (user == null) {
            throw new UnAuthenticationException();
        }
        return userService.getReservedRouteList(pageable, user.getId());
    }

    @GetMapping("/routes/{route_id}")
    public UserReservedRouteDetailDto getRouteInfo(@Login User user,
        @PathVariable(name = "route_id") Long routeId) {
        if (user == null) {
            throw new UnAuthenticationException();
        }
        return userService.getReservedRouteInfo(user.getId(), routeId)
            .orElseThrow(RuntimeException::new);
    }
}
