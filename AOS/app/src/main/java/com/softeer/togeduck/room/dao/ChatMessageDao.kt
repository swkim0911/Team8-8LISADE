package com.softeer.togeduck.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.softeer.togeduck.data.model.chatting.ChatMessageModel

@Dao
interface ChatMessageDao {
    @Query("select * from chat_message where room_id =:roomId")
    fun getMessagesByRoomId(roomId: Long) : List<ChatMessageModel>

    @Query("select * from chat_message where room_id =:roomId order by id desc limit 1")
    fun getLastMessage(roomId: Long) : ChatMessageModel?

    @Insert
    fun insert(chatMessage: ChatMessageModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(chatMessage: List<ChatMessageModel>)

    @Update
    fun update(chatMessage: ChatMessageModel)

    @Delete
    fun delete(chatMessage: ChatMessageModel)
}