package com.example.messenger.domain.media

import android.graphics.Bitmap
import android.net.Uri
import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import javax.inject.Inject

class GetPickedImage @Inject constructor(
    private val mediaRepository: IMediaRepository
) : UseCase<Bitmap, Uri?>() {

    override suspend fun run(params: Uri?): Either<Failure, Bitmap> = mediaRepository.getPickedImage(params)
}