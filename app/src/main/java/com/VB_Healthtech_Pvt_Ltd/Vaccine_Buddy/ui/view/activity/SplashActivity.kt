package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants.*
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.GPSService
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Walkthrough.TourActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil.checkGPS
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.RetrofitClient
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CartIdModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.TrackingPostModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.TrackingRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.traking.Main1Code
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.traking.MainCode
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.newOtp.NewOtpActivity
import com.catalyist.helper.ErrorUtil
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.veen.veenkumar.CommonUtils
import com.veen.veenkumar.network.NetworkConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity(), GPSService.OnLocationUpdate {
    private var androidIdd: String? = ""
    protected var snackbar: Snackbar? = null
    private var isFirstTime: Boolean = false
    private var isTutorial: Boolean = true
    lateinit var viewModel: ViewModalLogin
    val RESULT_PERMISSION_LOCATION = 1
    private val PERMISSION_LOCATION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var discountPercentage: Double? = 0.0
    var walletAmount: Double? = 0.0
    var discamount: Double? = 0.0
    var addressId: String = ""
    var cart_id_list = ""
    private var cart_total_price: Double? = 0.0
    private var getOrderId: String = ""
    private var status: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //timerStartQuestion()
        isFirstTime = PrefrencesHelper.getLoginStatus(this)
        isTutorial = PrefrencesHelper.getTutorialStatus(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val networkConnection = NetworkConnection(this)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                snackbar?.dismiss()
                //onCreate(savedInstanceState)
            } else {
                snackbar = CommonUtils.indefiniteSnack(this, "Your Internet Not Working!!")
            }
        })
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            androidIdd = task.result

            Log.d(TAG, "DeviceToken==>>: ${androidIdd} ")
            getSharedPreferences(kDefaultAppName, MODE_PRIVATE).edit {
                putString(kDeviceToken, androidIdd)
                apply()
            }
            PrefrencesHelper.getSaveDeviceID(applicationContext, androidIdd)
        })
        apiResponse()
        getOrderId = PrefrencesHelper.setOrderId(this)
        println("==========getOrderIdS$getOrderId")
        if (getOrderId.isNotEmpty()) {
            addressId = PrefrencesHelper.setAddressId(this)
            cart_total_price = PrefrencesHelper.setCartPriceStatus(this).toDouble()
            discountPercentage = PrefrencesHelper.setDiscountPercentageStatus(this).toDouble()
            walletAmount = PrefrencesHelper.setWalletAmountStatus(this).toDouble()
            discamount = PrefrencesHelper.setDiscamountStatus(this).toDouble()
            getOrderId = PrefrencesHelper.setOrderId(this)
            cart_id_list = PrefrencesHelper.getCartIDList(this)
            tracking(getOrderId)
            println("==========addressIdS$addressId")
            println("==========cart_total_priceS$cart_total_price")
            println("==========discountPercentageS$discountPercentage")
            println("==========walletAmountS$walletAmount")
            println("==========discamountS$discamount")
            println("==========getOrderIdS$getOrderId")
        } else {
            checkLocationPermissions()
        }


    }

    private fun checkLocationPermissions() {
        if (hasAccessFineLocationPermissions(this@SplashActivity)) {
            if (checkGPS(this@SplashActivity)) {
                Log.d(TAG, "GPS: ENABLED.....")
                GPSService(this, this)
                callScreen()
            } else {
                buildAlertMessageNoGps(getString(R.string.enable_gps))
            }
        } else {
            requestLocationPermissions(this@SplashActivity)
        }
    }

    fun hasAccessFineLocationPermissions(context: Context?): Boolean {
        return (ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun requestLocationPermissions(activity: Activity?) {
        ActivityCompat.requestPermissions(
            activity!!,
            PERMISSION_LOCATION,
            RESULT_PERMISSION_LOCATION
        )
    }


    /* private fun buildAlertMessageNoGps(message: String) {
         val builder = AlertDialog.Builder(this@SplashActivity)
         builder.setMessage(message)
             .setCancelable(false)
             .setPositiveButton(
                 "Yes"
             ) { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
         *//* .setNegativeButton(
             "No"
         ) { dialog, id -> dialog.cancel() }*//*
        val alert = builder.create()
        *//* alert.getWindow()?.setBackgroundDrawable(resources?.let { ColorDrawable(it.getColor(
             R.color.orange)) });*//*
        alert.show()
    }*/
    private fun buildAlertMessageNoGps(message: String) {
        val builder = AlertDialog.Builder(this@SplashActivity)
        builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog, id ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        val alert = builder.create()
        alert.getWindow()?.setBackgroundDrawable(resources?.let {
            ColorDrawable(
                it.getColor(
                    R.color.white
                )
            )
        })
        alert.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        /* for (permission in permissions) {
             if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission!!)) {
                 //denied
                 Log.e("denied", permission)
                 showSettingLocation(this)

             } else {
                 if (ActivityCompat.checkSelfPermission(
                         this,
                         permission) == PackageManager.PERMISSION_GRANTED
                 ) {
                     //allowed
                     Log.e("allowed", permission)
                     getLocation()
                     callScreen()
                 }
                 else {
 //                    callScreen()
                     //set to never ask again
                     Log.e("set to never ask again", permission)
                     //startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                     Toast.makeText(this@SplashActivity, getString(R.string.grant_location_permission_to_use_app), Toast.LENGTH_SHORT).show()
                     // User selected the Never Ask Again Option
                     val intent = Intent()
                     intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                     val uri = Uri.fromParts("package", packageName, null)
                     intent.data = uri
                     startActivityForResult(intent, 0)
                 }
             }
         }*/
        if (requestCode == RESULT_PERMISSION_LOCATION) {
            if (hasAccessFineLocationPermissions(this@SplashActivity)) {
                if (checkGPS(this@SplashActivity)) {
                    GPSService(this, this)
                    callScreen()
                } else {
                    buildAlertMessageNoGps(getString(R.string.enable_gps))
                }
            } else {
                requestLocationPermissions(this@SplashActivity)
            }
        }
    }

/*    fun showSettingLocation(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(getString(R.string.give_youapp_access_to_location))
        builder.setPositiveButton("Ok") { dialog, which -> // Do nothing but close the dialog
            dialog.dismiss()
            checkLocationPermissions()
        }
        val alert = builder.create()
        alert.setCanceledOnTouchOutside(false)
        if (!activity.isFinishing) {
            alert.show()
        }
        alert.setOnKeyListener { arg0, keyCode, event -> // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Log.d(TAG, "onKey: KEYCODE BACK...")
                alert.dismiss()
                checkLocationPermissions()
            }
            true
        }
    }*/

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_PERMISSION_LOCATION -> when (resultCode) {
                RESULT_OK -> {
                    getLocation()
                    callScreen()
                }
                RESULT_CANCELED -> {
                    callScreen()
                }
//                    startActivityForResult(
//                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
//                    0
//                )
            }
            0 -> checkLocationPermissions()
        }
    }*/

    /*   private fun getLocation() {
           GPSService(this, this)
       }*/

    override fun onLocationUpdate(latitude: Double, longitude: Double) {

    }

    fun callScreen() {
        Handler().postDelayed({
            if (isTutorial) {
                val intent = Intent(this, TourActivity::class.java)
                startActivity(intent)
                finish()
            } else if (isFirstTime) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                /* val intent = Intent(this, LoginActivity::class.java)
                 startActivity(intent)
                 finish()*/
                val intent = Intent(this, NewOtpActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000)
    }

    override fun onResume() {
        super.onResume()
        if (hasAccessFineLocationPermissions(this@SplashActivity)) {
            if (checkGPS(this@SplashActivity)) {
                GPSService(this, this)
                callScreen()
            }
        }
    }

    private fun tracking(
        order_id: String

    ) {
        println("=======get$order_id")
        val service: RetrofitClient.GitApiInterface = RetrofitClient.Interface()
        val call: Call<Main1Code> = service.tracking(
            TrackingPostModel(
                order_id,
            )
        )

        call.enqueue(object : Callback<Main1Code?> {
            override fun onResponse(
                call: Call<Main1Code?>,
                response: Response<Main1Code?>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.data?.find { it.error_desc?.equals("No Record Found") == true } != null) {
                        checkLocationPermissions()
                    } else if (response.body()!!.data?.find { it.orderStatus?.equals("Successful") == true } != null) {
                        val item: MainCode? =
                            response.body()!!.data?.find { it.orderStatus?.equals("Successful") == true}
                        status = item?.referenceNo ?: ""
                        apiCallingForCheckOut()
                    } else if (response.body()!!.data?.find { it.orderStatus?.equals("Shipped") == true } != null) {
                        val item: MainCode? =
                            response.body()!!.data?.find { it.orderStatus?.equals("Shipped") == true }
                        status = item?.referenceNo ?: ""
                        apiCallingForCheckOut()
                    } else if (response.body()!!.data?.find { it.orderStatus?.equals("Initiated") == true } != null){
                        checkLocationPermissions()
                    } else {
                        checkLocationPermissions()
                        print("=============response${response.body()!!}")
                    }

                    /* if (response.body()!!.data.get(0).error_desc.equals("No Record Found")) {
                         checkLocationPermissions()
                     } else if (response.body()!!.data.get(0).orderStatus.equals("Successful")) {
                         status = response.body()!!.data.get(1).referenceNo ?: ""
                         apiCallingForCheckOut()
                     } else if (response.body()!!.data.get(0).orderStatus.equals("Shipped")) {
                         status = response.body()!!.data.get(1).referenceNo ?: ""
                         apiCallingForCheckOut()
                     } else {
                         checkLocationPermissions()
                         print("=============response${response.body()!!}")
                     }*/


                } else {
                    print("=============response${response.code()}")
                }

            }

            override fun onFailure(call: Call<Main1Code?>, t: Throwable) {


            }
        })
    }

    fun apiCallingForCheckOut() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onCheckOut(this, userMap)

    }

    fun prepareData(): HashMap<String, Any> {
        val turnsType = Gson().fromJson(cart_id_list, CartIdModel::class.java)
        var formatedList = turnsType.ids
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[kaddressId] = PrefrencesHelper.setAddressId(this)
        hashMap[kpaymentId] = status
        hashMap[kpaymentType] = "UPI"
        hashMap[kamount] = cart_total_price.toString()
        hashMap[kitemData] = formatedList
        hashMap[kdiscount] = discountPercentage.toString()
        hashMap[kwalletAmount] = walletAmount.toString()
        hashMap[kdiscountAmount] = discamount.toString()
        return hashMap
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseCheckOutCart.observe(this) {
            if (it?.code == 200) {
                PrefrencesHelper.setOrderId(this)
                PrefrencesHelper.getOrderId(this, "")
                PrefrencesHelper.getCartPriceStatus(this, "")
                PrefrencesHelper.getDiscountPercentageStatus(this, "")
                PrefrencesHelper.getWalletAmountStatus(this, "")
                PrefrencesHelper.getDiscamountStatus(this, "")
                PrefrencesHelper.saveCartIDList(this, "")
                PrefrencesHelper.getAddressId(this, "")
                var intent =
                    Intent(this, MainActivity::class.java).putExtra("back", "back_to_home")
                startActivity(intent)
                finish()
            }

        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent
    }
}