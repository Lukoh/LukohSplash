package com.goforer.lukohsplash.presentation.vm.home

import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.domain.intermediator.home.GetPhotosUseCase
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotosViewModel
@Inject
constructor(override val useCase: GetPhotosUseCase) : TriggerViewModel<Resource>(useCase)