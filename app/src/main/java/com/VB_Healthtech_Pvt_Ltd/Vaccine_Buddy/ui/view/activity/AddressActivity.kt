package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityAddressBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Address.MainAddressAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.Activity.CCAvenueNewActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCModal.TestNewResPay
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCModal.TestPostModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.AvenuesParams
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.RetrofitClient
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.AddressList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CartIdModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import com.catalyist.helper.CustomLoader
import com.catalyist.helper.ErrorUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressActivity : AppCompatActivity(), View.OnClickListener, MainAddressAdapter.Radio {
    private lateinit var binding: ActivityAddressBinding
    private lateinit var viewModel: ViewModalLogin
    private var main_address_list = ArrayList<AskedData>()
    private var adpter: MainAddressAdapter? = null
    private var mPaymentId: String? = null
    private var cart_total_price: Double? = 0.0
    private var cart_id_list = ArrayList<String>()
    private var from = ""
    var discountPercentage: Double? = 0.0
    var walletAmount: Double? = 0.0
    var discamount: Double? = 0.0
    var progress: ProgressDialog? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var status_discount: Boolean = false
    private var order_id = ""

    //    var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        clickEvent()
        status_discount = intent.getBooleanExtra("status_discount", false)
        println("---bbbbb$status_discount")
        from = intent.getStringExtra("from").toString()
        if (from.equals("back_address_fill")) {
            cart_total_price = intent.getDoubleExtra("cart_price", 0.0)
            println("====backPrice$cart_total_price")
            order_id = intent.getStringExtra(AvenuesParams.ORDER_ID).toString()
            cart_id_list = intent.getSerializableExtra("cart_id_list") as ArrayList<String>
            discountPercentage = intent.getDoubleExtra("discount", 0.0)
            println("------discountPercentage$discountPercentage")
            walletAmount = intent.getDoubleExtra("walletAmount", 0.0)
            println("------walletAmount$walletAmount")
            discamount = intent.getDoubleExtra("discountAmount", 0.0)
            println("------discamount$discamount")
            order_id = PrefrencesHelper.setOrderId(this)
            println("====backOrderId$order_id")
            PrefrencesHelper.getOrderId(this, order_id)
            PrefrencesHelper.getCartPriceStatus(this, cart_total_price.toString())
            PrefrencesHelper.getDiscountPercentageStatus(this, discountPercentage.toString())
            PrefrencesHelper.getWalletAmountStatus(this, walletAmount.toString())
            PrefrencesHelper.getDiscamountStatus(this, discamount.toString())
            PrefrencesHelper.saveCartIDList(this, Gson().toJson(cart_id_list))
            PrefrencesHelper.getAddressId(this, main_id_layout)
        } else {
            order_id = intent.getStringExtra(AvenuesParams.ORDER_ID).toString()
            println("======order_id$order_id")
            cart_total_price = intent.getDoubleExtra("cart_price", 0.0)
            println("----car_price$cart_total_price")
            cart_id_list = intent.getSerializableExtra("cart_id_list") as ArrayList<String>
            println("------cart_lust$cart_id_list")
            discountPercentage = intent.getDoubleExtra("discount", 0.0)
            println("------discountPercentage$discountPercentage")
            walletAmount = intent.getDoubleExtra("walletAmount", 0.0)
            println("------walletAmount$walletAmount")
            discamount = intent.getDoubleExtra("discountAmount", 0.0)
            println("------discamount$discamount")
            PrefrencesHelper.getOrderId(this, order_id)

            PrefrencesHelper.getCartPriceStatus(this, cart_total_price.toString())
            PrefrencesHelper.getDiscountPercentageStatus(this, discountPercentage.toString())
            PrefrencesHelper.getWalletAmountStatus(this, walletAmount.toString())
            PrefrencesHelper.getDiscamountStatus(this, discamount.toString())
            val model = CartIdModel(cart_id_list)
            println("---cart_Id_listsave${Gson().toJson(model)}")
            PrefrencesHelper.saveCartIDList(this, Gson().toJson(model))

        }


    }

    private fun clickEvent() {
        //apiCallingforMainAddressList()
        CommonUtil.themeSet(this, window)
        binding.addressToolbar.tvTittle.text = "Address"
        binding.addressToolbar.ivBack.setOnClickListener(this)
        binding.textView34.setOnClickListener(this)
        binding.btn.setOnClickListener(this)
        binding.btnCash.setOnClickListener(this)
    }

    var oneClick: Boolean = true
    override fun onClick(p0: View?) {
        when (p0) {
            binding.addressToolbar.ivBack -> {
                finish()
            }

            binding.textView34 -> {
                startActivity(
                    Intent(this, AddNewAddress::class.java).putExtra(
                        "from",
                        "address_fill"
                    ).putExtra("cart_price", cart_total_price)
                        .putExtra("cart_id_list", cart_id_list)
                        .putExtra("walletAmount", walletAmount)
                        .putExtra("discount", discountPercentage)
                        .putExtra("discountAmount", discamount)
                )
            }

            binding.btnCash -> {
                /*   startActivity(Intent(this, MyCartActivity::class.java))
                   Checkout.preload(getApplicationContext())
                   razorPaymentGateway()*/
//                Toast.makeText(
//                    this,
//                    "This feature is available in upcoming version!",
//                    Toast.LENGTH_SHORT
//                ).show()
                binding.btnCash.visibility = View.GONE
                CustomLoader.showLoader(this)
                onClick()
                //cashCancelPopup()
            }

            binding.btn -> {
                if (oneClick) {
                    oneClick = false
                    cashCancelPopup()
                }

            }
        }
    }

    private fun cashCancelPopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.cash_cancel_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val btDismiss = dialog.findViewById<TextView>(R.id.dialog_CAshcancel)
        val btSubmit = dialog.findViewById<TextView>(R.id.dialogCashConfrom)
        btSubmit.setOnClickListener {
            dialog.dismiss()
            apiCallingForCheckOut()
        }

        btDismiss.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseAddressList.observe(this) {
            if (it?.code == 200) {
                for (gtId in it.askedData) {
                    if (gtId.defaultAddress) {
                        main_id_layout = gtId._id
                    } else {
                        main_id_layout = it.askedData.get(0)._id
                    }
                }

                PrefrencesHelper.getAddressId(this, main_id_layout)
                println("======${PrefrencesHelper.setAddressId(this)}")
                if (it.askedData.get(0).defaultAddress) {
                    it.askedData.get(0).location.coordinates?.let {
                        latitude = it[0]
                        longitude = it[1]
                    } ?: kotlin.run {
                        latitude = 0.0
                        longitude = 0.0
                    }
                    apiCallingForSubCenter()
                }
                /* if (it.askedData.get(0)._id.isNotEmpty()) {
                     it.askedData.get(0).location.coordinates?.let {
                         latitude = it[0]
                         longitude = it[1]
                     } ?: kotlin.run {
                         latitude = 0.0
                         longitude = 0.0
                     }
                     apiCallingForSubCenter()
                 }*/
                println("----mew_main_id$main_id_layout")
                main_address_list.clear()
                main_address_list.addAll(it.askedData)
                binding.rcyAddress.layoutManager =
                    LinearLayoutManager(this)
                adpter = MainAddressAdapter(this, main_address_list, this)
                binding.rcyAddress.adapter = adpter
                adpter!!.notifyDataSetChanged()
                ///Toast.makeText(this, "Address Data Found!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseCheckOutCart.observe(this) {
            if (it?.code == 200) {
                PrefrencesHelper.getOrderId(this, "")
                PrefrencesHelper.getOrderId(this, "")
                PrefrencesHelper.getCartPriceStatus(this, "")
                PrefrencesHelper.getDiscountPercentageStatus(this, "")
                PrefrencesHelper.getWalletAmountStatus(this, "")
                PrefrencesHelper.getDiscamountStatus(this,"")
                PrefrencesHelper.saveCartIDList(this, "")
                PrefrencesHelper.getAddressId(this,"")
                opendialogbox()
            }
        }
        viewModel.responseSubCenter.observe(this) {
            if (it?.code == 200) {
                if (it.result.get(0).is_cod != null) {
                    binding.btn.visibility = View.VISIBLE
                } else if (it.result.get(0).is_cod == true) {
                    binding.btn.visibility = View.VISIBLE
                } else {
                    binding.btn.visibility = View.GONE
                }
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
            Toast.makeText(
                this,
                "Cash on Delivery is not Available at this Location!!",
                Toast.LENGTH_SHORT
            ).show()
            binding.btn.visibility = View.GONE
        }
    }

    private fun apiCallingForSubCenter() {
        val userMap: HashMap<String, Any> = prepareDataforSubCenter()
        viewModel.onSubCenter(this, userMap)
    }

    private fun prepareDataforSubCenter(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.klat] = latitude
        hashMap[MyConstants.klong] = longitude
        return hashMap
    }

    private fun apiCallingforMainAddressList() {
        viewModel.onAddresseList(this)
    }

    fun apiCallingForCheckOut() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onCheckOut(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kaddressId] = main_id_layout
        hashMap[MyConstants.kpaymentType] = "CASH"
        hashMap[MyConstants.kpaymentId] = "Cash_Payment"
        hashMap[MyConstants.kamount] = cart_total_price.toString()
        hashMap[MyConstants.kitemData] = cart_id_list
        hashMap[MyConstants.kdiscount] = discountPercentage.toString()
        hashMap[MyConstants.kwalletAmount] = walletAmount.toString()
        hashMap[MyConstants.kdiscountAmount] = discamount.toString()
        hashMap[MyConstants.kdiscountStatus] = status_discount
        return hashMap
    }

    var main_id_layout: String = ""
    override fun onRadioCheck(id: String, msg: AskedData) {
        main_id_layout = id
        PrefrencesHelper.getAddressId(this, main_id_layout)
        msg.location.coordinates?.let {
            latitude = it[0]
            longitude = it[1]
        } ?: kotlin.run {
            latitude = 0.0
            longitude = 0.0
        }

        println("----main_id$main_id_layout")
        println("----la$latitude")
        println("----long$longitude")
        apiCallingForSubCenter()
    }

    private fun opendialogbox() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.book_now_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val btorder = dialog.findViewById<TextView>(R.id.done_button)
        val bthome = dialog.findViewById<TextView>(R.id.txtview)
        btorder.setText("Go to Order")

        btorder.setOnClickListener {
            dialog.dismiss()
            startActivity(
                Intent(this, MainActivity::class.java).putExtra("back", "back_to_home")
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }

        bthome.setOnClickListener {
            dialog.dismiss()
            startActivity(
                Intent(this, MainActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    /* private fun razorPaymentGateway() {
         val activity: Activity = this
         val co = Checkout()
         try {
             val options = JSONObject()
             options.put("name", "Vaccine Buddy")
             options.put("description", "Demoing Charges")
             options.put("send_sms_hash", true)
             options.put("allow_rotation", true)
             options.put("theme.color", "#3399cc");
             options.put("currency", "INR")
             options.put("amount", cart_total_price.toString() + "00")
             *//* val preFill = JSONObject()
             preFill.put("email", "kavitacs2015@gmail.com")
             preFill.put("contact", "7417665365")
             options.put("prefill", preFill)*//*
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_SHORT)
                .show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, paymentData: PaymentData) {
        try {
            Log.d("TAG", "onPaymentSuccess: .....${paymentData.paymentId}")
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
            mPaymentId = paymentData.paymentId
            apiCallingForCheckOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            // transactionStatus(paymentData.paymentId, mPaymentInternalId)

        } catch (e: java.lang.Exception) {
            Log.e("TAG", "Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentError(code: Int, response: String?, p2: PaymentData?) {
        try {
            Log.d("TAG", "onPaymentError: ........$response")
            //  Toast.makeText(this, "Payment failed: $code $response", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Payment failed!", Toast.LENGTH_SHORT).show()

        } catch (e: java.lang.Exception) {
            Log.e("TAG", "Exception in onPaymentError", e)
        }
    }*/


    private fun onClick() {
        initiatePayment(
            order_id = order_id
        )
    }

    private fun initiatePayment(
        order_id: String

    ) {
        val service: RetrofitClient.GitApiInterface = RetrofitClient.Interface()
        val call: Call<TestNewResPay> = service.initiatePayment(
            TestPostModel(
                order_id,
            )
        )
        show()
        call.enqueue(object : Callback<TestNewResPay?> {
            override fun onResponse(
                call: Call<TestNewResPay?>,
                response: Response<TestNewResPay?>
            ) {
                if (response.isSuccessful) {
                    binding.btnCash.visibility = View.VISIBLE
                    CustomLoader.hideLoader()
                    val intent =
                        Intent(this@AddressActivity, CCAvenueNewActivity::class.java)
                    intent.putExtra(
                        AvenuesParams.ACCESS_CODE, "AVDW70KE49CC63WDCC"
                    )
                    intent.putExtra(
                        AvenuesParams.MERCHANT_ID, "1947890"
                    )
                    intent.putExtra(
                        AvenuesParams.ORDER_ID, order_id
                    )
                    intent.putExtra(
                        AvenuesParams.CURRENCY, "INR"
                    )
                    intent.putExtra(
                        AvenuesParams.AMOUNT, cart_total_price
                    )
                    println("----getPrice$cart_total_price")
                    intent.putExtra(
                        /* AvenuesParams.REDIRECT_URL, "http://52.91.48.92/rest-api-php/items/create"*/
                        AvenuesParams.REDIRECT_URL,
                        "http://34.198.147.157/rest-api-php/items/create"
                    )
                    intent.putExtra(
                        /* AvenuesParams.CANCEL_URL, "http://52.91.48.92/rest-api-php/items/create"*/
                        AvenuesParams.CANCEL_URL, "http://34.198.147.157/rest-api-php/items/create"
                    )
                    intent.putExtra(
                        AvenuesParams.RSA_KEY_URL, response.body()!!.data.rsa
                    )
                    intent.putExtra(
                        AvenuesParams.ENC_VAL, response.body()!!.data.rsa
                    )
                    intent.putExtra("addressID", main_id_layout)
                    intent.putExtra("cart_id_list", cart_id_list)
                    intent.putExtra("walletAmount", walletAmount)
                    intent.putExtra("discount", discountPercentage)
                    startActivity(intent)

                } else {
                    print("${response.code()}")
                }

            }

            override fun onFailure(call: Call<TestNewResPay?>, t: Throwable) {
                hide()

            }
        })
    }

    fun show() {
        if (!(this@AddressActivity).isFinishing) if (progress != null && !progress!!.isShowing) progress!!.show()
    }

    fun hide() {
        if (!(this@AddressActivity as Activity).isFinishing) if (progress != null && progress!!.isShowing) progress!!.dismiss()
    }
    fun show_alert(msg: String) {
        var msg = msg
        val alertDialog = AlertDialog.Builder(
            this
        ).create()
        alertDialog.setTitle("Error!!!")
        if (msg.contains("\n")) msg = msg.replace("\\\n".toRegex(), "")
        alertDialog.setMessage(msg)
        alertDialog.setButton(
            Dialog.BUTTON_POSITIVE, "OK"
        ) { dialog, which -> finish() }
        alertDialog.show()
    }
    override fun onResume() {
        super.onResume()
        apiCallingforMainAddressList()
    }
}