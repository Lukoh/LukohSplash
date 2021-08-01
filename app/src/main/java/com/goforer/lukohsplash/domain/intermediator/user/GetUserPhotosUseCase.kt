package com.goforer.lukohsplash.domain.intermediator.user

import com.goforer.lukohsplash.data.repository.remote.user.GetUserPhotosRepository
import com.goforer.lukohsplash.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserPhotosUseCase
@Inject
constructor(override val repository: GetUserPhotosRepository) : RepoUseCase(repository)