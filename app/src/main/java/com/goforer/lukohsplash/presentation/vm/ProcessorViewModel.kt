package com.goforer.lukohsplash.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class ProcessorViewModel<Value>(private val useCase: UseCase<Value>, params: Params) :
    ViewModel() {
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

    // You can implement code blow:
    // Just go to Main or Challenge branch here if you'd like to know how to implement
    /*
    private var initValue: Value? = null
    val value = useCase.run(viewModelScope, params).flatMapLatest {
        flow {
            initValue = it
            emit(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = initValue
    )
     */
}
