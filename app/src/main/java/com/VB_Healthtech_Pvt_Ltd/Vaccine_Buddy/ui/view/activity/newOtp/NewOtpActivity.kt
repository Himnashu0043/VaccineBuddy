package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.newOtp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityNewOtpBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.About.AboutActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.LoginActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PhoneVerifyActivity
import java.util.regex.Matcher

class NewOtpActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
    }

    private fun initView() {

    }

    private fun lstnr() {
        binding.textView57.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.tvTermCondition.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getTerm"))
        }
        binding.tvprivate.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getPrivacy"))
        }
        binding.loginButton.setOnClickListener {
            if (!isValide()) {
                return@setOnClickListener
            }
            val email_phone = binding.edName.text!!.trim().toString()
            PrefrencesHelper.saveEmail_Phone(this, email_phone)
            var intent = Intent(applicationContext, PhoneVerifyActivity::class.java)
            intent.putExtra("mobile", email_phone)
            intent.putExtra("from","newOTPActivity")

            startActivity(intent)
        }
    }

    private fun isValide(): Boolean {
        var isValid = true
        if (Patterns.PHONE.matcher(binding.edName.text.toString()).matches()) {
            if (binding.edName.text!!.trim()
                    .toString().length < 8 || (binding.edName.text!!.trim()
                    .toString().length > 11)
            ) {
                Toast.makeText(this, "Please Enter Valid Phone Number", Toast.LENGTH_LONG).show()
                isValid = false
            }
        } else if (TextUtils.isEmpty(binding.edName.text.trim().toString())) {
            Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_LONG).show()
            isValid = false
        }

        return isValid


    }
}