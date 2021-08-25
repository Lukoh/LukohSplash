package com.goforer.lukohsplash.di.module.fragment.user

import com.goforer.lukohsplash.presentation.ui.user.UserCollectionPhotosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserCollectionPhotosFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeUserCollectionPhotosFragment(): UserCollectionPhotosFragment
}
