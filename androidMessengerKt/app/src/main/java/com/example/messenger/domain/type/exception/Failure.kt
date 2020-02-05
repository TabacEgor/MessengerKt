package com.example.messenger.domain.type.exception

/**
 *  Base class for handling errors/failures/exceptions
 */
sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()
    object EmailAlreadyExistError : Failure()
}