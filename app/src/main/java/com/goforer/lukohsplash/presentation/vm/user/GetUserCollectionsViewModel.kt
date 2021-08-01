package com.goforer.lukohsplash.presentation.vm.user

import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.domain.intermediator.user.GetUserCollectionsUseCase
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserCollectionsViewModel
@Inject
constructor(override val useCase: GetUserCollectionsUseCase) : TriggerViewModel<Resource>(useCase)