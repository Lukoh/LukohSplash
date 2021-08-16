package com.goforer.lukohsplash.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
open class ProcessorViewModel<Value>(useCase: UseCase<Value>, params: Params) : ViewModel() {
    private var initValue: Value? = null
    val value = useCase.run(viewModelScope, params).flatMapLatest {
        flow {
            initValue = it
            emit(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = initValue
    )

    // You can implement code blow:
    // Just please visit below link if you'd like to know [StatFlow & SharedFlow]
    // Link : https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
    /*
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
     */
}
