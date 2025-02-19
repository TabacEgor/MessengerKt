package com.example.messenger.domain.messages

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import javax.inject.Inject

class GetChats @Inject constructor(
    private val messagesRepository: IMessagesRepository
) : UseCase<List<MessageEntity>, GetChats.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<MessageEntity>> =
        messagesRepository.getChats(params.needFetch)

    data class Params(val needFetch: Boolean)
}