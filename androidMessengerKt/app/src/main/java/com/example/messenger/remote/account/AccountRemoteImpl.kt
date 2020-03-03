package com.example.messenger.remote.account

import com.example.messenger.data.IAccountRemote
import com.example.messenger.domain.account.AccountEntity
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.Failure
import com.example.messenger.remote.core.Request
import com.example.messenger.remote.service.IApiService
import javax.inject.Inject

class AccountRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: IApiService
) : IAccountRemote {

    override fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None> {
        return request.make(service.register(createRegisterMap(email, name, password, token, userDate))) { None() }
    }

    override fun login(
        email: String,
        password: String,
        token: String
    ): Either<Failure, AccountEntity> {
        return request.make(service.login(createLoginMap(email, password, token))) { it.user }
    }

    override fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, None> {
        return request.make(service.updateToken(createUpdateTokenMap(userId, token, oldToken))) { None() }
    }

    private fun createRegisterMap(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ) : Map<String, String> {
        val map = HashMap<String, String>()
        map.put(IApiService.PARAM_EMAIL, email)
        map.put(IApiService.PARAM_NAME, name)
        map.put(IApiService.PARAM_PASSWORD, password)
        map.put(IApiService.PARAM_TOKEN, token)
        map.put(IApiService.PARAM_USER_DATE, userDate.toString())
        return map
    }

    private fun createLoginMap(
        email: String,
        password: String,
        oldToken: String
    ) : Map<String, String> {
        val map = HashMap<String, String>()
        map.put(IApiService.PARAM_EMAIL, email)
        map.put(IApiService.PARAM_PASSWORD, password)
        map.put(IApiService.PARAM_TOKEN, oldToken)
        return map
    }

    private fun createUpdateTokenMap(
        userId: Long,
        token: String,
        oldToken: String
    ) : Map<String, String> {
        val map = HashMap<String, String>()
        map.put(IApiService.PARAM_USER_ID, userId.toString())
        map.put(IApiService.PARAM_TOKEN, token)
        map.put(IApiService.PARAM_OLD_TOKEN, oldToken)
        return map
    }
}