package com.softeer.togeduck.data.model.chatting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_room")
data class ChatRoomListModel(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "route_id")
    val routeId: Long,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    @ColumnInfo(name = "room_name")
    val roomName: String,
    @ColumnInfo(name = "recent_message")
    val recentMessage: String,
    @ColumnInfo(name = "recent_time")
    val recentTime: String,
    @ColumnInfo(name = "unread_message_count")
    var unreadMessageCount: Int
)
