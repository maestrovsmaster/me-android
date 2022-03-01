package io.forus.me.android.presentation.view.screens.provider_v2

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.forus.me.android.presentation.api_data.models.VoucherProvider
import io.forus.me.android.presentation.api_data.repo.VouchersRepositoryV2
import io.forus.me.android.presentation.internal.Injection
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.forus.me.android.presentation.helpers.addTo

import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

public class ProviderViewModel(application: Application) : BaseViewModel(application) {


    internal val message = MutableLiveData<String>()


    // lateinit var vouchersRepository: VouchersRepository

    fun showMessage(message: String) {
        this.message.postValue(message)
    }


    val voucherProvider = MutableLiveData<VoucherProvider?>(null)



    fun getVoucher(
        address: String
    ) {

        repository.getVoucherAsProvider(address).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("getVoucherAsProvider", "vouchers  success!    - $it")
                Log.d("getVoucherAsProvider", "vouchers  success data!    - ${it.data}")
                 voucherProvider.value = it.data

            }, {
                // error()
                Log.d("getVoucherAsProvider", "vouchers error1... $it")
                errorMessage.postValue(it.localizedMessage)

            }).addTo(disposable)

    }


    init {
        // repository= Injection.instance.vouchersRepositoryV2
    }


}