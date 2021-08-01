package com.goforer.lukohsplash.domain.intermediator.user

import com.goforer.lukohsplash.data.repository.remote.user.GetUserLikesRepository
import com.goforer.lukohsplash.domain.UseCaseTest
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
class GetUserLikesUseCaseTest : UseCaseTest() {
    @Before
    fun setup() {
        setBaseRepository(GetUserLikesRepository::class.java)
        useCase = GetUserLikesUseCase(repository as GetUserLikesRepository)
    }

    @Test
    fun executeTest() {
        runBlockingTest {
            useCase.run(this, defaultParams).test(this) {
                this.assertValueAt(0, getResponseResult())
            }
        }
    }
}