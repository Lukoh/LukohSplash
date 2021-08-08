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

package com.goforer.lukohsplash.data.source.network.worker

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.goforer.lukohsplash.data.source.network.response.*
import com.goforer.base.extension.isNullOnFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import timber.log.Timber

/**
 * A generic class that can provide a resource backed by the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 */
abstract class NetworkBoundWorker<Result, ResponseValue> constructor(
    private val enabledCache: Boolean, lifecycleScope: CoroutineScope
) {
    private val resource = Resource()

    companion object {
        internal const val YOUR_ACCESS_KEY = "V9sYHDmwcPc46chEOLA_bhTV3hwsWG0P1ta1vNZjmLs"
        internal const val NONE_ITEM_COUNT = 10
        internal const val LATEST = "latest"

        private const val LOADING = "loading"
    }

    internal val asStateFlow = flow {
        emit(resource.loading(LOADING))
        clearCache()

        val responseData = request()

        responseData.collect { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    val source = if (enabledCache) {
                        saveToCache(apiResponse.body)
                        loadFromCache(false, NONE_ITEM_COUNT, 1)

                    } else {
                        load(apiResponse.body, NONE_ITEM_COUNT)
                    }

                    source.isNullOnFlow({
                        emitAll(responseData.map {
                            resource.success(apiResponse.body)
                        })
                    }, {
                        it.collect { result ->
                            Timber.e("NetworkBoundWorker refreshed $result")
                            emit(resource.success(result))
                        }
                    })
                }

                is ApiEmptyResponse -> {
                    emit(resource.success(""))
                }

                is ApiErrorResponse -> {
                    Timber.e("Network-Error: ${apiResponse.errorMessage}")
                    emit(resource.error(apiResponse.errorMessage, apiResponse.statusCode))
                    onNetworkError(apiResponse.errorMessage, apiResponse.statusCode)
                }
            }
        }
    }.stateIn(
        scope = lifecycleScope,
        started = WhileSubscribed(5000),
        initialValue = resource.loading(LOADING)
    )

    protected open suspend fun onNetworkError(errorMessage: String, errorCode: Int) {
    }

    @WorkerThread
    protected open suspend fun saveToCache(value: ResponseValue) {
    }

    @MainThread
    protected open fun loadFromCache(
        isLatest: Boolean,
        itemCount: Int,
        pages: Int
    ): Flow<Result>? = null

    @MainThread
    protected open fun load(value: ResponseValue, itemCount: Int): Flow<Result>? = null

    @MainThread
    protected abstract fun request(): Flow<ApiResponse<ResponseValue>>

    protected open suspend fun clearCache() {}
}