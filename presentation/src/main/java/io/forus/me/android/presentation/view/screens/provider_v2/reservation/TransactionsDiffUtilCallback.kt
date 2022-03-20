package io.forus.me.android.presentation.view.screens.provider_v2.reservation

import androidx.recyclerview.widget.DiffUtil
import io.forus.me.android.presentation.api_data.models.ProductVoucher


class TransactionsDiffUtilCallback(
    private val oldList: List<ProductVoucher>,
    private val newList: List<ProductVoucher>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].address == newList[newItemPosition].address

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].address == newList[newItemPosition].address


    }
}