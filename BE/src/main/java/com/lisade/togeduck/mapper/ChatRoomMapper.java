package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.ChatRoomResponse;
import com.lisade.togeduck.entity.ChatRoom;

public class ChatRoomMapper {

    public static ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
            .id(chatRoom.getId())
            .roomName(chatRoom.getRoomName())
            .numberOfMembers(chatRoom.getNumberOfMembers())
            .thumbnailPath(chatRoom.getThumbnailPath()).build();
    }
}
