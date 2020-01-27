package com.example.messenger.domain.account

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.exception.Failure
import javax.inject.Inject

class Register @Inject constructor(
    private val repository: IAccountRepository
) : UseCase<None, Register.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return repository.register(params.email, params.name, params.password)
    }

    data class Params(val email: String, val name: String, val password: String)
}