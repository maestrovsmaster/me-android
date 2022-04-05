package io.forus.me.android.presentation.view.screens.account.account.pin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.models.ChangePinMode
import io.forus.me.android.presentation.view.activity.CommonActivity
import io.forus.me.android.presentation.view.screens.dashboard.DashboardViewModel

class ChangePinActivity : CommonActivity() {

    companion object {
        private val MODE_EXTRA = "MODE_EXTRA"

        fun getCallingIntent(context: Context, mode: ChangePinMode): Intent {
            val intent = Intent(context, ChangePinActivity::class.java)
            intent.putExtra(MODE_EXTRA, mode.name)
            return intent
        }
    }

    private val changePinViewModel by lazy {
        ViewModelProvider(this).get(ChangePinViewModel::class.java).apply {
            //  lifecycle.addObserver(this)
        }
    }

    override val viewID: Int
        get() = R.layout.activity_toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val mode = ChangePinMode.valueOf(intent.getStringExtra(MODE_EXTRA)!!)
            changePinViewModel.mode.value = mode
            val fragment = ChangePinFragment.newIntent(mode)
            addFragment(R.id.fragmentContainer, fragment)
        }
    }
}