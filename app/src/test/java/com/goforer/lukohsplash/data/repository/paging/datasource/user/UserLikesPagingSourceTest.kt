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
class UserLikesPagingSourceTest : BasePagingSourceTest() {
    @Test
    fun userPhotosPagingSourceTest() {
        pagingUserLikesSource.setData(
            QueryTool.getQuery("itfeelslikefilm", -1),
            mutableListOf(PhotosTest().photo0)
        )

        runBlockingTest {
            Assert.assertEquals(
                pagingUserLikesSource.load(PagingSource.LoadParams.Append(0, 10, true)),
                PagingSource.LoadResult.Page(
                    mutableListOf(responsePhotosTest.photo0),
                    null,
                    1
                )
            )
        }
    }
}