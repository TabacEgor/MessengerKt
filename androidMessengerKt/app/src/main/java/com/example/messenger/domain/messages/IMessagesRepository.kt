package com.example.messenger.domain.messages

import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None

interface IMessagesRepository {

    fun sendMessage(
        toId: Long,
        message: String,
        image: String
    ): Either<Failure, None>

    fun getChats(needFetch: Boolean): Either<Failure, List<MessageEntity>>

    fun getMessagesWithContact(
        contactId: Long,
        needFetch: Boolean
    ): Either<Failure, List<MessageEntity>>
}