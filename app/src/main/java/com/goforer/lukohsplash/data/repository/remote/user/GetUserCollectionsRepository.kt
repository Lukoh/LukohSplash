package com.goforer.lukohsplash.data.repository.remote.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.repository.paging.source.user.UserCollectionsPagingSource
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserCollectionsRepository
@Inject
constructor(val pagingSource: UserCollectionsPagingSource) : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<PagingData<Collection>, MutableList<Collection>>(false, viewModelScope) {
        override fun request() = restAPI.getUserCollections(
            query.firstParam as String, YOUR_ACCESS_KEY, 1, NONE_ITEM_COUNT
        )

        override fun load(value: MutableList<Collection>, itemCount: Int) = Pager(
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