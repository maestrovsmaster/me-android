package io.forus.me.android.presentation.view.screens.account.newaccount.pin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.view.activity.CommonActivity
import io.forus.me.android.presentation.view.screens.vouchers.item.VoucherViewModel

class NewPinActivity : CommonActivity() {

    private val newPinViewModel by lazy {
        ViewModelProvider(this).get(NewPinViewModel::class.java).apply {
        }
    }

    companion object {
        private val ACCESS_TOKEN_EXTRA = "ACCESS_TOKEN_EXTRA"

        fun getCallingIntent(context: Context, accessToken: String): Intent {
            val intent = Intent(context, NewPinActivity::class.java)
            intent.putExtra(ACCESS_TOKEN_EXTRA, accessToken)
            return intent
        }
    }

    override val viewID: Int
        get() = R.layout.activity_toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

            val accessToken = intent.getStringExtra(ACCESS_TOKEN_EXTRA)!!
            newPinViewModel.accessToken.value = accessToken
            val fragment = NewPinFragment.newIntent(accessToken)
            addFragment(R.id.fragmentContainer, fragment)
        }
    }
}