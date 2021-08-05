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

package com.goforer.lukohsplash.data.repository.remote.user

import androidx.paging.PagingData
import com.goforer.lukohsplash.data.repository.paging.source.user.UserCollectionsPagingSource
import com.goforer.lukohsplash.data.repository.remote.RepositoryTest
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.presentation.vm.Query
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
class GetUserCollectionsRepositoryTest : RepositoryTest() {
    @Before
    fun setup() {
        setBaseRepository(
            GetUserCollectionsRepository(UserCollectionsPagingSource()),
            QueryTool.getQuery("jimmydean", -1)
        )
    }

    @Test
    fun workTest() {
        runBlockingTest {
            coroutinesTestRule.managedCoroutineScope.launch {
                repository.doWork(this, Query().apply {
                    firstParam = "jimmydean"
                    secondParam = -1
                }).test(this) {
                    this.assertValue {
                        @Suppress("UNCHECKED_CAST")
                        (it.getData() as? PagingData<Collection>) is PagingData<Collection>
                    }
                }
            }
        }
    }
}