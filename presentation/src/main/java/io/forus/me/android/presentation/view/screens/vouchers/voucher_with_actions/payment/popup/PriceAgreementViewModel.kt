package io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment.popup
//import io.forus.me.android.data.entity.vouchers.response.Voucher
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.helpers.Converter
import io.forus.me.android.presentation.helpers.Strings
import io.forus.me.android.presentation.view.screens.main.BaseViewModel
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment.PriceType
import io.forus.me.android.presentation.view.screens.vouchers.voucher_with_actions.payment.ProductSerializable
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class PriceAgreementViewModel(application: Application) : BaseViewModel(application) {


    private var product: ProductSerializable? = null


    val headTitle = MutableLiveData<String>()
    val headPrice = MutableLiveData<String>()
    val headSubtitle = MutableLiveData<String>()

    val totalPrice = MutableLiveData<String>()

    //val discountByProviderName = MutableLiveData<String>()
    val discountByProviderPrice = MutableLiveData<String>()

    val contributionBySponsorName = MutableLiveData<String>()
    val contributionBySponsorPrice = MutableLiveData<String>()

    val userPrice = MutableLiveData<String>()

    val priceAgreementVisiblity = MutableLiveData<Boolean>()
    val headPriceVisiblity = MutableLiveData<Boolean>()
    val totalPriceVisiblity = MutableLiveData<Boolean>()
    val discountProviderVisiblity = MutableLiveData<Boolean>()
    val contributionSponsorVisiblity = MutableLiveData<Boolean>()
    val totalAmountVisiblity = MutableLiveData<Boolean>()

    init {
        priceAgreementVisiblity.value = false
        headPriceVisiblity.value = false
        totalPriceVisiblity.value = false
        discountProviderVisiblity.value = false
        contributionSponsorVisiblity.value = false
        totalAmountVisiblity.value = false

        headTitle.value = ""
        headPrice.value = ""
        headSubtitle.value = ""

        //discountByProviderName.value = ""
        discountByProviderPrice.value = ""

        contributionBySponsorName.value = ""
        contributionBySponsorPrice.value = ""

        userPrice.value = ""

    }

    fun setProduct(product: ProductSerializable) {

        this.product = product

        refreshUI()
    }


    private fun refreshUI() {

        val resources = getApplication<Application>().resources

        val nvtStr = resources.getString(R.string.price_agreement_n_v_t)




        headPriceVisiblity.value = product!!.priceType == PriceType.regular.name ||
                product!!.priceType == PriceType.free.name

        totalPriceVisiblity.value = product!!.priceType == PriceType.regular.name

        discountProviderVisiblity.value =
                product!!.priceType == PriceType.discount_percentage.name ||
                        product!!.priceType == PriceType.discount_fixed.name

        contributionSponsorVisiblity.value =
                //(!(product!!.priceType == PriceType.discount_percentage.name ||
                       // product!!.priceType == PriceType.free.name)) &&
                        ( product!!.sponsorSubsidy != null &&
                                product!!.sponsorSubsidy > BigDecimal.ZERO)

        totalAmountVisiblity.value = product!!.priceType == PriceType.regular.name

        priceAgreementVisiblity.value = totalPriceVisiblity.value!! ||
                discountProviderVisiblity.value!!  || contributionSponsorVisiblity.value!! ||
                totalAmountVisiblity.value!!


        if(product!!.priceType == PriceType.regular.name ){

            if(product!!.price == product!!.sponsorSubsidy){
                headTitle.value = ""
                headPrice.value = resources.getString(R.string.free)

            }else{
                headTitle.value = resources.getString(R.string.price_agreement_client_price)
                headPrice.value = Converter.convertBigDecimalToStringNL(product!!.priceUser)
            }

            if(product!!.price != null){
                totalPrice.value = Converter.convertBigDecimalToStringNL(product!!.price)
            }

        }
        if(product!!.priceType == PriceType.free.name){
            headTitle.value = ""
            headPrice.value = resources.getString(R.string.free)
        }

         userPrice.value = headPrice.value


        if(product!!.priceType == PriceType.discount_percentage.name ){
            discountByProviderPrice.value = Converter.convertBigDecimalToDiscountString(product!!.priceDiscount)
            if(product!!.sponsorSubsidy != null&&
                    product!!.sponsorSubsidy > BigDecimal.ZERO){
                contributionBySponsorPrice.value = Converter.convertBigDecimalToStringNL(product!!.sponsorSubsidy)
            }
        }

        if(product!!.priceType == PriceType.discount_fixed.name
                || product!!.priceType == PriceType.regular.name){
            if(product!!.priceDiscount != null &&
                    product!!.priceDiscount > BigDecimal.ZERO ) {
                discountByProviderPrice.value = Converter.convertBigDecimalToStringNL(product!!.priceDiscount)
            }


            if(product!!.sponsorSubsidy != null &&
                    product!!.sponsorSubsidy > BigDecimal.ZERO){


                contributionBySponsorPrice.value =Converter.convertBigDecimalToStringNL(product!!.sponsorSubsidy)



            }
        }



        contributionBySponsorName.value = resources.getString(R.string.price_agreement_sponsor_pays_you,
                product!!.sponsorName)


    }





}
