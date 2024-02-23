package com.softeer.togeduck.data.model.chatting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "chat_message", foreignKeys = [
    ForeignKey(
        entity = ChatRoomListModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("room_id"),
        onDelete = ForeignKey.CASCADE
    )
])
data class ChatMessageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "room_id")
    val roomId: Long,
    val nickname: String?,
    val message: String,
    val time: String?,
    val type: String?,
    val location: String
)
