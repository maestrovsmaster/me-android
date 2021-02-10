package io.forus.me.android.presentation.view.screens.records.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.forus.me.android.domain.models.records.Record
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.helpers.inflate
import kotlinx.android.synthetic.main.item_records.view.*


class RecordsVH(parent: ViewGroup, private val clickListener: ((Record) -> Unit)?) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_records)) {
    init {

    }

    fun render(item:  Record) = with(itemView) {
        type.text = item.name
        value.text = item.value

        root.setOnClickListener {
            clickListener?.invoke(item)
        }

    }
}