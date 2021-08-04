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

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(
    context: Context,
    @DimenRes spacingId: Int,
    val orientation: Int = RecyclerView.VERTICAL
) : RecyclerView.ItemDecoration() {

    private val spacing: Int = context.resources.getDimensionPixelSize(spacingId)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (orientation == RecyclerView.VERTICAL) {
            outRect.apply {
                left = spacing
                right = spacing
                bottom = spacing
                if (position == 0) top = spacing
            }
        } else if (orientation == RecyclerView.HORIZONTAL) {
            outRect.apply {
                right = spacing
                top = spacing
                bottom = spacing
                if (position == 0) left = spacing
            }
        }
    }
}
