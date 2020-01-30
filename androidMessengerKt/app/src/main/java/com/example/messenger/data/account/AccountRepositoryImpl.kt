package com.example.messenger.data.account

import com.example.messenger.data.IAccountRemote
import com.example.messenger.domain.account.AccountEntity
import com.example.messenger.domain.account.IAccountRepository
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.exception.Failure
import com.example.messenger.domain.type.flatMap
import java.util.*

class AccountRepositoryImpl(
    private val accountRemote: IAccountRemote,
    private val accountCache: IAccountCache
) : IAccountRepository {

    override fun login(email: String, password: String): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Login is not supported")
    }

    override fun logout(): Either<Failure, None> {
        throw UnsupportedOperationException("Logout is not supported")
    }

    override fun register(email: String, name: String, password: String): Either<Failure, None> {
        return accountCache.getToken().flatMap {
            accountRemote.register(email, name, password, it, Calendar.getInstance().timeInMillis)
        }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        throw UnsupportedOperationException("Password recovery is not supported")
    }

    override fun getCurrentAccount(): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Get account is not supported")
    }

    override fun updateAccountToken(token: String): Either<Failure, None> {
        return accountCache.saveToken(token)
    }

    override fun updateAccountLastSeen(): Either<Failure, None> {
        throw UnsupportedOperationException("Updating last seen is not supported")
    }

    override fun editAccount(entity: AccountEntity): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Editing account is not supported")
    }

}