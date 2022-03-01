package io.forus.me.android.presentation.view.screens.provider_v2.reservation

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import io.forus.me.android.presentation.api_data.models.VoucherProvider
import io.forus.me.android.presentation.databinding.FragmentReservationBinding

import io.forus.me.android.presentation.view.screens.provider_v2.BaseFragment
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderV2Activity
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderViewModel


class ReservationFragment : BaseFragment() {


    private val providerViewModel by lazy {
        ViewModelProvider(activity as ProviderV2Activity).get(ProviderViewModel::class.java).apply {
            //lifecycle.addObserver(this)
        }
    }

    private lateinit var address: String

    private var _binding: FragmentReservationBinding? = null
    private val binding get() = _binding!!


    fun getConnectivityManager() =
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        providerViewModel.navController = findNavController()
        _binding = FragmentReservationBinding.inflate(inflater, container, false)
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

        providerViewModel.voucherProvider.observe(viewLifecycleOwner,{ voucher ->
            voucher?.let {
                updateHeader(voucher)
            }
        })

       // providerViewModel.getVoucher(voucherAddress)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // binding.clMessage.animate().cancel()
        _binding = null
    }

    fun updateUI(){
        //binding.pr
    }


    fun updateHeader(voucher: VoucherProvider){



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