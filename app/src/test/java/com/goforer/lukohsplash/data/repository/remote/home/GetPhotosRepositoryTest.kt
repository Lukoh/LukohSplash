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

package com.goforer.lukohsplash.data.repository.remote.home

import androidx.paging.PagingData
import com.goforer.lukohsplash.data.repository.paging.source.home.PhotosPagingSource
import com.goforer.lukohsplash.data.repository.remote.RepositoryTest
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.test.kit.QueryTool
import com.goforer.test.kit.flow.test
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class GetPhotosRepositoryTest : RepositoryTest() {
    @Before
    fun setup() {
        setBaseRepository(
            GetPhotosRepository(PhotosPagingSource()),
            QueryTool.getQuery(0)
        )
    }

    @Test
    fun workTest() {
        runBlockingTest {
            coroutinesTestRule.managedCoroutineScope.launch {
                repository.doWork(this, defaultQuery).test(this) {
                    this.assertValue {
                        @Suppress("UNCHECKED_CAST")
                        (it.getData() as? PagingData<Photo>) is PagingData<Photo>
                    }
                }
            }
        }
    }
}