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

package com.goforer.lukohsplash.data.mock

import com.goforer.lukohsplash.data.source.model.cache.entity.*
import com.goforer.lukohsplash.data.source.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

open class MockBaseAPI(private val isMakeError: Boolean) {
    val baseTest = BaseTest()
    val photosTest = PhotosTest()
    val collectionsTest = CollectionsTest()
    val photoInfoTest = PhotoInfoTest()
    val userTest = UserTest()

    fun <T> getFlowResponse(body: T): Flow<ApiResponse<T>> {
        return flow {
            if (isMakeError)
                emit(ApiResponse.create<T>(Throwable()))
            else
                emit(ApiResponse.create(Response.success(body)))
        }
    }
}