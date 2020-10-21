package io.forus.me.android.presentation.view.screens.vouchers.item.transactions

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.forus.me.android.presentation.models.vouchers.Transaction
import kotlinx.android.synthetic.main.item_voucher_transcations_list.view.*

class TransactionsAdapter : RecyclerView.Adapter<TransactionsVH>() {


    var transactions: List<Transaction> = emptyList()
        set(value) {
            val old = field
            field = value
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize() = old.size
                override fun getNewListSize() = field.size
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = old[oldItemPosition] == field[newItemPosition]
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = old[oldItemPosition] == field[newItemPosition]
            }).dispatchUpdatesTo(this)
            notifyDataSetChanged()
        }

    var isActionsVoucher = false
    
    init {
        setHasStableIds(true)
    }

    var clickListener: ((Transaction) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionsVH(parent).apply {
        itemView.root.setOnClickListener {
            transactions.getOrNull(adapterPosition)?.let {transactions->
                clickListener?.invoke(transactions)
            }
        }
    }

    override fun onBindViewHolder(holder: TransactionsVH, position: Int) {
        holder.isActionsVoucher = isActionsVoucher
        holder.render(transactions[position])
    }
    override fun getItemCount() = transactions.size
    override fun getItemId(position: Int) = position.toLong()
}