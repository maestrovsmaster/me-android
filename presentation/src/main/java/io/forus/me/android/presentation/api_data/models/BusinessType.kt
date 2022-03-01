package io.forus.me.android.presentation.api_data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusinessType(
    val id: Int,
    val key: String,
    val name: String
): Parcelable