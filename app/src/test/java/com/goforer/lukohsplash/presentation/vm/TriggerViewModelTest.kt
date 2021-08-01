package com.goforer.lukohsplash.presentation.vm

import android.app.DownloadManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @DelicateCoroutinesApi
    inline fun <reified U : UseCase<Params, Resource>> getBaseUseCase(entity: Any): U {
        val useCase = Mockito.mock(U::class.java) as UseCase<Params, Resource>
        Mockito.`when`(useCase.run(isA(), isA()))
            .thenReturn(
                flowOf(getResponseResult(entity)).shareIn(
                    scope = GlobalScope,
                    started = SharingStarted.Eagerly,
                    replay = 1
                )
            )
        return useCase as U
    }

    @DelicateCoroutinesApi
    inline fun <reified T, reified U : UseCase<Params, T>> getProcessorUseCase(): U {
        val useCase = Mockito.mock(U::class.java) as UseCase<Params, T>
        Mockito.`when`(useCase.run(isA(), isA()))
            .thenReturn(
                flowOf(getResult() as T).shareIn(
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