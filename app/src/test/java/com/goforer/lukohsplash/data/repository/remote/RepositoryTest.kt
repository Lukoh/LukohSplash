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

package com.goforer.lukohsplash.data.repository.remote

import android.content.Context
import com.goforer.lukohsplash.data.mock.MockRestAPI
import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.source.model.cache.entity.BaseTest
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotoInfoTest
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotosTest
import com.goforer.lukohsplash.data.source.model.cache.entity.UserTest
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.base.network.NetworkErrorHandler
import com.goforer.test.kit.CoroutineTestRule
import com.goforer.test.kit.QueryTool
import com.goforer.test.kit.flow.test
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
open class RepositoryTest : TestWatcher() {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    lateinit var repository: Repository<Resource>
    lateinit var defaultQuery: Query

    @Mock
    private lateinit var mockContext: Context

    val baseTest = BaseTest()
    val photosTest = PhotosTest()
    val photoInfoTest = PhotoInfoTest()
    val userTest = UserTest()

    fun setBaseRepository(
        repository: Repository<Resource>,
        defaultQuery: Query = QueryTool.getQuery(0, 0)
    ) {
        repository.restAPI = MockRestAPI()
        this.repository = repository
        this.defaultQuery = defaultQuery
    }

    fun <T> getResourceValue(body: T): Resource {
        return Resource().success(body)
    }

    @Test
    fun networkErrorTest() {
        if (this::repository.isInitialized)
            runBlockingTest {
                coroutinesTestRule.managedCoroutineScope.launch {
                    repository.restAPI = MockRestAPI(true)
                    repository.networkErrorHandler = NetworkErrorHandler(
                        mockContext
                    )
                    repository.doWork(this, defaultQuery).test(this) {
                        this.assertValueAt(0, Resource().error("unknown error", -1))
                    }
                }
            }
    }
}