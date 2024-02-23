package com.lisade.togeduck.controller;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.response.ChatRoomResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/rooms")
    public void create(
        @Login User user) {
        chatRoomService.create(user, 1L); //todo 삭제 예정 (test 용)
    }

    @GetMapping("/{room_id}")
    public ChatRoomResponse join(@Login User user, @PathVariable("room_id") Long roomId) {
        return chatRoomService.get(roomId);
    }
}
