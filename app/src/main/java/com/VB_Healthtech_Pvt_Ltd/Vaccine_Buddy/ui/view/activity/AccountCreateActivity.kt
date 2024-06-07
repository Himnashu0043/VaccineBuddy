package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityAccountCreateBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.Enums
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.About.AboutActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.catalyist.helper.ErrorUtil
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.*
import com.google.gson.Gson
import com.hbb20.CountryCodePicker
import java.text.SimpleDateFormat
import java.util.*

class AccountCreateActivity : AppCompatActivity(), CountryCodePicker.OnCountryChangeListener {
    private lateinit var binding: ActivityAccountCreateBinding
    private lateinit var genderadapter: ArrayAdapter<String>
    private lateinit var viewModel: ViewModalLogin
    private lateinit var auth: FirebaseAuth
    private var email: String = ""
    private var mobilenumber: String = ""
    private var ccp: CountryCodePicker? = null
    private var countryCode: String? = null
    var package_data_transfer: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage? =
        null
    var key: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        key = PrefrencesHelper.setKey(this)
        /* if (key.equals("package")) {
             package_data_transfer = Gson().fromJson(
                 intent.getStringExtra("Pkg"),
                 com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage::class.java
             )
         }
         println("-----ppppp$package_data_transfer")*/

        auth = FirebaseAuth.getInstance()
        apiResponse()
        initview()
        lstnr()
//        var currentUser = auth.currentUser
//        if (currentUser != null) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
        val back_name = intent.getStringExtra("back_name")
        println("--back_name$back_name")
        binding.nameview.setText(back_name)
        val back_email = intent.getStringExtra("back_email")
        println("--back_email$back_email")
        binding.emailview.setText(back_email)
        val back_dob = intent.getStringExtra("back_dob")
        println("--back_dob$back_dob")
        binding.Dob.setText(back_dob)
        val back_phone = intent.getStringExtra("back_phone")
        println("--back_phone$back_phone")
        binding.phoneview.setText(back_phone)
//        val back_reff = intent.getStringExtra("back_referral")
//        println("--back_referral$back_reff")
//        binding.referal.setText(back_reff)
        val back_gender = intent.getStringExtra("back_gender")
        println("--back_gender$back_gender")
        when (back_gender) {
            Enums.Male.toString() -> {
                binding.gender.setSelection(1)
            }
            Enums.Female.toString() -> {
                binding.gender.setSelection(2)
            }
            Enums.Other.toString() -> {
                binding.gender.setSelection(3)
            }
        }


    }


    private fun initview() {
//        CommonUtil.themeSet(this, window)
        binding.btn.tv.text = "Sign Up"

        val genderList = resources.getStringArray(R.array.EditProfile)
        binding.gender.adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, genderList)
        // genderadapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        binding.gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {
                binding.tvGender.setText(parent.getItemAtPosition(position).toString())
                println(
                    "------${
                        binding.tvGender.setText(
                            parent.getItemAtPosition(position).toString()
                        )
                    }"
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun lstnr() {
        binding.imageView3.setOnClickListener {
            finish()
        }
        binding.edGender.setOnClickListener {
            binding.gender.performClick()
        }

        binding.tvGender.setOnClickListener {
            binding.gender.performClick()
        }

        binding.tvTerm.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getTerm"))
        }
        binding.tvPrivate.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getPrivacy"))
        }
        binding.btn.tv.setOnClickListener {
            if (validationData()) {
                apiCallingForCheckUser()
            }
        }
        binding.countryCodePicker.setOnClickListener {
            println("----coo$countryCode")
        }
        binding.loginID.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

        binding.Dob.setOnClickListener {
            ///futrue date disable
            MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build()).setSelection(Date().time)
                .build()
                .apply {
                    show(supportFragmentManager, this@AccountCreateActivity.toString())
                    addOnPositiveButtonClickListener {
                        binding.Dob.setText(
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                Date(it)
                            )
                        )

                    }

                }

        }
    }

    var otpemail: String = ""
    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseCommonModel.observe(this) {
            if (it?.code == 200) {
                Toast.makeText(this, "OTP send successfully!", Toast.LENGTH_SHORT).show()
                ///save date
                PrefrencesHelper.saveFullName(this, binding.nameview.text!!.trim().toString())
                PrefrencesHelper.saveGender(this, binding.gender.selectedItem.toString())
                PrefrencesHelper.saveDate(this, binding.Dob.text.trim().toString())
                PrefrencesHelper.saveEmail(this, binding.emailview.text!!.trim().toString())
                PrefrencesHelper.saveRefCode(this, binding.referal.text!!.trim().toString())
                ///save date
                email = binding.emailview.text!!.trim().toString()
                mobilenumber = binding.phoneview.text!!.trim().toString()
                if (!email.isEmpty()) {
                    apiCallingForOtpEmail()
                } else {
                    var intent = Intent(applicationContext, PhoneVerifyActivity::class.java)
                    intent.putExtra("mobile", mobilenumber)
                    intent.putExtra("email", email)
                    intent.putExtra("otp", otpemail)
                    startActivity(intent)
                }


            } else if (it?.code == 409) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseOtpEmail.observe(this) {
            if (it?.code == 200) {
                otpemail = it.user.otp
                println("--otp${otpemail}")
                /* if (key.equals("package")) {
                     var intent = Intent(applicationContext, PhoneVerifyActivity::class.java)
                     intent.putExtra("mobile", mobilenumber)
                     intent.putExtra("email", email)
                     intent.putExtra("otp", otpemail)
                     intent.putExtra(
                         "Pkg",
                         Gson().toJson(package_data_transfer)
                     )
                     startActivity(intent)
                 } else {*/
                var intent = Intent(applicationContext, PhoneVerifyActivity::class.java)
                intent.putExtra("from", "newOTPActivity")
                intent.putExtra("mobile", mobilenumber)
                intent.putExtra("email", email)
                intent.putExtra("otp", otpemail)
                startActivity(intent)
                finish()


            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForOtpEmail() {
        val userMap: HashMap<String, Any> = prepareDataforOtpEmail()
        viewModel.onOtpEmail(this, userMap)
    }

    private fun prepareDataforOtpEmail(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kEmail] = binding.emailview.text!!.trim().toString()
        return hashMap
    }

    private fun apiCallingForCheckUser() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onCheckUser(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        if (!binding.emailview.text!!.trim().toString().isNullOrEmpty()) {
            hashMap[MyConstants.kEmail] = binding.emailview.text!!.trim().toString()
        }
        hashMap[MyConstants.kphoneNumber] = binding.phoneview.text.toString().trim()
        return hashMap
    }

    private fun validationData(): Boolean {
        if (binding.nameview.text!!.trim().toString().length < 4) {
            Toast.makeText(
                applicationContext,
                "Please Enter Name!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } /*else if (TextUtils.isEmpty(binding.phoneview.text!!.trim().toString())) {
            Toast.makeText(
                applicationContext,
                "Please Enter Phone Number !!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.phoneview.text!!.trim()
                .toString().length < 10 || binding.phoneview.text!!.trim().toString().length > 12
        ) {
            Toast.makeText(
                applicationContext,
                "Please Enter Valid Phone Number !!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }*/ else if (TextUtils.isEmpty(binding.Dob.text.trim().toString())) {
            Toast.makeText(
                applicationContext,
                "Please Select Date !!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.gender.selectedItemPosition.equals(0)) {
            Toast.makeText(
                applicationContext,
                "Please Select Gender !!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (!binding.checkBox.isChecked) {
            Toast.makeText(
                applicationContext,
                "Please Accept Term And Policy!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (!binding.vacctineCheckBox.isChecked) {
            Toast.makeText(
                applicationContext,
                "Please Accept Home Vaccination!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCountrySelected() {
        countryCode = binding.countryCodePicker.selectedCountryCodeWithPlus

    }

/*
    override fun onComplete(p0: Task<AuthResult>) {
        if (p0.isSuccessful) {
            apiCallingForCheckUser()
        } else {
            if (p0.exception is FirebaseAuthInvalidCredentialsException) {

                Toast.makeText(this, "" + p0.exception, Toast.LENGTH_LONG).show()


            }
        }
    }*/
}