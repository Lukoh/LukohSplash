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

package com.goforer.base.view.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredGridItemOffsetDecoration(
    private val spacing: Int,
    private val spanCount: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val params = view.layoutParams as? StaggeredGridLayoutManager.LayoutParams
        val spanIndex = params?.spanIndex ?: 0
        val position = params?.absoluteAdapterPosition ?: 0

        outRect.left = if (spanIndex == 0) spacing else 0
        outRect.top = if (position < spanCount) spacing else 0
        outRect.right = spacing
        outRect.bottom = spacing
    }
}