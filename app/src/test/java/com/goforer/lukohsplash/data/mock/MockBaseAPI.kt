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