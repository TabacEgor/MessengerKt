package com.example.messenger.domain.media

import android.graphics.Bitmap
import android.net.Uri
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure

interface IMediaRepository {

    fun createImageFile(): Either<Failure, Uri>

    fun encodeImageBitmap(bitmap: Bitmap?): Either<Failure, String>

    fun getPickedImage(uri: Uri?): Either<Failure, Bitmap>
}