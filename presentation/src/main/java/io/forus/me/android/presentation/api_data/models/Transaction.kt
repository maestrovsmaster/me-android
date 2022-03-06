package io.forus.me.android.presentation.api_data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val address: String,
    val allowed_organizations: List<Organization>,
    val created_at: String,
    val created_at_locale: String,
    val fund: Fund,
    val fund_id: Int,
    val identity_address: String,
    val product: Product,
    val type: String
): Parcelable