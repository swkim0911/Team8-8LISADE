package com.softeer.togeduck.data.repository

import android.app.Application
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel
import com.softeer.togeduck.room.TogeduckDatabase
import com.softeer.togeduck.room.dao.ChatRoomListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRoomListRepository(application: Application) {

    private val chatRoomListDao: ChatRoomListDao

    init {
        val database = TogeduckDatabase.getInstance(application)
        chatRoomListDao = database!!.chatRoomsDao()
    }

    suspend fun insert(chatRoomListModel: ChatRoomListModel) {
        withContext(Dispatchers.IO) {
            chatRoomListDao.insert(chatRoomListModel)
        }
    }

    suspend fun insertAll(chatRoomListModelList: List<ChatRoomListModel>) {
        withContext(Dispatchers.IO) {
            chatRoomListDao.insertAll(chatRoomListModelList)
        }
    }

    suspend fun get(roomId: Long) : ChatRoomListModel? {
        return withContext(Dispatchers.IO) {
            chatRoomListDao.get(roomId)
        }
    }

    suspend fun getAll() : List<ChatRoomListModel> {
        return withContext(Dispatchers.IO) {
            chatRoomListDao.getAll()
        }
    }

    suspend fun update(chatRoomListModel: ChatRoomListModel) {
        withContext(Dispatchers.IO) {
            chatRoomListDao.update(chatRoomListModel)
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            chatRoomListDao.deleteAll()
        }
    }
}