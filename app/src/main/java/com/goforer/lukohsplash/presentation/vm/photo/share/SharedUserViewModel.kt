package com.goforer.lukohsplash.presentation.vm.photo.share

import com.goforer.lukohsplash.data.source.model.entity.user.response.User
import com.goforer.lukohsplash.presentation.vm.SharedViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedUserViewModel
@Inject
constructor() : SharedViewModel<User>()