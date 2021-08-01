package com.goforer.lukohsplash.presentation.vm.photo

import android.graphics.Bitmap
import com.goforer.lukohsplash.domain.processor.photo.CropImageUseCase
import com.goforer.lukohsplash.presentation.vm.TriggerViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CropImageViewModel
@Inject
constructor(override val useCase: CropImageUseCase) : TriggerViewModel<Bitmap>(useCase)