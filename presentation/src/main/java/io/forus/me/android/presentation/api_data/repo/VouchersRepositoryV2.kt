package io.forus.me.android.presentation.api_data.repo

import android.content.Context
import io.forus.me.android.data.repository.datasource.RemoteDataSource
import io.forus.me.android.presentation.api_data.RestApi


class VouchersRepositoryV2(val context: Context, f: () -> RestApi) : RemoteDataSource<RestApi>(f) {

   // fun getVoucher(address: String) =
     //   service.getVoucher(address)

    fun getVoucherAsProvider(address: String) =
        service.getVoucherAsProvider(address)


}




