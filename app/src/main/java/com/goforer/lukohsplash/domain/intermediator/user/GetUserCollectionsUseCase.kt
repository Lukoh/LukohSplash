package com.goforer.lukohsplash.domain.intermediator.user

import com.goforer.lukohsplash.data.repository.remote.user.GetUserCollectionsRepository
import com.goforer.lukohsplash.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserCollectionsUseCase
@Inject
constructor(override val repository: GetUserCollectionsRepository) : RepoUseCase(repository)