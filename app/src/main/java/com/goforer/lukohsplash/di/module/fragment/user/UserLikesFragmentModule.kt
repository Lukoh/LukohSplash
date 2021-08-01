package com.goforer.lukohsplash.di.module.fragment.user

import com.goforer.lukohsplash.presentation.ui.user.UserLikesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserLikesFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeUserLikesFragment(): UserLikesFragment
}