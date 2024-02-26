package com.softeer.togeduck.data.dto.response.chat

data class ChatRoomListResponse(
    val id : Long,
    val routeId : Long,
    val roomName : String,
    val thumbnail : String
)
