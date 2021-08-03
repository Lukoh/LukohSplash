package com.goforer.lukohsplash.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class TriggerViewModel<Value>(open val useCase: UseCase<Params, Value>) : ViewModel() {
    private var value: Any? = null

    @ExperimentalCoroutinesApi
    open fun pullTrigger(params: Params, doOnResult: (result: Value) -> Unit) {
        viewModelScope.launch {
            useCase.run(viewModelScope, params).mapLatest {
                value = it
                doOnResult(it)
            }.stateIn(
                scope = viewModelScope,
                started = Eagerly,
                initialValue = value
            )
        }
    }
}