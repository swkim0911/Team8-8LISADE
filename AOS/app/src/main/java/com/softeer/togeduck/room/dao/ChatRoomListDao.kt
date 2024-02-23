package com.softeer.togeduck.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel

@Dao
interface ChatRoomListDao {
    @Query("select * from chat_room where id=:roomId")
    fun get(roomId: Long) : ChatRoomListModel

    @Query("select * from chat_room order by recent_time desc")
    fun getAll() : List<ChatRoomListModel>

    @Insert
    fun insert(chatRooms: ChatRoomListModel)

    @Update
    fun update(chatRooms: ChatRoomListModel)

    @Delete
    fun delete(chatRooms: ChatRoomListModel)
}