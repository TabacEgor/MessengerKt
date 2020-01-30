package com.example.messenger.cache

import com.example.messenger.data.account.IAccountCache
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.exception.Failure
import javax.inject.Inject

class AccountCacheImpl @Inject constructor(private val prefsManager: SharedPrefsManager) : IAccountCache {

    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }

    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }
}