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

package com.goforer.lukohsplash.domain.processor.photo

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.goforer.lukohsplash.domain.processor.photo.workmanager.DownLoadPhotoWorker
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DownloadWorkerInstrumentedTest {
    val url =
        "https://images.unsplash.com/photo-1629521446236-fd258987fd24?ixid=MnwyNDc1MTN8MHwxfGFsbHx8fHx8fHx8fDE2Mjk1NDc2Mzk&ixlib=rb-1.2.1"

    @Before
    fun setup() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    @Throws(Exception::class)
    fun testDownLoadPhotoWorker() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Define input data
        val input = workDataOf("url" to url)

        // Create request
        val request = OneTimeWorkRequestBuilder<DownLoadPhotoWorker>()
            .setInputData(input)
            .build()

        val workManager = WorkManager.getInstance(context)
        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        workManager.enqueue(request).result.get()
        // Get WorkInfo and outputData
        val workInfo = workManager.getWorkInfoById(request.id).get()

        when (workInfo.state) {
            WorkInfo.State.ENQUEUED -> {
                assertThat(workInfo.state, `is`(WorkInfo.State.ENQUEUED))
            }
            WorkInfo.State.RUNNING -> {
                assertThat(workInfo.state, `is`(WorkInfo.State.RUNNING))
            }
            WorkInfo.State.SUCCEEDED -> {
                assertThat(workInfo.state, `is`(WorkInfo.State.SUCCEEDED))
            }
            WorkInfo.State.BLOCKED -> {
                assertThat(workInfo.state, `is`(WorkInfo.State.BLOCKED))
            }
            WorkInfo.State.FAILED -> {
                assertThat(workInfo.state, `is`(WorkInfo.State.FAILED))
            }
            WorkInfo.State.CANCELLED -> {
                assertThat(workInfo.state, `is`(WorkInfo.State.CANCELLED))
            }
        }
    }
}
