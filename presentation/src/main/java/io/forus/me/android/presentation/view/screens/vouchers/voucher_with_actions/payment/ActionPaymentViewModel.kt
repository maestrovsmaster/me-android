package io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment
//import io.forus.me.android.data.entity.vouchers.response.Voucher
import android.app.Application

import android.util.Log
import android.view.View
import androidx.databinding.Bindable

import io.forus.me.android.domain.repository.vouchers.VouchersRepository
import io.forus.me.android.presentation.internal.Injection
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import androidx.lifecycle.MutableLiveData
import io.forus.me.android.presentation.api_data.models.Product
import java.math.BigDecimal


class ActionPaymentViewModel(application: Application) : BaseViewModel(application) {

    var vouchersRepository: VouchersRepository = Injection.instance.vouchersRepository



    @Bindable
    var note = MutableLiveData<String>()

    private var product: Product? = null
    public fun setProduct(product: Product) {
        this.product = product
        refreshUI()
    }

    var voucherAddress: String? = null
    var sponsorName: String? = null
    var isProductVoucher: Boolean = false

    val productName = MutableLiveData<String>()
    val productPrice = MutableLiveData<String>()
    val orgName = MutableLiveData<String>()

    val confirmPayment = MutableLiveData<Boolean>()

    val successPayment = MutableLiveData<Boolean>()
    val errorPayment = MutableLiveData<Throwable?>()

    val commitButtonEnable  = MutableLiveData<Boolean>()
    val commitButtonAlpha  = MutableLiveData<Float>()

    val progress = MutableLiveData<Boolean>()

    init {

        productName.value = ""
        productPrice.value = ""
        orgName.value = ""
        note.value = ""
        progress.value = false

        confirmPayment.value = false
        successPayment.value = false
        errorPayment.value = null

        setCommitButtonEnable(true)
    }



    fun onSaveClick(view: View?) {
        confirmPayment.postValue(true)
    }

    fun onPricesClick(view: View?) {

    }




    private fun refreshUI() {

        val resources = getApplication<Application>().resources



        productName.value = product!!.name


    }

    fun makeSummaVoucherTransaction(voucherAddress: String, amount: BigDecimal, note: String?, organizationId: Long) {

        progress.postValue(true)
        commitButtonEnable.postValue(false)
        vouchersRepository.makeTransaction(voucherAddress, amount, note?:"",  organizationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                progress.postValue(false)
                successPayment.postValue(true)
                //commitButtonEnable.postValue(true)
            }
            .onErrorReturn {
                progress.postValue(false)
                errorPayment.postValue(it)
                //commitButtonEnable.postValue(true)
            }
            .subscribe()
    }

    /*fun makeProductVoucherTransaction() {

        progress.postValue(true)
        commitButtonEnable.postValue(false)
        vouchersRepository.makeActionTransaction(voucherAddress!!, note.value ?: "",  product!!.id.toLong(),
            product!!.organization.id.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                progress.postValue(false)
                successPayment.postValue(true)
                //commitButtonEnable.postValue(true)
            }
            .onErrorReturn {
                progress.postValue(false)
                errorPayment.postValue(it)
                //commitButtonEnable.postValue(true)
            }
            .subscribe()
    }*/

    fun makeProductTransaction() {

        progress.postValue(true)
        commitButtonEnable.postValue(false)
        vouchersRepository.makeActionTransaction(voucherAddress!!, note.value ?: "",  product!!.id.toLong(),
            product!!.organization.id.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    progress.postValue(false)
                    successPayment.postValue(true)
                    //commitButtonEnable.postValue(true)
                }
                .onErrorReturn {
                    progress.postValue(false)
                    errorPayment.postValue(it)
                    //commitButtonEnable.postValue(true)
                }
                .subscribe()
    }

    fun setCommitButtonEnable(enable:Boolean){
        commitButtonEnable.value = enable
        commitButtonAlpha.postValue(if(enable){1.0f}else{0.6f})
    }

    /*private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }




    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }*/


}
