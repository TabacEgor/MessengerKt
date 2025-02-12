package com.example.messenger.remote.core

import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Request @Inject constructor(private val networkHandler: NetworkHandler) {

    fun <T : BaseResponse, R> make(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return when (networkHandler.isConnected) {
            true -> execute(call, transform)
            false, null -> Either.Left(Failure.NetworkConnectionError)
        }
    }

    private fun <T : BaseResponse, R> execute(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return try {
            var response = call.clone().execute()
            when (response.isSucceed()) {
                true -> Either.Right(transform(response.body()!!))
                false -> Either.Left(response.parseError())
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}

fun <T : BaseResponse> Response<T>.isSucceed(): Boolean {
    return isSuccessful && body() != null && (body() as BaseResponse).success == 1
}

fun <T : BaseResponse> Response<T>.parseError(): Failure {
    val message = (body() as BaseResponse).message
    return when (message) {
        "there is a user has this email",
        "email already exists" -> Failure.EmailAlreadyExistError
        "error in email or password" -> Failure.AuthError
        "Token is invalid" -> Failure.TokenError
        "this contact is already in your friends list" -> Failure.AlreadyFriendError
        "already found in your friend requests",
        "you requested adding this friend before" -> Failure.AlreadyRequestedFriendError
        "No Contact has this email" -> Failure.ContactNotFoundError
        else -> Failure.ServerError
    }
}