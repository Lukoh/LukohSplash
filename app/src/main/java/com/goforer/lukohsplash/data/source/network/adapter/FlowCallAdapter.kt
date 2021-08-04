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

package com.goforer.lukohsplash.data.source.network.adapter

import com.goforer.lukohsplash.data.source.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

class FlowCallAdapter<Data>(private val responseType: Type) : CallAdapter<Data, Flow<ApiResponse<Data>>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<Data>): Flow<ApiResponse<Data>> = flow {
        val response = call.awaitResponse()

        emit(ApiResponse.create(response))
    }.catch { error ->
        emit(ApiResponse.create(error))
    }
}