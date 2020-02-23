package com.example.messenger.presentation.injection

import android.content.Context
import com.example.messenger.data.IAccountRemote
import com.example.messenger.data.account.AccountRepositoryImpl
import com.example.messenger.data.account.IAccountCache
import com.example.messenger.domain.account.IAccountRepository
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
}