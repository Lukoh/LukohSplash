package com.goforer.lukohsplash.domain.processor.photo

import android.app.DownloadManager
import android.app.DownloadManager.STATUS_PENDING
import android.app.DownloadManager.STATUS_SUCCESSFUL
import android.content.Context
import android.database.Cursor
import android.os.Environment
import com.goforer.lukohsplash.data.source.model.entity.photo.SaveFileInfo
import com.goforer.lukohsplash.domain.UseCase
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.base.worker.download.wrapper.DownloaderQueryWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadPhotosUseCase
@Inject
constructor(
    private val context: Context,
    private val downloaderQueryInterface: DownloaderQueryWrapper
) : UseCase<Params, Int?>() {
    private lateinit var params: Params

    companion object {
        internal const val FILE_EXISTED = 9999
    }

    override fun run(viewModelScope: CoroutineScope, params: Params) = flow {
        var downloading = true
        val downloadManager = params.query.firstParam as DownloadManager
        val url = params.query.secondParam as String
        val file = params.query.thirdParam as File
        val fileName = url.substring(url.lastIndexOf("/") + 1).take(19)
        val myFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "${fileName}.jpg")

        this@DownloadPhotosUseCase.params = params
        if (myFile.exists()) {
            emit(FILE_EXISTED)
        } else {
            val query = downloaderQueryInterface.takeQuery(downloadManager, SaveFileInfo(url, file, fileName))

            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(
                        cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    )
                    == STATUS_SUCCESSFUL
                ) {
                    downloading = false
                }

                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                emit(status)
                cursor.close()

            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = STATUS_PENDING
    )
}
