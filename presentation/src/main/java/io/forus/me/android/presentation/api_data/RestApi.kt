package io.forus.me.android.presentation.api_data



import io.forus.me.android.presentation.api_data.models.ProductsResponse
import io.forus.me.android.presentation.api_data.models.TransactionsResponse
import io.forus.me.android.presentation.api_data.models.VoucherProviderResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("api/v1/platform/provider/vouchers/{address}")
    fun getVoucherAsProvider(@Path("address") address: String): Observable<VoucherProviderResponse>

    @GET("api/v1/platform/provider/vouchers/{address}/product-vouchers")
    fun getReservedProducts(@Path("address") address: String, @Query("organization_id") organization_id: String): Observable<TransactionsResponse>

    @GET("api/v1/platform/provider/vouchers/{address}/products")
    fun getAvailableProducts(@Path("address") address: String, @Query("organization_id") organization_id: String): Observable<ProductsResponse>


}
