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

    override fun getUserCollectionPhotos(
        id: String,
        clientId: String,
        page: Int?,
        per_page: Int?
    ): Flow<ApiResponse<MutableList<Photo>>> {
        return getFlowResponse(mutableListOf(photosTest.photo0))
    }
}
