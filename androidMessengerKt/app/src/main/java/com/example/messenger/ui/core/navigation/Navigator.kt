package com.example.messenger.ui.core.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.messenger.ui.home.HomeActivity
import com.example.messenger.ui.login.LoginActivity
import com.example.messenger.ui.register.RegisterActivity
import java.net.Authenticator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(private val authenticator: com.example.messenger.presentation.Authenticator) {

    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showHome(context, false)
            false -> showLogin(context, false)
        }
    }

    fun showHome(context: Context, newTask: Boolean = true) = context.startActivity<HomeActivity>(newTask = newTask)

    fun showLogin(context: Context, newTask: Boolean = true) = context.startActivity<LoginActivity>(newTask = newTask)

    fun showSignUp(context: Context) = context.startActivity<RegisterActivity>()

    // newTask – определяет, очищать(если true), или не очищать(если false) backstack приложения
    private inline fun <reified T> Context.startActivity(newTask: Boolean = false, args: Bundle? = null) {
        this.startActivity(Intent(this, T::class.java).apply {
            if (newTask) {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // флаги очищения стека
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            putExtra("args", args)
        })
    }
}