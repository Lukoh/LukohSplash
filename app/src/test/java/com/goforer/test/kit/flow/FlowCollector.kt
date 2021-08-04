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

package com.goforer.test.kit.flow

import android.app.DownloadManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn

import kotlin.coroutines.coroutineContext

suspend fun <T> Flow<T>.test(
    scope: CoroutineScope,
    block: suspend FlowTestCollector<T>.() -> Unit
): Job {
    return scope.launch(coroutineContext) {
        FlowTestCollectorImpl(this@test).block()
    }
}

@DelicateCoroutinesApi
fun <T> Flow<T>.doTest(
    scope: CoroutineScope,
    block: suspend FlowTestCollector<T>.() -> Unit
) = flowOf(result()).shareIn(
    scope = GlobalScope,
    started = SharingStarted.Eagerly,
    replay = 1
)

fun result() = DownloadManager.STATUS_SUCCESSFUL