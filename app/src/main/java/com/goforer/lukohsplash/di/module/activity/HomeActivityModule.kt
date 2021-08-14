/*
 * Copyright (C) 2021 Lukoh Nam, goForer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.goforer.lukohsplash.di.module.activity

import com.goforer.lukohsplash.di.module.fragment.home.PhotosFragmentModule
import com.goforer.lukohsplash.di.module.fragment.home.SettingFragmentModule
import com.goforer.lukohsplash.di.module.fragment.photo.PhotoDetailFragmentModule
import com.goforer.lukohsplash.di.module.fragment.user.UserCollectionFragmentModule
import com.goforer.lukohsplash.di.module.fragment.user.UserFragmentModule
import com.goforer.lukohsplash.di.module.fragment.user.UserLikesFragmentModule
import com.goforer.lukohsplash.di.module.fragment.user.UserPhotosFragmentModule
import com.goforer.lukohsplash.presentation.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {
    @ContributesAndroidInjector(
        modules = [
            //sign up
            PhotosFragmentModule::class,
            PhotoDetailFragmentModule::class,
            SettingFragmentModule::class,
            UserFragmentModule::class,
            UserPhotosFragmentModule::class,
            UserLikesFragmentModule::class,
            UserCollectionFragmentModule::class
        ]
    )

    abstract fun contributeHomeActivity(): HomeActivity
}
