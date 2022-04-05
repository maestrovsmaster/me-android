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
import io.forus.me.android.presentation.helpers.PaginationScrollListener

import io.forus.me.android.presentation.view.screens.provider_v2.BaseFragment
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderV2Activity
import io.forus.me.android.presentation.view.screens.provider_v2.ProviderViewModel
import io.forus.me.android.presentation.view.screens.provider_v2.offer.ProductsAdapter
import io.forus.me.android.presentation.view.screens.provider_v2.reservation.TransactionsAdapter
import kotlinx.android.synthetic.main.activity_actions.*


class ProviderFragmentV2 : BaseFragment() {

    private var currentPage = 0

    private var isLastPage = false
    private var isLoading = false

    var listType = ListType.None

    private val providerViewModel by lazy {
        ViewModelProvider(activity as ProviderV2Activity).get(ProviderViewModel::class.java).apply {
            //lifecycle.addObserver(this)
        }
    }

    //private lateinit var address: String

    private var _binding: FragmentProviderv2Binding? = null
    private val binding get() = _binding!!


    var selectedOrganization: Organization? = null

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

        return root
    }

    lateinit var voucherAddress: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
//"0x35ce84143e1066ae8e05a6455089568cc566e0b9"
        voucherAddress = requireActivity().intent.getStringExtra("VOUCHER_ADDRESS_EXTRA")!!
        Log.d("ProviderFragmentV2", "ProviderFragmentV2 extras = $voucherAddress")
    }

    private var voucherProvider: VoucherProvider? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progress.setVisible(true)
        providerViewModel.voucherProvider.observe(viewLifecycleOwner) { voucher ->
            voucher?.let {
                binding.progress.setVisible(false)
                voucherProvider = it
                updateUI(voucher)

            }
        }

        providerViewModel.voucherSet.observe(viewLifecycleOwner) { set ->
            if (set != null) {
                binding.progress.setVisible(false)
                updateLists(set)
                Log.d("asdasdasd", "Set = $set")
            } else {
                // fgTODO
            }
        }

        /*providerViewModel.productsList.observe(viewLifecycleOwner){
            it?.let { list->
                showProducts(list)
            }
        }

        providerViewModel.transactionsList.observe(viewLifecycleOwner){
            it?.let { list->
                showProductVouchers(list)
            }
        }*/


        providerViewModel.getVoucher(voucherAddress)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        // binding.clMessage.animate().cancel()
        _binding = null
    }


    fun updateUI(voucherProvider: VoucherProvider) {

        binding.btnMake.setVisible(voucherProvider.allowed_organizations.isNotEmpty())

        binding.btnMake.setOnClickListener {
            goToSummaPayment(voucherProvider)
        }

        if(voucherProvider.allowed_product_organizations.isNotEmpty()) {

            binding.titleBar.setOnBack(
                View.OnClickListener { requireActivity().finish() }

            )

            updateHeader(voucherProvider)
            binding.recycler.layoutManager = LinearLayoutManager(context)
           // val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
           // binding.recycler.layoutManager = linearLayoutManager
            /*recycler.addOnScrollListener(object : PaginationScrollListener(LinearLayoutManager(context)) {
                override fun isLastPage(): Boolean {
                    return this.isLastPage
                }

                override fun loadMoreItems() {
                    this@ProviderFragmentV2.isLoading = true


                    loadNextPage()
                    this@ProviderFragmentV2.currentPage += 1
                }

                override fun isLoading(): Boolean {
                    return this@ProviderFragmentV2.isLoading
                }
            })*/

            binding.tabLayout.removeAllTabs()

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

            binding.tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab!!.position == 0) {
                        providerViewModel.transactionsList.value?.let {
                            showProductVouchers(it)
                        }

                    } else {
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
                selectedOrganization = selectedOrg
                binding.progress.setVisible(true)
                providerViewModel.getVoucherSet(voucherAddress, selectedOrg.id.toString(), currentPage.toString())
            } else {
                //error  Продукт использован
            }
        }else{
            goToSummaPayment(voucherProvider)
        }
    }

    fun goToSummaPayment(voucherProvider: VoucherProvider){
        selectedOrganization?.let { organization ->
            findNavController().navigate(ProviderFragmentV2Directions.actionProviderFragmentV2ToSummaPaymentFragment(voucherProvider,
                organization))
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
                selectedOrganization = selectedOrg
                Log.d("Itemwd", "selectedOrg $selectedOrg")
                binding.progress.setVisible(true)
                providerViewModel.getVoucherSet(voucherAddress, selectedOrg.id.toString() ,
                    currentPage.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        binding.tvCurrentOrg.text = (binding.spOrgs.selectedItem as Organization).name

    }

    private fun loadNextPage(){
        when(listType){
            ListType.None -> {}
            ListType.Products -> {
                selectedOrganization?.let { org ->
                    providerViewModel.getAvailableProducts(voucherAddress, org.id.toString(),currentPage.toString())
                }
            }
            ListType.ProductsVoucher -> {
                selectedOrganization?.let { org ->
                    providerViewModel.getProductVouchers(voucherAddress, org.id.toString(),currentPage.toString())
                }
            }
        }
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

        val hasTransactions = set.productVouchers != null && set.productVouchers.isNotEmpty()
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
            showProductVouchers(set.productVouchers!!)
        } else if (hasProducts) {
            showProducts(set.products!!)
        }
    }



    fun showProductVouchers(list: List<ProductVoucher>) {
        val adapter = TransactionsAdapter() {
            goToActionPayment(it.product,true)
        }
        adapter.setItems(list)
        binding.recycler.adapter = adapter
        listType = ListType.ProductsVoucher
        currentPage = 1
    }

    fun showProducts(list: List<Product>) {
        val adapter = ProductsAdapter {
            goToActionPayment(it, false)
        }
        adapter.setItems(list)
        binding.recycler.adapter = adapter

        listType = ListType.Products
        currentPage = 1
    }


    fun createTabView(text: String, icon: Drawable) =
        LayoutInflater.from(requireContext()).inflate(R.layout.item_provider_tab, null).apply {
            findViewById<ImageView>(R.id.img)
                .setImageDrawable(icon)
            findViewById<TextView>(R.id.tvText)
                .setText(text)
        }


    fun goToActionPayment(product: Product,isProductVoucher: Boolean) {

        voucherProvider?.let {
            findNavController().navigate(
                ProviderFragmentV2Directions.actionProviderFragmentV2ToActionPaymentFragment(
                    voucherAddress,
                    product, it.fund.name,
                    isProductVoucher
                )
            )
        }
    }


    enum class ListType{
        None, Products, ProductsVoucher
    }

}