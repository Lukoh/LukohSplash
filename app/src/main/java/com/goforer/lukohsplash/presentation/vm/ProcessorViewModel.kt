package com.goforer.lukohsplash.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class ProcessorViewModel<Value>(private val useCase: UseCase<Value>, params: Params) : ViewModel() {
    private var initValue: Value? = null

    private val _value = MutableStateFlow(initValue)
    val value = _value

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
