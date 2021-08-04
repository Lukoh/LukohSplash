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

package com.goforer.lukohsplash.domain

import androidx.paging.PagingData
import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.nhaarman.mockitokotlin2.isA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import org.junit.rules.TestWatcher
import org.mockito.Mockito

open class PagingDataUseCaseTest<P : Any> : TestWatcher() {
    lateinit var useCase: UseCase<Params, PagingData<P>>
    lateinit var repository: Repository<PagingData<P>>
    lateinit var viewModel: TriggerViewModel<PagingData<P>>
    var defaultQuery = Query()
    var defaultParams = Params(defaultQuery)

    @DelicateCoroutinesApi
    fun <T> setBaseRepository(repoClass: Class<T>) {
        repository = Mockito.mock(repoClass as Class<Repository<PagingData<P>>>)
        Mockito.`when`(repository.doWork(isA<CoroutineScope>(), isA<Query>()))
            .thenReturn(
                flowOf(PagingData.empty<P>()).shareIn(
                    scope = GlobalScope,
                    started = SharingStarted.Eagerly,
                    replay = 1
                )
            )
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