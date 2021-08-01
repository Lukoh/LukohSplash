package com.goforer.lukohsplash.domain.intermediator.photo

import com.goforer.lukohsplash.data.repository.remote.photo.GetPhotoInfoRepository
import com.goforer.lukohsplash.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotoInfoUseCase
@Inject
constructor(override val repository: GetPhotoInfoRepository) : RepoUseCase(repository)