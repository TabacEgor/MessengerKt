package com.example.messenger.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.messenger.cache.friends.FriendsDao
import com.example.messenger.cache.messages.MessagesDao
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.domain.messages.MessageEntity

@Database(entities = [FriendEntity::class, MessageEntity::class], version = 5, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {

    abstract val friendsDao: FriendsDao
    abstract val messagesDao: MessagesDao

    companion object {
        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getInstance(context: Context): ChatDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }

            return instance
        }
    }
}