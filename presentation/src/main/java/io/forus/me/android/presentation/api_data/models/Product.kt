package io.forus.me.android.presentation.api_data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val description: String,
    val description_html: String,
    val id: Int,
    val name: String,
    val organization_id: Int,
    val photo: String,
    val organization: Organization,
    val price: String,
    val price_locale: String,
    val price_user: String,
    val price_user_locale: String,
    val product_category_id: Int,
    val reservation_enabled: Boolean,
    val reservation_policy: String,
    val sold_out: Boolean,
    val sponsor_subsidy: String,
    val sponsor_subsidy_locale: String
): Parcelable