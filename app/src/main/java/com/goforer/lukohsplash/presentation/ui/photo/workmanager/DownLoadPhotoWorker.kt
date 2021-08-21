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

package com.goforer.lukohsplash.presentation.ui.photo.workmanager

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

class DownLoadPhotoWorker
@AssistedInject
constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private var downloading = true

    /**
     * Workmanager worker thread which do processing
     * in background, so it will not impact to main thread or UI
     *
     */
    override suspend fun doWork(): Result {
        var isSuccessful = true
        var errorStatus = DownloadManager.ERROR_UNKNOWN

        try {
            withContext(Dispatchers.IO) {
                val downloadManager =
                    context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val url = inputData.getString("url")!!
                val file = File(Environment.DIRECTORY_PICTURES)
                val fileName = url.substring(url.lastIndexOf("/") + 1).take(19)
                val downloadUri = Uri.parse(url)
                val dirType = file.toString()
                val request = DownloadManager.Request(downloadUri).apply {
                    setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(url.substring(url.lastIndexOf("/") + 1))
                        .setDescription("")
                        .setDestinationInExternalFilesDir(context, dirType, "${fileName}.jpg")
                }

                val query = DownloadManager.Query().setFilterById(downloadManager.enqueue(request))

                if (!file.exists()) {
                    file.mkdirs()
                }

                while (downloading) {
                    val cursor: Cursor = downloadManager.query(query)
                    cursor.moveToFirst()
                    when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                        DownloadManager.STATUS_FAILED -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.STATUS_FAILED
                        }

                        DownloadManager.STATUS_PAUSED -> {
                            Timber.d("DownloadManager Status : PAUSED")
                        }

                        DownloadManager.STATUS_PENDING -> {
                            Timber.d("DownloadManager Status : PENDING")
                        }

                        DownloadManager.STATUS_RUNNING -> {
                            Timber.d("DownloadManager Status : RUNNING")
                        }

                        DownloadManager.STATUS_SUCCESSFUL -> {
                            isSuccessful = true
                            downloading = false
                        }

                        DownloadManager.ERROR_UNKNOWN -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_UNKNOWN
                        }

                        DownloadManager.ERROR_FILE_ERROR -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_FILE_ERROR
                        }

                        DownloadManager.ERROR_INSUFFICIENT_SPACE -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_INSUFFICIENT_SPACE
                        }

                        DownloadManager.ERROR_HTTP_DATA_ERROR -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_HTTP_DATA_ERROR
                        }

                        DownloadManager.ERROR_UNHANDLED_HTTP_CODE -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_UNHANDLED_HTTP_CODE
                        }

                        else -> {
                        }
                    }

                    cursor.close()
                }
            }
        } catch (e: Exception) {
            return Result.failure()
        }

        return if (isSuccessful)
            Result.success()
        else
            Result.failure(workDataOf("error" to errorStatus))
    }
}
