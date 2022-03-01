package io.forus.me.android.presentation.view.screens.provider_v2.provider_main

import android.content.Context
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.api_data.models.Organization

class OrgsSpinnerAdapter(
    context: Context, textViewResourceId: Int,
    val list: List<Organization>,
    val callback: (Organization)->(Unit)
) : ArrayAdapter<Organization>(
    context, textViewResourceId, list
) {
    override fun getDropDownView(
        position: Int, convertView: View,
        parent: ViewGroup
    ): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    fun getCustomView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View {

        val item = list[position]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row: View = inflater.inflate(R.layout.item_organization_spinner_row, parent, false)

        row.setOnClickListener {
            callback(item)
        }

        val tv_fund = row.findViewById<View>(R.id.tv_fund) as TextView
        tv_fund.text = item.name
       // val iv_icon = row.findViewById<View>(R.id.iv_icon) as io.forus.me.android.presentation.view.component.images.AutoLoadImageView
       // iv_icon.setImageUrl(vs.model.selectedOrganization.logo)
        return row
    }
}