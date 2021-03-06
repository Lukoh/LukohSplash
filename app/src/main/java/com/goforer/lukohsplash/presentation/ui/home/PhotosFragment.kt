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

package com.goforer.lukohsplash.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.goforer.base.extension.*
import com.goforer.base.view.decoration.StaggeredGridItemOffsetDecoration
import com.goforer.lukohsplash.data.repository.paging.source.home.PhotosPagingSource
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Status
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.databinding.FragmentPhotosBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment
import com.goforer.lukohsplash.presentation.ui.home.adapter.PhotosAdapter
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.home.GetPhotosViewModel
import com.goforer.lukohsplash.presentation.vm.home.share.SharedPhotoIdViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs

class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotosBinding
        get() = FragmentPhotosBinding::inflate

    private var photoAdapter: PhotosAdapter? = null

    private var isAppBarExpended = true

    private var isFromBackStack = false

    @Inject
    lateinit var getPhotosViewModelFactory: GetPhotosViewModel.AssistedPhotosFactory

    @Inject
    internal lateinit var sharedPhotoIdViewModel: SharedPhotoIdViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(FRAGMENT_REQUEST_FROM_BACKSTACK) { _, bundle ->
            isFromBackStack = bundle.getBoolean(FRAGMENT_RESULT_FROM_BACKSTACK)
        }

        setAppBar()
        getPhotos(PhotosPagingSource.nextPage)
        photoAdapter = photoAdapter ?: PhotosAdapter(homeActivity) { itemView, item ->
            sharedPhotoIdViewModel.share(item.id)
            itemView.findNavController().navigate(
                PhotosFragmentDirections.actionPhotosFragmentToPhotoDetailFragment()
            )
        }
        with(binding) {
            rvPhotos.apply {
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
                isVerticalScrollBarEnabled = false
                layoutManager = gridManager
            }

            swipeRefreshContainer.setOnRefreshListener {
                isFromBackStack = false
                getPhotos(1)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            photoAdapter?.loadStateFlow?.collect {
                var state: LoadState = LoadState.Loading

                when {
                    it.append is LoadState.Error -> state = it.append
                    it.refresh is LoadState.Error -> state = it.refresh
                    it.refresh is LoadState.NotLoading -> {
                        launch {
                            with(binding) {
                                if (photoAdapter?.itemCount == 0)
                                    showNoPhotoMessage(rvPhotos, noPhotoContainer.root, true)
                                else
                                    showNoPhotoMessage(rvPhotos, noPhotoContainer.root, false)
                            }
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
                                showNoPhotoMessage(rvPhotos, noPhotoContainer.root, true)
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

        PhotosPagingSource.nextPage = 1
        isFromBackStack = false
    }

    private fun setAppBar() {
        with(binding) {
            appbarPhotos.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (appBarLayout.totalScrollRange == 0 || verticalOffset == 0) {
                    tvTitleCollapsed.alpha = 0f
                    tvTitleExpanded.alpha = 1f
                } else {
                    val ratio = appBarLayout.getCollapsedRatio(verticalOffset)
                    val referencedRatio =
                        appBarLayout.getCollapsedRatioWithReference(verticalOffset)

                    tvTitleCollapsed.alpha = referencedRatio
                    tvTitleExpanded.alpha = 1 - ratio
                }

                isAppBarExpended = if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                    if (isAppBarExpended) {
                        vToolbarLine.show()
                    }

                    false
                } else {
                    if (!isAppBarExpended) {
                        vToolbarLine.gone()
                    }

                    true
                }
            })
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getPhotos(page: Int) {
        val getPhotosViewModel: GetPhotosViewModel by viewModels {
            GetPhotosViewModel.provideFactory(
                getPhotosViewModelFactory,
                Params(Query().apply {
                    firstParam = page
                    secondParam = NetworkBoundWorker.NONE_ITEM_COUNT
                    thirdParam = NetworkBoundWorker.LATEST
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getPhotosViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                binding.swipeRefreshContainer.isRefreshing = false
                                @Suppress("UNCHECKED_CAST")
                                val photos = resource.getData() as? PagingData<Photo>

                                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
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
