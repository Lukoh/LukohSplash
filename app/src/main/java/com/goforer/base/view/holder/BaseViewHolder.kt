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

package com.goforer.base.view.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * A BaseViewHolder describes an item view and metadata about its place within the RecyclerView.
 *
 *
 * [BaseAdapter] implementations should subclass ViewHolder and add fields for caching
 * potentially expensive [View.findViewById] results.
 *
 *
 * See [RecyclerView.ViewHolder] if you'd like to get more.
 */
abstract class BaseViewHolder<T>(val view: View) : RecyclerView.ViewHolder(view),
    ItemHolderBinder<T> {
    val context: Context = view.context.applicationContext
}