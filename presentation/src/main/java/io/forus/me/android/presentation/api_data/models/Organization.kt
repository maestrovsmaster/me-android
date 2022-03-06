package io.forus.me.android.presentation.api_data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Organization(
    val business_type: BusinessType?,
    val business_type_id: Int?,
    val email: String?,
    val email_public: Boolean?,
    val id: Int,
    val logo: String?,
    val name: String,
    val phone: String?,
    val phone_public: Boolean?,
    val website: String?,
    val website_public: Boolean?
): Parcelable{

    override fun toString(): String {
        return "$name"
    }
}