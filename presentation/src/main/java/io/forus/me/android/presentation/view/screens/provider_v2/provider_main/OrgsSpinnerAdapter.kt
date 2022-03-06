package io.forus.me.android.presentation.view.screens.provider_v2.provider_main

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.api_data.models.Organization
import android.widget.BaseAdapter
import io.forus.me.android.presentation.view.component.images.AutoLoadImageView


class OrgsSpinnerAdapter(val context: Context, var dataSource: List<Organization>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View? {
        return getCustomView(position, convertView!!, parent!!)
    }

    fun getCustomView( position: Int,  convertView: View?,
                       parent:ViewGroup?): View{

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_organization_spinner_row, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        val org = dataSource.get(position)

        vh.label.text = org.name


        org.logo?.let {
            vh.img.setImageUrl(org.logo)
        }

        return view

    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        Log.d("mSpinner","size = ${dataSource.size}")
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView
        val img: AutoLoadImageView

        init {
            label = row?.findViewById(R.id.tv_fund) as TextView
            img = row?.findViewById(R.id.iv_icon) as AutoLoadImageView
        }
    }

}