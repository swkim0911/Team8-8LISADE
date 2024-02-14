package com.lisade.togeduck.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.lisade.togeduck.dto.request.LoginDto;
import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.mapper.UserMapper;
import com.lisade.togeduck.repository.UserRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원가입 join() 잘되는지 테스트")
    void userJoin() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
            .userId("userId")
            .password("password12@")
            .nickname("nickname")
            .email("right@email.com")
            .build();
        User user = UserMapper.toUser(signUpDto);

        //when
        when(userRepository.save(any())).thenReturn(user);
        userService.join(signUpDto);// 실제 db가 아니여서 id를 검증할 수 없음.
    }

    @Test
    @DisplayName("로그인 login() 잘되는지 테스트")
    void userLogin() {
        //given
        String userId = "userId";
        String password = "password12@";

        LoginDto loginDto = LoginDto.builder()
            .userId(password)
            .password(password)
            .build();

        SignUpDto signUpDto = SignUpDto.builder()
            .userId("userId")
            .password("password12@")
            .nickname("nickname")
            .email("right@email.com")
            .build();

        User user = UserMapper.toUser(signUpDto);

        //when
        when(userRepository.findByUserIdAndPassword(any(), any())).thenReturn(
            Optional.ofNullable(user));

        User loginUser = userService.login(loginDto);

        //then
        Assertions.assertThat(loginUser.getUserId()).isEqualTo(userId);
        Assertions.assertThat(loginUser.getPassword()).isEqualTo(password);

    }
}