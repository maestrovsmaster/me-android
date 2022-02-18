package io.forus.me.android.presentation.view.screens.provider_v2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.view.activity.CommonActivity
import io.forus.me.android.presentation.view.screens.vouchers.provider.ProviderActivity
import kotlinx.android.synthetic.main.activity_create_category_flow.*

class ProviderV2Activity :  AppCompatActivity() {


    companion object {

        val VOUCHER_ADDRESS_EXTRA = "VOUCHER_ADDRESS_EXTRA"
        val IS_DEMO_VOUCHER = "IS_DEMO_VOUCHER"

        fun getCallingIntent(context: Context, id: String, isDemoVoucher: Boolean? = false): Intent {
            val intent = Intent(context, ProviderV2Activity::class.java)
            intent.putExtra(VOUCHER_ADDRESS_EXTRA, id)
            if(isDemoVoucher != null)intent.putExtra(IS_DEMO_VOUCHER, isDemoVoucher)
            return intent
        }
    }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_provider2)

        Log.d("ProviderV2Activity","welcome ProviderV2Activity")

        val voucherAddress = intent.getStringExtra(ProviderActivity.VOUCHER_ADDRESS_EXTRA)

        Log.d("ProviderV2Activity","voucherAddress = $voucherAddress")

       /* val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            .build()*/

        //nav_host_fragment.findNavController().navigate(R.id.createRecordFragment, args, navOptions)

        /*if (savedInstanceState == null) {
            val fragment = ProviderFragment.newIntent(intent.getStringExtra(VOUCHER_ADDRESS_EXTRA), intent.getBooleanExtra(IS_DEMO_VOUCHER, false))

            addFragment(R.id.fragmentContainer, fragment)
        }*/
    }


}