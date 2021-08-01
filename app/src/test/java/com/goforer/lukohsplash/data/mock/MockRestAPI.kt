package com.goforer.lukohsplash.data.mock

import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.data.source.model.entity.user.response.User
import com.goforer.lukohsplash.data.source.network.api.RestAPI
import com.goforer.lukohsplash.data.source.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow

class MockRestAPI(isMakeError: Boolean = false) : RestAPI, MockBaseAPI(isMakeError) {
    override fun getPhotos(
        clientId: String,
        page: Int?,
        per_page: Int?,
        order_by: String?
    ): Flow<ApiResponse<MutableList<Photo>>> {
        return getFlowResponse(mutableListOf(photosTest.photo0))
    }

    override fun getPhoto(id: String, clientId: String): Flow<ApiResponse<Photo>> {
        return getFlowResponse(photoInfoTest.photo0)
    }

    override fun getUserPublicProfile(username: String): Flow<ApiResponse<User>> {
        return getFlowResponse(userTest.user)
    }

    override fun getUserPhotos(
        username: String,
        clientId: String,
        page: Int?,
        per_page: Int?,
        order_by: String?,
        stats: Boolean?,
        resolution: String?,
        quantity: Int?,
        orientation: String?
    ): Flow<ApiResponse<MutableList<Photo>>> {
        return getFlowResponse(mutableListOf(photosTest.photo0))
    }

    override fun getUserLikes(
        username: String,
        clientId: String,
        page: Int?,
        per_page: Int?,
        order_by: String?,
        orientation: String?
    ): Flow<ApiResponse<MutableList<Photo>>> {
        return getFlowResponse(mutableListOf(photosTest.photo0))
    }

    override fun getUserCollections(
        username: String,
        clientId: String,
        page: Int?,
        per_page: Int?
    ): Flow<ApiResponse<MutableList<Collection>>> {
        return getFlowResponse(mutableListOf(collectionsTest.collection0))
    }
}