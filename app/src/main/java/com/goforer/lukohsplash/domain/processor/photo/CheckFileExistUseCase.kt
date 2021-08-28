package com.goforer.lukohsplash.domain.processor.photo

import android.content.Context
import android.os.Environment
import com.goforer.lukohsplash.domain.UseCase
import com.goforer.lukohsplash.presentation.vm.Params
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckFileExistUseCase
@Inject
constructor(
    private val context: Context
) : UseCase<Boolean>() {
    override fun run(viewModelScope: CoroutineScope, params: Params) = flow {
        val url = params.query.firstParam as String
        val fileName = url.substring(url.lastIndexOf("/") + 1).take(19)
        val myFile =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "${fileName}.jpg")

        if (myFile.exists()) {
            emit(true)
        } else {
            emit(false)
        }
    }.shareIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        replay = 1
    )
}
