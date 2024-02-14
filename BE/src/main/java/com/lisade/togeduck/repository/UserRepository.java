package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String UserId);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Optional<User> findByUserIdAndPassword(String userId, String password);
}
