package com.example.messenger.data

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
}