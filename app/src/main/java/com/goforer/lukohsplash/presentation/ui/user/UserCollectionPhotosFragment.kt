package com.goforer.lukohsplash.presentation.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.goforer.base.extension.*
import com.goforer.base.view.decoration.StaggeredGridItemOffsetDecoration
import com.goforer.lukohsplash.data.repository.paging.source.home.PhotosPagingSource
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Status
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.databinding.FragmentCollectionPhotosBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment
import com.goforer.lukohsplash.presentation.ui.user.adapter.UserCollectionPhotosAdapter
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.photo.share.SharedUserViewModel
import com.goforer.lukohsplash.presentation.vm.user.GetUserCollectionPhotosViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs

class UserCollectionPhotosFragment : BaseFragment<FragmentCollectionPhotosBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCollectionPhotosBinding
        get() = FragmentCollectionPhotosBinding::inflate

    private var userCollectionPhotoAdapter: UserCollectionPhotosAdapter? = null

    private var isAppBarExpended = true

    private var isFromBackStack = false

    private val args: UserCollectionPhotosFragmentArgs by navArgs()

    @Inject
    lateinit var getUserCollectionPhotosFactory: GetUserCollectionPhotosViewModel.AssistedUserCollectionPhotosFactory

    @Inject
    internal lateinit var sharedUserViewModel: SharedUserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(FRAGMENT_REQUEST_FROM_BACKSTACK) { _, bundle ->
            isFromBackStack = bundle.getBoolean(FRAGMENT_RESULT_FROM_BACKSTACK)
        }

        observeUser()
        setAppBar()
        getCollectionPhotos(args.collectionId, PhotosPagingSource.nextPage)
        userCollectionPhotoAdapter =
            userCollectionPhotoAdapter ?: UserCollectionPhotosAdapter(homeActivity) { _, item ->
                item.links?.html?.let {
                    loadContent(it)
                }
            }
        with(binding) {
            rvPhotos.apply {
                val gridManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL).apply {
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                }

                adapter = userCollectionPhotoAdapter
                userCollectionPhotoAdapter?.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
                getCollectionPhotos(args.collectionId, 1)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            userCollectionPhotoAdapter?.loadStateFlow?.collect {
                var state: LoadState = LoadState.Loading

                when {
                    it.append is LoadState.Error -> state = it.append
                    it.refresh is LoadState.Error -> state = it.refresh
                    it.refresh is LoadState.NotLoading -> {
                        launch {
                            with(binding) {
                                if (userCollectionPhotoAdapter?.itemCount == 0)
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

    override fun onBackPressed() {
        setFragmentResult(
            FRAGMENT_REQUEST_FROM_BACKSTACK,
            bundleOf(FRAGMENT_RESULT_FROM_BACKSTACK to true)
        )
        NavHostFragment.findNavController(this).popBackStack()
    }

    private fun setAppBar() {
        with(binding) {
            tvTitleCollapsed.text = args.collectionTitle
            tvTitleExpanded.text = args.collectionTitle
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
    private fun getCollectionPhotos(id: String, page: Int) {
        val getUserCollectionPhotosViewModel: GetUserCollectionPhotosViewModel by viewModels {
            GetUserCollectionPhotosViewModel.provideFactory(
                getUserCollectionPhotosFactory,
                Params(Query().apply {
                    firstParam = id
                    secondParam = page
                    thirdParam = NetworkBoundWorker.NONE_ITEM_COUNT
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getUserCollectionPhotosViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                binding.swipeRefreshContainer.isRefreshing = false
                                @Suppress("UNCHECKED_CAST")
                                val photos = resource.getData() as? PagingData<Photo>

                                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                                    userCollectionPhotoAdapter?.submitData(photos!!)
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

    private fun observeUser() {
        sharedUserViewModel.shared {
            binding.ivUser.loadProfilePicture(it.profile_image?.large)
        }
    }
}
