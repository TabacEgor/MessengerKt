package com.example.messenger.domain.messages

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None
import javax.inject.Inject

class DeleteMessage @Inject constructor(
    private val messagesRepository: IMessagesRepository
) : UseCase<None, DeleteMessage.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        messagesRepository.deleteMessagesByUser(params.messageId)

    data class Params(val messageId: Long)
}