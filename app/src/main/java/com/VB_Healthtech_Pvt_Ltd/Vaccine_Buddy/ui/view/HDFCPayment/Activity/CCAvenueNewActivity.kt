package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.Activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityCcavenueBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCModal.orderDetails
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.AvenuesParams
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.Constants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.RSAUtility
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.ServiceUtility
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PaymentGatway.PaymentGatwayRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.catalyist.helper.ErrorUtil
import com.google.gson.Gson
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


private var progress: ProgressDialog? = null
private var order: orderDetails? = null
private var mContext: Context? = null
var cart_total_price: Double? = 0.0
var cart_id_list = ArrayList<String>()
var addressId = ""
var discountPercentage: Double? = 0.0
var walletAmount: Double? = 0.0
var discamount: Double? = 0.0
var order_id: String = ""
var reUrl: String = ""
var canUrl: String = ""
var enCode: String = ""
var mainIntent: Intent? = null
var encVal = ""
var vResponse = ""
private var txn_id = ""
private var paymentMode = ""
private var amountSend: Double = 0.0

lateinit var viewModel: ViewModalLogin


class CCAvenueNewActivity : AppCompatActivity() {
    lateinit var binding: ActivityCcavenueBinding

    //lateinit var viewModel: ViewModalLogin
    private var webview: WebView? = null
    private var TAG = "CCAvenueNewActivity"
    private var RSA = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCcavenueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainIntent = intent
        RSA = mainIntent!!.getStringExtra(AvenuesParams.RSA_KEY_URL).toString()
        get_RSA_key()
        order_id = intent.getStringExtra(AvenuesParams.ORDER_ID).toString()
        reUrl = intent.getStringExtra(AvenuesParams.REDIRECT_URL).toString()
        canUrl = intent.getStringExtra(AvenuesParams.CANCEL_URL).toString()
        enCode = intent.getStringExtra(AvenuesParams.ENC_VAL).toString()

        cart_total_price = intent.getDoubleExtra(AvenuesParams.AMOUNT, 0.0)
        println("----priceADD$cart_total_price")
        cart_id_list = intent.getSerializableExtra("cart_id_list") as ArrayList<String>

        discountPercentage = intent.getDoubleExtra("discount", 0.0)
        println("$TAG discountPercentage==>> $discountPercentage")
        walletAmount = intent.getDoubleExtra("walletAmount", 0.0)
        println("$TAG walletAmount ==>> $walletAmount")
        discamount = intent.getDoubleExtra("discountAmount", 0.0)
        println("$TAG discamount ==>>$discamount")
        addressId = intent.getStringExtra("addressID").toString()
        println("$TAG addressId==>> $addressId")




        initView()

    }

    private fun initView() {
        mContext = this@CCAvenueNewActivity
        progress = ProgressDialog(this)
        progress!!.setMessage("Loading...")
        progress!!.setCanceledOnTouchOutside(false)
        progress!!.setCancelable(true)
        webview = findViewById<View>(R.id.webview) as WebView
        webview!!.settings.javaScriptEnabled = true
        webview!!.settings.setSupportMultipleWindows(true)
        webview!!.settings.javaScriptCanOpenWindowsAutomatically = true
        webview!!.addJavascriptInterface(MyJavaScriptInterface(), "HTMLOUT")
        webview!!.webChromeClient = WebChromeClient()
        webview!!.webViewClient = MyWebViewClient(this)

        show()
        apiResponse()
        try {
            val postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(
                mainIntent!!.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8"
            ) + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(
                mainIntent!!.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8"
            ) + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(
                mainIntent!!.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8"
            ) + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(
                mainIntent!!.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8"
            ) + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(
                mainIntent!!.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8"
            ) + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(
                encVal,
                "UTF-8"
            )

            webview!!.postUrl(Constants.CCAVENUE_URL, postData.toByteArray())

        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this)[ViewModalLogin::class.java]
        viewModel.responseCheckOutCart.observe(this) {
            if (it?.code == 200) {
                PrefrencesHelper.getOrderId(this, "")
                PrefrencesHelper.getOrderId(this, "")
                PrefrencesHelper.getCartPriceStatus(this, "")
                PrefrencesHelper.getDiscountPercentageStatus(this, "")
                PrefrencesHelper.getWalletAmountStatus(this, "")
                PrefrencesHelper.getDiscamountStatus(this, "")
                PrefrencesHelper.saveCartIDList(this, "")
                PrefrencesHelper.getAddressId(this, "")

                //                finish()
            }

        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }


    private fun get_RSA_key() {
        vResponse = RSA
        if (vResponse.contains("!ERROR!")) {
            show_alert(vResponse!!)
        } else {
            MyJavaScriptInterface.RenderView().execute()
        }
    }

    private fun show_alert(msg: String) {
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


    fun show() {
        if (!(this@CCAvenueNewActivity).isFinishing) if (progress != null && !progress!!.isShowing) progress!!.show()
    }


}

class MyWebViewClient(val activity: Activity) : WebViewClient() {

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        if (progress!!.isShowing()) {
            if (!(activity).isFinishing) if (progress != null && progress!!.isShowing) progress!!.dismiss()
        }
        if (url.equals(
                reUrl,
                ignoreCase = true
            ) || url.equals(canUrl, ignoreCase = true)
        ) {
            view.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
//            activity.finish()
            activity.finishAffinity()
            val intent =
                Intent(
                    mContext,
                    MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }
}

var hh = ""
var hh1 = ""

class MyJavaScriptInterface {
    var pay_type: String = ""

    @JavascriptInterface
    fun processHTML(html: String) {
        hh =
            Html.fromHtml(html).toString().replace("=", "\":\"").replace("[", "{").replace("]", "}")
        val (data) = Gson().fromJson<PaymentGatwayRes>(hh, PaymentGatwayRes::class.java)
        hh1 = data.amount
        txn_id = data.tracking_id
        paymentMode = data.payment_mode
        amountSend = data.amount.toDouble()
        if (html.indexOf("Failure") != -1) {
            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show()
            val intent = Intent(mContext, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext!!.startActivity(intent)
        } else (if (html.indexOf("Success") != -1) {
            apiCallingForCheckOut()
            /*val intent = Intent(mContext, PaymentStatusNewActivity::class.java)
            intent.putExtra("addressID", addressId)
            intent.putExtra("cart_price", data.amount.toDouble())
            intent.putExtra("cart_id_list", cart_id_list)
            intent.putExtra("walletAmount", walletAmount)
            intent.putExtra("discount", discountPercentage)
            intent.putExtra("discountAmount", discamount)
            intent.putExtra("txn_id", txn_id)
            intent.putExtra("txn_date_time", data.trans_date)
            intent.putExtra("tran_status", data.order_status)
            intent.putExtra("paymentType", data.payment_mode)
            mContext!!.startActivity(intent)*/
        } else if (html.indexOf("Aborted") != -1) {
            Toast.makeText(mContext, "Payment Failure", Toast.LENGTH_SHORT).show()
        } else {
            "Status Not Known!"
        })


    }

    fun apiCallingForCheckOut() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onCheckOut(mContext!!, userMap)

    }

    //
    fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kaddressId] = addressId
        hashMap[MyConstants.kpaymentId] = txn_id
        hashMap[MyConstants.kpaymentType] = paymentMode
        hashMap[MyConstants.kamount] = amountSend
        hashMap[MyConstants.kitemData] = cart_id_list
        hashMap[MyConstants.kdiscount] = discountPercentage.toString()
        hashMap[MyConstants.kwalletAmount] = walletAmount.toString()
        hashMap[MyConstants.kdiscountAmount] = discamount.toString()
        return hashMap
    }

    class RenderView : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            if (ServiceUtility.chkNull(vResponse) != "" && ServiceUtility.chkNull(vResponse)
                    .toString().indexOf("ERROR") == -1
            ) {
                val vEncVal = StringBuffer("")
                vEncVal.append(
                    ServiceUtility.addToPostParams(
                        AvenuesParams.AMOUNT, cart_total_price.toString()
                    )
                )

                vEncVal.append(
                    ServiceUtility.addToPostParams(
                        AvenuesParams.CURRENCY, "INR"
                    )
                )
                encVal = RSAUtility.encrypt(
                    vEncVal.substring(0, vEncVal.length - 1),
                    vResponse
                )
                Log.d("qwertyuiop", "qwertyuiop: $encVal")
            }

            return null

        }

    }

    /* fun apiCallingForCheckOut() {
         val userMap: HashMap<String, Any> = prepareData()
         viewModel.onCheckOut(mContext!!, userMap)

     }*/

    /* fun prepareData(): HashMap<String, Any> {
         val hashMap: HashMap<String, Any> = HashMap()
         // val turnsType = Gson().fromJson(cart_id_list, CartIdModel::class.java)
         // var formatedList = turnsType.ids
         *//*val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kaddressId] = PrefrencesHelper.setAddressId(this)
        hashMap[MyConstants.kpaymentId] = status
        hashMap[MyConstants.kpaymentType] = "UPI"
        hashMap[MyConstants.kamount] = cart_total_price.toString()
        hashMap[MyConstants.kitemData] = formatedList
        hashMap[MyConstants.kdiscount] = discountPercentage.toString()
        hashMap[MyConstants.kwalletAmount] = walletAmount.toString()
        hashMap[MyConstants.kdiscountAmount] = discamount.toString()*//*
        hashMap[MyConstants.kaddressId] = addressId
        hashMap[MyConstants.kpaymentId] = txn_id
        hashMap[MyConstants.kpaymentType] = paymentMode
        hashMap[MyConstants.kamount] = amountSend
        hashMap[MyConstants.kitemData] = cart_id_list
        hashMap[MyConstants.kdiscount] = discountPercentage.toString()
        hashMap[MyConstants.kwalletAmount] = walletAmount.toString()
        hashMap[MyConstants.kdiscountAmount] = discamount.toString()
        return hashMap
    }*/
}

