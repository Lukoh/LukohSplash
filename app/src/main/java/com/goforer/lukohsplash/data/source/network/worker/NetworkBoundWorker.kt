/*
 * Copyright (C)  2020 Blue-Ocean
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.goforer.lukohsplash.data.source.network.worker

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.goforer.lukohsplash.data.source.network.response.*
import com.goforer.base.extension.isNullOnFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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
    private val enabledCache: Boolean,
) {
    companion object {
        internal const val YOUR_ACCESS_KEY = "ogfHK8SPaobznFUnD4jXUe4TIIgsh5pmUdfZ4Ra91Zw"
        internal const val NONE_ITEM_COUNT = 10
        internal const val LATEST = "latest"

        private const val LOADING = "loading"
    }

    private val resource = Resource()

    internal fun asSharedFlow() = flow {
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
    }.shareIn(
        scope = ProcessLifecycleOwner.get().lifecycleScope,
        started = WhileSubscribed(),
        replay = 1
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