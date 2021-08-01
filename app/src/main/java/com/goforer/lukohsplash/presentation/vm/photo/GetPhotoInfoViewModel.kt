package com.goforer.lukohsplash.presentation.vm.photo

import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.domain.intermediator.photo.GetPhotoInfoUseCase
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotoInfoViewModel
@Inject
constructor(override val useCase: GetPhotoInfoUseCase) : TriggerViewModel<Resource>(useCase)