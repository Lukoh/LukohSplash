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