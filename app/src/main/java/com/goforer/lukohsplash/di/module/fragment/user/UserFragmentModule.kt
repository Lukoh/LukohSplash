package com.goforer.lukohsplash.di.module.fragment.user

import com.goforer.lukohsplash.presentation.ui.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment
}