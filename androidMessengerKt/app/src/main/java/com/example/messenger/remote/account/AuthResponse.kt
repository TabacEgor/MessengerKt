package com.example.messenger.remote.account

import com.example.messenger.domain.account.AccountEntity
import com.example.messenger.remote.core.BaseResponse

class AuthResponse(
    success: Int,
    message: String,
    val user: AccountEntity
) : BaseResponse(success, message)