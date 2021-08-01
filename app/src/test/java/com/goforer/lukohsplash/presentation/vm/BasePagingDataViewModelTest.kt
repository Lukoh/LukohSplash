package com.goforer.lukohsplash.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.goforer.lukohsplash.data.source.model.cache.entity.BaseTest
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

open class BasePagingDataViewModelTest<P : Any> : TestWatcher() {
    lateinit var viewModel: TriggerViewModel<PagingData<P>>

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @DelicateCoroutinesApi
    inline fun <reified U : UseCase<Params, PagingData<P>>> getBaseUseCase(): U {
        val useCase = Mockito.mock(U::class.java) as UseCase<Params, PagingData<P>>
        Mockito.`when`(useCase.run(isA(), isA()))
            .thenReturn(
                flowOf(PagingData.empty<P>()).shareIn(
                    scope = GlobalScope,
                    started = SharingStarted.Eagerly,
                    replay = 1
                )
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

    fun getResponseResult(): Resource {
        return Resource().success(BaseTest().responseResult0)
    }
}