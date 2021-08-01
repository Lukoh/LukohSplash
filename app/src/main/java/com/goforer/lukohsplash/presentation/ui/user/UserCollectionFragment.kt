package com.goforer.lukohsplash.presentation.ui.user

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.goforer.base.extension.RECYCLER_VIEW_CACHE_SIZE
import com.goforer.base.extension.isNull
import com.goforer.base.view.decoration.StaggeredGridItemOffsetDecoration
import com.goforer.base.view.dialog.NormalDialog
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.data.source.network.response.Status
import com.goforer.lukohsplash.databinding.FragmentItemListBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment
import com.goforer.lukohsplash.presentation.ui.user.adapter.UserCollectionAdapter
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.photo.share.SharedUserNameViewModel
import com.goforer.lukohsplash.presentation.vm.user.GetUserCollectionsViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

class UserCollectionFragment : BaseFragment<FragmentItemListBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemListBinding
        get() = FragmentItemListBinding::inflate

    private lateinit var userName: String

    private lateinit var collectionAdapter: UserCollectionAdapter

    @Inject
    internal lateinit var getUserCollectionsViewModel: GetUserCollectionsViewModel

    @Inject
    internal lateinit var sharedUserNameViewModel: SharedUserNameViewModel

    companion object {
        fun newInstance() = UserCollectionFragment().apply {
            arguments = Bundle(1).apply {
                putString(FRAGMENT_TAG, UserCollectionFragment::class.java.name)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserName()
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (userName != "")
                getUserCollection(userName)
        }

        collectionAdapter = UserCollectionAdapter(homeActivity) { _, _ ->
        }

        binding.recyclerView.apply {
            val gridManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }

            adapter = collectionAdapter
            gridManager.spanCount = 1
            gridManager.orientation = resources.configuration.orientation
            addItemDecoration(StaggeredGridItemOffsetDecoration(0, 1), 0)
            setItemViewCacheSize(RECYCLER_VIEW_CACHE_SIZE)
            layoutManager = gridManager
        }

        lifecycleScope.launchWhenCreated {
            collectionAdapter.loadStateFlow
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
                        binding.swipeRefreshLayout.isRefreshing = false
                }
        }
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
                getUserCollection(name)
            })
        }
    }

    private fun getUserCollection(name: String) {
        getUserCollectionsViewModel.pullTrigger(Params(Query().apply {
            firstParam = name
            secondParam = -1
        })) { resource ->
            when (resource.getStatus()) {
                Status.SUCCESS -> {
                    resource.getData()?.let {
                        binding.swipeRefreshLayout.isRefreshing = false
                        @Suppress("UNCHECKED_CAST")
                        val collections = resource.getData() as? PagingData<Collection>

                        lifecycleScope.launchWhenCreated {
                            collectionAdapter.submitData(collections!!)
                        }
                    }
                }

                Status.ERROR -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    showErrorPopup(resource.getMessage()!!) {}

                }

                Status.LOADING -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
            }
        }
    }
}