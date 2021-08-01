package com.goforer.lukohsplash.data.repository.paging.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goforer.lukohsplash.data.mock.MockRestAPI
import com.goforer.lukohsplash.data.repository.paging.source.home.PhotosPagingSource
import com.goforer.lukohsplash.data.repository.paging.source.user.UserCollectionsPagingSource
import com.goforer.lukohsplash.data.repository.paging.source.user.UserLikesPagingSource
import com.goforer.lukohsplash.data.repository.paging.source.user.UserPhotosPagingSource
import com.goforer.lukohsplash.data.source.model.cache.entity.CollectionsTest
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotosTest
import com.goforer.test.kit.CoroutineTestRule
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
abstract class BasePagingSourceTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    internal val responsePhotosTest = PhotosTest.getInstance()
    internal val responseCollectionsTest = CollectionsTest.getInstance()


    @Inject
    internal lateinit var pagingPhotosSource: PhotosPagingSource

    @Inject
    internal lateinit var pagingUserLikesSource: UserLikesPagingSource

    @Inject
    internal lateinit var pagingUserPhotoSource: UserPhotosPagingSource

    @Inject
    internal lateinit var pagingUserCollectionsSource: UserCollectionsPagingSource

    @Inject
    lateinit var restAPI: MockRestAPI

    @Before
    fun setup() {
        pagingPhotosSource = PhotosPagingSource()
        pagingUserLikesSource = UserLikesPagingSource()
        pagingUserPhotoSource = UserPhotosPagingSource()
        pagingUserCollectionsSource = UserCollectionsPagingSource()
        restAPI = MockRestAPI()
        pagingPhotosSource.restAPI = restAPI
        pagingUserLikesSource.restAPI = restAPI
        pagingUserPhotoSource.restAPI = restAPI
        pagingUserCollectionsSource.restAPI = restAPI
    }
}