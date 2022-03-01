package io.forus.me.android.presentation.api_data


import io.forus.me.android.data.entity.vouchers.response.GetVoucher
import io.forus.me.android.data.entity.vouchers.response.ListAllVouchers
import io.forus.me.android.presentation.api_data.models.VoucherProviderResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    //@GET("api/v1/platform/vouchers/{address}")
   // fun getVoucher(@Path("address") address: String): Observable<VoucherProviderResponse>

    @GET("api/v1/platform/provider/vouchers/{address}")
    fun getVoucherAsProvider(@Path("address") address: String): Observable<VoucherProviderResponse>

   // @GET("api/v1/platform/provider/vouchers/{address}/product-vouchers")
   // fun getProductVouchersAsProvider(@Path("address") address: String): Observable<VoucherProviderResponse>





}
