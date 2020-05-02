package com.example.messenger.data.messages

import com.example.messenger.domain.messages.MessageEntity

interface IMessagesCache {

    fun saveMessage(entity: MessageEntity)

    fun getChats(): List<MessageEntity>

    fun getMessagesWithContact(contactId: Long): List<MessageEntity>
}