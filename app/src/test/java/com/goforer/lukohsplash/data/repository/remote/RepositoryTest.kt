package com.goforer.lukohsplash.data.repository.remote

import android.content.Context
import com.goforer.lukohsplash.data.mock.MockRestAPI
import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.source.model.cache.entity.BaseTest
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotoInfoTest
import com.goforer.lukohsplash.data.source.model.cache.entity.PhotosTest
import com.goforer.lukohsplash.data.source.model.cache.entity.UserTest
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.base.network.NetworkErrorHandler
import com.goforer.test.kit.CoroutineTestRule
import com.goforer.test.kit.QueryTool
import com.goforer.test.kit.flow.test
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
open class RepositoryTest : TestWatcher() {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    lateinit var repository: Repository<Resource>
    lateinit var defaultQuery: Query

    @Mock
    private lateinit var mockContext: Context

    val baseTest = BaseTest()
    val photosTest = PhotosTest()
    val photoInfoTest = PhotoInfoTest()
    val userTest = UserTest()

    fun setBaseRepository(
        repository: Repository<Resource>,
        defaultQuery: Query = QueryTool.getQuery(0, 0)
    ) {
        repository.restAPI = MockRestAPI()
        this.repository = repository
        this.defaultQuery = defaultQuery
    }

    fun <T> getResourceValue(body: T): Resource {
        return Resource().success(body)
    }

    @Test
    fun networkErrorTest() {
        if (this::repository.isInitialized)
            runBlockingTest {
                coroutinesTestRule.managedCoroutineScope.launch {
                    repository.restAPI = MockRestAPI(true)
                    repository.networkErrorHandler = NetworkErrorHandler(
                        mockContext
                    )
                    repository.doWork(this, defaultQuery).test(this) {
                        this.assertValueAt(0, Resource().error("unknown error", -1))
                    }
                }
            }
    }
}