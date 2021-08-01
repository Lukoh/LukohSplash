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

package com.goforer.base.utility.keyboard

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import com.goforer.lukohsplash.presentation.ui.HomeActivity
import timber.log.Timber

abstract class BaseKeyboardObserver(private val activity: HomeActivity) {
    private lateinit var onKeyboardListener: OnKeyboardListener

    private var originalWindowHeight: Int = 0

    private val onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener =
        ViewTreeObserver.OnGlobalLayoutListener { onGlobalLayout() }

    private fun setOriginalWindowHeight() {
        val r = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(r)
        originalWindowHeight = r.bottom
    }

    private fun getWindowHeight(): Int {
        val r = Rect()
        activity.findViewById<View>(android.R.id.content).rootView.getWindowVisibleDisplayFrame(r)
        return r.bottom
    }

    protected fun addListener(onKeyboardListener: OnKeyboardListener) {
        setOriginalWindowHeight()

        this.onKeyboardListener = onKeyboardListener
        activity.findViewById<View>(android.R.id.content).rootView.viewTreeObserver.addOnGlobalLayoutListener(
            onGlobalLayoutListener
        )
        onKeyboardListener.onKeyboardChange(KEYBOARD_INIT)
    }

    fun removeListener() {
        activity.findViewById<View>(android.R.id.content).rootView.viewTreeObserver.removeOnGlobalLayoutListener(
            onGlobalLayoutListener
        )
    }

    private var isShown: Boolean? = null
    private fun onGlobalLayout() {
        runCatching {
            val windowHeight = getWindowHeight()
            if (originalWindowHeight < windowHeight)
                originalWindowHeight = windowHeight

            val show = originalWindowHeight > windowHeight

            if (isShown == null)
                onKeyboardListener.onKeyboardChange(KEYBOARD_INIT)
            else if (isShown != show)
                onKeyboardListener.onKeyboardChange(if (show) KEYBOARD_OPEN else KEYBOARD_CLOSE)

            isShown = show
        }.onFailure { e ->
            Timber.e(e.toString())
        }
    }

    interface OnKeyboardListener {
        fun onKeyboardChange(status: Int)
    }

    companion object {
        const val KEYBOARD_INIT = 0
        const val KEYBOARD_OPEN = 1
        const val KEYBOARD_CLOSE = 2
    }
}