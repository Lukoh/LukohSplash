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