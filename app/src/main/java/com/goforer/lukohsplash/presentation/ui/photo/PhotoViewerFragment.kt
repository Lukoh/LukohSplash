package com.goforer.lukohsplash.presentation.ui.photo

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.goforer.base.extension.gone
import com.goforer.base.extension.loadPhotoUrl
import com.goforer.base.view.widget.SwipeConstraintLayout
import com.goforer.lukohsplash.databinding.FragmentPhotoViewerBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment

class PhotoViewerFragment : BaseFragment<FragmentPhotoViewerBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotoViewerBinding
        get() = FragmentPhotoViewerBinding::inflate

    private val args: PhotoViewerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            homeActivity.window.setDecorFitsSystemWindows(false)
            homeActivity.window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            homeActivity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }

        homeActivity.binding.bottomNav.gone()
        with(binding) {
            loLoading.playAnimation()
            pvView.loadPhotoUrl(
                url = args.photoUrl,
                requestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loLoading.pauseAnimation()
                        loLoading.gone()

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?, target: Target<Drawable>?,
                        dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        loLoading.pauseAnimation()
                        loLoading.gone()

                        return false
                    }
                }
            )
            swipeContainer.setOnSwipeOutListener(
                homeActivity,
                object : SwipeConstraintLayout.OnSwipeOutListener {
                    override fun onSwipeLeft(x: Float, y: Float, distance: Float) {
                        onBackPressed()
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
    }

    override fun onBackPressed() {
        setFragmentResult(
            FRAGMENT_REQUEST_FROM_BACKSTACK,
            bundleOf(FRAGMENT_RESULT_FROM_BACKSTACK to true)
        )
        NavHostFragment.findNavController(this).popBackStack()
    }
}
