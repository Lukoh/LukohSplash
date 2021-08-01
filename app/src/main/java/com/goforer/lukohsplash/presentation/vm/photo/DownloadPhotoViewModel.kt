package com.goforer.lukohsplash.presentation.vm.photo

import com.goforer.lukohsplash.domain.processor.photo.DownloadPhotosUseCase
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadPhotoViewModel
@Inject
constructor(override val useCase: DownloadPhotosUseCase) : TriggerViewModel<Int?>(useCase)