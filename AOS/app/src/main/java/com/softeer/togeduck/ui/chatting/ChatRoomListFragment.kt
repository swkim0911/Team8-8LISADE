package com.softeer.togeduck.ui.chatting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel
import com.softeer.togeduck.databinding.FragmentChatRoomListBinding
import com.softeer.togeduck.utils.ItemClick

class ChatRoomListFragment : Fragment() {
    private lateinit var binding: FragmentChatRoomListBinding
    private lateinit var viewModel: ChatRoomListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ChatRoomListViewModel::class.java]
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadChatRooms()
    }

    private fun initObservers() {
        viewModel.chatRooms.observe(viewLifecycleOwner) { chatRooms ->
            val adapter = ChatRoomListAdapter(chatRooms)
            binding.rvChatRoomList.adapter = adapter

            adapter.itemClick = object : ItemClick {
                override fun onClick(view: View, position: Int) {
                    val chatRoom = chatRooms[position]
                    val intent = Intent(requireContext(), ChatRoomActivity::class.java).apply {
                        putExtra("id", chatRoom.id)
                        putExtra("roomName", chatRoom.roomName)
                    }

                    setUnreadMessageCountZero(chatRoom)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setUnreadMessageCountZero(chatRoomListModel: ChatRoomListModel) {
        viewModel.updateChatRoom(chatRoomListModel.copy(unreadMessageCount = 0))
    }
}