package io.forus.me.android.presentation.view.screens.provider_v2.offer

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import io.forus.me.android.domain.models.vouchers.Voucher
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.databinding.FragmentOffersBinding
import io.forus.me.android.presentation.databinding.FragmentProviderv2Binding
import io.forus.me.android.presentation.databinding.FragmentReservationBinding

import io.forus.me.android.presentation.view.screens.provider_v2.BaseFragment
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderV2Activity
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderViewModel
import kotlinx.android.synthetic.main.fragment_voucher_provider.*
import kotlinx.android.synthetic.main.view_organization.*


class OffersFragment : BaseFragment() {


    private val providerViewModel by lazy {
        ViewModelProvider(activity as ProviderV2Activity).get(ProviderViewModel::class.java).apply {
            //lifecycle.addObserver(this)
        }
    }

    private lateinit var address: String

    private var _binding: FragmentOffersBinding? = null
    private val binding get() = _binding!!


    fun getConnectivityManager() =
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        providerViewModel.navController = findNavController()
        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //applyFont(requireContext(), root, "fonts/NouvelR.ttf")


        val needVerification = true




        return root
    }

    lateinit var voucherAddress: String

    override fun onAttach(context: Context) {
        super.onAttach(context)

        voucherAddress = requireActivity().intent.getStringExtra("VOUCHER_ADDRESS_EXTRA")!!
        Log.d("ProviderFragmentV2","ProviderFragmentV2 extras = $voucherAddress")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        providerViewModel.voucher.observe(viewLifecycleOwner,{ voucher ->
            voucher?.let {
                updateHeader(voucher)
            }
        })

        providerViewModel.getVoucher(voucherAddress)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // binding.clMessage.animate().cancel()
        _binding = null
    }

    fun updateUI(){
        //binding.pr
    }


    fun updateHeader(voucher: Voucher){



       /* if(isDemoVoucher!= null && isDemoVoucher!!) {
            tv_organization_name.text = requireContext().getString(R.string.check_email_open_mail_app)
        }else{
            if (vs.model.selectedOrganization != null) {
                tv_organization_name.text = vs.model.selectedOrganization.name
                if (vs.model.selectedOrganization.logo?.isBlank() != true)
                    iv_organization_icon.setImageUrl(vs.model.selectedOrganization.logo)
            }
        }*/

    }


}