package com.goforer.lukohsplash.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.lukohsplash.domain.UseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class TriggerViewModel<Resource>(open val useCase: UseCase<Params, Resource>) : ViewModel() {
    open fun pullTrigger(params: Params, doOnResult: (result: Resource) -> Unit) {
        viewModelScope.launch {
            useCase.run(viewModelScope, params) .collect {
                doOnResult(it)
            }
        }
    }
}