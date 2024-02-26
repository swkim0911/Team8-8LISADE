package com.softeer.togeduck.ui.chatting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel
import com.softeer.togeduck.data.repository.ChatRoomListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomListViewModel @Inject constructor(
    private val chatRoomRepository: ChatRoomListRepository
) : ViewModel() {

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