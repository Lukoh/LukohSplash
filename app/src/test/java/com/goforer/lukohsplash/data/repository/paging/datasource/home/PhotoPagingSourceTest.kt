package com.goforer.lukohsplash.data.repository.paging.datasource.home

import androidx.paging.PagingSource
import com.goforer.lukohsplash.data.repository.paging.datasource.BasePagingSourceTest
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotosTest
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.LATEST
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.NONE_ITEM_COUNT
import com.goforer.test.kit.QueryTool
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class PhotoPagingSourceTest : BasePagingSourceTest() {
    @Test
    fun photoPagingSourceTest() {
        pagingPhotosSource.setData(
            QueryTool.getQuery(1, NONE_ITEM_COUNT, LATEST),
            mutableListOf(PhotosTest().photo0)
        )

        runBlockingTest {
            Assert.assertEquals(
                pagingPhotosSource.load(PagingSource.LoadParams.Append(0, 10, true)),
                PagingSource.LoadResult.Page(
                    mutableListOf(responsePhotosTest.photo0),
                    null,
                    1
                )
            )
        }
    }
}