package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.response.chat.ChatRoomListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ChatService {
    @GET("/chats/rooms")
    suspend fun getChatRoomList(): Response<List<ChatRoomListResponse>>
}