package com.goforer.lukohsplash.data.repository.paging.datasource.user

import androidx.paging.PagingSource
import com.goforer.lukohsplash.data.repository.paging.datasource.BasePagingSourceTest
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotosTest
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
class UserPhotosPagingSourceTest : BasePagingSourceTest() {
    @Test
    fun userPhotosPagingSourceTest() {
        pagingUserPhotoSource.setData(
            QueryTool.getQuery("itfeelslikefilm", false, "days", 30),
            mutableListOf(PhotosTest().photo0)
        )

        runBlockingTest {
            Assert.assertEquals(
                pagingUserPhotoSource.load(PagingSource.LoadParams.Append(0, 10, true)),
                PagingSource.LoadResult.Page(
                    mutableListOf(responsePhotosTest.photo0),
                    null,
                    1
                )
            )
        }
    }
}