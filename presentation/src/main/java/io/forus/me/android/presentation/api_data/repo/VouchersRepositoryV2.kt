package io.forus.me.android.presentation.api_data.repo

import android.content.Context
import io.forus.me.android.data.repository.datasource.RemoteDataSource
import io.forus.me.android.domain.models.account.Account
import io.forus.me.android.presentation.api_data.RestApi
import io.forus.me.android.presentation.api_data.models.VoucherProviderResponse
import io.forus.me.android.presentation.api_data.models.VoucherSet
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import retrofit2.http.Path


class VouchersRepositoryV2(val context: Context, f: () -> RestApi) : RemoteDataSource<RestApi>(f) {

   // fun getVoucher(address: String) =
     //   service.getVoucher(address)

    fun getVoucherAsProvider(address: String) =
        service.getVoucherAsProvider(address)


    fun getReservedTransactions(address: String, organizationId: String) =
        service.getReservedProducts(address,organizationId)

    fun getAvailableProducts(address: String, organizationId: String) =
        service.getAvailableProducts(address,organizationId)


    fun getVoucherSet(address: String, organizationId: String): Observable<VoucherSet> {
        return Observable.zip(
            service.getReservedProducts(address,organizationId),
            service.getAvailableProducts(address,organizationId),
            BiFunction { transactionsResponse, productsResponse ->
                val voucherSet = VoucherSet(transactionsResponse.data, productsResponse.data)
                voucherSet
            }
        )
    }

}




