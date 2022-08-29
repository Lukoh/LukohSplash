/*
 * Copyright (C) 2021-2022 Lukoh Nam, goforers (https://github.com/goforers)
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
package com.bp.oheadline.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.lang.Long.MAX_VALUE

/**
 * Stuff is a central post/subscribe the data system for Kotlin and Android.
 * Stuff are posted to the Fragment, which is subscribed the data as subscribers, and delivers it to subscribers that declare it
 * To receive the data, subscribers must declare it into Fragment.
 * Once declared, subscribers receive the data
 *
 * Converts a cold Flow into a hot SharedFlow that is started in the given coroutine scope,
 * sharing emissions from a single running instance of the upstream flow with multiple downstream subscribers,
 * and replaying a specified number of replay values to new subscribers.
 *
 * The shareIn operator is useful in situations when there is a cold flow that is expensive to create
 * and/or to maintain, but there are multiple subscribers that need to collect its values.
 *
 * @author Lukoh Nam, goforers
 */
open class Stuff<Data> : ViewModel() {
    private var sharedData: SharedFlow<Data>? = null
    private var job: Job? = null

    internal fun post(data: Data, disposable: Boolean = true) {
        val replayCount: Int
        val replayExpirationMills: Long

        if (disposable) {
            replayCount = 0
            replayExpirationMills = 0
        } else {
            replayCount = 1
            replayExpirationMills = MAX_VALUE
        }

        sharedData = flowOf(data).shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0, replayExpirationMills),
            replay = replayCount
        )
    }

    internal fun subscribe(isDisposable: Boolean = false, doOnResult: (data: Data) -> Unit) {
        viewModelScope.launch {
            sharedData?.collectLatest {
                doOnResult(it)
                if (isDisposable)
                    sharedData = null
            }
        }
    }

    internal fun cancel() {
        job?.cancel()
        this.viewModelScope.coroutineContext.job.cancel()
    }

    override fun onCleared() {
        super.onCleared()

        sharedData = null
    }
}