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

package com.goforer.lukohsplash.data.source.network.response

import com.goforer.lukohsplash.data.source.model.entity.ResponseResult
import com.goforer.lukohsplash.data.source.network.response.ApiSuccessResponse.Companion.extractLinks
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class ApiResponseTest : TestWatcher() {

    @Test
    fun ApiResponseCreateTest() {
        val errorResponse = ApiResponse.create<ResponseResult>(Throwable())

        Assert.assertEquals(errorResponse, ApiErrorResponse<ResponseResult>("unknown error", -1))


        val successResponse0 =
            ApiResponse.create<ResponseResult>(Response.success(204, ResponseResult("OK")))

        Assert.assertTrue(successResponse0 is ApiEmptyResponse)

        val successResponse1 =
            ApiResponse.create<ResponseResult>(Response.success(ResponseResult("OK")))

        Assert.assertEquals(
            successResponse1,
            ApiSuccessResponse(ResponseResult("OK"), null)
        )

        val successResponse2 = ApiResponse.create<ResponseResult>(
            Response.error(400, "".toResponseBody(null))
        )

        Assert.assertEquals(
            successResponse2,
            ApiErrorResponse<ResponseResult>("Response.error()", 400)
        )
    }

    @Test
    fun ApiSuccessResponseTest() {
        val map = "<test> ; rel=\"link\"".extractLinks()
        Assert.assertEquals(map, mapOf(Pair("link", "test")))
    }

    @Test
    fun nextPageTest() {
        val response0 =
            ApiSuccessResponse(ResponseResult("OK"), "<> ; rel=\"next\"")
        Assert.assertEquals(response0.nextPage, null)

        val response1 =
            ApiSuccessResponse(ResponseResult("OK"), "<page=0> ; rel=\"next\"")
        Assert.assertEquals(response1.nextPage, 0)

        val response2 =
            ApiSuccessResponse(ResponseResult("OK"), "<page=n> ; rel=\"next\"")
        Assert.assertEquals(response2.nextPage, null)
    }
}