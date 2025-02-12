package com.example.messenger.remote.account

import com.example.messenger.data.account.IAccountRemote
import com.example.messenger.domain.account.AccountEntity
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.Failure
import com.example.messenger.remote.core.Request
import com.example.messenger.remote.service.IApiService
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

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

    override fun editUser(
        userId: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ): Either<Failure, AccountEntity> {
        return request.make(service.editUser(createUserEditMap(userId, email, name, password, status,
            token, image))) { it.user }
    }

    override fun updateAccountLastSeen(
        userId: Long,
        token: String,
        lastSeen: Long
    ): Either<Failure, None> {
        return request.make(service.updateUserLastSeen(createUpdateLastSeenMap(userId, token, lastSeen))) { None() }
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

    private fun createUserEditMap(
        id: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(IApiService.PARAM_USER_ID, id.toString())
        map.put(IApiService.PARAM_EMAIL, email)
        map.put(IApiService.PARAM_NAME, name)
        map.put(IApiService.PARAM_PASSWORD, password)
        map.put(IApiService.PARAM_STATUS, status)
        map.put(IApiService.PARAM_TOKEN, token)
        if (image.startsWith("../")) {
            map.put(IApiService.PARAM_IMAGE_UPLOADED, image)
        } else {
            map.put(IApiService.PARAM_IMAGE_NEW, image)
            map.put(IApiService.PARAM_IMAGE_NEW_NAME, "user_${id}_${Date().time}_photo")
        }
        return map
    }

    private fun createUpdateLastSeenMap(
        userId: Long,
        token: String,
        lastSeen: Long
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(IApiService.PARAM_USER_ID, userId.toString())
        map.put(IApiService.PARAM_TOKEN, token)
        map.put(IApiService.PARAM_LAST_SEEN, lastSeen.toString())
        return map
    }
}