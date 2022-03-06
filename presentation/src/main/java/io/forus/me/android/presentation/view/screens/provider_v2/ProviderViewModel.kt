package io.forus.me.android.presentation.view.screens.provider_v2

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.forus.me.android.presentation.api_data.models.*
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.forus.me.android.presentation.helpers.addTo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

public class ProviderViewModel(application: Application) : BaseViewModel(application) {


    internal val message = MutableLiveData<String>()



    fun showMessage(message: String) {
        this.message.postValue(message)
    }


    val voucherProvider = MutableLiveData<VoucherProvider?>(null)

    val transactionsList = MutableLiveData<List<Transaction>?>(null)
    val productsList = MutableLiveData<List<Product>?>(null)
    val voucherSet = MutableLiveData<VoucherSet?>(null)


    fun getVoucher(
        address: String
    ) {

        repository.getVoucherAsProvider(address)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                 voucherProvider.value = it.data
            }, {
                errorMessage.postValue(it.localizedMessage)
            }).addTo(disposable)

    }



    fun getVoucherSet(
        address: String, organizationId: String
    ) {

        repository.getVoucherSet(address,organizationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                voucherSet.value = it
                transactionsList.value = it.transactions
                productsList.value = it.products

            }, {
                errorMessage.postValue(it.localizedMessage)

            }).addTo(disposable)

    }




}