package io.forus.me.android.presentation.api_data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fund(
    val id: Int,
    val logo: String,
    val name: String,
    val organization: Organization,
    val state: String,
    val type: String
):Parcelable