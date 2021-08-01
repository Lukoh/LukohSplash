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