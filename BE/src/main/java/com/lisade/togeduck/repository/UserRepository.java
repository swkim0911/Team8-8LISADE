package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Optional<User> findByUserIdAndPassword(String userId, String password);

    @Query("select u from User u join fetch u.userRoutes where u.id =:userId")
    Optional<User> findUserByIdWithUserRoute(Long userId);
}
