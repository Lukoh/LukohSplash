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