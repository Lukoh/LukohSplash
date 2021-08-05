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

package com.goforer.lukohsplash.presentation.vm.photo

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotoInfoTest
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.TriggerViewModelTest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class GetPhotoInfoViewModelTest : TriggerViewModelTest() {
    @Before
    @DelicateCoroutinesApi
    override fun setup() {
        super.setup()

        lifecycleOwner = TestLifecycleOwner()
        viewModel = GetPhotoInfoViewModel(getBaseUseCase(PhotoInfoTest().photo0))
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun pullTriggerTest() {
        viewModel.pullTrigger(Params(Query().apply {
            firstParam = "Jvw3pxgeiZw"
            secondParam = -1
        }), lifecycleOwner) {
            Assert.assertEquals(it, getResponseResult(PhotoInfoTest().photo0))
        }
    }
}