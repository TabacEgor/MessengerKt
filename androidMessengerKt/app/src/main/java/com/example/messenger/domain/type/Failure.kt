package com.example.messenger.domain.type

/**
 *  Base class for handling errors/failures/exceptions
 */
sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()
    object EmailAlreadyExistError : Failure()
    object AuthError : Failure()
    object TokenError : Failure()
    object NoSavedAccountsError : Failure()
    object AlreadyFriendError : Failure()
    object AlreadyRequestedFriendError : Failure()
    object ContactNotFoundError : Failure()
}