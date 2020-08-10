package com.example.messenger.domain.media

import android.graphics.Bitmap
import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import javax.inject.Inject

class EncodeImageBitmap @Inject constructor(
    private val mediaRepository: IMediaRepository
) : UseCase<String, Bitmap?>() {

    override suspend fun run(params: Bitmap?): Either<Failure, String> = mediaRepository.encodeImageBitmap(params)
}