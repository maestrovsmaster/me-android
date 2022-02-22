package io.forus.me.android.presentation.view.screens.account.newaccount.pin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.view.activity.CommonActivity

class NewPinActivity : CommonActivity() {


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
            val fragment = NewPinFragment.newIntent(intent.getStringExtra(ACCESS_TOKEN_EXTRA)!!)
            addFragment(R.id.fragmentContainer, fragment)
        }
    }
}