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

package com.goforer.lukohsplash.presentation.ui

import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.browser.customtabs.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.goforer.base.extension.gone
import com.goforer.base.extension.show
import com.goforer.base.extension.upShow
import com.goforer.base.utility.ConnectionUtils
import com.goforer.base.utility.keyboard.BaseKeyboardObserver
import com.goforer.base.view.dialog.LoadingDialog
import com.goforer.base.view.dialog.NormalDialog
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.di.Injectable

abstract class BaseFragment<T : ViewBinding> : Fragment(), Injectable {
    private var _binding: T? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    internal val binding
        get() = _binding as T

    internal var isLoading = false

    protected lateinit var homeActivity: HomeActivity
    private lateinit var context: Context

    private var errorDialogMsg = ""

    private var loadingDialog: LoadingDialog? = null

    private var customTabsServiceConnection: CustomTabsServiceConnection? = null

    protected var client: CustomTabsClient? = null
    protected var customTabsSession: CustomTabsSession? = null

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    companion object {
        const val FRAGMENT_TAG = "fragment_tag"

        const val FRAGMENT_REQUEST_FROM_BACKSTACK = "requestKey_from_back"
        const val FRAGMENT_RESULT_FROM_BACKSTACK = "resultKey_get_photos"

        const val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = _binding ?: bindingInflater.invoke(inflater, container, false)
        if (activity is HomeActivity) {
            homeActivity = (activity as HomeActivity?)!!
            (activity as HomeActivity).supportActionBar?.hide()
        }

        return requireNotNull(_binding).root
    }

    override fun onResume() {
        super.onResume()

        if (activity is HomeActivity)
            homeActivity.addKeyboardListener()
    }

    override fun onPause() {
        if (activity is HomeActivity)
            homeActivity.removeKeyboardListener()

        hideKeyboard()

        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        customTabsServiceConnection?.let {
            homeActivity.unbindService(customTabsServiceConnection!!)
            customTabsServiceConnection = null
            client = null
            customTabsSession = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.context = context
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()

        onBackPressedCallback.remove()
    }

    override fun getContext() = context

    protected open fun checkSession() {
    }

    protected open fun onBackPressed() {
    }

    protected fun setKeyboardStateRelatedViews(
        buttonBig: View?, buttonSmall: View?, onOpen: () -> Unit = {}
    ) {
        if (activity is HomeActivity) {
            homeActivity.onKeyboardChange = {
                when (it) {
                    BaseKeyboardObserver.KEYBOARD_INIT -> {
                        buttonSmall?.gone()
                        buttonBig?.show()
                    }
                    BaseKeyboardObserver.KEYBOARD_OPEN -> {
                        buttonBig?.gone()
                        buttonSmall?.upShow()
                        onOpen()
                    }
                    BaseKeyboardObserver.KEYBOARD_CLOSE -> {
                        buttonSmall?.gone()
                        buttonBig?.upShow()
                    }
                }
            }
        }
    }

    protected open fun makeLoading(show: Boolean) {
        isLoading = show
        if (show) {
            if (loadingDialog?.isShowing() == true)
                return

            loadingDialog = LoadingDialog.getInstance()
            loadingDialog?.show(this.childFragmentManager, null)

        } else {
            if (loadingDialog?.isShowing() == true)
                loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

    protected fun createCustomTabsServiceConnection() {
        customTabsServiceConnection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(
                componentName: ComponentName,
                customTabsClient: CustomTabsClient
            ) {
                //Pre-warming
                client = customTabsClient
                client?.warmup(0L)
                customTabsSession = client?.newSession(null)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                client = null
            }
        }

        CustomTabsClient.bindCustomTabsService(
            this.context,
            CUSTOM_TAB_PACKAGE_NAME,
            customTabsServiceConnection!!
        )
    }

    protected fun loadContent(url: String, color: Int = Color.TRANSPARENT) {
        if (Patterns.WEB_URL.matcher(url).matches()) {
            val params = CustomTabColorSchemeParams.Builder()
                .setToolbarColor(color)
                .build()
            val customTabsIntent = CustomTabsIntent.Builder()
                .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, params)
                .setShowTitle(false)
                .setUrlBarHidingEnabled(true)
                .build()


            customTabsIntent.launchUrl(homeActivity, ConnectionUtils.getUri(url))
        }
    }

    internal fun hideKeyboard() {
        if (activity is HomeActivity)
            homeActivity.hideKeyboard()
    }

    internal fun hideKeyboardAndResetFocus(layout: ViewGroup) {
        hideKeyboard()
        layout.requestFocus()
    }

    private fun showDefaultDialog(
        message: CharSequence, title: CharSequence? = null,
        onDismiss: (() -> Unit)? = null
    ) {
        hideKeyboard()

        val builder = NormalDialog.Builder(homeActivity)

        title?.let { builder.setTitle(title) }
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok) { _, _ ->
        }

        builder.setOnDismissListener(
            DialogInterface.OnDismissListener {
                onDismiss?.let { it1 -> it1() }
            }
        )
        builder.show(homeActivity.supportFragmentManager)
    }

    internal fun showErrorPopup(event: String, onDismiss: () -> Unit) {
        if (errorDialogMsg != event) {
            errorDialogMsg = event
            showDefaultDialog(event, null) {
                onDismiss()
                errorDialogMsg = ""
            }
        }
    }

    internal fun showNoPhotoMessage(view: View, containerView: ConstraintLayout, noPhoto: Boolean) {
        if (noPhoto) {
            view.gone()
            containerView.show()
        } else {
            view.show()
            containerView.gone()
        }
    }
}
