package com.goforer.lukohsplash.di.module.fragment.photo

import com.goforer.lukohsplash.presentation.ui.photo.PhotoDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PhotoDetailFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributePhotoDetailFragment(): PhotoDetailFragment
}