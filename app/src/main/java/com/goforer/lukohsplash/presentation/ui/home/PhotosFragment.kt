package com.goforer.lukohsplash.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Status
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.LATEST
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker.Companion.NONE_ITEM_COUNT
import com.goforer.lukohsplash.databinding.FragmentPhotosBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment
import com.goforer.lukohsplash.presentation.ui.home.adapter.PhotosAdapter
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.home.GetPhotosViewModel
import com.goforer.lukohsplash.presentation.vm.home.share.SharedPhotoIdViewModel
import com.goforer.base.extension.*
import com.goforer.base.view.decoration.StaggeredGridItemOffsetDecoration
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs

class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotosBinding
        get() = FragmentPhotosBinding::inflate

    private var photoAdapter: PhotosAdapter? = null

    private var isAppBarExpended = true

    private var currentPosition = 0

    @Inject
    internal lateinit var getPhotosViewModel: GetPhotosViewModel

    @Inject
    internal lateinit var sharedPhotoIdViewModel: SharedPhotoIdViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBar()
        photoAdapter ?: getPhotos()
        photoAdapter = photoAdapter ?: PhotosAdapter(homeActivity) { itemView, item, position ->
            currentPosition = position
            sharedPhotoIdViewModel.share(item.id)
            itemView.findNavController().navigate(
                PhotosFragmentDirections.actionPhotosFragmentToPhotoDetailFragment()
            )
        }

        binding.rvPhotos.apply {
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

        binding.swipeRefreshContainer.setOnRefreshListener {
            getPhotos()
        }

        lifecycleScope.launchWhenCreated {
            photoAdapter?.loadStateFlow?.collectLatest {
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
                    it.refresh !is LoadState.Loading -> binding.swipeRefreshContainer.isRefreshing = false
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

    @ExperimentalCoroutinesApi
    private fun getPhotos() {
        getPhotosViewModel.pullTrigger(Params(Query().apply {
            firstParam = 1
            secondParam = NONE_ITEM_COUNT
            thirdParam = LATEST
        })) { resource ->
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