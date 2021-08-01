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