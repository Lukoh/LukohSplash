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

package com.goforer.lukohsplash.data.repository.remote.photo

import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotoInfoRepository
@Inject
constructor() : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<Photo, Photo>(false) {
        override fun request() = restAPI.getPhoto(query.firstParam as String, YOUR_ACCESS_KEY)

        override suspend fun onNetworkError(errorMessage: String, errorCode: Int) {
            handleNetworkError(errorMessage)
        }

    }.asFlow()
}
