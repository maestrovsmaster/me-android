package io.forus.me.android.presentation.api_data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VoucherProvider(
    val address: String,
    val allowed_organizations: List<Organization>,
    val allowed_product_organizations: List<Organization>,
    val amount: String,
    val amount_locale: String,
    val created_at: String,
    val created_at_locale: String,
    val fund: Fund,
    val fund_id: Int,
    val identity_address: String,
    val type: String
):Parcelable