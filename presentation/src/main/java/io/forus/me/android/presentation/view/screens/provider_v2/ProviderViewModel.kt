package io.forus.me.android.presentation.view.screens.provider_v2

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.forus.me.android.domain.models.vouchers.Voucher
import io.forus.me.android.presentation.internal.Injection
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.forus.me.android.domain.repository.vouchers.VouchersRepository
import io.forus.me.android.presentation.helpers.addTo

import io.reactivex.android.schedulers.AndroidSchedulers

public class ProviderViewModel(application: Application) : BaseViewModel(application) {

    internal val message = MutableLiveData<String>()


    lateinit var vouchersRepository: VouchersRepository

    fun showMessage(message: String){
        this.message.postValue(message)
    }


    val voucher = MutableLiveData<Voucher?>(null)


    fun getVoucher(address: String){
        Log.d("ProviderFragmentV2","ProviderFragmentV2 vouchersRepository =$vouchersRepository")
        vouchersRepository.getVoucher(address)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("ProviderFragmentV2", "vouchers  success!    - $it")

                voucher.value = it

            }, {
                // error()
                Log.d("ProviderFragmentV2", "vouchers error1... $it")
                errorMessage.postValue(it.localizedMessage)

            }).addTo(disposable)
    }



    init {
        vouchersRepository= Injection.instance.vouchersRepository
    }


}