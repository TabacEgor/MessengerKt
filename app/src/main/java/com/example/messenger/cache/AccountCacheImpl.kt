package com.example.messenger.cache

import com.example.messenger.data.account.IAccountCache
import com.example.messenger.domain.account.AccountEntity
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.Failure
import javax.inject.Inject

class AccountCacheImpl @Inject constructor(
    private val prefsManager: SharedPrefsManager,
    private val chatDatabase: ChatDatabase
) : IAccountCache {

    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }

    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }

    override fun logout(): Either<Failure, None> {
        chatDatabase.clearAllTables()
        return prefsManager.removeAccount()
    }

    override fun getCurrentAccount(): Either<Failure, AccountEntity> {
        return prefsManager.getAccount()
    }

    override fun saveAccount(account: AccountEntity): Either<Failure, None> {
        return prefsManager.saveAccount(account)
    }

    override fun checkAuth(): Either<Failure, Boolean> = prefsManager.containsAnyAccount()
}