package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.ChatRoomListResponse;
import com.lisade.togeduck.entity.ChatRoom;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.UserChatRoom;
import com.lisade.togeduck.exception.ChatRoomNotFoundException;
import com.lisade.togeduck.exception.FestivalNotFoundException;
import com.lisade.togeduck.repository.ChatRoomRepository;
import com.lisade.togeduck.repository.FestivalRepository;
import com.lisade.togeduck.repository.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final FestivalRepository festivalRepository;

    @Transactional
    public void create(User user, Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId)
            .orElseThrow(FestivalNotFoundException::new);

        ChatRoom chatRoom = ChatRoom.builder()
            .roomName(festival.getTitle() + " 채팅방")
            .numberOfMembers(0)
            .thumbnailPath(festival.getThumbnailPath())
            .build();
        chatRoomRepository.save(chatRoom);

        UserChatRoom userChatRoom = UserChatRoom.builder()
            .user(user)
            .chatRoom(chatRoom)
            .build();
        userChatRoomRepository.save(userChatRoom);
    }

    public boolean exist(Long userId, Long roomId) {
        return userChatRoomRepository.existsByUserIdAndChatRoomId(userId, roomId);
    }

    public Slice<ChatRoomListResponse> getList(Pageable pageable, Long userId) {
        return chatRoomRepository.findJoinedChatRooms(pageable, userId);
    }

    @Transactional
    public ChatRoom get(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
            .orElseThrow(ChatRoomNotFoundException::new);
        chatRoom.increaseMember();
        return chatRoom;
    }

}
