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

package com.goforer.lukohsplash.data.repository.remote.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.repository.paging.source.user.UserPhotosPagingSource
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserPhotosRepository
@Inject
constructor(val pagingSource: UserPhotosPagingSource) : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<PagingData<Photo>, MutableList<Photo>>(false) {
        override fun request() = restAPI.getUserPhotos(
            query.firstParam as String, YOUR_ACCESS_KEY, 1, NONE_ITEM_COUNT, LATEST,
            query.secondParam as Boolean, query.thirdParam as String,
            query.forthParam as Int, null
        )

        override fun load(value: MutableList<Photo>, itemCount: Int) = Pager(
            config = PagingConfig(
                pageSize = itemCount,
                prefetchDistance = itemCount,
                initialLoadSize = itemCount
            )
        ) {
            pagingSource.setData(query, value)
            pagingSource
        }.flow.cachedIn(viewModelScope).shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1
        )
    }.asSharedFlow()
}