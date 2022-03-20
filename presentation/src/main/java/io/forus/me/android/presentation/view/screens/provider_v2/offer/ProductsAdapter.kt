package io.forus.me.android.presentation.view.screens.provider_v2.offer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.api_data.models.Product
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_voucher_transcation2.view.*
import java.text.NumberFormat
import java.util.*

class ProductsAdapter (private val callback: (Product) -> (Unit)) :
    RecyclerView.Adapter<ProductsAdapter.ItemViewHolder>() {

    private val list = emptyList<Product>().toMutableList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_voucher_transcation_v2, parent, false)
        return ItemViewHolder(view)
    }


    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        with(holder.containerView) {

            subtitle1.text = item.name
            val price = item.price.toDouble()

            overline1.text = if(price > 0.0) {
                overline1.visibility = View.VISIBLE
                "${context.getString(R.string.price)}: ${NumberFormat.getCurrencyInstance(Locale("nl", "NL")).format(price)}"
            } else {
                overline1.visibility = View.INVISIBLE
                "" }

            iv_icon.setImageUrl(item.photo)

            root.setOnClickListener { callback(item) }

        }
    }


    fun setItems(newList: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductsDiffUtilCallback(list, newList))

        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

}


