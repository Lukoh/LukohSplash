/*
 * Copyright (C) 2021 Lukoh Nam, goForer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.goforer.lukohsplash.domain.processor.photo

import android.app.DownloadManager
import android.app.DownloadManager.STATUS_SUCCESSFUL
import android.content.Context
import android.database.Cursor
import android.os.Environment
import com.goforer.lukohsplash.data.source.model.entity.photo.SaveFileInfo
import com.goforer.lukohsplash.domain.UseCase
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.base.worker.download.wrapper.DownloaderQueryWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
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

    override fun run(lifecycleScope: CoroutineScope, params: Params) = flow {
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
    }
}
