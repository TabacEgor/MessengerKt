package com.example.messenger.remote.messages

import com.example.messenger.domain.messages.MessageEntity
import com.example.messenger.remote.core.BaseResponse

class GetMessagesResponse(
    success: Int,
    message: String,
    val messages: List<MessageEntity>
) : BaseResponse(success, message)