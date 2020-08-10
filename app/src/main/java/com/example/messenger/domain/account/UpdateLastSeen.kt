package com.example.messenger.domain.account

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None
import javax.inject.Inject

class UpdateLastSeen @Inject constructor(
    private val accountRepository: IAccountRepository
) : UseCase<None, None>() {

    override suspend fun run(params: None): Either<Failure, None> =
        accountRepository.updateAccountLastSeen()
}