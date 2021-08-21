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

import android.app.DownloadManager
import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.WorkInfo
import com.goforer.lukohsplash.presentation.ui.HomeActivity
import com.goforer.lukohsplash.presentation.uitest.TestFragment
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@InternalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DownloadPhotosUseCaseInstrumentedTest {
    private lateinit var context: Context
    private lateinit var file: File
    private lateinit var url: String
    private lateinit var useCaseForDownloadPhoto: DownloadPhotosUseCase
    private lateinit var manager: DownloadManager

    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> =
        ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        file = File("Pictures")
        url =
            "https://images.unsplash.com/photo-1625582709381-8350d178fdc6?ixid=MnwyNDc1MTN8MHwxfGFsbHwzMXx8fHx8fDJ8fDE2MjcyNjE1NjU\\u0026ixlib=rb-1.2.1"
        context = InstrumentationRegistry.getInstrumentation().targetContext
        useCaseForDownloadPhoto = DownloadPhotosUseCase(context)
        manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Test
    fun executeTest() {
        val scenario = launchFragmentInContainer<TestFragment>()

        scenario.onFragment { fragment ->
            fragment.setupPermission(object : TestFragment.PermissionCallback {
                override fun onPermissionGranted() {
                    runBlocking {
                        useCaseForDownloadPhoto.run(this, Params(Query().apply {
                            firstParam = url
                            secondParam = 1
                        })).collect {
                            when (it.state) {
                                WorkInfo.State.ENQUEUED -> {
                                    assert(it.state == WorkInfo.State.ENQUEUED)
                                }
                                WorkInfo.State.RUNNING -> {
                                    assert(it.state == WorkInfo.State.RUNNING)
                                }
                                WorkInfo.State.BLOCKED -> {
                                    assert(it.state == WorkInfo.State.BLOCKED)
                                }
                                WorkInfo.State.FAILED -> {
                                    assert(it.state == WorkInfo.State.FAILED)
                                }
                                WorkInfo.State.CANCELLED -> {
                                    assert(it.state == WorkInfo.State.CANCELLED)
                                }
                                else -> {
                                    assert(it.state == it.state)
                                }
                            }
                        }
                    }
                }
            })
        }
    }

}
