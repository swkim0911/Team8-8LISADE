package com.softeer.togeduck.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.softeer.togeduck.R
import com.softeer.togeduck.data.local.datasource.UserDataStore
import com.softeer.togeduck.data.repository.FcmTokenRepository
import com.softeer.togeduck.room.TogeduckDatabase
import com.softeer.togeduck.ui.chatting.ChatRoomActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FcmNotificationService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CoroutineScope(Dispatchers.IO).launch {
            FcmTokenRepository(UserDataStore(applicationContext)).storeFcmToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createNotification(message)
    }

    private fun createNotification(remoteMessage: RemoteMessage) {
        val id = remoteMessage.data["roomId"]!!.toLong()
        val intent = Intent(this, ChatRoomActivity::class.java)

        intent.putExtra("id", id)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, id.toInt(), intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE)

        val channelId = "chatNotification"
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val sender = remoteMessage.data["sender"]!!.toString()
        val message = remoteMessage.data["message"]!!.toString()

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setContentTitle(sender)
            .setContentText(message)
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

    private fun saveMessage(remoteMessage: RemoteMessage) {
        val db = TogeduckDatabase.getInstance(applicationContext)

        val id = remoteMessage.data["roomId"]!!.toLong()
        val sender = remoteMessage.data["sender"]!!.toString()
        val message = remoteMessage.data["message"]!!.toString()
        val time = remoteMessage.data["createdAt"]!!.toString()
        val type = remoteMessage.data["action"]!!.toString()

        CoroutineScope(Dispatchers.IO).launch {
            // TODO 메세지 데이터베이스에 저장
        }
    }
}