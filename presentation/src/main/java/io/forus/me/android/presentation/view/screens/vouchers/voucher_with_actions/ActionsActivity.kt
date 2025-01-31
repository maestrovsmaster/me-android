package io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.forus.me.android.domain.models.vouchers.ProductAction
import io.forus.me.android.domain.models.vouchers.VoucherProvider
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.databinding.ActivityActionsBinding
import io.forus.me.android.presentation.helpers.PaginationScrollListener
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.adapter.ActionsAdapter
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.model.ActionsViewModel
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment.ActionPaymentActivity
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment.ProductSerializable
import kotlinx.android.synthetic.main.activity_actions.*
import kotlinx.android.synthetic.main.toolbar_view.*
import java.lang.Exception
import java.math.BigDecimal


class ActionsActivity : AppCompatActivity() {


    companion object {


        val VOUCHER_ADDRESS_EXTRA = "VOUCHER_ADDRESS_EXTRA"

        fun getCallingIntent(context: Context, voucherAddress: String): Intent {
            val intent = Intent(context, ActionsActivity::class.java)
            intent.putExtra(VOUCHER_ADDRESS_EXTRA, voucherAddress)
            return intent
        }
    }

    var voucherAddress: String? = null

    lateinit var mainViewModel: ActionsViewModel

    lateinit var binding: ActivityActionsBinding


    var transactionsAdapter: ActionsAdapter? = null


    private var currentPage = 1
    private var isLastPage = false

    private var isLoading = false
    var itemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))

        mainViewModel = ViewModelProviders.of(this).get(ActionsViewModel::class.java)
        voucherAddress = intent.getSerializableExtra(VOUCHER_ADDRESS_EXTRA) as String
        mainViewModel.voucherAddress = voucherAddress
        binding = DataBindingUtil.setContentView<ActivityActionsBinding>(this@ActionsActivity, R.layout.activity_actions)
        binding.lifecycleOwner = this
        binding.model = mainViewModel

        toolbar_title.text = getString(R.string.payment)
        profile_button.setImageDrawable(ContextCompat.getDrawable(this@ActionsActivity, R.drawable.ic_back))
        profile_button.setOnClickListener {
            finish()
        }

        descrTV.text = HtmlCompat.fromHtml(getString(R.string.choose_an_action_descr),
                HtmlCompat.FROM_HTML_MODE_LEGACY)



        transactionsAdapter = ActionsAdapter(arrayListOf(), object : ActionsAdapter.Callback {
            override fun onItemClicked(item: ProductAction) {

                startActivity(ActionPaymentActivity.getCallingIntent(this@ActionsActivity,
                        ProductSerializable(item.id!!, item.name, item.organization!!.name,
                                item.organization!!.id,
                                item.price, item.priceUser,
                                item.priceType , item.priceDiscount,
                                item.priceLocale,
                                item.priceUserLocale,
                                item.sponsorSubsidy , if(item.sponsor != null){
                            item.sponsor!!.name
                        } else{
                            null}, item.photoURL
                        ),
                        voucherAddress!!))
            }
        }, this@ActionsActivity)


        mainViewModel.productActionsLiveData.observe(this, Observer {

            if (it != null) {
                canWork = true
                transactionsAdapter!!.addAll(it)

                if (transactionsAdapter!!.items.isEmpty()) {
                    mainViewModel.productsListIsEmpty.postValue(true)
                } else {
                    mainViewModel.productsListIsEmpty.postValue(false)

                }
            }

        })

        mainViewModel.voucher.observe(this, Observer {
            if (it != null) {
                refreshVoucherUI(it)
                mainViewModel.getVoucherActionGoods(currentPage)
                this@ActionsActivity.currentPage += 1
            }
        })


        val linearLayoutManager =
            LinearLayoutManager(
                this@ActionsActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = transactionsAdapter

        recycler.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun isLastPage(): Boolean {
                return this@ActionsActivity.isLastPage
            }

            override fun loadMoreItems() {
                this@ActionsActivity.isLoading = true


                mainViewModel.getVoucherActionGoods(this@ActionsActivity.currentPage)
                this@ActionsActivity.currentPage += 1
            }

            override fun isLoading(): Boolean {
                return this@ActionsActivity.isLoading
            }
        })

        mainViewModel.getVoucherDetails()
    }

    var canWork = true

    fun refreshVoucherUI(voucher: VoucherProvider) {

        val orgNames: MutableList<String> = ArrayList()

        for (org in voucher.allowedOrganizations) {
            orgNames.add(org.name!!)
        }

        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, orgNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



        organizationsSpinner.adapter = adapter
        organizationsSpinner.setSelection(0, false);
        organizationsSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, p2: Int, p3: Long) {

                if(canWork) {
                    canWork = false
                    val tv = view as androidx.appcompat.widget.AppCompatTextView
                    val orgName = tv.text.toString()

                    val id = mainViewModel.selectedOrgIdByName(orgName)
                    transactionsAdapter!!.clearAll()
                    currentPage = 1
                    mainViewModel.getVoucherActionGoods(currentPage)
                     this@ActionsActivity.currentPage += 1;

                }
            }

        }
    }
}