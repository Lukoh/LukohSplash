package com.goforer.lukohsplash.domain.intermediator.user

import com.goforer.lukohsplash.data.repository.remote.user.GetUserLikesRepository
import com.goforer.lukohsplash.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserLikesUseCase
@Inject
constructor(override val repository: GetUserLikesRepository) : RepoUseCase(repository)