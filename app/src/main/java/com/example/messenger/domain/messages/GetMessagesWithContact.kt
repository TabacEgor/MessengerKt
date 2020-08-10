package com.example.messenger.domain.messages

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import javax.inject.Inject

class GetMessagesWithContact @Inject constructor(
    private val messagesRepository: IMessagesRepository
) : UseCase<List<MessageEntity>, GetMessagesWithContact.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<MessageEntity>> =
        messagesRepository.getMessagesWithContact(params.contactId, params.needFetch)

    data class Params(val contactId: Long, val needFetch: Boolean)
}