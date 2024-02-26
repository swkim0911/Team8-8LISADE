package com.softeer.togeduck.data.remote.datasource.chat

import com.softeer.togeduck.data.dto.response.chat.ChatRoomListResponse
import com.softeer.togeduck.data.remote.service.ChatService
import retrofit2.Response
import javax.inject.Inject

class ChatDataSource @Inject constructor(
    private val chatService: ChatService
) {

    suspend fun getChatRoomList() : Response<List<ChatRoomListResponse>> {
        return chatService.getChatRoomList()
    }
}