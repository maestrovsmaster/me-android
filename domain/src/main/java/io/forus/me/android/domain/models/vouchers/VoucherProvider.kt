package io.forus.me.android.domain.models.vouchers

class VoucherProvider {

    var voucher: Voucher

    var allowedOrganizations: List<Organization>

   // var allowedProductOrganizations: List<Organization>

    var allowedProductCategories: List<ProductCategory>

    constructor(voucher: Voucher, allowedOrganizations: List<Organization>, allowedProductCategories: List<ProductCategory>
                ) {
        this.voucher = voucher
        this.allowedOrganizations = allowedOrganizations
        this.allowedProductCategories = allowedProductCategories
       // this.allowedProductOrganizations = allowedProductOrganizations
    }

    override fun toString(): String {
        return "VoucherProvider(voucher=$voucher, allowedOrganizations=$allowedOrganizations, allowedProductCategories=$allowedProductCategories)"
    }


}