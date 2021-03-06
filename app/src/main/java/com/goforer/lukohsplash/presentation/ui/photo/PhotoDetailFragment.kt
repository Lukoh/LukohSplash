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

package com.goforer.lukohsplash.presentation.ui.photo

import android.Manifest
import android.app.DownloadManager
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.goforer.base.extension.*
import com.goforer.base.view.decoration.SpacingItemDecoration
import com.goforer.base.view.dialog.NormalDialog
import com.goforer.base.view.widget.SwipeCoordinatorLayout
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Status
import com.goforer.lukohsplash.databinding.FragmentPhotoDetailBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment
import com.goforer.lukohsplash.presentation.ui.photo.adapter.ExifAdapter
import com.goforer.lukohsplash.presentation.ui.photo.adapter.TagAdapter
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.home.share.SharedPhotoIdViewModel
import com.goforer.lukohsplash.presentation.vm.photo.CheckFileExistViewModel
import com.goforer.lukohsplash.presentation.vm.photo.DownloadPhotoViewModel
import com.goforer.lukohsplash.presentation.vm.photo.GetPhotoInfoViewModel
import com.goforer.lukohsplash.presentation.vm.photo.share.SharedUserNameViewModel
import com.goforer.lukohsplash.presentation.vm.photo.share.SharedUserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PhotoDetailFragment : BaseFragment<FragmentPhotoDetailBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotoDetailBinding
        get() = FragmentPhotoDetailBinding::inflate

    private lateinit var listener: PermissionCallback

    private var downloadCount = 0

    private var isFromBackStack = false

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val grantedPermission = HashMap<Int, Permission>()
            permissions.entries.forEachIndexed { index, item ->
                grantedPermission[index] = Permission(item.key, item.value)
            }

            if (grantedPermission[0]?.isGranted!! && grantedPermission[1]?.isGranted!!) {
                listener.onPermissionGranted()
            } else {
                val showRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        homeActivity, grantedPermission[0]?.permissionName!!
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        homeActivity, grantedPermission[1]?.permissionName!!
                    )
                if (!showRationale) {
                    createDialogForStoragePermission()
                }
            }
        }

    @Inject
    lateinit var getPhotoInfoViewModelFactory: GetPhotoInfoViewModel.AssistedPhotoInfoFactory

    @Inject
    lateinit var downloadPhotoViewModelFactory: DownloadPhotoViewModel.AssistedDownloadPhotoFactory

    @Inject
    lateinit var checkFileExistViewModelFactory: CheckFileExistViewModel.AssistedFileExistFactory

    /*
    @Inject
    lateinit var cropImageViewModel: CropImageViewModel

     */

    @Inject
    lateinit var sharedPhotoIdViewModel: SharedPhotoIdViewModel

    @Inject
    lateinit var sharedUserViewModel: SharedUserViewModel

    @Inject
    lateinit var sharedUserNameViewModel: SharedUserNameViewModel

    companion object {
        private const val RAW = "raw"
        private const val FULL = "full"
        private const val REGULAR = "regular"
        private const val SMALL = "small"
        private const val THUMB = "thumb"
    }

    interface PermissionCallback {
        fun onPermissionGranted()
    }

    inner class Permission(val permissionName: String, val isGranted: Boolean)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(FRAGMENT_REQUEST_FROM_BACKSTACK) { _, bundle ->
            homeActivity.binding.bottomNav.show()
            isFromBackStack = bundle.getBoolean(FRAGMENT_RESULT_FROM_BACKSTACK)
        }

        observePhotoID()
        binding.swipeContainer.setOnSwipeOutListener(
            homeActivity,
            object : SwipeCoordinatorLayout.OnSwipeOutListener {
                override fun onSwipeLeft(x: Float, y: Float, distance: Float) {
                }

                override fun onSwipeRight(x: Float, y: Float, distance: Float) {
                }

                override fun onSwipeDown(x: Float, y: Float, distance: Float) {
                    onBackPressed()
                }

                override fun onSwipeUp(x: Float, y: Float, distance: Float) {
                }

                override fun onSwipeDone() {
                }
            }
        )
    }

    override fun onBackPressed() {
        setFragmentResult(
            FRAGMENT_REQUEST_FROM_BACKSTACK,
            bundleOf(FRAGMENT_RESULT_FROM_BACKSTACK to true)
        )
        findNavController(this).popBackStack()
    }

    private fun setupPermission(callback: PermissionCallback) {
        listener = callback
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        )
    }

    private fun observePhotoID() {
        sharedPhotoIdViewModel.shared {
            getPhoto(it)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getPhoto(id: String) {
        val getPhotoInfoViewModel: GetPhotoInfoViewModel by viewModels {
            GetPhotoInfoViewModel.provideFactory(
                getPhotoInfoViewModelFactory,
                Params(Query().apply {
                    firstParam = id
                    secondParam = -1
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getPhotoInfoViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                val photo = it as Photo

                                setup(photo)
                                displayPhotoDetails(photo)
                            }
                        }

                        Status.ERROR -> {
                            showErrorPopup(resource.getMessage()!!) {}
                        }

                        Status.LOADING -> {
                        }
                    }
                }
            }
        }
    }

    private fun setup(photo: Photo) = with(binding) {
        ivPhoto.loadPhotoUrlWithThumbnail(
            getPhotoUrl(photo, THUMB),
            photo.urls.thumb,
            photo.color,
            centerCrop = true
        )

        ivPhoto.setSecureOnClickListener {
            it.findNavController().navigate(
                PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoViewerFragment(photo.urls.raw)
            )
        }
    }

    private fun displayPhotoDetails(photo: Photo) = with(binding) {
        photo.user?.let { user ->
            tvUser.text = user.name ?: getString(R.string.unknown)
            ivUser.loadProfilePicture(user)
        }

        photo.location?.let { location ->
            val locationString = when {
                location.city != null && location.country != null ->
                    getString(R.string.location_template, location.city, location.country)
                location.city != null && location.country == null -> location.city
                location.city == null && location.country != null -> location.country
                else -> null
            }

            tvLocation.setTextAndVisibility(locationString)
        }

        rvExif.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ExifAdapter(context).apply { setExif(photo) }
        }

        tvViewsCount.text = (photo.views ?: 0).toPrettyString()
        tvDownloadsCount.text = (photo.downloads ?: 0).toPrettyString()
        tvLikesCount.text = (photo.likes ?: 0).toPrettyString()
        rvTag.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false).apply {
                addItemDecoration(
                    SpacingItemDecoration(
                        context,
                        R.dimen.dp_12,
                        RecyclerView.HORIZONTAL
                    )
                )
            }

            adapter = TagAdapter {}.apply { submitList(photo.tags) }
        }

        setLikeButtonState(photo.liked_by_user ?: false)

        userContainer.setSecureOnClickListener {
            photo.user.isNull({
                NormalDialog.Builder(context)
                    .setTitle(R.string.no_user)
                    .setMessage(R.string.no_user_info_phrase)
                    .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
                    }.setOnDismissListener {
                    }.show(homeActivity.supportFragmentManager)
            }, { user ->
                sharedUserNameViewModel.share(user.username)
                sharedUserViewModel.share(user)
            })
            it.findNavController().navigate(
                PhotoDetailFragmentDirections.actionPhotoDetailFragmentToUserFragment()
            )
        }

        ivDownload.setSecureOnClickListener {
            setupPermission(object : PermissionCallback {
                override fun onPermissionGranted() {
                    checkExistFile(photo.urls.raw)
                }
            })
        }
    }

    private fun checkExistFile(url: String) {
        val checkFileExistViewModel: CheckFileExistViewModel by viewModels {
            CheckFileExistViewModel.provideFactory(
                checkFileExistViewModelFactory,
                Params(Query().apply {
                    firstParam = url
                    secondParam = -1
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                checkFileExistViewModel.value.collectLatest {
                    it?.let {
                        if (it) {
                            NormalDialog.Builder(context)
                                .setTitle(R.string.title_photo_download)
                                .setMessage(getString(R.string.photo_existed))
                                .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
                                }.setOnDismissListener {
                                }.show(homeActivity.supportFragmentManager)
                        } else {
                            downloadPhoto(url)
                        }
                    }
                }
            }
        }
    }

    private fun createDialogForStoragePermission() {
        NormalDialog.Builder(homeActivity.applicationContext)
            .setMessage(R.string.request_permission)
            .setPositiveButton(R.string.ok) { _, _ ->
                startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:${activity?.packageName}"))
                )
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
            }
            .show(childFragmentManager)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun downloadPhoto(url: String) {
        makeLoading(true)
        val downloadPhotoViewModel: DownloadPhotoViewModel by viewModels {
            DownloadPhotoViewModel.provideFactory(
                downloadPhotoViewModelFactory,
                Params(Query().apply {
                    firstParam = url
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                downloadPhotoViewModel.value.collectLatest {
                    when (it?.state) {
                        WorkInfo.State.ENQUEUED -> {
                        }

                        WorkInfo.State.RUNNING -> {
                        }

                        WorkInfo.State.SUCCEEDED -> {
                            val phrase = if (downloadCount == 0)
                                getString(R.string.download_success)
                            else
                                getString(R.string.download_already_saved)

                            NormalDialog.Builder(context)
                                .setTitle(R.string.title_photo_download)
                                .setMessage(phrase)
                                .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
                                }.setOnDismissListener {
                                }.show(homeActivity.supportFragmentManager)
                            makeLoading(false)
                            downloadCount++
                        }

                        WorkInfo.State.BLOCKED -> {
                            makeLoading(false)
                            Toast.makeText(
                                homeActivity,
                                getString(R.string.download_fail_phrase),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        WorkInfo.State.FAILED -> {
                            val phrase = when (it.outputData.getInt(
                                "error",
                                DownloadManager.ERROR_UNKNOWN
                            )) {
                                DownloadManager.ERROR_FILE_ERROR -> {
                                    getString(R.string.download_fail_file)
                                }

                                DownloadManager.ERROR_INSUFFICIENT_SPACE -> {
                                    getString(R.string.download_fail_insufficient_space)
                                }

                                DownloadManager.ERROR_HTTP_DATA_ERROR -> {
                                    getString(R.string.download_fail_http_data)
                                }

                                DownloadManager.ERROR_UNHANDLED_HTTP_CODE -> {
                                    getString(R.string.download_fail_unhandled_http_code)
                                }

                                else -> {
                                    getString(R.string.download_fail)
                                }
                            }

                            NormalDialog.Builder(context)
                                .setTitle(phrase)
                                .setMessage(getString(R.string.download_fail))
                                .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
                                }.setOnDismissListener {
                                }.show(homeActivity.supportFragmentManager)
                            makeLoading(false)

                        }

                        WorkInfo.State.CANCELLED -> {
                            makeLoading(false)
                            Toast.makeText(
                                homeActivity,
                                getString(R.string.download_fail_phrase),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setLikeButtonState(likedByUser: Boolean) {
        binding.ivLike.setImageResource(
            if (likedByUser) R.drawable.ic_favorite_filled_24dp
            else R.drawable.ic_favorite_border_24dp
        )
    }

    private fun getPhotoUrl(photo: Photo, quality: String?): String {
        return when (quality) {
            RAW -> photo.urls.raw
            FULL -> photo.urls.full
            REGULAR -> photo.urls.regular
            SMALL -> photo.urls.small
            THUMB -> photo.urls.thumb
            else -> photo.urls.regular
        }
    }
}
