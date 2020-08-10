package com.example.messenger.presentation

import com.example.messenger.domain.account.CheckAuth
import com.example.messenger.domain.type.None
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator @Inject constructor(
    val checkAuth: CheckAuth
) {
    fun userLoggedIn(body: (Boolean) -> Unit) = checkAuth(None()) {
        it.either({ body(false) }, { body(it) })
    }
}