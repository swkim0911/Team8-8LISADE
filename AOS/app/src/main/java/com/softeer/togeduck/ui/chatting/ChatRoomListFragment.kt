package com.softeer.togeduck.ui.chatting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel
import com.softeer.togeduck.databinding.FragmentChatRoomListBinding
import com.softeer.togeduck.room.TogeduckDatabase
import com.softeer.togeduck.utils.ItemClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatRoomListFragment : Fragment() {
    private lateinit var binding: FragmentChatRoomListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init() {
        val db = TogeduckDatabase.getInstance(binding.root.context)

        binding.apply {
            CoroutineScope(Dispatchers.IO).launch {
                val chatRooms = db!!.chatRoomsDao().getAll()
                val adapter = ChatRoomListAdapter(chatRooms)

                withContext(Dispatchers.Main){
                    rvChatRoomList.adapter = adapter

                    adapter.itemClick = object : ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(binding.root.context, ChatRoomActivity::class.java)

                            intent.putExtra("id", chatRooms[position].id)
                            intent.putExtra("roomName", chatRooms[position].roomName)

                            setUnreadMessageCountZero(chatRooms[position])

                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun setUnreadMessageCountZero(chatRoomListModel: ChatRoomListModel){
        val db = TogeduckDatabase.getInstance(binding.root.context)

        CoroutineScope(Dispatchers.IO).launch {
            chatRoomListModel.unreadMessageCount = 0

            db!!.chatRoomsDao().update(chatRoomListModel)
        }
    }
}