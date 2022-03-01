package io.forus.me.android.domain.models.vouchers

class Organization(var id: Long,
                   var name: String?,
                   var logo: String?,
                   var lat: Double?,
                   var lon: Double?,
                   var address: String?,
                   var phone: String?,
                   var email: String?){
    override fun toString(): String {
        return "Organization(id=$id, name=$name, logo=$logo, lat=$lat, lon=$lon, address=$address, phone=$phone, email=$email)"
    }
}