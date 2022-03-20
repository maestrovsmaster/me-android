package io.forus.me.android.presentation.view.screens.provider_v2.offer

import androidx.recyclerview.widget.DiffUtil
import io.forus.me.android.presentation.api_data.models.Product


class ProductsDiffUtilCallback(
    private val oldList: List<Product>,
    private val newList: List<Product>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id


    }
}