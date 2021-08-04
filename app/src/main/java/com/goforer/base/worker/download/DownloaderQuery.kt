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

package com.goforer.base.worker.download

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import com.goforer.lukohsplash.data.source.model.entity.photo.SaveFileInfo
import com.goforer.base.worker.download.wrapper.DownloaderQueryWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloaderQuery
@Inject
constructor(val context: Context) : DownloaderQueryWrapper {
    override fun takeQuery(downloadManager: DownloadManager, saveFileInfo: SaveFileInfo): DownloadManager.Query {
        with(saveFileInfo) {
            if (!file.exists()) {
                file.mkdirs()
            }

            val downloadUri = Uri.parse(url)
            val dirType = file.toString()
            val request = DownloadManager.Request(downloadUri).apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(url.substring(url.lastIndexOf("/") + 1))
                    .setDescription("")
                    .setDestinationInExternalFilesDir(context,  dirType, "${fileName}.jpg")
            }

            return DownloadManager.Query().setFilterById(downloadManager.enqueue(request))
        }
    }
}