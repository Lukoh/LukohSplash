package com.goforer.lukohsplash.presentation.vm.user

import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.domain.intermediator.user.GetUserLikesUseCase
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserLikesViewModel
@Inject
constructor(override val useCase: GetUserLikesUseCase) : TriggerViewModel<Resource>(useCase)