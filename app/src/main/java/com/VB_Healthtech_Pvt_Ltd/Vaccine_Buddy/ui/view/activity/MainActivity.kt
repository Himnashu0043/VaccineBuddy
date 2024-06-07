package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityBottomNavBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MyCart.MyCartActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.BuildConfig
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var bin: ActivityBottomNavBinding
    var doubleBackToExitPressedOnce = false
    val fragHome = HomeFragment()
    val fragOrder = MyOrderFragment()
    val fragNoti = Notifications()
    val versionCode = BuildConfig.VERSION_CODE
    val versionName = BuildConfig.VERSION_NAME

    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(bin.root)
        initCtrl()

        val come = intent.getStringExtra("back")
        if (intent != null && come.equals("back_to_home")) {
            onSelectView("Order")
        } else {
            onSelectView("Home")
        }
    }

    private fun initCtrl() {
        bin.ivHome.setOnClickListener(this)
        bin.ivCart.setOnClickListener(this)
        bin.ivOrder.setOnClickListener(this)
        bin.ivNoti.setOnClickListener(this)

    }

    private fun onSelectView(from: String) {
        if (from == "Home") {
            bin.ivHome.setImageDrawable(resources.getDrawable(R.drawable.home))
            bin.ivOrder.setImageDrawable(resources.getDrawable(R.drawable.order_nonactive))
            bin.ivCart.setImageDrawable(resources.getDrawable(R.drawable.cart_nonactive))
            bin.ivNoti.setImageDrawable(resources.getDrawable(R.drawable.notification_nonactive))
            replaceFrag(fragHome, "fragHome", null)

        } else if (from == "Order") {
            bin.ivHome.setImageDrawable(resources.getDrawable(R.drawable.home_nonactive))
            bin.ivOrder.setImageDrawable(resources.getDrawable(R.drawable.order_active))
            bin.ivCart.setImageDrawable(resources.getDrawable(R.drawable.cart_nonactive))
            bin.ivNoti.setImageDrawable(resources.getDrawable(R.drawable.notification_nonactive))
            replaceFrag(fragOrder, "fragOrder", null)

        } else if (from == "MyCart") {
            startActivity(Intent(this, MyCartActivity::class.java))
        } else {
            bin.ivHome.setImageDrawable(resources.getDrawable(R.drawable.home_nonactive))
            bin.ivOrder.setImageDrawable(resources.getDrawable(R.drawable.order_nonactive))
            bin.ivCart.setImageDrawable(resources.getDrawable(R.drawable.cart_nonactive))
            bin.ivNoti.setImageDrawable(resources.getDrawable(R.drawable.notification_active))
            replaceFrag(fragNoti, "fragNoti", null)

        }

    }

    private fun replaceFrag(frag: Fragment, nameTag: String, bundle: Bundle?) {
        if (bundle != null) frag.setArguments(bundle) else frag.setArguments(null)
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, frag, nameTag)
            .addToBackStack(nameTag).commit()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.finishAffinity()
            return
        }
        if (supportFragmentManager.findFragmentByTag("fragHome") != null) {
            onSelectView("Home")
        }
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = true
        }, 1000)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_home -> {
                onSelectView("Home")
            }
            R.id.iv_order -> {
                onSelectView("Order")
            }
            R.id.iv_cart -> {
                onSelectView("MyCart")

            }
            R.id.iv_noti -> {
                onSelectView("Noti")
            }
        }

    }

    fun UpdateAppnew() {
        val appUpdateManager = AppUpdateManagerFactory.create(this@MainActivity)
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { result ->
            Log.e("TAG", "UpdateAppnew:  ${Gson().toJson(result)}")
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                Log.e("TAG", "UpdateAppnew:1")
//                requestUpdate(result);
                /*  val ctw: ContextThemeWrapper =
                      ContextThemeWrapper(this, R.style.CustomTheme)
                  val alertDialogBuilder = AlertDialog.Builder(ctw)
                  alertDialogBuilder.setView(R.layout.get_up_notification_popup)
                  // alertDialogBuilder.setTitle("Update NTT Netmagic")
                  alertDialogBuilder.setCancelable(false)
                  //  alertDialogBuilder.setIcon(com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R.drawable.circle_app_icon)
                  //  alertDialogBuilder.setMessage("Vaccine Buddy")
                  alertDialogBuilder.setPositiveButton(
                      "Update"
                  ) { dialog, id ->
                      try {
                          startActivity(
                              Intent(
                                  "android.intent.action.VIEW",
                                  Uri.parse("market://details?id=$packageName")
                              )
                          )
                      } catch (e: ActivityNotFoundException) {
                          startActivity(
                              Intent(
                                  "android.intent.action.VIEW",
                                  Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                              )
                          )
                      }
                  }
                  alertDialogBuilder.setNegativeButton(
                      "No Thanks"
                  ) { dialog, id -> }
                  alertDialogBuilder.show()*/
                val dialog = this.let { Dialog(this) }
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.get_up_notification_popup)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()
                val update = dialog.findViewById<TextView>(R.id.getUpUpgrade)
                val cancel = dialog.findViewById<TextView>(R.id.getAppdialog_cancel)
                val des = dialog.findViewById<TextView>(R.id.getUpDes)
                des.setText("New version ${result.availableVersionCode()}.${result.updateAvailability()} is available on PlayStore!!")
                cancel.setOnClickListener {
                    dialog.dismiss()
                }
                update.setOnClickListener {
                    dialog.dismiss()
                    try {
                        startActivity(
                            Intent(
                                "android.intent.action.VIEW",
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                "android.intent.action.VIEW",
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                }
                val window = dialog.window
                if (window != null) {
                    window.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                }
            } else {
                Log.e("TAG", "UpdateAppnew: ")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        UpdateAppnew()
    }
}
