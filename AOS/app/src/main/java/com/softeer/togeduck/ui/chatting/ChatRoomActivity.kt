package com.softeer.togeduck.ui.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.data.model.chatting.ChatMessageModel
import com.softeer.togeduck.databinding.ActivityChatRoomBinding
import com.softeer.togeduck.room.TogeduckDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    private var newMessages = arrayListOf<ChatMessageModel>()

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.onBackPressedDispatcher.addCallback(this, callback)

        val id = intent.getLongExtra("id", 0)
        val roomName = intent.getStringExtra("roomName")

        binding.apply {
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

        }

        getMessages(id)
    }

    private fun getMessages(roomId: Long){
        val db = TogeduckDatabase.getInstance(binding.root.context)

        binding.apply {
            CoroutineScope(Dispatchers.IO).launch {
                val messages = db!!.chatMessageDao().getMessagesByRoomId(roomId) as ArrayList<ChatMessageModel>

                val adapter = ChatMessageListAdapter(messages)

                withContext(Dispatchers.Main) {
                    rvChatRoomMessage.layoutManager = LinearLayoutManager(this@ChatRoomActivity,
                        LinearLayoutManager.VERTICAL,
                        false)
                    rvChatRoomMessage.adapter = adapter
                    rvChatRoomMessage.scrollToPosition(messages.size - 1)
                }
            }
        }
    }
}