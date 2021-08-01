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