package com.example.messenger.remote.messages

import com.example.messenger.data.messages.IMessagesRemote
import com.example.messenger.domain.messages.MessageEntity
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None
import com.example.messenger.remote.core.Request
import com.example.messenger.remote.service.IApiService
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class MessagesRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: IApiService
) : IMessagesRemote {

    override fun getChats(userId: Long, token: String): Either<Failure, List<MessageEntity>> {
        return request.make(service.getLastMessages(
            createGetLastMessagesMap(userId, token))) { it.messages }
    }

    override fun getMessagesWithContact(
        contactId: Long,
        userId: Long,
        token: String
    ): Either<Failure, List<MessageEntity>> {
        return request.make(service.getMessagesWithContact(
            createGetMessagesWithContactMap(contactId, userId, token))) { it.messages }
    }

    override fun sendMessage(
        fromId: Long,
        toId: Long,
        token: String,
        message: String,
        image: String
    ): Either<Failure, None> {
        return request.make(service.sendMessage(
            createSendMessageMap(fromId, toId, token, message, image))) { None() }
    }

    override fun deleteMessagesByUser(
        userId: Long,
        messageId: Long,
        token: String
    ): Either<Failure, None> {
        return request.make(service.deleteMessagesByUser(
            createDeleteMessagesMap(userId, messageId, token))) { None() }
    }

    private fun createGetLastMessagesMap(
        userId: Long,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(IApiService.PARAM_USER_ID, userId.toString())
        map.put(IApiService.PARAM_TOKEN, token)

        return map
    }

    private fun createGetMessagesWithContactMap(
        contactId: Long,
        userId: Long,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(IApiService.PARAM_CONTACT_ID, contactId.toString())
        map.put(IApiService.PARAM_USER_ID, userId.toString())
        map.put(IApiService.PARAM_TOKEN, token)

        return map
    }

    private fun createSendMessageMap(
        fromId: Long,
        toId: Long,
        token: String,
        message: String,
        image: String
    ): Map<String, String> {
        val date = Date().time
        var type = 1

        val map = HashMap<String, String>()

        if (image.isNotBlank()) {
            type = 2
            map.put(IApiService.PARAM_IMAGE_NEW, image)
            map.put(IApiService.PARAM_IMAGE_NEW_NAME, "user_${fromId}_${date}_photo")
        }

        map.put(IApiService.PARAM_SENDER_ID, fromId.toString())
        map.put(IApiService.PARAM_RECEIVER_ID, toId.toString())
        map.put(IApiService.PARAM_TOKEN, token)
        map.put(IApiService.PARAM_MESSAGE, message)
        map.put(IApiService.PARAM_MESSAGE_TYPE, type.toString())
        map.put(IApiService.PARAM_MESSAGE_DATE, date.toString())

        return map
    }

    private fun createDeleteMessagesMap(
        userId: Long,
        messageId: Long,
        token: String
    ): Map<String, String> {
        val itemsArrayObject = JSONObject()
        val itemsArray = JSONArray()
        val itemObject = JSONObject()

        itemObject.put("message_id", messageId)
        itemsArray.put(itemObject)
        itemsArrayObject.put("messages", itemsArray)

        val map = HashMap<String, String>()
        map.put(IApiService.PARAM_USER_ID, userId.toString())
        map.put(IApiService.PARAM_MESSAGES_IDS, itemsArrayObject.toString())
        map.put(IApiService.PARAM_TOKEN, token)

        return map
    }
}