package com.example.messenger.ui

import android.app.Application
import com.example.messenger.presentation.injection.AppModule
import com.example.messenger.presentation.injection.CacheModule
import com.example.messenger.presentation.injection.RemoteModule
import com.example.messenger.presentation.injection.ViewModelModule
import com.example.messenger.ui.core.navigation.RouteActivity
import com.example.messenger.ui.register.RegisterActivity
import com.example.messenger.ui.firebase.FirebaseService
import com.example.messenger.ui.home.ChatsFragment
import com.example.messenger.ui.home.HomeActivity
import com.example.messenger.ui.login.LoginFragment
import com.example.messenger.ui.register.RegisterFragment
import dagger.Component
import okhttp3.Route
import javax.inject.Singleton

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}

@Singleton
@Component(modules = [AppModule::class, CacheModule::class, RemoteModule::class, ViewModelModule::class])
interface AppComponent {

    // activities
    fun inject(activity: RegisterActivity)
    fun inject(activity: RouteActivity)
    fun inject(activity: HomeActivity)

    // fragments
    fun inject(fragment: RegisterFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: ChatsFragment)

    // services
    fun inject(service: FirebaseService)
}