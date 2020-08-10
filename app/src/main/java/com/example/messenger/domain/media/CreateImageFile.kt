package com.example.messenger.domain.media

import android.net.Uri
import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None
import javax.inject.Inject

class CreateImageFile @Inject constructor(
    private val mediaRepository: IMediaRepository
) : UseCase<Uri, None>() {

    override suspend fun run(params: None): Either<Failure, Uri> = mediaRepository.createImageFile()
}