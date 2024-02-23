package com.softeer.togeduck.ui.chatting

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.chatting.ChatMessageModel
import com.softeer.togeduck.databinding.RvItemChatMessageCenterBinding
import com.softeer.togeduck.databinding.RvItemChatMessageFirstLeftBinding
import com.softeer.togeduck.databinding.RvItemChatMessageLeftBinding
import com.softeer.togeduck.databinding.RvItemChatMessageRightBinding
import com.softeer.togeduck.utils.TimeFormatter

class ChatMessageListAdapter(private val items: ArrayList<ChatMessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var firstLeftBinding: RvItemChatMessageFirstLeftBinding
    private lateinit var leftBinding: RvItemChatMessageLeftBinding
    private lateinit var centerBinding: RvItemChatMessageCenterBinding
    private lateinit var rightBinding: RvItemChatMessageRightBinding

    override fun getItemViewType(position: Int): Int {
        return when(items[position].location){
            "FIRST_LEFT" -> 1
            "LEFT"-> 2
            "CENTER" -> 3
            else -> 4
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            1 -> {
                firstLeftBinding = RvItemChatMessageFirstLeftBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return FirstLeftViewHolder(firstLeftBinding)
            }

            2 -> {
                leftBinding = RvItemChatMessageLeftBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return LeftViewHolder(leftBinding)
            }

            3 -> {
                centerBinding = RvItemChatMessageCenterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return CenterViewHolder(centerBinding)
            }

            else -> {
                rightBinding = RvItemChatMessageRightBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return RightViewHolder(rightBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FirstLeftViewHolder -> holder.bind(items, position)
            is LeftViewHolder -> holder.bind(items, position)
            is CenterViewHolder -> holder.bind(items[position])
            is RightViewHolder -> holder.bind(items, position)
        }
    }

    fun addItem(chatMessageModel: ChatMessageModel){
        items.add(chatMessageModel)
        notifyItemInserted(items.size - 1)
    }

    fun getLastItem() : ChatMessageModel{
        return items[items.size - 1]
    }

    class FirstLeftViewHolder(private val binding: RvItemChatMessageFirstLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(items: List<ChatMessageModel>, position: Int) {
            val item = items[position]

            binding.apply {
                chatMessageNickname.text = item.nickname
                chatMessageMessage.text = item.message

                if (isTimeVisibility(items, position)) {
                    chatMessageTime.text = TimeFormatter.ahhmm(item.time!!)
                    chatMessageTime.visibility = View.VISIBLE
                } else {
                    chatMessageTime.visibility = View.INVISIBLE
                }
            }
        }

        private fun isTimeVisibility(items: List<ChatMessageModel>, position: Int) : Boolean {
            if (position + 1 == items.size) return true

            return items[position].nickname != items[position + 1].nickname
        }
    }

    class LeftViewHolder(private val binding: RvItemChatMessageLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(items: List<ChatMessageModel>, position: Int) {
            val item = items[position]

            binding.apply {
                chatMessageMessage.text = item.message

                if (isTimeVisibility(items, position)) {
                    chatMessageTime.text = TimeFormatter.ahhmm(item.time!!)
                    chatMessageTime.visibility = View.VISIBLE
                } else {
                    chatMessageTime.visibility = View.INVISIBLE
                }
            }
        }

        private fun isTimeVisibility(items: List<ChatMessageModel>, position: Int) : Boolean {
            if (position + 1 == items.size) return true

            return items[position].nickname != items[position + 1].nickname
        }
    }

    class CenterViewHolder(private val binding: RvItemChatMessageCenterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatMessageModel) {
            binding.apply {
                chatMessageMessage.text = item.message
            }
        }
    }

    class RightViewHolder(private val binding: RvItemChatMessageRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(items: List<ChatMessageModel>, position: Int) {
            val item = items[position]

            binding.apply {
                chatMessageMessage.text = item.message

                if (isTimeVisibility(items, position)) {
                    chatMessageTime.text = TimeFormatter.ahhmm(item.time!!)
                    chatMessageTime.visibility = View.VISIBLE
                } else {
                    chatMessageTime.visibility = View.INVISIBLE
                }
            }
        }

        private fun isTimeVisibility(items: List<ChatMessageModel>, position: Int) : Boolean {
            if (position + 1 == items.size) return true

            return items[position].nickname != items[position + 1].nickname
        }
    }
}