package com.example.messenger.presentation.injection

import com.example.messenger.BuildConfig
import com.example.messenger.data.account.IAccountRemote
import com.example.messenger.data.friends.IFriendsRemote
import com.example.messenger.data.messages.IMessagesRemote
import com.example.messenger.remote.account.AccountRemoteImpl
import com.example.messenger.remote.core.Request
import com.example.messenger.remote.friends.FriendsRemoteImpl
import com.example.messenger.remote.messages.MessagesRemoteImpl
import com.example.messenger.remote.service.IApiService
import com.example.messenger.remote.service.ServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideApiService(): IApiService = ServiceFactory.makeService(BuildConfig.DEBUG)

    @Provides
    @Singleton
    fun provideAccountRemote(request: Request, apiService: IApiService): IAccountRemote {
        return AccountRemoteImpl(request, apiService)
    }

    @Provides
    @Singleton
    fun provideFriendsRemote(request: Request, apiService: IApiService): IFriendsRemote {
        return FriendsRemoteImpl(request, apiService)
    }

    @Provides
    @Singleton
    fun provideMessagesRemote(request: Request, apiService: IApiService): IMessagesRemote {
        return MessagesRemoteImpl(request, apiService)
    }
}