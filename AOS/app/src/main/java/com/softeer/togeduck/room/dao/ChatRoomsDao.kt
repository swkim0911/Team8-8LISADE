package com.softeer.togeduck.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.softeer.togeduck.data.model.chatting.ChatRoomsModel

@Dao
interface ChatRoomsDao {
    @Query("select * from chat_room")
    fun getAll() : List<ChatRoomsModel>

    @Insert
    fun insert(chatRooms: ChatRoomsModel)

    @Update
    fun update(chatRooms: ChatRoomsModel)

    @Delete
    fun delete(chatRooms: ChatRoomsModel)
}