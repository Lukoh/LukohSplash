package com.goforer.lukohsplash.di.module.fragment.photo

import com.goforer.lukohsplash.presentation.ui.photo.PhotoViewerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PhotoViewerFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributePhotoViewerFragment(): PhotoViewerFragment
}
