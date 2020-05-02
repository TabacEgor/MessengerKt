package com.example.messenger.presentation.injection

import android.content.Context
import android.content.SharedPreferences
import com.example.messenger.cache.AccountCacheImpl
import com.example.messenger.cache.ChatDatabase
import com.example.messenger.cache.SharedPrefsManager
import com.example.messenger.cache.friends.FriendsDao
import com.example.messenger.data.account.IAccountCache
import com.example.messenger.data.friends.IFriendsCache
import com.example.messenger.data.messages.IMessagesCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAccountCache(
        prefsManager: SharedPrefsManager,
        chatDatabase: ChatDatabase
    ): IAccountCache = AccountCacheImpl(prefsManager, chatDatabase)

    @Provides
    @Singleton
    fun provideChatDatabase(context: Context): ChatDatabase = ChatDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideFriendsCache(chatDatabase: ChatDatabase): IFriendsCache = chatDatabase.friendsDao

    @Provides
    @Singleton
    fun provideMessagesCache(chatDatabase: ChatDatabase): IMessagesCache = chatDatabase.messagesDao
}