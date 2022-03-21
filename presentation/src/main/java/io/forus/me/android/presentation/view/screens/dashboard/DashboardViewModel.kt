package io.forus.me.android.presentation.view.screens.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.forus.me.android.presentation.api_data.models.*
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.forus.me.android.presentation.helpers.addTo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

public class DashboardViewModel(application: Application) : BaseViewModel(application) {


    val voucher = MutableLiveData<io.forus.me.android.presentation.models.vouchers.Voucher?>()
    var address =  MutableLiveData<String?>()



}