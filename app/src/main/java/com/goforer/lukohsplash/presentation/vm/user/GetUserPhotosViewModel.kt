package com.goforer.lukohsplash.presentation.vm.user

import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.domain.intermediator.user.GetUserPhotosUseCase
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserPhotosViewModel
@Inject
constructor(override val useCase: GetUserPhotosUseCase) : TriggerViewModel<Resource>(useCase)
