package com.example.messenger.data.messages

import com.example.messenger.data.account.IAccountCache
import com.example.messenger.domain.messages.IMessagesRepository
import com.example.messenger.domain.messages.MessageEntity
import com.example.messenger.domain.type.*

class MessagesRepositoryImpl(
    private val messagesRemote: IMessagesRemote,
    private val messagesCache: IMessagesCache,
    private val accountCache: IAccountCache
) : IMessagesRepository {

    override fun getChats(needFetch: Boolean): Either<Failure, List<MessageEntity>> {
        return accountCache.getCurrentAccount().flatMap { account ->
            return@flatMap if (needFetch) {
                messagesRemote.getChats(account.id, account.token).onNext {
                    it.map { message ->
                        if (message.senderId == account.id) {
                            message.fromMe = true
                        }
                    }
                    messagesCache.saveMessages(it)
                }
            } else {
                Either.Right(messagesCache.getChats())
            }
        }.map {
            it.distinctBy {
                it.contact?.id
            }
        }
    }

    override fun getMessagesWithContact(
        contactId: Long,
        needFetch: Boolean
    ): Either<Failure, List<MessageEntity>> {
        return accountCache.getCurrentAccount().flatMap { account ->
            return@flatMap if (needFetch) {
                messagesRemote.getMessagesWithContact(contactId, account.id, account.token).onNext {
                    it.map { message ->

                        if (message.senderId == account.id) {
                            message.fromMe = true
                        }

                        val contact = messagesCache.getChats().firstOrNull() { it.contact?.id == contactId }?.contact
                        message.contact = contact
                    }
                    messagesCache.saveMessages(it)
                }
            } else {
                Either.Right(messagesCache.getMessagesWithContact(contactId))
            }
        }
    }

    override fun deleteMessagesByUser(messageId: Long): Either<Failure, None> {
        return accountCache.getCurrentAccount().flatMap {
            messagesCache.deleteMessagesByUser(messageId)
            messagesRemote.deleteMessagesByUser(it.id, messageId, it.token)
        }
    }

    override fun sendMessage(toId: Long, message: String, image: String): Either<Failure, None> =
        accountCache.getCurrentAccount().flatMap {
            messagesRemote.sendMessage(it.id, toId, it.token, message, image)
        }
}