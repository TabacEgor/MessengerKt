package com.example.messenger.ui.firebase

import android.util.Log
import com.example.messenger.domain.account.UpdateToken
import com.example.messenger.ui.App
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

const val TAG = "FirebaseService"

class FirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var updateToken: UpdateToken

    override fun onCreate() {
        super.onCreate()
        App.appComponent.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
    }

    override fun onNewToken(token: String) {
        Log.e(TAG, "Firebase token : $token")
        if (token.isNotEmpty()) {
            updateToken(UpdateToken.Params(token))
        }
    }
}