package io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment


import android.content.DialogInterface

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.api_data.models.Product
import io.forus.me.android.presentation.databinding.FragmentActionPaymentBinding
import io.forus.me.android.presentation.view.fragment.BaseFragment
import io.forus.me.android.presentation.view.screens.vouchers.dialogs.FullscreenDialog
import io.forus.me.android.presentation.view.screens.vouchers.dialogs.ThrowableErrorDialog
import kotlinx.android.synthetic.main.fragment_action_payment.*
import java.util.*


class ActionPaymentFragment : BaseFragment() {

    private var _binding: FragmentActionPaymentBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: ActionPaymentFragmentArgs by navArgs()

    lateinit var product: Product

    override val toolbarTitle: String
        get() = getString(R.string.restore_login)

    lateinit var voucherAddress: String


    lateinit var fundName: String

    var isProductVoucher: Boolean = false


    private val actionPaymentViewModel: ActionPaymentViewModel by lazy {
        ViewModelProvider(this).get(ActionPaymentViewModel::class.java).apply {
            // lifecycle.addObserver(this)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //providerViewModel.navController = findNavController()
        _binding = FragmentActionPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        voucherAddress = safeArgs.voucherAddress
        product = safeArgs.product
        fundName = safeArgs.sponsorName
        isProductVoucher = safeArgs.isProductVoucher


        actionPaymentViewModel.setProduct(product)
        actionPaymentViewModel.voucherAddress = voucherAddress
        actionPaymentViewModel.sponsorName = fundName
        actionPaymentViewModel.isProductVoucher = isProductVoucher


        val view: View = binding.getRoot()
        binding.model = actionPaymentViewModel

        actionPaymentViewModel.confirmPayment.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                /*if (!it!!) return@Observer

                if (product!!.priceUser > BigDecimal.ZERO) {
                    showConfirmDialog()
                } else {
                    btn_make.active = false
                    btn_make.isEnabled = false
                    progress.visibility = View.VISIBLE
                    mainViewModel.makeTransaction()
                }*/
            })

        actionPaymentViewModel.successPayment.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (!it!!) return@Observer
                FullscreenDialog.display(
                    fragmentManager,
                    getString(R.string.vouchers_apply_success),
                    getString(R.string.vouchers_transaction_duration_of_payout),
                    getString(R.string.me_ok)
                ) {
                    requireActivity().finish()
                }
            })

        actionPaymentViewModel.errorPayment.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                progress.visibility = View.GONE
                btn_make.active = true
                btn_make.isEnabled = true
                if (it != null) {
                    ThrowableErrorDialog(
                        it,
                        requireActivity(),
                        object : DialogInterface.OnDismissListener, () -> Unit {
                            override fun invoke() {
                            }

                            override fun onDismiss(p0: DialogInterface?) {
                            }
                        }).show()
                }
            })








        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val url = product!!.photoURL
        if (url != null && url.isNotEmpty()) {
            Glide.with(context).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_action_icon)
        }*/

        setUI()

    }

    fun setUI() {

        binding.titleBar.setOnBack(
            View.OnClickListener { findNavController().popBackStack() }
        )

        binding.btnMake.setOnClickListener {
            binding.btnMake.isEnabled = false
            if(isProductVoucher) {
                actionPaymentViewModel.makeProductTransaction() //???
            }else {
                actionPaymentViewModel.makeProductTransaction()
            }
        }

        binding.headPrice.text = product.price_user_locale
        binding.tvTotalPrice.text = product.price_locale
        binding.tvSponsorPriceHeader.text = resources.getString(
            R.string.price_agreement_sponsor_pays_you,
            fundName
        )
        binding.tvContributionBy.text = product.sponsor_subsidy_locale
        binding.tvTotalAmount.text = product.price_user_locale

        binding.tvOrganizationName.text = product.organization.name

        product.organization.logo?.let {
            binding.ivOrganizationIcon.setImageUrl(it)
        }
        product.photo?.let {
            binding.ivActionIcon.setImageUrl(it)
        }


    }

    /* fun showConfirmDialog() {

         var title = getString(R.string.submit_price_title)
         var toPay = Converter.convertBigDecimalToStringNL(product!!.priceUser)
         var subtitle = getString(R.string.submit_price_subtitle)



         ApplyActionTransactionDialog(requireActivity(), title, toPay, subtitle) {
             btn_make.active = false
             btn_make.isEnabled = false
             progress.visibility = View.VISIBLE
             mainViewModel.makeTransaction()
         }.show()
     }*/

    override fun initUI() {
        //Not used
    }


}

