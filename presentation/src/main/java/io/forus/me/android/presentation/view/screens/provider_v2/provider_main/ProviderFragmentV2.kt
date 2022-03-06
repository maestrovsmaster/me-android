package io.forus.me.android.presentation.view.screens.provider_v2.provider_main

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.api_data.models.*
import io.forus.me.android.presentation.databinding.FragmentProviderv2Binding
import io.forus.me.android.presentation.extension.setVisible

import io.forus.me.android.presentation.view.screens.provider_v2.BaseFragment
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderV2Activity
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderViewModel
import io.forus.me.android.presentation.view.screens.provider_v2.offer.ProductsAdapter
import io.forus.me.android.presentation.view.screens.provider_v2.reservation.TransactionsAdapter
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment.ActionPaymentActivity
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment.ActionPaymentFragment
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment.ProductSerializable


class ProviderFragmentV2 : BaseFragment() {


    private val providerViewModel by lazy {
        ViewModelProvider(activity as ProviderV2Activity).get(ProviderViewModel::class.java).apply {
            //lifecycle.addObserver(this)
        }
    }

    //private lateinit var address: String

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
//"0x35ce84143e1066ae8e05a6455089568cc566e0b9"
        voucherAddress = requireActivity().intent.getStringExtra("VOUCHER_ADDRESS_EXTRA")!!
        Log.d("ProviderFragmentV2", "ProviderFragmentV2 extras = $voucherAddress")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progress.setVisible(true)
        providerViewModel.voucherProvider.observe(viewLifecycleOwner, { voucher ->
            voucher?.let {
                binding.progress.setVisible(false)
                updateUI(voucher)

            }
        })

        providerViewModel.voucherSet.observe(viewLifecycleOwner) { set ->
            if (set != null) {
                binding.progress.setVisible(false)
                updateLists(set)
                Log.d("asdasdasd", "Set = $set")
            } else {
                // fgTODO
            }
        }

        providerViewModel.getVoucher(voucherAddress)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        // binding.clMessage.animate().cancel()
        _binding = null
    }


    fun updateUI(voucherProvider: VoucherProvider) {
        updateHeader(voucherProvider)
        binding.recycler.layoutManager = LinearLayoutManager(context)


        binding.tabLayout.addTab(binding.tabLayout.newTab().apply {
            customView = createTabView(
                getString(R.string.reserving), ContextCompat.getDrawable(
                    requireContext(), R.drawable.ic_add_shopping_cart_24px
                )!!
            )
        })
        binding.tabLayout.addTab(binding.tabLayout.newTab().apply {
            customView = createTabView(
                getString(R.string.offer), ContextCompat.getDrawable(
                    requireContext(), R.drawable.ic_market
                )!!
            )
        })

        binding.tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!!.position == 0){
                    providerViewModel.transactionsList.value?.let {
                        showTransactions(it)
                    }

                }else{
                    providerViewModel.productsList.value?.let {
                        showProducts(it)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })


        val allowedOrganizations =
            voucherProvider.allowed_product_organizations //allowed_organizations
        if (allowedOrganizations.size > 0) {
            updateOrgsSpinner(allowedOrganizations)

            val selectedOrg = allowedOrganizations[0]
            binding.progress.setVisible(true)
            providerViewModel.getVoucherSet(voucherAddress, selectedOrg.id.toString())
        } else {
            //error  Продукт использован
        }
    }

    fun updateOrgsSpinner(orgs: List<Organization>) {

        val adapter: ArrayAdapter<Organization> = ArrayAdapter<Organization>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            orgs
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spOrgs.adapter = adapter

        binding.spOrgs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedOrg = orgs[position]
                Log.d("Itemwd", "selectedOrg $selectedOrg")
                binding.progress.setVisible(true)
                providerViewModel.getVoucherSet(voucherAddress, selectedOrg.id.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        binding.tvCurrentOrg.text = (binding.spOrgs.selectedItem as Organization).name

    }


    fun updateHeader(voucherProvider: VoucherProvider) {
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


    fun updateLists(set: VoucherSet) {

        val hasTransactions = set.transactions != null && set.transactions.isNotEmpty()
        val hasProducts = set.products != null && set.products.isNotEmpty()


        binding.tabLayout.setVisible(hasTransactions && hasProducts)

        binding.clListHeader.setVisible(hasTransactions && !hasProducts || !hasTransactions && hasProducts)
        binding.tvListTitle.text = if (hasTransactions) {
            getString(R.string.reserving_title)
        } else if (hasProducts) {
            getString(R.string.offer_title)
        } else {
            ""
        }

        binding.tvListSubTitle.text = if (hasTransactions) {
            getString(R.string.reserving_subtitle)
        } else if (hasProducts) {
            getString(R.string.offer_subtitle)
        } else {
            ""
        }

        if (hasTransactions) {
            showTransactions(set.transactions!!)
        } else if (hasProducts) {
            showProducts(set.products!!)
        }
    }

    fun showTransactions(list: List<Transaction>) {
        val adapter = TransactionsAdapter() {

        }
        adapter.setItems(list)
        binding.recycler.adapter = adapter

    }

    fun showProducts(list: List<Product>) {
        val adapter = ProductsAdapter {
            goToProduct(it)
        }
        adapter.setItems(list)
        binding.recycler.adapter = adapter

    }


    fun createTabView(text: String, icon: Drawable) =
        LayoutInflater.from(requireContext()).inflate(R.layout.item_provider_tab, null).apply {
            findViewById<ImageView>(R.id.img)
                .setImageDrawable(icon)
            findViewById<TextView>(R.id.tvText)
                .setText(text)
        }


    fun goToProduct(prod: Product){
        /*startActivity(
            ActionPaymentActivity.getCallingIntent(requireContext(),
            ProductSerializable(prod.id.toLong(), prod.name, prod.organization.name,
                prod.organization.id.toLong(),
                prod.price.toBigDecimal(), prod.price_user.toBigDecimal(),
                //prod.priceType , item.priceDiscount,
                null,null,
                prod.price_locale,
                prod.price_user_locale,
                prod.sponsor_subsidy.toBigDecimal() , null , prod.photo
            ),
            voucherAddress))*/
        val product = ProductSerializable(prod.id.toLong(), prod.name, prod.organization.name,
            prod.organization.id.toLong(),
            prod.price.toBigDecimal(), prod.price_user.toBigDecimal(),
            //"",prod.price_user.toBigDecimal(),
            //prod.priceType , item.priceDiscount,
            null,null,
            prod.price_locale,
            prod.price_user_locale,
            prod.sponsor_subsidy.toBigDecimal() , null , prod.photo
        )

       // (activity as ProviderV2Activity).replaceFragment(R.id.dashboard_content, ActionPaymentFragment.newIntent(product, voucherAddress!!
       // ))

        findNavController().navigate(ProviderFragmentV2Directions.actionProviderFragmentV2ToActionPaymentFragment(voucherAddress, product))
    }


}