package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자가 DB에 잘 저장되는지 확인한다.")
    void saveUser() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
            .userId("testId")
            .password("password12@")
            .nickname("nickname")
            .email("right@email.com")
            .build();
        User user = UserMapper.toUser(signUpDto);
        //when
        User savedUser = userRepository.save(user);

        //then
        Assertions.assertThat(savedUser.getUserId()).isEqualTo(user.getUserId());
    }
}