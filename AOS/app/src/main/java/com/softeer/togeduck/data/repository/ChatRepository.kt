package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.dto.response.chat.ChatRoomListResponse
import com.softeer.togeduck.data.remote.datasource.chat.ChatDataSource
import retrofit2.Response
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatDataSource: ChatDataSource
) {
    suspend fun getChatRoomList(): Response<List<ChatRoomListResponse>> {
        return chatDataSource.getChatRoomList()
    }
}