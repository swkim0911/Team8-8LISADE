package com.softeer.togeduck.data.repository

import android.app.Application
import com.softeer.togeduck.data.model.chatting.ChatMessageModel
import com.softeer.togeduck.room.TogeduckDatabase
import com.softeer.togeduck.room.dao.ChatMessageDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatMessageRepository(application: Application) {
    private val chatMessageDao: ChatMessageDao

    init {
        val database = TogeduckDatabase.getInstance(application)
        chatMessageDao = database!!.chatMessageDao()
    }

    suspend fun insert(message: ChatMessageModel) {
        withContext(Dispatchers.IO) {
            chatMessageDao.insert(message)
        }
    }

    suspend fun insertAll(messages: List<ChatMessageModel>) {
        withContext(Dispatchers.IO) {
            chatMessageDao.insertAll(messages)
        }
    }

    suspend fun getLastMessage(roomId: Long) : ChatMessageModel? {
        return withContext(Dispatchers.IO) {
            chatMessageDao.getLastMessage(roomId)
        }
    }

    suspend fun getMessagesByRoomId(roomId: Long) : List<ChatMessageModel> {
        return withContext(Dispatchers.IO) {
            chatMessageDao.getMessagesByRoomId(roomId)
        }
    }
}