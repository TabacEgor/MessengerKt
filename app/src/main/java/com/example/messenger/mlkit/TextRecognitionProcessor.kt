package com.example.messenger.mlkit

import android.graphics.Bitmap
import android.util.Log
import com.example.messenger.mlkit.common.CameraImageGraphic
import com.example.messenger.mlkit.common.FrameMetadata
import com.example.messenger.mlkit.common.GraphicOverlay
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import java.io.IOException

/** Processor for the text recognition demo.  */
class TextRecognitionProcessor(val callback: ICallback) : VisionProcessorBase<FirebaseVisionText>() {

    private val detector: FirebaseVisionTextRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer

    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Text Detector: $e")
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<FirebaseVisionText> {
        return detector.processImage(image)
    }

    override fun onSuccess(
        originalCameraImage: Bitmap?,
        results: FirebaseVisionText,
        frameMetadata: FrameMetadata,
        graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.clear()
        originalCameraImage.let { image ->
            val imageGraphic = CameraImageGraphic(graphicOverlay, image)
            graphicOverlay.add(imageGraphic)
        }
        val blocks = results.textBlocks
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                val elements = lines[j].elements
                for (k in elements.indices) {
                    val textGraphic = TextGraphic(graphicOverlay, elements[k])
                    graphicOverlay.add(textGraphic)
                }
            }
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "Text detection failed.$e")
    }

    companion object {

        private const val TAG = "TextRecProc"
    }
}