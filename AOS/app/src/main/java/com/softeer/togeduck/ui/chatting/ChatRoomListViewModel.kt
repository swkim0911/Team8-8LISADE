package com.softeer.togeduck.ui.chatting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel
import com.softeer.togeduck.data.repository.ChatRoomListRepository
import kotlinx.coroutines.launch

class ChatRoomListViewModel(application: Application) : AndroidViewModel(application) {
    private val chatRoomRepository: ChatRoomListRepository = ChatRoomListRepository(application)
    private val _chatRooms = MutableLiveData<List<ChatRoomListModel>>()
    val chatRooms: LiveData<List<ChatRoomListModel>> get() = _chatRooms

    fun loadChatRooms() {
        viewModelScope.launch {
            val chatRooms = chatRoomRepository.getAll()
            _chatRooms.value = chatRooms
        }
    }

    fun updateChatRoom(chatRoomListModel: ChatRoomListModel) {
        viewModelScope.launch {
            chatRoomRepository.update(chatRoomListModel)
        }
    }
}