package com.example.messenger.data

import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.exception.Failure

interface IAccountCache {

    fun getToken(): Either<Failure, String>

    fun saveToken(token: String): Either<Failure, None>

//    fun logout(): Either<Failure, None>
//
//    fun getCurrentAccount(): Either<Failure, AccountEntity>
//
//    fun saveAccount(account: AccountEntity): Either<Failure, None>
//
//    fun checkAuth(): Either<Failure, Boolean>
}
