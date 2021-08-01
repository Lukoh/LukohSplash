package com.goforer.lukohsplash.domain

import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.source.model.cache.entity.BaseTest
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import com.nhaarman.mockitokotlin2.isA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import org.junit.rules.TestWatcher
import org.mockito.Mockito

open class UseCaseTest : TestWatcher() {
    lateinit var useCase: UseCase<Params, Resource>
    lateinit var useCaseForDownloadPhoto: UseCase<Params, Int?>

    lateinit var repository: Repository<Resource>

    lateinit var viewModel: TriggerViewModel<*>

    var defaultQuery = Query()
    var defaultParams = Params(defaultQuery)

    @OptIn(DelicateCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    fun <T> setBaseRepository(repoClass: Class<T>) {
        repository = Mockito.mock(repoClass as Class<Repository<Resource>>)
        Mockito.`when`(repository.doWork(isA<CoroutineScope>(), isA<Query>()))
            .thenReturn(
                flowOf(getResponseResult()).shareIn(
                    scope = GlobalScope,
                    started = SharingStarted.Eagerly,
                    replay = 1
                )
            )
    }

    fun getResponseResult(): Resource {
        return Resource().success(BaseTest().responseResult0)
    }

    fun getParams(vararg params: Any?): Params {
        val query = Query()
        params.forEachIndexed { index, any ->
            when (index) {
                0 -> query.firstParam = any ?: 0
                1 -> query.secondParam = any ?: 0
                2 -> query.thirdParam = any
                3 -> query.forthParam = any
            }
        }

        return Params(query)
    }
}