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
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs

class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotosBinding
        get() = FragmentPhotosBinding::inflate

    private lateinit var photoAdapter: PhotosAdapter

    private var isAppBarExpended = true

    @Inject
    internal lateinit var getPhotosViewModel: GetPhotosViewModel

    @Inject
    internal lateinit var sharedPhotoIdViewModel: SharedPhotoIdViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBar()
        photoAdapter = PhotosAdapter(homeActivity) { itemView, item ->
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
            gridManager.spanCount = 1
            gridManager.orientation = resources.configuration.orientation
            addItemDecoration(StaggeredGridItemOffsetDecoration(0, 1), 0)
            setItemViewCacheSize(RECYCLER_VIEW_CACHE_SIZE)
            layoutManager = gridManager
        }

        binding.swipeContainer.setOnRefreshListener {
            getPhotos()
        }

        lifecycleScope.launchWhenCreated {
            photoAdapter.loadStateFlow
                .collectLatest {
                    var state: LoadState = LoadState.Loading
                    if (it.append is LoadState.Error) state = it.append
                    else if (it.refresh is LoadState.Error) state = it.refresh

                    Timber.e("state.toString() $state")
                    /*
                    if (state is LoadState.Error) { // check error state
                        when (state.error.message) {
                            PagingErrorMessage.ERROR_MESSAGE_PAGING_EMPTY -> {
                                binding.exRecyclerView.showNoItemMessage(true)
                            }
                            else -> showErrorPopup(state.error.message ?: "") {
                            }
                        }
                    }

                     */


                    if (it.refresh !is LoadState.Loading)
                        binding.swipeContainer.isRefreshing = false
                }
        }

        getPhotos()
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

    private fun getPhotos() {
        getPhotosViewModel.pullTrigger(Params(Query().apply {
            firstParam = 1
            secondParam = NONE_ITEM_COUNT
            thirdParam = LATEST
        })) { resource ->
            when (resource.getStatus()) {
                Status.SUCCESS -> {
                    resource.getData()?.let {
                        binding.swipeContainer.isRefreshing = false
                        @Suppress("UNCHECKED_CAST")
                        val photos = resource.getData() as? PagingData<Photo>

                        lifecycleScope.launchWhenCreated {
                            photoAdapter.submitData(photos!!)
                        }
                    }
                }

                Status.ERROR -> {
                    binding.swipeContainer.isRefreshing = false
                    showErrorPopup(resource.getMessage()!!) {}

                }

                Status.LOADING -> {
                    binding.swipeContainer.isRefreshing = true
                }
            }
        }
    }
}