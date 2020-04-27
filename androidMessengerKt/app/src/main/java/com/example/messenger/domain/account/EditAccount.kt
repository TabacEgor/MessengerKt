package com.example.messenger.domain.account

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import javax.inject.Inject

class EditAccount @Inject constructor(
    private val accountRepository: IAccountRepository
) : UseCase<AccountEntity, AccountEntity>() {

    override suspend fun run(params: AccountEntity): Either<Failure, AccountEntity> {
        return accountRepository.editAccount(params)
    }
}