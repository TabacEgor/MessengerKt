package com.example.messenger.presentation.injection

import android.content.Context
import com.example.messenger.data.account.IAccountRemote
import com.example.messenger.data.account.AccountRepositoryImpl
import com.example.messenger.data.account.IAccountCache
import com.example.messenger.data.friends.FriendsRepositoryImpl
import com.example.messenger.data.friends.IFriendsCache
import com.example.messenger.data.friends.IFriendsRemote
import com.example.messenger.data.media.MediaRepositoryImpl
import com.example.messenger.domain.account.IAccountRepository
import com.example.messenger.domain.friends.IFriendsRepository
import com.example.messenger.domain.media.IMediaRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideAccountRepository(remote : IAccountRemote, cache : IAccountCache): IAccountRepository {
        return AccountRepositoryImpl(remote, cache)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(remote: IFriendsRemote, accountCache: IAccountCache, friendsCache: IFriendsCache): IFriendsRepository {
        return FriendsRepositoryImpl(accountCache, remote, friendsCache)
    }

    @Provides
    @Singleton
    fun provideMediaRepository(context: Context): IMediaRepository {
        return MediaRepositoryImpl(context)
    }
 }