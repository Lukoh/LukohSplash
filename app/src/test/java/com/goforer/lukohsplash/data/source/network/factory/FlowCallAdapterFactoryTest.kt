package com.goforer.lukohsplash.data.source.network.factory

import com.goforer.lukohsplash.data.source.network.response.ApiResponse
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class FlowCallAdapterFactoryTest : TestWatcher() {
    lateinit var mockFlowCallAdapterFactory: FlowCallAdapterFactory

    lateinit var retrofit: Retrofit

    @Before
    fun setup() {
        mockFlowCallAdapterFactory = FlowCallAdapterFactory.create()
        retrofit = Mockito.mock(Retrofit::class.java)
    }

    @Test
    fun mockFlowCallAdapterFactoryTest() {
        val type = object : ParameterizedType {
            override fun getRawType(): Type {
                return Flow::class.java
            }

            override fun getOwnerType(): Type? {
                return Flow::class.java
            }

            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(
                    object : WildcardType {
                        val arr: Array<ParameterizedType> = arrayOf(object : ParameterizedType {
                            override fun getRawType(): Type {
                                return ApiResponse::class.java
                            }

                            override fun getOwnerType(): Type? {
                                return ApiResponse::class.java
                            }

                            override fun getActualTypeArguments(): Array<Type> {
                                return arrayOf(ApiResponse::class.java)
                            }

                        })

                        override fun getLowerBounds(): Array<ParameterizedType> {
                            return arr
                        }

                        override fun getUpperBounds(): Array<ParameterizedType> {
                            return arr
                        }

                    }
                )
            }

        }
        val callAdapter = mockFlowCallAdapterFactory.get(type, arrayOf(), retrofit)

        println(callAdapter?.responseType()?.typeName)
        Assert.assertEquals(callAdapter?.responseType(), ApiResponse::class.java)
    }
}