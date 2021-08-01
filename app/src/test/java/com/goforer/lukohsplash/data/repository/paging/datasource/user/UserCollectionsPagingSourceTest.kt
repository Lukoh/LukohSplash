package com.goforer.lukohsplash.data.repository.paging.datasource.user

import androidx.paging.PagingSource
import com.goforer.lukohsplash.data.repository.paging.datasource.BasePagingSourceTest
import com.goforer.lukohsplash.data.source.model.cache.entity.CollectionsTest
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
class UserCollectionsPagingSourceTest : BasePagingSourceTest() {
    @Test
    fun userPhotosPagingSourceTest() {
        pagingUserCollectionsSource.setData(
            QueryTool.getQuery("itfeelslikefilm", -1),
            mutableListOf(CollectionsTest().collection0)
        )

        runBlockingTest {
            Assert.assertEquals(
                pagingUserCollectionsSource.load(PagingSource.LoadParams.Append(0, 10, true)),
                PagingSource.LoadResult.Page(
                    mutableListOf(responseCollectionsTest.collection0),
                    null,
                    1
                )
            )
        }
    }
}