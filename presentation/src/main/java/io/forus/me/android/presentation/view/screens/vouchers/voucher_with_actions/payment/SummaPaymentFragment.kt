package io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.api_data.models.Organization
import io.forus.me.android.presentation.api_data.models.VoucherProvider
import io.forus.me.android.presentation.databinding.FragmentSummaPaymentBinding
import io.forus.me.android.presentation.extension.setVisible
import io.forus.me.android.presentation.view.fragment.BaseFragment
import io.forus.me.android.presentation.view.screens.vouchers.dialogs.FullscreenDialog
import io.forus.me.android.presentation.view.screens.vouchers.dialogs.ThrowableErrorDialog
import kotlinx.android.synthetic.main.settings_title_value_card_title.view.*
import kotlinx.android.synthetic.main.view_organization.*
import java.math.BigDecimal
import java.util.*


class SummaPaymentFragment : BaseFragment() {

    private var _binding: FragmentSummaPaymentBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: SummaPaymentFragmentArgs by navArgs()


    override val toolbarTitle: String
        get() = getString(R.string.restore_login)

    lateinit var voucherProvider: VoucherProvider
    lateinit var organization: Organization




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
        _binding = FragmentSummaPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        voucherProvider = safeArgs.voucherProvider


        organization = safeArgs.organization

        val view: View = binding.getRoot()


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
                binding.progress.visibility = View.GONE
                binding.btnMake.active = true
                binding.btnMake.isEnabled = true
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


        setUI()

    }

    fun setUI() {

        binding.titleBar.setOnBack(
            View.OnClickListener { findNavController().popBackStack() }
        )

        binding.btnMake.setOnClickListener {
            val amountStr = binding.amount.getText()
            var amount: BigDecimal? = null
            try {
                amount = amountStr.toBigDecimal()
            }catch (e: Exception){}

            var note = binding.note.getText()

            amount?.let {
                actionPaymentViewModel.makeSummaVoucherTransaction(voucherProvider.address,
                    amount, note, organization.id.toLong())
            }

        }

        binding.tvName.text = voucherProvider.fund.name
        voucherProvider.fund.logo?.let{
            binding.ivIcon.setImageUrl(it)
        }

        tv_organization_name.text = organization.name

        organization.logo?.let {
            iv_organization_icon.setImageUrl(it)
        }

        iv_organization_select.setVisible(false)



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

