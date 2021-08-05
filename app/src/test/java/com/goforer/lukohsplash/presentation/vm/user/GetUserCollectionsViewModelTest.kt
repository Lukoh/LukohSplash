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

package com.goforer.lukohsplash.presentation.vm.user

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.paging.PagingData
import com.goforer.lukohsplash.data.source.model.cache.entity.BaseTest
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.TriggerViewModelTest
import com.goforer.test.kit.CoroutineTestRule
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.*
import org.mockito.Mockito

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class GetUserCollectionsViewModelTest : TriggerViewModelTest() {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @DelicateCoroutinesApi
    @Before
    override fun setup() {
        super.setup()

        lifecycleOwner = TestLifecycleOwner()
        viewModel = GetUserCollectionsViewModel(getBaseUseCase(BaseTest().responseResult0))
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun pullTriggerTest() {
        viewModel.pullTrigger(Params(Query().apply {
            firstParam = "jimmydean"
            secondParam = -1
        }), lifecycleOwner) {
            coroutineTestRule.managedCoroutineScope.launch {
                @Suppress("UNCHECKED_CAST")
                Assert.assertTrue(((it as Resource).getData() as? PagingData<Collection>) is PagingData<Collection>)
            }
        }
    }
}