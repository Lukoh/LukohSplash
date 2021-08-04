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

package com.goforer.lukohsplash.data.source.network.factory

import com.goforer.lukohsplash.data.source.network.response.ApiResponse
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
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
class NullOrEmptyConverterFactoryTest : TestWatcher() {
    lateinit var mockNullOnEmptyConverterFactory: NullOnEmptyConverterFactory

    lateinit var retrofit: Retrofit

    @Before
    fun setup() {
        mockNullOnEmptyConverterFactory = NullOnEmptyConverterFactory()
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
        val callAdapter =
            mockNullOnEmptyConverterFactory.responseBodyConverter(type, arrayOf(), retrofit)

        Assert.assertEquals(
            callAdapter.convert(ResponseBody.create(null, "")),
            null
        )
    }
}