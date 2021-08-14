package com.goforer.lukohsplash.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProcessorViewModel<Value>(
    private val useCase: UseCase<Value>,
    params: Params,
    delayTimeout: Long
) : ViewModel() {
    private var initValue: Value? = null

    private val _value = MutableStateFlow(initValue)
    val value = flow {
        delay(delayTimeout)
        emit(_value.value)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = initValue
    )

    init {
        viewModelScope.launch {
            useCase.run(viewModelScope, params).collect {
                initValue = it
                _value.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        _value.value = initValue
    }
}
