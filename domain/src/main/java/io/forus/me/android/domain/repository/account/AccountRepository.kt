package io.forus.me.android.domain.repository.account

import io.forus.me.android.domain.models.account.*
import io.reactivex.Observable

interface AccountRepository {

    fun newUser(model: NewAccountRequest) : Observable<String>


    fun restoreByEmail(email: String) : Observable<RequestDelegatesEmailModel>


    fun restoreByQrToken(): Observable<RequestDelegatesQrModel>


    fun restoreByPinCode() : Observable<RequestDelegatesPinModel>


    fun createIdentity(identity: Identity): Observable<Boolean>


    fun unlockIdentity(pin: String): Observable<Boolean>


    fun exitIdentity(): Observable<Boolean>

}
