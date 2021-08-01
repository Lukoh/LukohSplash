package com.goforer.base.view.holder

import androidx.recyclerview.widget.ItemTouchHelper

/**
 * A ItemHolderBinder describes an item view and metadata about its place within the RecyclerView.
 *
 *
 * All ViewHolder extended [BaseViewHolder] implementations should subclass ItemHolderBinder
 * and implements bindItemHolder method to to display the item at the specified position.
 *
 */
interface ItemHolderBinder<T> {
    /**
     * Called by RecyclerView to display the item at the specified position. This method
     * should update the contents of the item's view to reflect the item at the given position.
     *
     * @param holder A BaseViewHolder describes an item view and metadata about its place within
     * the RecyclerView
     * @param item The ItemHolder which should be updated to represent the contents of the
     * item at the given position in the data set
     * @param position The ItemHolder given position in the data set
     */
    fun bindItemHolder(holder: BaseViewHolder<T>, item: T, position: Int)

    /**
     * Called when the [ItemTouchHelper] first registers an item as being moved or swiped.
     * Implementations should update the item view to indicate it's active state.
     */
    fun onItemSelected()


    /**
     * Called when the [ItemTouchHelper] has completed the move or swipe, and the active item
     * state should be cleared.
     */
    fun onItemClear()
}