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

package com.goforer.base.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlin.math.abs

/**
 * SwipeCoordinatorLayout detect when user is trying to swipe out of bounds
 */
class SwipeCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout(context, attrs) {
    private var startDragX: Float = 0.toFloat()
    private var startDragY: Float = 0.toFloat()

    private var listener: OnSwipeOutListener? = null

    private lateinit var activity: AppCompatActivity

    companion object {
        private const val SWIPE_THRESHOLD_VELOCITY_X = 150F
        private const val SWIPE_THRESHOLD_VELOCITY_Y = 25F
    }

    interface OnSwipeOutListener {
        fun onSwipeLeft(x: Float, y: Float, distance: Float)

        fun onSwipeRight(x: Float, y: Float, distance: Float)

        fun onSwipeDown(x: Float, y: Float, distance: Float)

        fun onSwipeUp(x: Float, y: Float, distance: Float)

        fun onSwipeDone()
    }

    fun setOnSwipeOutListener(
        activity: AppCompatActivity,
        listener: OnSwipeOutListener
    ) {
        this.listener = listener
        this.activity = activity
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.actionMasked
        val x = ev.x
        val y = ev.y

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                startDragX = ev.x
                startDragY = ev.y
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                listener?.onSwipeDone()
            }

            MotionEvent.ACTION_SCROLL -> {
            }

            MotionEvent.ACTION_MOVE -> {
                val distanceY = y - startDragY
                val distanceX = x - startDragX

                if (abs(distanceY) > abs(distanceX)) {
                    if (distanceY < 0 && abs(distanceY) > SWIPE_THRESHOLD_VELOCITY_Y) {
                        listener?.onSwipeUp(x, y, abs(distanceY))

                    } else if (abs(distanceY) > SWIPE_THRESHOLD_VELOCITY_Y) {
                        listener?.onSwipeDown(x, y, abs(distanceY))
                    }
                } else {
                    if (distanceX > 0 && abs(distanceX) > SWIPE_THRESHOLD_VELOCITY_X) {
                        listener?.onSwipeRight(x, y, abs(distanceX))
                    } else if (abs(distanceX) > SWIPE_THRESHOLD_VELOCITY_X) {
                        listener?.onSwipeLeft(x, y, abs(distanceX))
                    }
                }
            }
        }

        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.actionMasked
        val x = ev.x
        val y = ev.y

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                startDragX = ev.x
                startDragY = ev.y
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                listener?.onSwipeDone()
            }

            MotionEvent.ACTION_SCROLL -> {
            }

            MotionEvent.ACTION_MOVE -> {
                val distanceY = y - startDragY
                val distanceX = x - startDragX

                if (abs(distanceY) > abs(distanceX)) {
                    if (distanceY < 0 && abs(distanceY) > SWIPE_THRESHOLD_VELOCITY_Y) {
                        listener?.onSwipeUp(x, y, abs(distanceY))
                    } else if (abs(distanceY) > SWIPE_THRESHOLD_VELOCITY_Y) {
                        listener?.onSwipeDown(x, y, abs(distanceY))
                    }
                } else {
                    if (distanceX > 0 && abs(distanceX) > SWIPE_THRESHOLD_VELOCITY_X) {
                        listener?.onSwipeRight(x, y, abs(distanceX))
                    } else if (abs(distanceX) > SWIPE_THRESHOLD_VELOCITY_X) {
                        listener?.onSwipeLeft(x, y, abs(distanceX))
                    }
                }
            }
        }

        return super.onTouchEvent(ev)
    }
}