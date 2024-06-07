package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityPaymentStatusBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.OrderDetails.OrderDetailsActivity
import com.catalyist.helper.ErrorUtil

class PaymentStatusNewActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentStatusBinding
    private var trans = ""
    var cart_total_price: Double? = 0.0
    var cart_id_list = ArrayList<String>()
    var addressId = ""
    var discountPercentage: Double? = 0.0
    var walletAmount: Double? = 0.0
    var discamount: Double? = 0.0
    var txn_id: String = ""
    var txn_date_time: String = ""
    var paymentType: String = ""
    lateinit var viewModel: ViewModalLogin
    var doubleBackToExitPressedOnce: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        trans = intent.getStringExtra("tran_status").toString()
        txn_id = intent.getStringExtra("txn_id").toString()
        println("----------porederId$txn_id")
        txn_date_time = intent.getStringExtra("txn_date_time").toString()
        paymentType = intent.getStringExtra("paymentType").toString()
        println("----------txn_date_time$txn_date_time")
        cart_total_price = intent.getDoubleExtra("cart_price", 0.0)
        println("----pcar_price$cart_total_price")
        cart_id_list = intent.getSerializableExtra("cart_id_list") as ArrayList<String>
        println("------pcart_lust$cart_id_list")
        discountPercentage = intent.getDoubleExtra("discount", 0.0)
        println("------pdiscountPercentage$discountPercentage")
        walletAmount = intent.getDoubleExtra("walletAmount", 0.0)
        println("------pwalletAmount$walletAmount")
        discamount = intent.getDoubleExtra("discountAmount", 0.0)
        println("------pdiscamount$discamount")
        addressId = intent.getStringExtra("addressID").toString()
        println("------paddressId$addressId")
        initView()
        apiCallingForCheckOut()

    }

    private fun initView() {
        binding.tvOrderId.text = txn_id
        binding.tvAmt.text = cart_total_price.toString()
        binding.tvTxn.text = txn_date_time
        binding.tvTran.text = trans

        binding.tvreturn.setOnClickListener {
            apiCallingForCheckOut()
        }


    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseCheckOutCart.observe(this) {
            if (it?.code == 200) {
                PrefrencesHelper.setOrderId(this)
                PrefrencesHelper.getOrderId(this, "")
                PrefrencesHelper.getOrderId(this, "")
                PrefrencesHelper.getCartPriceStatus(this, "")
                PrefrencesHelper.getDiscountPercentageStatus(this, "")
                PrefrencesHelper.getWalletAmountStatus(this, "")
                PrefrencesHelper.getDiscamountStatus(this,"")
                PrefrencesHelper.saveCartIDList(this, "")
                PrefrencesHelper.getAddressId(this,"")
                var intent =
                    Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }

        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    fun apiCallingForCheckOut() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onCheckOut(this, userMap)

    }

    fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kaddressId] = addressId
        hashMap[MyConstants.kpaymentId] = txn_id
        hashMap[MyConstants.kpaymentType] = paymentType
        hashMap[MyConstants.kamount] = cart_total_price.toString()
        hashMap[MyConstants.kitemData] = cart_id_list
        hashMap[MyConstants.kdiscount] = discountPercentage.toString()
        hashMap[MyConstants.kwalletAmount] = walletAmount.toString()
        hashMap[MyConstants.kdiscountAmount] = discamount.toString()
        return hashMap
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            startActivity(
                Intent(this@PaymentStatusNewActivity, MainActivity::class.java)
                    .putExtra("back", "back_to_home")
            )
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(
                this@PaymentStatusNewActivity,
                "Press back again to exit.",
                Toast.LENGTH_LONG
            ).show()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 1500)
        }
    }
}