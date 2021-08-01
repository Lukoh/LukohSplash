package com.goforer.lukohsplash.di.module.fragment.home

import com.goforer.lukohsplash.presentation.ui.home.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeSettingFragment(): SettingFragment
}