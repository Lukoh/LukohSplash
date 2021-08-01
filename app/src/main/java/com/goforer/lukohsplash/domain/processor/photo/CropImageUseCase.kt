package com.goforer.lukohsplash.domain.processor.photo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.goforer.lukohsplash.domain.UseCase
import com.goforer.lukohsplash.presentation.vm.Params
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CropImageUseCase
@Inject
constructor() : UseCase<Params, Bitmap>() {
    @Suppress("UNCHECKED_CAST")
    override fun run(viewModelScope: CoroutineScope, params: Params) = flow {
        emit(cropImage(params.query.firstParam as Bitmap))
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        replay = 1
    )

    private fun cropImage(bitmap: Bitmap): Bitmap {
        val corpBitmap = Bitmap.createBitmap(
            bitmap, 0, (bitmap.height / 2) - CORRECTION_Y_VALUE, bitmap.width, bitmap.height / 2
        )

        val stream = ByteArrayOutputStream()

        corpBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val bitmapData = stream.toByteArray()

        return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size)
    }
}