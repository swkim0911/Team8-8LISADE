package com.softeer.togeduck.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.softeer.togeduck.R
import com.softeer.togeduck.data.local.datasource.UserDataStore
import com.softeer.togeduck.data.model.chatting.ChatMessageModel
import com.softeer.togeduck.data.repository.ChatMessageRepository
import com.softeer.togeduck.data.repository.ChatRoomListRepository
import com.softeer.togeduck.ui.chatting.ChatRoomActivity
import com.softeer.togeduck.utils.TimeFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FcmNotificationService: FirebaseMessagingService() {

    private lateinit var chatRoomListRepository : ChatRoomListRepository
    private lateinit var chatMessageRepository : ChatMessageRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        chatMessageRepository = ChatMessageRepository(this.application)
        chatRoomListRepository = ChatRoomListRepository(this.application)

        createNotification(message)
        saveMessage(message)
        sendBroadcast()
    }

    private fun createNotification(remoteMessage: RemoteMessage) {
        val channelId = "chatNotification"
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val roomName = remoteMessage.data["roomName"]!!.toString()
        val sender = remoteMessage.data["sender"]!!.toString()
        val message = remoteMessage.data["message"]!!.toString()

        val id = remoteMessage.data["roomId"]!!.toLong()
        val intent = Intent(this, ChatRoomActivity::class.java)

        intent.putExtra("id", id)
        intent.putExtra("roomName", roomName)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, id.toInt(), intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setContentTitle(roomName)
            .setContentText(message)
            .setSubText(sender)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id.toInt(), notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveMessage(remoteMessage: RemoteMessage) {
        val id = remoteMessage.data["roomId"]!!.toLong()
        val sender = remoteMessage.data["sender"]!!.toString()
        val message = remoteMessage.data["message"]!!.toString()
        val time = remoteMessage.data["createdAt"]!!.toString()
        val type = remoteMessage.data["action"]!!.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val chatRoomListModel = chatRoomListRepository.get(id)

            if (chatRoomListModel != null) {
                chatRoomListModel.recentMessage = message
                chatRoomListModel.recentTime = time
                chatRoomListModel.unreadMessageCount++

                chatRoomListRepository.update(chatRoomListModel)
            }

            val lastMessage = chatMessageRepository.getLastMessage(id)
            var location = ""

            val now = TimeFormatter.now()

            if (lastMessage == null){
                val timeChatMessageModel = ChatMessageModel(0, id, "", TimeFormatter.yyyyMMddE(now), now, "DATE", "CENTER")
                chatMessageRepository.insert(timeChatMessageModel)

                location = "FIRST_LEFT"
            } else {
                val lastMessageDate = TimeFormatter.toLocalDateTime(lastMessage.time!!).toLocalDate()
                val nowDate = TimeFormatter.toLocalDateTime(now).toLocalDate()

                if (!lastMessageDate.isEqual(nowDate)) {
                    val timeChatMessageModel = ChatMessageModel(0, id, "", TimeFormatter.yyyyMMddE(now), now, "DATE", "CENTER")
                    chatMessageRepository.insert(timeChatMessageModel)
                }

                location = "LEFT"
            }

            chatMessageRepository.insert(ChatMessageModel(0, id, sender, message, time, type, location))
        }
    }

    private fun sendBroadcast() {
        val intent = Intent().setAction("com.package.notification")
        sendBroadcast(intent)
    }
}