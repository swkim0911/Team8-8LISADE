package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {

    boolean existsByUserIdAndChatRoomId(Long userId, Long roomId); // user 가 chatroom에 입장했는지 확인
}
