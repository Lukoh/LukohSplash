package com.goforer.lukohsplash.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.goforer.base.extension.loadPhotoUrlWithThumbnail
import com.goforer.base.extension.loadProfilePicture
import com.goforer.base.extension.margin
import com.goforer.base.extension.setAspectRatio
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.databinding.ItemPhotoBinding
import com.goforer.lukohsplash.presentation.ui.HomeActivity
import com.goforer.base.view.holder.BaseViewHolder
import com.goforer.lukohsplash.presentation.scheduler.UIJobScheduler

class PhotosAdapter(
    private val context: Context,
    val doOnClick: (view: View, asset: Photo) -> Unit
) : PagingDataAdapter<Photo, BaseViewHolder<Photo>>(DIFF_CALLBACK) {
        companion object {
            private const val RAW = "raw"
            private const val FULL = "full"
            private const val REGULAR = "regular"
            private const val SMALL = "small"
            private const val THUMB = "thumb"

            private val PAYLOAD_TITLE = Any()

            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {
                override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                    oldItem == newItem

                override fun getChangePayload(oldItem: Photo, newItem: Photo): Any? {
                    return if (sameExceptTitle(oldItem, newItem)) {
                        PAYLOAD_TITLE
                    } else {
                        null
                    }

                }
            }

            private fun sameExceptTitle(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.copy(id = newItem.id) == newItem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Photo> {
            val contextThemeWrapper =
                ContextThemeWrapper(parent.context.applicationContext, R.style.AppTheme)

            val binding =
                ItemPhotoBinding.inflate(LayoutInflater.from(contextThemeWrapper), parent, false)

            return PhotoItemHolder(binding, this)
        }

        override fun onBindViewHolder(holder: BaseViewHolder<Photo>, position: Int) {
            val item = getItem(position)

            item?.let {
                if (holder is PhotoItemHolder) {
                    holder.bindItemHolder(holder, it, position)
                }
            }
        }

//    override fun getItemId(position: Int): Long {
//        return getItem(position)?.id.hashCode().toLong()
//    }

//    internal fun searchText(text: String) {
//        searchedText = text
//    }

        class PhotoItemHolder(
            private val binding: ItemPhotoBinding,
            private val adapter: PhotosAdapter
        ) : BaseViewHolder<Photo>(binding.root) {
            @SuppressLint("SetTextI18n")
            override fun bindItemHolder(holder: BaseViewHolder<Photo>, item: Photo, position: Int) {
                val context = adapter.context
                val coroutineScope = (context as HomeActivity).lifecycleScope

                with(binding) {
                    itemView.margin(bottom = itemView.resources.getDimensionPixelSize(R.dimen.dp_12))
                    item.user?.let { user ->
                        userContainer.isVisible = true
                        userContainer.setOnClickListener {

                        }

                        UIJobScheduler.submitJob(coroutineScope) {
                            ivUser.loadProfilePicture(user)
                        }

                        UIJobScheduler.submitJob(coroutineScope) {
                            tvUser.text = user.name ?: itemView.context.getString(R.string.unknown)
                        }
                    }

                    val url = getPhotoUrl(item, FULL)

                    ivPhoto.setAspectRatio(item.width, item.height)

                    UIJobScheduler.submitJob(coroutineScope) {
                        ivPhoto.loadPhotoUrlWithThumbnail(url, item.urls.thumb, item.color)
                    }

                    holder.itemView.setOnClickListener {
                        adapter.doOnClick(holder.view, item)
                    }
                }
            }

            override fun onItemSelected() {
                view.setBackgroundColor(Color.LTGRAY)
            }

            override fun onItemClear() {
                view.setBackgroundColor(0)
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
}