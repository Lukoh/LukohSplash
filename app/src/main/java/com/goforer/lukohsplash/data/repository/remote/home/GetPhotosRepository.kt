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

package com.goforer.lukohsplash.data.repository.remote.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.repository.paging.source.home.PhotosPagingSource
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotosRepository
@Inject
constructor(val pagingSource: PhotosPagingSource) : Repository<Resource>() {
    @ExperimentalCoroutinesApi
    override fun doWork(lifecycleScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<PagingData<Photo>, MutableList<Photo>>(false, lifecycleScope) {
            override fun request() = restAPI.getPhotos(
                YOUR_ACCESS_KEY, query.firstParam as Int, query.secondParam as Int, query.thirdParam as String
            )

            override fun load(value: MutableList<Photo>, itemCount: Int)  = Pager(
                config = PagingConfig(
                    pageSize = itemCount,
                    prefetchDistance = itemCount,
                    initialLoadSize = itemCount
                )
            ) {
                pagingSource.setData(query, value)
                pagingSource
            }.flow.cachedIn(lifecycleScope).shareIn(
                scope = lifecycleScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
        }.asSharedFlow
}
