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

package com.goforer.lukohsplash.domain.intermediator.user

import com.goforer.lukohsplash.data.repository.remote.user.GetUserCollectionsRepository
import com.goforer.lukohsplash.domain.UseCaseTest
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
class GetUserCollectionsUseCaseTest : UseCaseTest() {
    @Before
    fun setup() {
        setBaseRepository(GetUserCollectionsRepository::class.java)
        useCase = GetUserCollectionsUseCase(repository as GetUserCollectionsRepository)
    }

    @Test
    fun executeTest() {
        runBlockingTest {
            useCase.run(this, defaultParams).test(this) {
                this.assertValueAt(0, getResponseResult())
            }
        }
    }
}