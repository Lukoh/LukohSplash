package com.goforer.lukohsplash.data.repository.remote.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.repository.paging.source.user.UserLikesPagingSource
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserLikesRepository
@Inject
constructor(val pagingSource: UserLikesPagingSource) : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<PagingData<Photo>, MutableList<Photo>>(false, viewModelScope) {
        override fun request() = restAPI.getUserLikes(
            query.firstParam as String, YOUR_ACCESS_KEY, 1, NONE_ITEM_COUNT, LATEST, null
        )

        override fun load(item: MutableList<Photo>, itemCount: Int) = Pager(
            config = PagingConfig(
                pageSize = itemCount,
                prefetchDistance = itemCount,
                initialLoadSize = itemCount
            )
        ) {
            pagingSource.setData(query, item)
            pagingSource
        }.flow.cachedIn(viewModelScope)
    }.asSharedFlow()
}