package com.softeer.togeduck.ui.chatting

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel
import com.softeer.togeduck.databinding.RvItemChatRoomListBinding
import com.softeer.togeduck.utils.ItemClick
import com.softeer.togeduck.utils.TimeFormatter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

class ChatRoomListAdapter(private val items: List<ChatRoomListModel>) :
    RecyclerView.Adapter<ChatRoomListAdapter.MyViewHolder>() {

    private lateinit var binding: RvItemChatRoomListBinding
    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = RvItemChatRoomListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(private val binding: RvItemChatRoomListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: ChatRoomListModel) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(item.thumbnail)
                    .into(chatRoomThumbnail)

                chatRoomName.text = item.roomName
                chatRoomRecentMessage.text = item.recentMessage
                chatRoomRecentTime.text = setTimeFormat(item.recentTime)

                if(item.unreadMessageCount == 0){
                    chatRoomUnreadMessageCount.visibility = View.INVISIBLE
                } else{
                    chatRoomUnreadMessageCount.text = item.unreadMessageCount.toString()
                    chatRoomUnreadMessageCount.visibility = View.VISIBLE
                }

            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun setTimeFormat(time: String) : String {
            val now = LocalDateTime.now()
            val parsedTime = TimeFormatter.toLocalDateTime(time)

            if(parsedTime.toLocalDate().isEqual(now.toLocalDate())){
                return TimeFormatter.ahhmm(time)
            } else if(parsedTime.toLocalDate().isEqual(now.toLocalDate().minusDays(1L))){
                return TimeFormatter.yesterday()
            } else if(parsedTime.year == now.year){
                return TimeFormatter.MMdd(time)
            } else{
                return TimeFormatter.yyyyMMdd(time)
            }
        }
    }
}