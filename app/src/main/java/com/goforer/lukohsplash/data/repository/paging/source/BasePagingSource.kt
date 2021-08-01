package com.goforer.lukohsplash.data.repository.paging.source

import androidx.paging.PagingSource
import com.goforer.lukohsplash.data.repository.paging.PagingErrorMessage
import com.goforer.lukohsplash.data.source.network.api.RestAPI
import com.goforer.lukohsplash.presentation.vm.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class BasePagingSource<Key : Any, Value : Any> : PagingSource<Key, Value>() {
    protected lateinit var pagingList: MutableList<Value>

    protected var errorMessage = PagingErrorMessage.ERROR_MESSAGE_PAGING_EMPTY

    protected lateinit var query: Query

    @Inject
    internal lateinit var restAPI: RestAPI

    abstract fun setData(query: Query, items: MutableList<Value>)
}