package com.goforer.lukohsplash.presentation.ui.user.adapter

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
import com.goforer.base.extension.show
import com.goforer.base.view.holder.BaseViewHolder
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.databinding.ItemCollectionBinding
import com.goforer.lukohsplash.presentation.scheduler.UIJobScheduler
import com.goforer.lukohsplash.presentation.ui.HomeActivity
import com.goforer.lukohsplash.presentation.ui.home.adapter.PhotosAdapter

class UserCollectionAdapter(
    private val context: Context,
    val doOnClick: (view: View, collection: Collection) -> Unit
) : PagingDataAdapter<Collection, BaseViewHolder<Collection>>(DIFF_CALLBACK) {
    companion object {
        private const val RAW = "raw"
        private const val FULL = "full"
        private const val REGULAR = "regular"
        private const val SMALL = "small"
        private const val THUMB = "thumb"

        private val PAYLOAD_TITLE = Any()

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Collection>() {
            override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean =
                oldItem == newItem

            override fun getChangePayload(oldItem: Collection, newItem: Collection): Any? {
                return if (sameExceptTitle(oldItem, newItem)) {
                    PAYLOAD_TITLE
                } else {
                    null
                }

            }
        }

        private fun sameExceptTitle(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem.copy(id = newItem.id) == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Collection> {
        val contextThemeWrapper =
            ContextThemeWrapper(parent.context.applicationContext, R.style.AppTheme)

        val binding =
            ItemCollectionBinding.inflate(LayoutInflater.from(contextThemeWrapper), parent, false)

        return CollectionItemHolder(binding, this)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Collection>, position: Int) {
        val item = getItem(position)

        item?.let {
            if (holder is CollectionItemHolder) {
                holder.bindItemHolder(holder, it, position)
            }
        }
    }

    class CollectionItemHolder(
        private val binding: ItemCollectionBinding,
        private val adapter: UserCollectionAdapter
    ) : BaseViewHolder<Collection>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bindItemHolder(holder: BaseViewHolder<Collection>, item: Collection, position: Int) {
            val context = adapter.context
            val coroutineScope = (context as HomeActivity).lifecycleScope

            with(binding) {
                item.cover_photo?.let { photo ->
                    val url = getPhotoUrl(photo, THUMB)

                    UIJobScheduler.submitJob(coroutineScope) {
                        ivCollection.minimumHeight = itemView.resources.getDimensionPixelSize(R.dimen.dp_250)
                        ivCollection.loadPhotoUrlWithThumbnail(url, photo.urls.full, photo.color, true)
                    }
                }

                UIJobScheduler.submitJob(coroutineScope) {
                    tvCollectionName.text = item.title
                    tvCollectionCount.text = itemView.resources.getQuantityString(
                        R.plurals.photos,
                        item.total_photos,
                        item.total_photos
                    )
                    ivCollectionPrivate.isVisible = item.private ?: false
                }

                item.cover_photo?.let {
                    vLine.show()
                }
            }

            holder.itemView.setOnClickListener {
                adapter.doOnClick(holder.view, item)
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