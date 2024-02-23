package com.softeer.togeduck.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.softeer.togeduck.data.model.chatting.ChatMessageModel

@Dao
interface ChatMessageDao {
    @Query("select * from chat_message where room_id =:roomId")
    fun getMessagesByRoomId(roomId: Long) : List<ChatMessageModel>

    @Insert
    fun insert(chatMessage: ChatMessageModel)

    @Update
    fun update(chatMessage: ChatMessageModel)

    @Delete
    fun delete(chatMessage: ChatMessageModel)
}