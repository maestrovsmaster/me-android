package io.forus.me.android.presentation.view.screens.account.account.pin

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.forus.me.android.presentation.api_data.models.*
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.forus.me.android.presentation.helpers.addTo
import io.forus.me.android.presentation.models.ChangePinMode

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

public class ChangePinViewModel(application: Application) : BaseViewModel(application) {



    var mode =  MutableLiveData<ChangePinMode>(ChangePinMode.SET_NEW)



}