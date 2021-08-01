package com.goforer.lukohsplash.presentation.scheduler

import android.view.Choreographer
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch

object UIJobScheduler {
    private const val MAX_JOB_TIME_MS: Float = 4F

    private var elapsed = 0L
    private val jobQueue = ArrayDeque<() -> Unit>()
    private val isOverMaxTime get() = elapsed > MAX_JOB_TIME_MS * 1_000_000

    fun submitJob(coroutineScope: LifecycleCoroutineScope, job: () -> Unit) {
        jobQueue.add(job)
        if (jobQueue.size == 1) {
            coroutineScope.launch {
                processJobs()
            }
        }
    }

    private fun processJobs() {
        while (!jobQueue.isEmpty() && !isOverMaxTime) {
            val start = System.nanoTime()
            jobQueue.removeFirst().invoke()
            elapsed += System.nanoTime() - start
        }

        if (jobQueue.isEmpty()) {
            elapsed = 0
        } else if (isOverMaxTime) {
            onNextFrame {
                elapsed = 0
                processJobs()
            }
        }
    }

    private fun onNextFrame(callback: () -> Unit) =
        Choreographer.getInstance().postFrameCallback { callback() }
}