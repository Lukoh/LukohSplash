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

package com.goforer.lukohsplash.data.source.network.api

import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.data.source.model.entity.user.response.User
import com.goforer.lukohsplash.data.source.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestAPI {
    @GET("photos")
    fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?,
        @Query("order_by") order_by: String?
    ): Flow<ApiResponse<MutableList<Photo>>>

    @GET("photos/{id}")
    fun getPhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String,
    ): Flow<ApiResponse<Photo>>

    @GET("users/{username}")
    fun getUserPublicProfile(
        @Path("username") username: String
    ): Flow<ApiResponse<User>>

    @GET("users/{username}/photos")
    fun getUserPhotos(
        @Path("username") username: String,
        @Query("client_id") clientId: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?,
        @Query("order_by") order_by: String?,
        @Query("stats") stats: Boolean?,
        @Query("resolution") resolution: String?,
        @Query("quantity") quantity: Int?,
        @Query("orientation") orientation: String?
    ): Flow<ApiResponse<MutableList<Photo>>>

    @GET("users/{username}/likes")
    fun getUserLikes(
        @Path("username") username: String,
        @Query("client_id") clientId: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?,
        @Query("order_by") order_by: String?,
        @Query("orientation") orientation: String?
    ): Flow<ApiResponse<MutableList<Photo>>>

    @GET("users/{username}/collections")
    fun getUserCollections(
        @Path("username") username: String,
        @Query("client_id") clientId: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): Flow<ApiResponse<MutableList<Collection>>>

    @GET("collections/{id}/photos")
    fun getUserCollectionPhotos(
        @Path("id") id: String,
        @Query("client_id") clientId: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): Flow<ApiResponse<MutableList<Photo>>>
}
