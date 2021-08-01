package com.goforer.lukohsplash.di.module.fragment.user

import com.goforer.lukohsplash.presentation.ui.user.UserPhotosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserPhotosFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeUserPhotosFragment(): UserPhotosFragment
}