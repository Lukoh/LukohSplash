package com.goforer.lukohsplash.data.source.network.adapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goforer.lukohsplash.data.source.model.entity.ResponseResult
import com.goforer.test.kit.CoroutineTestRule
import com.goforer.test.kit.flow.test
import com.nhaarman.mockitokotlin2.mock
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import java.lang.reflect.Type

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class FlowCallAdapterTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    lateinit var mockFlowCallAdapter: FlowCallAdapter<ResponseResult>
    lateinit var callComplete: Call<ResponseResult>

    val type = object : Type {
    }

    @Before
    fun setup() {
        mockFlowCallAdapter = FlowCallAdapter(type)

        callComplete = mock()
    }

    @Test
    fun flowCallAdapterTestComplete() {
        runBlockingTest {
            Assert.assertEquals(mockFlowCallAdapter.responseType(), type)

            mockFlowCallAdapter.adapt(callComplete).test(this) {

                assertNotComplete()
            }
        }
    }
}