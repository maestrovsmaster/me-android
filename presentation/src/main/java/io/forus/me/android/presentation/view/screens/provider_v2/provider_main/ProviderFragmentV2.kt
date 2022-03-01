package io.forus.me.android.presentation.view.screens.provider_v2.provider_main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.api_data.models.Organization
import io.forus.me.android.presentation.api_data.models.VoucherProvider
import io.forus.me.android.presentation.databinding.FragmentProviderv2Binding

import io.forus.me.android.presentation.view.screens.provider_v2.BaseFragment
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderV2Activity
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderViewModel
import io.forus.me.android.presentation.view.screens.provider_v2.offer.OffersFragment
import io.forus.me.android.presentation.view.screens.provider_v2.reservation.ReservationFragment
import java.util.ArrayList





class ProviderFragmentV2 : BaseFragment(), NavigationView.OnNavigationItemSelectedListener  {


    private val providerViewModel by lazy {
        ViewModelProvider(activity as ProviderV2Activity).get(ProviderViewModel::class.java).apply {
            //lifecycle.addObserver(this)
        }
    }

    private lateinit var address: String

    private var _binding: FragmentProviderv2Binding? = null
    private val binding get() = _binding!!


    fun getConnectivityManager() =
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        providerViewModel.navController = findNavController()
        _binding = FragmentProviderv2Binding.inflate(inflater, container, false)
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
                updateUI(voucher)
            }
        })

        providerViewModel.getVoucher(voucherAddress)

        setupViewPager()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // binding.clMessage.animate().cancel()
        _binding = null
    }



    fun updateUI(voucherProvider: VoucherProvider){
        updateHeader(voucherProvider)
        updateOrgsSpinner(voucherProvider.allowed_product_organizations)

    }

    fun updateOrgsSpinner(orgs: List<Organization>){
        /*binding.spOrgs.adapter = OrgsSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, orgs){

        }*/



        val adapter: ArrayAdapter<Organization> = ArrayAdapter<Organization>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            orgs
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spOrgs.adapter = adapter

        /*createFromResource(
            requireContext(),
            R.array.history_period_types,
            R.layout.item_spinner_history
        )*/
      //  binding.spOrgs.setSpinnerEventsListener(this)
       /* binding.spOrgs.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.tvCurrentOrg.text = binding.spOrgs.selectedItem.toString()
               // setSpinnerState(false)

                Log.d("Spinner","position = $position")
             //   historyActionsViewModel.movementStatuseListRequest(getPeriod(position))
            }
        }*/

        binding.tvCurrentOrg.text = (binding.spOrgs.selectedItem as Organization).name
        /*binding.spOrgs.setOnClickListener {
            binding.spFilter.performClick()
            spinnerState = !spinnerState
            setSpinnerState(spinnerState)
        }*/
    }


    fun updateHeader(voucherProvider: VoucherProvider){
        binding.tvName.text = voucherProvider.fund.name//tv_name.text = vs.model.item?.voucher?.name
        binding.tvOrganization.text = voucherProvider.fund.organization.name
        //tv_organization.text = vs.model.item?.voucher?.organizationName
        binding.ivIcon.setImageUrl(voucherProvider.fund.logo)//iv_icon.setImageUrl(vs.model.item?.voucher?.logo)


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

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
       // TODO("Not yet implemented")

        return false
    }

    internal var viewPagerAdapter: ViewPagerAdapter? = null

    var reservationFragment: ReservationFragment? = null
    var offersFragment: OffersFragment? = null

    private fun setupViewPager() {
        if (!requireActivity().isDestroyed) {
            if (requireActivity().supportFragmentManager != null) {

                val cfManager: FragmentManager = requireActivity().supportFragmentManager
                viewPagerAdapter = ViewPagerAdapter(cfManager)

                reservationFragment =
                    ReservationFragment()


                offersFragment = OffersFragment()

                viewPagerAdapter!!.addFragment(
                    reservationFragment!!,
                    getString(R.string.reserving)
                )

                viewPagerAdapter!!.addFragment(
                    offersFragment!!,
                    getString(R.string.offer)
                )
                binding.viewPager.adapter = viewPagerAdapter

                binding.tabLayout.addOnTabSelectedListener(object :
                    TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                    //    getTextViewFromTab(tab.position).typeface = getBoldTypeface()
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {
                       // getTextViewFromTab(tab.position).typeface = getNormalTypeface()
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        // TODO("Not yet implemented")
                    }
                })

                binding.tabLayout.setupWithViewPager(binding.viewPager)
            }
        }
    }

    internal class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            Log.d("fragment", "ViewPagerAdapter addFragment $title")
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }
    }





}