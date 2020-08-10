package com.example.messenger.domain.messages

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val messagesRepository: IMessagesRepository
) : UseCase<None, SendMessage.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        messagesRepository.sendMessage(params.toId, params.message, params.image)

    data class Params(val toId: Long, val message: String, val image: String)
}