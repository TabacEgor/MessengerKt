package com.example.messenger.presentation

import com.example.messenger.cache.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator @Inject constructor(
    val sharedPrefsManager: SharedPrefsManager
) {
    fun userLoggedIn() = sharedPrefsManager.containsAnyAccount()
}