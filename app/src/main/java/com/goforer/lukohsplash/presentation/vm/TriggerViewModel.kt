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

import androidx.lifecycle.*
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
open class TriggerViewModel<Value>(open val useCase: UseCase<Params, Value>) : ViewModel() {
    private var initValue: Value? = null

    private val _value = MutableStateFlow(initValue)

    val value = flow {
        delay(800)
        emit(_value.value)
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = initValue
    )

    @ExperimentalCoroutinesApi
    open fun pullTrigger(params: Params, lifecycleOwner: LifecycleOwner): TriggerViewModel<Value> {
        viewModelScope.launch {
            useCase.run(viewModelScope, params).collect {
                initValue = it
                _value.value = it
            }
        }

        return this@TriggerViewModel
    }
}
