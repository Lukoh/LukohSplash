package com.goforer.lukohsplash.di.module.fragment.user

import com.goforer.lukohsplash.presentation.ui.user.UserCollectionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserCollectionFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeUserCollectionFragment(): UserCollectionFragment
}