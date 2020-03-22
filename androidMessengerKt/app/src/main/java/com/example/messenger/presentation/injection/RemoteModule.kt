package com.example.messenger.presentation.injection

import com.example.messenger.BuildConfig
import com.example.messenger.data.account.IAccountRemote
import com.example.messenger.remote.account.AccountRemoteImpl
import com.example.messenger.remote.core.Request
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
}