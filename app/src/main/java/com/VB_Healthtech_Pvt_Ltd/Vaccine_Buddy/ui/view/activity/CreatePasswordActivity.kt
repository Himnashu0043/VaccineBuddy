package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityCreatePasswordBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.PrecomputedText
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.VaccineDoseDetailsLayBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MyProfile.MainProfileActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity
import com.catalyist.helper.ErrorUtil
import com.google.gson.Gson
import java.util.HashMap

class CreatePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePasswordBinding
    private var newPassword: String = ""
    private var conPassword: String = ""
    private var getphone: String = ""
    private var getemail: String = ""
    private lateinit var viewModel: ViewModalLogin
    private var key: String = ""
    var packageId: String = ""
    private var noofDoes: Int = 0
    var package_total_price: Double = 0.0
    private var ageGrorp: String = ""
  //  var package_data_transfer: String = ""

    var package_data_transfer: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage? =
        null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        key = PrefrencesHelper.setKey(this)
        println("----key$key")
       /* if (!key.equals("package")) {
            package_data_transfer = Gson().fromJson(
                intent.getStringExtra("Pkg"),
                com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage::class.java
            )
        }
        println("-----ppppphonccccce$package_data_transfer")*/
        binding.btn.tv.text = "Confirm"
        getemail = intent.getStringExtra("phoneEmail").toString()
        getphone = intent.getStringExtra("phoneNumber").toString()
        binding.btn.tv.setOnClickListener {
            if (!isValideField()) {
                return@setOnClickListener
            }

            apiCallingForSetPassword()
            Handler().postDelayed({
                showDialog()
            }, 1000)
        }
        binding.imageView3.setOnClickListener {
            val intent = Intent(this, PhoneVerifyActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.change_popup)
        val btn: TextView = dialog.findViewById(R.id.dialog_done)
        btn.setOnClickListener {
            dialog.dismiss()
            if (key.equals("profile")) {
                val intent = Intent(
                    this, MainProfileActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else if (key.equals("package")) {
                packageId = PrefrencesHelper.getPackageId(this)
               /* package_total_price = PrefrencesHelper.getPrice(this).toDouble()
                noofDoes = PrefrencesHelper.getNoDose(this).toInt()
                ageGrorp = PrefrencesHelper.getAge(this)*/
                /*packageId = PrefrencesHelper.getPackageId(this)
                package_total_price = PrefrencesHelper.getPrice(this).toDouble()
                noofDoes = PrefrencesHelper.getNoDose(this).toInt()
                ageGrorp = PrefrencesHelper.getAge(this)
                package_data_transfer = PrefrencesHelper.getPackageList(this)
                var fag = PrefrencesHelper.getFagStatus(this)
                val intent = Intent(this, FillInformationActivity::class.java)
                intent.putExtra("packageId", packageId)
                intent.putExtra("package_total_price", package_total_price)
                intent.putExtra("Pkg",Gson().toJson(package_data_transfer))
                intent.putExtra("totalcount", noofDoes)
                intent.putExtra("ageGroup", ageGrorp)
                intent.putExtra("fag", fag)
                startActivity(intent)*/
                val intent = Intent(
                    this, PackageDetailsActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("packageId", packageId)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

               /* val intent = Intent(this, FillInformationActivity::class.java)
                intent.putExtra("packageId", packageId)
                intent.putExtra("package_total_price", package_total_price)
                intent.putExtra("Pkg",Gson().toJson(package_data_transfer))
                intent.putExtra("totalcount", noofDoes)
                intent.putExtra("ageGroup", ageGrorp)*/

                startActivity(intent)
            } else if (key.equals("vaccine")) {
                packageId = PrefrencesHelper.getPackageId(this)
                val intent = Intent(
                    this, VaccineDetailsActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("vaccineId", packageId)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else if (key.equals("profile")) {
                val intent = Intent(
                    this, MainProfileActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else if (key.equals("home")) {
                val intent =
                    Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()

            } else {
                val intent =
                    Intent(this, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }

        }
        dialog.show()
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseCommonModel.observe(this) {
            if (it?.code == 200) {
                //showDialog()
                // Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }


    private fun apiCallingForSetPassword() {
        val userMap: HashMap<String, Any> = prepareDataforSetPassword()
        viewModel.onSetPassword(this, userMap)
    }

    private fun prepareDataforSetPassword(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kEmail] = getemail
        hashMap[MyConstants.kphoneNumber] = getphone
        hashMap[MyConstants.kPassword] = binding.edConPassword.text?.trim().toString()
        return hashMap
    }

    private fun isValideField(): Boolean {
        val password: String = binding.edNewPassword.text?.trim().toString()
        val cnPassword: String = binding.edConPassword.text?.trim().toString()
        var isValid = true
        if (TextUtils.isEmpty(binding.edNewPassword.text?.trim().toString())) {
            Toast.makeText(this, "Please Enter New Password!", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (TextUtils.isEmpty(binding.edConPassword.text?.trim().toString())) {
            Toast.makeText(this, "Please Enter Confirm Password!", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (!password.equals(cnPassword)) {
            Toast.makeText(this, "Confirm Password does not Match!", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid

    }
}