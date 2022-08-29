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

package com.goforer.lukohsplash.presentation.vm

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
