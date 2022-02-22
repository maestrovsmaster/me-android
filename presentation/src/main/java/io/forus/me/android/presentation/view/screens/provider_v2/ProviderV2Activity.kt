package io.forus.me.android.presentation.view.screens.provider_v2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.databinding.ActivityProvider2Binding
import io.forus.me.android.presentation.view.screens.vouchers.provider.ProviderActivity

class ProviderV2Activity :  AppCompatActivity() {


    private val providerViewModel by lazy {
        ViewModelProvider(this).get(ProviderViewModel::class.java).apply {
          //  lifecycle.addObserver(this)
        }
    }

    //lateinit var providerViewModel:ProviderViewModel


    private lateinit var binding: ActivityProvider2Binding

    val navController by lazy { findNavController(R.id.nav_host_fragment_provider) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProvider2Binding.inflate(layoutInflater)
        setContentView(binding.root)
      //  setContentView(R.layout.activity_provider2)

       // providerViewModel =  ViewModelProvider(this).get(ProviderViewModel::class.java).apply {
           // lifecycle.addObserver(this)
       // }

       // navController.setGraph(R.navigation.navigation_provider, intent.extras)

        providerViewModel.message.observe(this,{

        })
        Log.d("ProviderV2Activity","welcome ProviderV2Activity")

        val voucherAddress = intent.getStringExtra(ProviderActivity.VOUCHER_ADDRESS_EXTRA)


          //  .setGraph(R.navigation.product_detail_graph, intent.extras)

        Log.d("ProviderV2Activity","providerViewModel = $providerViewModel")

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



}