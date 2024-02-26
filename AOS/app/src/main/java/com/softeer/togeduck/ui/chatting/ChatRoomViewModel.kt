package com.softeer.togeduck.ui.chatting

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.chatting.ChatMessageModel
import com.softeer.togeduck.data.repository.ChatMessageRepository
import com.softeer.togeduck.data.repository.ChatRoomListRepository
import com.softeer.togeduck.data.repository.UserRepository
import com.softeer.togeduck.utils.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatMessageRepository : ChatMessageRepository,
    private val chatRoomListRepository : ChatRoomListRepository,
    private val userRepository : UserRepository) : ViewModel() {

    private val _chatMessages = MutableLiveData<List<ChatMessageModel>>()
    val chatMessages: LiveData<List<ChatMessageModel>> get() = _chatMessages

    private var _newMessages = MutableLiveData<ArrayList<ChatMessageModel>>()
    val newMessages: LiveData<ArrayList<ChatMessageModel>> get() = _newMessages

    private var id = 0L
    private var roomName = ""

    private val url = "ws://10.0.2.2:8080/chat"
    private val stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    private val uuid = UUID.randomUUID().toString()

    fun init(id: Long, roomName: String) {
        this.id = id
        this.roomName = roomName
        loadChatMessages(id)
    }

    private fun loadChatMessages(id: Long) {
        viewModelScope.launch {
            val messages = chatMessageRepository.getMessagesByRoomId(id)
            _chatMessages.postValue(messages)
        }
    }

    @SuppressLint("CheckResult")
    fun connectToStomp() {
        stomp.lifecycle().subscribe { lifecycle ->
            when (lifecycle.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("OPEND", "!!")
                }

                LifecycleEvent.Type.CLOSED -> {
                    Log.i("CLOSED", "!!")

                }

                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycle.exception.toString())
                }

                else -> {
                    Log.i("ELSE", lifecycle.message)
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val session = userRepository.getUserSessionId().first()

            withContext(Dispatchers.Main) {
                val headerList = ArrayList<StompHeader>()
                headerList.add(StompHeader("Cookie", session))
                stomp.connect(headerList)
            }
        }
    }

    fun disconnectStomp() {
        stomp.disconnect()
    }

    fun saveMessages() {
        if (!_newMessages.value.isNullOrEmpty()) {
            insertNewMessages()
            updateChatRoom()
        }
    }

    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.O)
    fun readMessage() {
        stomp.topic("/topic/message/$id").subscribe { stompMessage ->
            val data = JSONObject(stompMessage.payload)

            val uuid = data.get("uuid") as String

            if(this.uuid != uuid) {
                val nickname = data.get("sender") as String
                val message = data.get("message") as String
                val time = data.get("createdAt") as String
                val type = data.get("action") as String

                var location = ""

                val lastMessage = getLastItem()

                if (type == "JOIN" || type == "LEAVE") {
                    location = "CENTER"
                } else if (lastMessage == null || nickname != lastMessage.nickname) {
                    location = "FIRST_LEFT"
                } else {
                    location = "LEFT"
                }

                val newChatMessageModel =
                    ChatMessageModel(0, id, nickname, message, time, type, location)

                addTimeMessage(time)
                addNewMessage(newChatMessageModel)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: String) {
        val data = JSONObject()
        val now = TimeFormatter.now()

        data.put("roomId", id)
        data.put("roomName", roomName)
        data.put("message", message)
        data.put("createdAt", now)
        data.put("action", "MESSAGE")
        data.put("uuid", uuid)

        stomp.send("/app/chat/message", data.toString()).subscribe()

        val newChatMessageModel = ChatMessageModel(0, id, "!", message, now, "MESSAGE", "RIGHT")

        addTimeMessage(now)
        addNewMessage(newChatMessageModel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addTimeMessage(now: String) {
        val lastMessage = getLastItem()

        if (lastMessage == null) {
            val newChatMessageModel = ChatMessageModel(0, id, "", TimeFormatter.yyyyMMddE(now), now, "DATE", "CENTER")
            addNewMessage(newChatMessageModel)

            return
        }

        val lastMessageDate = TimeFormatter.toLocalDateTime(lastMessage.time!!).toLocalDate()
        val nowDate = TimeFormatter.toLocalDateTime(now).toLocalDate()

        if (!lastMessageDate.isEqual(nowDate)) {
            val newChatMessageModel = ChatMessageModel(0, id, "", TimeFormatter.yyyyMMddE(now), now, "DATE", "CENTER")
            addNewMessage(newChatMessageModel)
        }
    }

    private fun getLastItem(): ChatMessageModel? {
        return _chatMessages.value?.lastOrNull()
    }

    private fun addNewMessage(message: ChatMessageModel) {
        val currentMessages = _newMessages.value ?: arrayListOf()
        currentMessages.add(message)
        _newMessages.postValue(currentMessages)
    }

    private fun insertNewMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            chatMessageRepository.insertAll(newMessages.value!!)
        }
    }

    private fun updateChatRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val chatRoomListModel = chatRoomListRepository.get(id)

            if(chatRoomListModel != null) {
                chatRoomListModel.recentTime = newMessages.value!!.last().time!!
                chatRoomListModel.recentMessage = newMessages.value!!.last().message

                chatRoomListRepository.update(chatRoomListModel)
            }
        }
    }
}