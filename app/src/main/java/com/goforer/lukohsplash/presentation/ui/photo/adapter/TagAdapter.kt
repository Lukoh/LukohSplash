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

package com.goforer.lukohsplash.presentation.ui.photo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.databinding.ItemPhotoTagBinding

class TagAdapter( val doOnClick: (tag: String) -> Unit) : ListAdapter<Photo.Tag, TagAdapter.TagViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Photo.Tag>() {
            override fun areItemsTheSame(oldItem: Photo.Tag, newItem: Photo.Tag) = oldItem.title == newItem.title
            override fun areContentsTheSame(oldItem: Photo.Tag, newItem: Photo.Tag) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val contextThemeWrapper =
            ContextThemeWrapper(parent.context.applicationContext, R.style.AppTheme)

        val binding =
            ItemPhotoTagBinding.inflate(LayoutInflater.from(contextThemeWrapper), parent, false)

        return TagViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TagViewHolder(private val binding: ItemPhotoTagBinding, private val adapter: TagAdapter) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Photo.Tag?, ) {
            with(binding) {
                tag?.title?.let { title ->
                    chipTag.text = title
                    itemView.setOnClickListener {
                        adapter.doOnClick(title)
                    }
                }
            }
        }
    }
}