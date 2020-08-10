package com.example.messenger.data.messages

import com.example.messenger.domain.messages.MessageEntity
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None

interface IMessagesRemote {

    fun getChats(userId: Long, token: String): Either<Failure, List<MessageEntity>>

    fun getMessagesWithContact(
        contactId: Long,
        userId: Long,
        token: String
    ): Either<Failure, List<MessageEntity>>

    fun sendMessage(
        fromId: Long,
        toId: Long,
        token: String,
        message: String,
        image: String
    ): Either<Failure, None>

    fun deleteMessagesByUser(userId: Long, messageId: Long, token: String): Either<Failure, None>
}