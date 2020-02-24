package com.example.messenger.domain.account

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.None
import javax.inject.Inject

class GetAccount @Inject constructor(
    private val accountRepository: IAccountRepository
) : UseCase<AccountEntity, None>() {

    override suspend fun run(params: None) = accountRepository.getCurrentAccount()
}