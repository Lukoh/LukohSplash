package com.goforer.lukohsplash.domain.mediator.user

import com.goforer.lukohsplash.data.repository.remote.user.GetUserCollectionPhotosRepository
import com.goforer.lukohsplash.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserCollectionPhotosUseCase
@Inject
constructor(override val repository: GetUserCollectionPhotosRepository) : RepoUseCase(repository)
