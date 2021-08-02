package com.goforer.lukohsplash.data.repository.paging.source.user

import androidx.paging.PagingState
import com.goforer.base.extension.isNullOnFlow
import com.goforer.lukohsplash.data.repository.paging.PagingErrorMessage
import com.goforer.lukohsplash.data.repository.paging.source.BasePagingSource
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.data.source.network.response.ApiEmptyResponse
import com.goforer.lukohsplash.data.source.network.response.ApiErrorResponse
import com.goforer.lukohsplash.data.source.network.response.ApiSuccessResponse
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.NONE_ITEM_COUNT
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.YOUR_ACCESS_KEY
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCollectionsPagingSource
@Inject
constructor() : BasePagingSource<Int, Collection>() {
    override fun setData(query: Query, value: MutableList<Collection>) {
        this.query = query
        pagingList = value
    }

    @SuppressWarnings("unchecked")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collection> {
        return try {
            params.key.isNullOnFlow({}, {
                restAPI.getUserCollections(
                    query.firstParam as String,
                    YOUR_ACCESS_KEY,  params.key?.plus(1), NONE_ITEM_COUNT
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

    override fun getRefreshKey(state: PagingState<Int, Collection>): Int? {
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