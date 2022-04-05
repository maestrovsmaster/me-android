package io.forus.me.android.presentation.view.screens.provider_v2

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.forus.me.android.presentation.api_data.models.*
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.forus.me.android.presentation.helpers.addTo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

public class ProviderViewModel(application: Application) : BaseViewModel(application) {


    internal val message = MutableLiveData<String>()

    private val perPage = 100

    fun showMessage(message: String) {
        this.message.postValue(message)
    }


    val voucherProvider = MutableLiveData<VoucherProvider?>(null)

    val transactionsList = MutableLiveData<List<ProductVoucher>?>(null)
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


    fun getAvailableProducts(
        address: String, organizationId: String,page: String
    ) {

        repository.getAvailableProducts(address,organizationId,page,perPage.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                productsList.value = it.data
            }, {
                errorMessage.postValue(it.localizedMessage)

            }).addTo(disposable)
    }

    fun getProductVouchers(
        address: String, organizationId: String,page: String
    ) {

        repository.getProductVouchers(address,organizationId,page,perPage.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                transactionsList.value = it.data
            }, {
                errorMessage.postValue(it.localizedMessage)

            }).addTo(disposable)
    }


    fun getVoucherSet(
        address: String, organizationId: String,page: String
    ) {

        repository.getVoucherSet(address,organizationId,page,perPage.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                voucherSet.value = it
                transactionsList.value = it.productVouchers
                productsList.value = it.products

            }, {
                errorMessage.postValue(it.localizedMessage)

            }).addTo(disposable)

    }




}