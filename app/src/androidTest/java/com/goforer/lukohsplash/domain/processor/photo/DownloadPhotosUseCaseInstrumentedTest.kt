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
import com.goforer.base.worker.download.DownloaderQuery
import com.goforer.lukohsplash.presentation.uitest.TestFragment
import com.goforer.lukohsplash.presentation.ui.HomeActivity
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
    var activityRule: ActivityScenarioRule<HomeActivity>
        = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        file = File("Pictures")
        url = "https://images.unsplash.com/photo-1625582709381-8350d178fdc6?ixid=MnwyNDc1MTN8MHwxfGFsbHwzMXx8fHx8fDJ8fDE2MjcyNjE1NjU\\u0026ixlib=rb-1.2.1"
        context = InstrumentationRegistry.getInstrumentation().targetContext
        useCaseForDownloadPhoto = DownloadPhotosUseCase(context, DownloaderQuery(context))
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
                            firstParam = manager
                            secondParam = url
                            thirdParam = file
                        })).collect {
                            when (it) {
                                DownloadManager.STATUS_FAILED -> {
                                    assert(it == DownloadManager.STATUS_FAILED)
                                }

                                DownloadManager.STATUS_PAUSED -> {
                                    assert(it == DownloadManager.STATUS_PAUSED)
                                }

                                DownloadManager.STATUS_PENDING -> {
                                    assert(it == DownloadManager.STATUS_PENDING)
                                }

                                DownloadManager.STATUS_RUNNING -> {
                                    assert(it ==  DownloadManager.STATUS_RUNNING)
                                }

                                DownloadManager.STATUS_SUCCESSFUL -> {
                                    assert(it ==  DownloadManager.STATUS_SUCCESSFUL)
                                }

                                DownloadPhotosUseCase.FILE_EXISTED -> {
                                    assert(it == DownloadPhotosUseCase.FILE_EXISTED)
                                }
                            }
                        }
                    }
                }
            })
        }
    }

}