package com.example.messenger.presentation.injection

import android.content.Context
import android.content.SharedPreferences
import com.example.messenger.cache.AccountCacheImpl
import com.example.messenger.cache.SharedPrefsManager
import com.example.messenger.data.account.IAccountCache
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
    fun provideAccountCache(prefsManager: SharedPrefsManager): IAccountCache = AccountCacheImpl(prefsManager)
}