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

package com.goforer.lukohsplash.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.LOADING
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed

@OptIn(ExperimentalCoroutinesApi::class)
open class MediatorViewModel(useCase: UseCase<Resource>, params: Params) : ViewModel() {
    val value = useCase.run(viewModelScope, params).flatMapLatest {
        flow {
            emit(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = Resource().loading(LOADING)
    )


    // You can implement code blow:
    // Just please visit below link if you'd like to know [StatFlow & SharedFlow]
    // Link : https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
    /*
    private val _value = MutableStateFlow(Resource().loading(LOADING))
    val value = _value

    init {
        viewModelScope.launch {
            useCase.run(viewModelScope, params).collect {
                _value.value = it
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()

        _value.value = Resource().loading(LOADING)
    }
     */
}
