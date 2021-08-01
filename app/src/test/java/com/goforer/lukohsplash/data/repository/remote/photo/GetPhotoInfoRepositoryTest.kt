package com.goforer.lukohsplash.data.repository.remote.photo

import com.goforer.lukohsplash.data.repository.remote.RepositoryTest
import com.goforer.test.kit.QueryTool
import com.goforer.test.kit.flow.test
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class GetPhotoInfoRepositoryTest : RepositoryTest() {
    @Before
    fun setup() {
        setBaseRepository(
            GetPhotoInfoRepository(),
            QueryTool.getQuery("0")
        )
    }

    @Test
    fun workTest() {
        runBlockingTest {
            repository.doWork(this, defaultQuery).test(this) {
                this.assertValueAt(0, getResourceValue(photoInfoTest.photo0))
            }
        }
    }
}