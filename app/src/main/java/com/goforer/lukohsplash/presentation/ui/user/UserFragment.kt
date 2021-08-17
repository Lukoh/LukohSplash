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

package com.goforer.lukohsplash.presentation.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.goforer.base.extension.*
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.data.source.model.entity.user.response.User
import com.goforer.lukohsplash.databinding.FragmentUserBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment
import com.goforer.lukohsplash.presentation.vm.photo.share.SharedUserViewModel
import com.google.android.material.appbar.AppBarLayout
import javax.inject.Inject
import kotlin.math.abs

class UserFragment : BaseFragment<FragmentUserBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserBinding
        get() = FragmentUserBinding::inflate

    private var isAppBarExpended = true

    private var currentTab = USER_PHOTOS

    private val fragments: List<BaseFragment<*>> = listOf(
        UserPhotosFragment.newInstance(this),
        UserLikesFragment.newInstance(this),
        UserCollectionFragment.newInstance(this)
    )

    @Inject
    internal lateinit var sharedUserViewModel: SharedUserViewModel

    companion object {
        const val USER_PHOTOS = 0
        const val USER_LIKES = 1
        const val USER_COLLECTION = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        observeUser()
        onTabChanged(binding.viewPager.currentItem)
    }

    override fun onBackPressed() {
        findNavController(this).popBackStack()
    }

    private fun observeUser() {
        sharedUserViewModel.shared {
            setup(it)
        }
    }

    private fun setAppBar() {
        with(binding) {
            appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (appBarLayout.totalScrollRange == 0 || verticalOffset == 0) {
                    tvUserNameCollapsed.alpha = 0f
                    tvUserName.alpha = 1f
                } else {
                    val ratio = appBarLayout.getCollapsedRatio(verticalOffset)
                    val referencedRatio =
                        appBarLayout.getCollapsedRatioWithReference(verticalOffset)

                    tvUserNameCollapsed.alpha = referencedRatio
                    tvUserName.alpha = 1 - ratio
                }

                isAppBarExpended = if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                    if (isAppBarExpended) {
                        toolbar.title = tvUserName.text
                    }

                    false
                } else {
                    if (!isAppBarExpended) {
                        toolbar.title = ""
                    }

                    true
                }
            })
        }
    }

    private fun setAdapter() {
        with(binding) {
            with(viewPager) {
                adapter = object : FragmentStateAdapter(this@UserFragment) {
                    private val mList: List<BaseFragment<*>> = fragments

                    override fun getItemCount(): Int {
                        return mList.size
                    }

                    override fun createFragment(position: Int): Fragment {
                        return mList[position]
                    }
                }

                registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        onTabChanged(position)
                        currentTab = position
                    }
                })

                tabItemPhotos.setOnClickListener {
                    currentItem = USER_PHOTOS
                }

                tabItemLikes.setOnClickListener {
                    currentItem = USER_LIKES
                }

                tabItemCollection.setOnClickListener {
                    currentItem = USER_COLLECTION
                }

                currentItem = currentTab
            }
        }
    }

    private fun setup(user: User) = with(binding) {
        toolbar.setOnClickListener { appBar.setExpanded(true) }
        userContentLayout.isVisible = true
        ivUser.loadProfilePicture(user)
        tvUserName.text = user.name
        tvPhotosCount.text = user.total_photos?.toPrettyString()
        tvLikesCount.text = user.total_likes?.toPrettyString()
        tvCollectionsCount.text = user.total_collections?.toPrettyString()
        user.location.isNull({
            tvLocation.hide()
        }, { location ->
            tvLocation.setTextAndVisibility(location)
        })

        bioTextView.setTextAndVisibility(user.bio?.trimEnd())
        setAppBar()
    }

    private fun onTabChanged(fragmentNum: Int) =
        with(binding) {
            setTabButtonUI(tabItemPhotos, fragmentNum == USER_PHOTOS)
            setTabButtonUI(tabItemLikes, fragmentNum == USER_LIKES)
            setTabButtonUI(tabItemCollection, fragmentNum == USER_COLLECTION)
        }

    private fun setTabButtonUI(view: TextView?, isSelected: Boolean) {
        view?.let {
            if (isSelected) {
                it.setTextColor(context.getColor(R.color.white))
                it.backgroundTintList =
                    ContextCompat.getColorStateList(homeActivity, R.color.colorPrimary)
            } else {
                it.setTextColor(context.getColor(R.color.colorPrimary))
                it.backgroundTintList =
                    ContextCompat.getColorStateList(homeActivity, R.color.colorTransparent)
            }
        }
    }
}
