package com.goforer.lukohsplash.presentation.vm.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.goforer.lukohsplash.domain.processor.photo.CheckFileExistUseCase
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.ProcessorViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CheckFileExistViewModel
@AssistedInject
constructor(
    useCase: CheckFileExistUseCase,
    @Assisted private val params: Params,
) : ProcessorViewModel<Boolean>(useCase, params) {
    @AssistedFactory
    interface AssistedFileExistFactory {
        fun create(params: Params): CheckFileExistViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFileExistFactory, params: Params
        ) = object : Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(params) as T
            }
        }
    }
}
