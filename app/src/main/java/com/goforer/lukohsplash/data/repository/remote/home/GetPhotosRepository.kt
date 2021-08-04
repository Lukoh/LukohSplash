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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotosRepository
@Inject
constructor(val pagingSource: PhotosPagingSource) : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<PagingData<Photo>, MutableList<Photo>>(false) {
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
            }.flow.cachedIn(viewModelScope)
        }.asSharedFlow()
}
