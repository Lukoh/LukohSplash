package com.goforer.lukohsplash.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class TriggerViewModel<Resource>(open val useCase: UseCase<Params, Resource>) : ViewModel() {
    private var value: Any? = null

    @ExperimentalCoroutinesApi
    open fun pullTrigger(params: Params, doOnResult: (result: Resource) -> Unit) {
        viewModelScope.launch {
            useCase.run(viewModelScope, params).flatMapLatest {
                value = it
                flow {
                    emit(doOnResult(it))
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = value
            )
        }
    }
}