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

package com.goforer.lukohsplash.presentation.vm

import android.app.DownloadManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.goforer.lukohsplash.data.source.model.cache.entity.BaseTest
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotoInfoTest
import com.goforer.lukohsplash.data.source.model.cache.entity.UserTest
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.model.entity.user.response.User
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.domain.UseCase
import com.nhaarman.mockitokotlin2.isA
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.mockito.Mockito

open class TriggerViewModelTest : TestWatcher() {
    lateinit var viewModel: TriggerViewModel<*>
    lateinit var lifecycleOwner: LifecycleOwner

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @DelicateCoroutinesApi
    inline fun <reified U : UseCase<Params, Resource>> getBaseUseCase(entity: Any): U {
        val useCase = Mockito.mock(U::class.java) as UseCase<Params, Resource>
        Mockito.`when`(useCase.run(isA(), isA()))
            .thenReturn(
                flowOf(getResponseResult(entity))
            )
        return useCase as U
    }

    @DelicateCoroutinesApi
    inline fun <reified T, reified U : UseCase<Params, T>> getProcessorUseCase(): U {
        val useCase = Mockito.mock(U::class.java) as UseCase<Params, T>
        Mockito.`when`(useCase.run(isA(), isA()))
            .thenReturn(
                flowOf(getResult() as T)
            )
        return useCase as U
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    open fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    open fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    fun getResponseResult(entity: Any): Resource {
        return when(entity) {
            is Photo -> {
                Resource().success(PhotoInfoTest().photo0)
            }

            is User -> {
                Resource().success(UserTest().user)
            }

            else ->{
                Resource().success(BaseTest().responseResult0)
            }
        }
    }

    fun getResult() = DownloadManager.STATUS_RUNNING
}