package com.example.messenger.ui.core.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.messenger.R
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

    fun showEmailInvite(context: Context, email: String) {
        val appPackageName = context.packageName
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.resources.getString(R.string.message_subject_promt_app))
        emailIntent.putExtra(Intent.EXTRA_TEXT, context.resources.getString(R.string.message_text_promt_app)
                + " " + context.resources.getString(R.string.url_google_play) + appPackageName)

        context.startActivity(Intent.createChooser(emailIntent, "Send"))
    }

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