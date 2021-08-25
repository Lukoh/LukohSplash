package com.goforer.lukohsplash.data.repository.remote.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.repository.paging.source.user.UserCollectionPhotoPagingSource
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserCollectionPhotosRepository
@Inject
constructor(val pagingSource: UserCollectionPhotoPagingSource) : Repository<Resource>() {
    @ExperimentalCoroutinesApi
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<PagingData<Photo>, MutableList<Photo>>(false, viewModelScope) {
        override fun request() = restAPI.getUserCollectionPhotos(
            query.firstParam as String,
            YOUR_ACCESS_KEY, 1, NONE_ITEM_COUNT
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
        }.flow.cachedIn(viewModelScope)
    }.asSharedFlow
}
