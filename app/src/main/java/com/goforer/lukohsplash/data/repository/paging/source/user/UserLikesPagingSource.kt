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

package com.goforer.lukohsplash.data.repository.paging.source.user

import androidx.paging.PagingState
import com.goforer.base.extension.isNullOnFlow
import com.goforer.lukohsplash.data.repository.paging.PagingErrorMessage
import com.goforer.lukohsplash.data.repository.paging.source.BasePagingSource
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.ApiEmptyResponse
import com.goforer.lukohsplash.data.source.network.response.ApiErrorResponse
import com.goforer.lukohsplash.data.source.network.response.ApiSuccessResponse
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.LATEST
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.NONE_ITEM_COUNT
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.YOUR_ACCESS_KEY
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLikesPagingSource
@Inject
constructor() : BasePagingSource<Int, Photo>() {
    override fun setData(query: Query, value: MutableList<Photo>) {
        this.query = query
        pagingList = value
    }

    @SuppressWarnings("unchecked")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            params.key.isNullOnFlow({}, {
                nextPage = params.key?.plus(1)!!
                restAPI.getUserLikes(
                    query.firstParam as String,
                    YOUR_ACCESS_KEY,
                    params.key?.plus(1)!!,
                    NONE_ITEM_COUNT,
                    LATEST,
                    null
                ).collect { apiResponse ->
                    pagingList = when (apiResponse) {
                        is ApiSuccessResponse -> {
                            apiResponse.body
                        }

                        is ApiEmptyResponse -> {
                            errorMessage = PagingErrorMessage.ERROR_MESSAGE_PAGING_EMPTY
                            arrayListOf()
                        }

                        is ApiErrorResponse -> {
                            errorMessage = apiResponse.errorMessage
                            arrayListOf()
                        }
                    }
                }
            })

            if (pagingList.isNotEmpty())
                LoadResult.Page(
                    data = pagingList,
                    prevKey = null,
                    nextKey = params.key?.plus(1) ?: 1
                )
            else
                LoadResult.Error(Throwable(errorMessage))
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            // Handle errors in this block
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
