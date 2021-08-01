package com.goforer.lukohsplash.domain.intermediator.home

import com.goforer.lukohsplash.data.repository.remote.home.GetPhotosRepository
import com.goforer.lukohsplash.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotosUseCase
@Inject
constructor(override val repository: GetPhotosRepository) : RepoUseCase(repository)