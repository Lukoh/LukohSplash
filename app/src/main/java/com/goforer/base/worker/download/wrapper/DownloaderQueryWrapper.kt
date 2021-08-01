package com.goforer.base.worker.download.wrapper

import android.app.DownloadManager
import com.goforer.lukohsplash.data.source.model.entity.photo.SaveFileInfo

interface DownloaderQueryWrapper {
    fun takeQuery(downloadManager: DownloadManager, saveFileInfo: SaveFileInfo): DownloadManager.Query
}