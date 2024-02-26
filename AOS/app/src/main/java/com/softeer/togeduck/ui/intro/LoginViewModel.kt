package com.softeer.togeduck.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.softeer.togeduck.data.dto.request.LoginRequest
import com.softeer.togeduck.data.dto.response.chat.ChatRoomListResponse
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel
import com.softeer.togeduck.data.repository.ChatRepository
import com.softeer.togeduck.data.repository.ChatRoomListRepository
import com.softeer.togeduck.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val chatRoomListRepository: ChatRoomListRepository,
    private val chatRepository: ChatRepository,
    private val loginRepository: UserRepository
) : ViewModel() {

    fun saveSessionId() {
        viewModelScope.launch {
            val response = loginRepository.login(LoginRequest("user1", "password1"))
            if (response.isSuccessful) {
                val sessionId = extractSessionId(response.headers()["Set-Cookie"])
                sessionId?.let {
                    viewModelScope.launch {
                        sessionStore(it)
                        getChatRoomList()
                    }
                }
            } else {

            }
        }
    }

    private fun getChatRoomList() {
        viewModelScope.launch {
            val response = chatRepository.getChatRoomList()

            if(response.isSuccessful) {
                if (!response.body().isNullOrEmpty()) {
                    saveChatRoomList(response.body()!!)
                }
            } else {

            }
        }
    }

    private suspend fun saveChatRoomList(chatRoomListResponse: List<ChatRoomListResponse>) {
        if(chatRoomListResponse.isNotEmpty()) {
            chatRoomListResponse.forEach {
                FirebaseMessaging.getInstance().subscribeToTopic(it.id.toString())
            }

            chatRoomListRepository.deleteAll()
            chatRoomListRepository.insertAll(
                chatRoomListResponse.map {
                    ChatRoomListModel(it.id, it.routeId, it.thumbnail, it.roomName, "", "", 0)
                }.toList()
            )
        }
    }

    private suspend fun sessionStore(sessionId: String) {
        loginRepository.storeUser(sessionId)
    }


    private fun extractSessionId(sessionId: String?): String? {
        val sessionIdPrefix = "JSESSIONID="
        val parts = sessionId?.split(";")
        parts?.forEach { part ->
            if (part.trim().startsWith(sessionIdPrefix)) {
                return part.substringAfter(sessionIdPrefix)
            }
        }
        return null
    }
}