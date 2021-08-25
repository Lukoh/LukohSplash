package com.goforer.lukohsplash.presentation.vm.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goforer.lukohsplash.domain.mediator.user.GetUserCollectionPhotosUseCase
import com.goforer.lukohsplash.presentation.vm.MediatorViewModel
import com.goforer.lukohsplash.presentation.vm.Params
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetUserCollectionPhotosViewModel
@AssistedInject
constructor(
    useCase: GetUserCollectionPhotosUseCase,
    @Assisted private val params: Params
) : MediatorViewModel(useCase, params) {
    @AssistedFactory
    interface AssistedUserCollectionPhotosFactory {
        fun create(params: Params): GetUserCollectionPhotosViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedUserCollectionPhotosFactory, params: Params) =
            object :
                ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return assistedFactory.create(params) as T
                }
            }
    }
}
