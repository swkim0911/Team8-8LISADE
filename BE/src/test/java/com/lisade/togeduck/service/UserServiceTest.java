package com.lisade.togeduck.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.mapper.UserMapper;
import com.lisade.togeduck.repository.UserRepository;
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
    @DisplayName("회원가입 join이 잘되는지 테스트")
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
}