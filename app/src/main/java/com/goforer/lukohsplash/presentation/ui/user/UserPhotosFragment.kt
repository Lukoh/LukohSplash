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

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.goforer.base.extension.RECYCLER_VIEW_CACHE_SIZE
import com.goforer.base.extension.isNull
import com.goforer.base.view.decoration.StaggeredGridItemOffsetDecoration
import com.goforer.base.view.dialog.NormalDialog
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.data.repository.paging.source.user.UserPhotosPagingSource
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Status
import com.goforer.lukohsplash.databinding.FragmentItemListBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment
import com.goforer.lukohsplash.presentation.ui.user.adapter.UserPhotosAdapter
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.photo.share.SharedUserNameViewModel
import com.goforer.lukohsplash.presentation.vm.user.GetUserPhotosViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

class UserPhotosFragment : BaseFragment<FragmentItemListBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemListBinding
        get() = FragmentItemListBinding::inflate

    private lateinit var userFragment: UserFragment

    private var photoAdapter: UserPhotosAdapter? = null

    private lateinit var userName: String

    @Inject
    lateinit var getUserPhotosViewModelFactory: GetUserPhotosViewModel.AssistedUserPhotosFactory

    @Inject
    internal lateinit var sharedUserNameViewModel: SharedUserNameViewModel

    companion object {
        fun newInstance(fragment: UserFragment) = UserPhotosFragment().apply {
            userFragment = fragment
            arguments = Bundle(1).apply {
                putString(FRAGMENT_TAG, UserPhotosFragment::class.java.name)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoAdapter ?: observeUserName()
        with(binding) {
            swipeRefreshLayoutContainer.layoutTransition.setAnimateParentHierarchy(false)
            swipeRefreshContainer.setOnRefreshListener {
                if (userName != "")
                    getUserPhotos(userName, 1)
            }

            photoAdapter = photoAdapter ?: UserPhotosAdapter(homeActivity) { itemView, item ->
                itemView.findNavController().navigate(
                    UserFragmentDirections.actionUserFragmentToPhotoViewerFragment(
                        item.urls.raw
                    )
                )
            }

            rvList.apply {
                val gridManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL).apply {
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                }

                adapter = photoAdapter
                photoAdapter?.stateRestorationPolicy = PREVENT_WHEN_EMPTY
                gridManager.spanCount = 1
                gridManager.orientation = resources.configuration.orientation
                itemAnimator?.changeDuration = 0
                addItemDecoration(StaggeredGridItemOffsetDecoration(0, 1), 0)
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                setItemViewCacheSize(RECYCLER_VIEW_CACHE_SIZE)
                layoutManager = gridManager
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            photoAdapter?.loadStateFlow?.collectLatest {
                var state: LoadState = LoadState.Loading

                when {
                    it.append is LoadState.Error -> state = it.append
                    it.refresh is LoadState.Error -> state = it.refresh
                    it.refresh is LoadState.NotLoading -> {
                        with(binding) {
                            if (photoAdapter?.itemCount == 0)
                                showNoPhotoMessage(rvList, noPhotoContainer.root, true)
                            else
                                showNoPhotoMessage(rvList, noPhotoContainer.root, false)
                        }
                    }
                    it.refresh !is LoadState.Loading -> binding.swipeRefreshContainer.isRefreshing =
                        false
                }

                Timber.e("state.toString() $state")

                /*
                if (state is LoadState.Error) { // check error state
                    when (state.error.message) {
                        PagingErrorMessage.ERROR_MESSAGE_PAGING_EMPTY -> {
                            with(binding) {
                                showNoPhotoMessage(rvList, noPhotoContainer.root, true)
                            }
                        }

                        else -> showErrorPopup(state.error.message ?: "") {
                        }
                    }
                }

                 */
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        UserPhotosPagingSource.nextPage = 1
    }

    override fun onDestroy() {
        super.onDestroyView()

        UserPhotosPagingSource.nextPage = 1
    }

    override fun onBackPressed() {
        findNavController(userFragment).popBackStack()
    }

    private fun observeUserName() {
        sharedUserNameViewModel.shared {
            it?.isNull({
                userName = ""
                NormalDialog.Builder(context)
                    .setTitle(R.string.no_user_name)
                    .setMessage(R.string.no_user_name_phrase)
                    .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
                    }.setOnDismissListener {
                    }.show(homeActivity.supportFragmentManager)
            }, { name ->
                userName = name
                getUserPhotos(userName, UserPhotosPagingSource.nextPage)
            })
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getUserPhotos(name: String, page: Int) {
        val getUserPhotosViewModel: GetUserPhotosViewModel by viewModels {
            GetUserPhotosViewModel.provideFactory(
                getUserPhotosViewModelFactory,
                Params(Query().apply {
                    firstParam = name
                    secondParam = page
                    thirdParam = "days"
                    forthParam = 30
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getUserPhotosViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                binding.swipeRefreshContainer.isRefreshing = false
                                @Suppress("UNCHECKED_CAST")
                                val photos = resource.getData() as? PagingData<Photo>

                                lifecycleScope.launchWhenCreated {
                                    photoAdapter?.submitData(photos!!)
                                }
                            }
                        }

                        Status.ERROR -> {
                            binding.swipeRefreshContainer.isRefreshing = false
                            showErrorPopup(resource.getMessage()!!) {}

                        }

                        Status.LOADING -> {
                            binding.swipeRefreshContainer.isRefreshing = true
                        }
                    }
                }
            }
        }
    }
}
