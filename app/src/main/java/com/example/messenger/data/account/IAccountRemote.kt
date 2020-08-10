package com.example.messenger.data.account

import com.example.messenger.domain.account.AccountEntity
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.Failure

interface IAccountRemote {
    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ) : Either<Failure, None>

    fun login(email: String, password: String, token: String): Either<Failure, AccountEntity>

    fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, None>

    fun editUser(
        userId: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ) : Either<Failure, AccountEntity>

    fun updateAccountLastSeen(userId: Long, token: String, lastSeen: Long): Either<Failure, None>
}