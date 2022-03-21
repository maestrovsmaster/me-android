package io.forus.me.android.presentation.view.screens.vouchers.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.forus.me.android.presentation.view.base.lr.LRViewState
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.internal.Injection
import io.forus.me.android.presentation.models.vouchers.Voucher
import io.forus.me.android.presentation.view.activity.BaseActivity
import io.forus.me.android.presentation.view.fragment.ToolbarLRFragment
import io.forus.me.android.presentation.view.screens.dashboard.DashboardViewModel
import io.forus.me.android.presentation.view.screens.vouchers.item.VoucherFragment
import io.forus.me.android.presentation.view.screens.vouchers.transactions_log.TransactionsActivity
import kotlinx.android.synthetic.main.fragment_vouchers_recycler.*
import kotlinx.android.synthetic.main.toolbar_view.*

/**
 * Fragment Vouchers Delegates Screen.
 */
class VouchersFragment : ToolbarLRFragment<VouchersModel, VouchersView, VouchersPresenter>(), VouchersView {

    companion object {
        fun newIntent(): VouchersFragment {
            return VouchersFragment()
        }
    }

    private val dashboardViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java).apply {
            //  lifecycle.addObserver(this)
        }
    }

    private lateinit var adapter: VouchersAdapter

    override val toolbarTitle: String
        get() = getString(R.string.dashboard_vouchers)


    override val allowBack: Boolean
        get() = false

    override val showAccount: Boolean
        get() = false

    override val showInfo: Boolean
        get() = true


    override fun viewForSnackbar(): View = root

    override fun loadRefreshPanel() = lr_panel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_vouchers_recycler, container, false).also {
        adapter = VouchersAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clickListener = { voucher: Voucher, sharedViews: List<View>, position: Int ->
            dashboardViewModel.voucher.value = voucher
            dashboardViewModel.address.value = voucher.address
            (activity as? BaseActivity)?.replaceFragment(VoucherFragment.newInstance(voucher, position), sharedViews)
        }
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        info_button.setImageResource(R.drawable.ic_transactions)
        info_button.setOnClickListener {
            startActivity(TransactionsActivity.getCallingIntent(requireContext()))
        }

    }


    override fun createPresenter() = VouchersPresenter(
            Injection.instance.vouchersRepository
    )


    override fun render(vs: LRViewState<VouchersModel>) {
        super.render(vs)

        tv_no_vouchers.visibility = if (!vs.loading && vs.loadingError == null && vs.model.items.isEmpty()) View.VISIBLE else View.INVISIBLE

        adapter.vouchers = vs.model.items
    }
}

