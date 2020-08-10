package com.example.messenger.domain.account

import com.example.messenger.domain.interactor.UseCase
import javax.inject.Inject

class Login @Inject constructor(
    private val accountRepository: IAccountRepository
) : UseCase<AccountEntity, Login.Params>() {

    override suspend fun run(params: Params) = accountRepository.login(params.email, params.password)

    data class Params(val email: String, val password: String)
}