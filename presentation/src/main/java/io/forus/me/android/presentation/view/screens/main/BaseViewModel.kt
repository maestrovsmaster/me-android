package io.forus.me.android.presentation.view.screens.main

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import io.forus.me.android.domain.repository.vouchers.VouchersRepository
import io.forus.me.android.presentation.api_data.RestApi
import io.forus.me.android.presentation.api_data.repo.VouchersRepositoryV2
import io.forus.me.android.presentation.internal.Injection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

const val KEY_TOKEN_EXPIRED = "401"

open class BaseViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver, Observable {
    @Inject
    lateinit var rest: RestApi

    @Inject
    lateinit var context: Context

   // @Inject
    lateinit var repository: VouchersRepositoryV2






    protected val disposable = CompositeDisposable()

    lateinit var scanType: String
    lateinit var navController: NavController

    val progressState: LiveData<Boolean> get() = isProgress
    var isProgress = MutableLiveData<Boolean>()

    val _goToLogout = MutableLiveData<Boolean>()

    val circuitName: LiveData<String> get() = dataCircuitName
    private val dataCircuitName = MutableLiveData<String>()




    val errorState: LiveData<String> get() = isError
    var isError = MutableLiveData<String>()

    val successState: LiveData<Boolean> get() = isSuccess
    var isSuccess = MutableLiveData<Boolean>()



    val errorMessage = MutableLiveData<String>()



    init {
        isProgress.value = false
        isError.value = ""
        isSuccess.value = false
      //  (application as ALRDriver).appComponent.inject(this)

        repository =  Injection.instance.vouchersRepositoryV2
    }

    fun onError(error: String) {
//        if (error.equals(KEY_TOKEN_EXPIRED)) {
//            navController.graph.startDestination = R.id.fragment_login
//            navController.popBackStack(R.id.mobile_navigation, true)
//            navController.navigate(R.id.fragment_login)
//        } else {
//            isProgress.value = false
//            isError.value = error
//        }
    }

    fun onSuccess() {
        isSuccess.value = true
    }


    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry()}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }



}