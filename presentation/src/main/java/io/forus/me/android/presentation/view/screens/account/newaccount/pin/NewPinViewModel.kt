package io.forus.me.android.presentation.view.screens.account.newaccount.pin

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.forus.me.android.presentation.api_data.models.*
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.forus.me.android.presentation.helpers.addTo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

public class NewPinViewModel(application: Application) : BaseViewModel(application) {


    var accessToken =  MutableLiveData<String?>()



}