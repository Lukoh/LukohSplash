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

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.*
import com.goforer.lukohsplash.domain.UseCase
import com.goforer.lukohsplash.domain.processor.photo.workmanager.DownLoadPhotoWorker
import com.goforer.lukohsplash.presentation.vm.Params
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadPhotosUseCase
@Inject
constructor(private val context: Context) : UseCase<WorkInfo>() {
    override fun run(viewModelScope: CoroutineScope, params: Params): SharedFlow<WorkInfo> {
        val work = OneTimeWorkRequestBuilder<DownLoadPhotoWorker>()
            .setInputData(workDataOf("url" to params.query.firstParam as String))
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(work)

        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(work.id)
            .asFlow()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
    }
}
