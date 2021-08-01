package com.goforer.lukohsplash.data.repository

import com.goforer.lukohsplash.data.source.network.api.RestAPI
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.base.network.NetworkErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class Repository<Resource> {
    @Inject
    lateinit var restAPI: RestAPI

    @Inject
    lateinit var networkErrorHandler: NetworkErrorHandler

    abstract fun doWork(viewModelScope: CoroutineScope, query: Query): SharedFlow<Resource>

    protected fun handleNetworkError(errorMessage: String) {
        networkErrorHandler.handleError(errorMessage)
    }
}