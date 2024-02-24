package com.softeer.togeduck.ui.chatting

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.data.model.chatting.ChatMessageModel
import com.softeer.togeduck.databinding.ActivityChatRoomBinding
import com.softeer.togeduck.room.TogeduckDatabase
import com.softeer.togeduck.utils.TimeFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.util.UUID

class ChatRoomActivity: AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding

    private var newMessages = arrayListOf<ChatMessageModel>()
    private var id = 0L

    private val url = "ws://10.0.2.2:8080/chat"
    private val stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    private val uuid = UUID.randomUUID().toString()

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.onBackPressedDispatcher.addCallback(this, callback)

        id = intent.getLongExtra("id", 0)
        val roomName = intent.getStringExtra("roomName")

        binding.apply {
            setSupportActionBar(chatRoomToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            chatRoomName.text = roomName

            chatRoomBackButton.setOnClickListener {
                finish()
            }

            rvChatRoomMessage.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                if (bottom < oldBottom) {
                    rvChatRoomMessage.postDelayed({
                        if (rvChatRoomMessage.adapter!!.itemCount > 0) {
                            rvChatRoomMessage.scrollToPosition(rvChatRoomMessage.adapter!!.itemCount - 1)
                        }
                    }, 100)
                }
            }

            chatMessageSendBtn.setOnClickListener {
                if (chatMessageEditText.text.isEmpty()) {
                    chatMessageEditText.requestFocus()
                    keyBordShow()
                    Toast.makeText(this@ChatRoomActivity, "메세지를 입력하세요", Toast.LENGTH_SHORT).show()
                } else {
                    sendMessage(chatMessageEditText.text.toString())
                    chatMessageEditText.text.clear()
                    rvChatRoomMessage.scrollToPosition(rvChatRoomMessage.adapter!!.itemCount - 1)
                }
            }
        }

        getMessages()
    }

    override fun onDestroy() {
        super.onDestroy()
        stomp.disconnect()

        if(newMessages.isNotEmpty()) {
            updateChatRoom()
        }
    }

    override fun onPause() {
        super.onPause()
        stomp.disconnect()

        if(newMessages.isNotEmpty()) {
            insertMessages()
            updateChatRoom()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        runStomp()
        readMessage()
    }

    private fun keyBordShow() {
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())
    }

    private fun getMessages() {
        val db = TogeduckDatabase.getInstance(binding.root.context)

        binding.apply {
            CoroutineScope(Dispatchers.IO).launch {
                val messages =
                    db!!.chatMessageDao().getMessagesByRoomId(id) as ArrayList<ChatMessageModel>

                val adapter = ChatMessageListAdapter(messages)

                withContext(Dispatchers.Main) {
                    rvChatRoomMessage.layoutManager = LinearLayoutManager(
                        this@ChatRoomActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    rvChatRoomMessage.adapter = adapter
                    rvChatRoomMessage.scrollToPosition(messages.size - 1)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun runStomp() {
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

        stomp.connect()
    }

    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun readMessage() {
        stomp.topic("/topic/message/$id").subscribe { stompMessage ->
            val data = JSONObject(stompMessage.payload)

            val uuid = data.get("uuid") as String

            if (this.uuid != uuid) {
                val nickname = data.get("sender") as String
                val message = data.get("message") as String
                val time = data.get("createdAt") as String
                val type = data.get("action") as String

                var location = ""

                var lastMessage = getLastMessage()

                if (type == "JOIN" || type == "LEAVE") {
                    location = "CENTER"
                } else if (lastMessage == null || nickname != lastMessage.nickname) {
                    location = "FIRST_LEFT"
                } else {
                    location = "LEFT"
                }

                runOnUiThread {
                    addTimeMessage(time)
                    updateMessage(nickname, message, time, type, location)
                    binding.rvChatRoomMessage.scrollToPosition(binding.rvChatRoomMessage.adapter!!.itemCount - 1)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(message: String) {
        val data = JSONObject()
        val now = TimeFormatter.now()

        data.put("roomId", id)
        data.put("message", message)
        data.put("createdAt", now)
        data.put("action", "MESSAGE")
        data.put("uuid", uuid)

        stomp.send("/app/chat/message", data.toString()).subscribe()

        addTimeMessage(now)
        updateMessage("!", message, now, "MESSAGE", "RIGHT")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addTimeMessage(now: String) {
        val lastMessage = getLastMessage()

        if (lastMessage == null) {
            updateMessage("", TimeFormatter.yyyyMMddE(now), now, "DATE", "CENTER")

            return
        }

        val lastMessageDate = TimeFormatter.toLocalDateTime(lastMessage.time!!).toLocalDate()
        val nowDate = TimeFormatter.toLocalDateTime(now).toLocalDate()

        if (!lastMessageDate.isEqual(nowDate)) {
            updateMessage("", TimeFormatter.yyyyMMddE(now), now, "DATE", "CENTER")
        }
    }

    private fun isFirstMessage(): Boolean {
        binding.apply {
            val adapter = rvChatRoomMessage.adapter as ChatMessageListAdapter

            return adapter.itemCount == 0
        }
    }

    private fun getLastMessage(): ChatMessageModel? {
        binding.apply {
            if (isFirstMessage()){
                return null
            }

            val adapter = rvChatRoomMessage.adapter as ChatMessageListAdapter

            return adapter.getLastItem()
        }
    }

    private fun updateMessage(nickname: String, message: String, time: String, type: String, location: String) {
        val newChatMessageModel = ChatMessageModel(0, id, nickname, message, time, type, location)

        addMessageInRecyclerView(newChatMessageModel)
        newMessages.add(newChatMessageModel)
    }

    private fun addMessageInRecyclerView(chatMessageModel: ChatMessageModel) {
        binding.apply {
            val adapter = rvChatRoomMessage.adapter as ChatMessageListAdapter

            adapter.addItem(chatMessageModel)
        }
    }

    private fun insertMessages() {
        val db = TogeduckDatabase.getInstance(binding.root.context)

        CoroutineScope(Dispatchers.IO).launch {
            db!!.chatMessageDao().insertAll(newMessages)
        }
    }

    private fun updateChatRoom() {
        val db = TogeduckDatabase.getInstance(binding.root.context)

        CoroutineScope(Dispatchers.IO).launch {
            val chatRoomListModel = db!!.chatRoomsDao().get(id)

            chatRoomListModel.recentTime = newMessages[newMessages.size - 1].time!!
            chatRoomListModel.recentMessage = newMessages[newMessages.size - 1].message

            db.chatRoomsDao().update(chatRoomListModel)
        }
    }
}