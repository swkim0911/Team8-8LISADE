package com.softeer.togeduck.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.softeer.togeduck.data.model.chatting.ChatRoomsModel
import com.softeer.togeduck.room.dao.ChatRoomsDao

@Database(entities = [ChatRoomsModel::class], version = 1)
abstract class TogeduckDatabase : RoomDatabase(){
    abstract fun chatRoomsDao(): ChatRoomsDao

    companion object {
        private var instance: TogeduckDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TogeduckDatabase? {
            if (instance == null) {
                synchronized(TogeduckDatabase::class){
                    instance = databaseBuilder(
                        context.applicationContext,
                        TogeduckDatabase::class.java,
                        "togeduck.db"
                    ).build()
                }
            }
            return instance
        }
    }
}