package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityChangePasswordBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.catalyist.helper.ErrorUtil
import java.util.HashMap

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var viewModel: ViewModalLogin
    private var get_forgot_data = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()


    }

    private fun initView() {
        binding.confirmButton.tv.text = "Confirm"
        get_forgot_data = intent.getStringExtra("forgot_data").toString()
        println("---get_forgot_data$get_forgot_data")
    }

    private fun lstnr() {
        binding.imageView3.setOnClickListener {
            onBackPressed()
        }
        binding.confirmButton.tv.setOnClickListener {
            if (!isValideField()) {
                return@setOnClickListener
            }
            apiCallingForForgotPassword()

        }
    }

    private fun isValideField(): Boolean {
        val new_password: String = binding.editText.text?.trim().toString()
        val cnPassword: String = binding.editText2.text?.trim().toString()
        var isValid = true
        if (TextUtils.isEmpty(binding.editText.text?.trim().toString())) {
            Toast.makeText(this, "Please Enter New Password!", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (TextUtils.isEmpty(binding.editText2.text?.trim().toString())) {
            Toast.makeText(this, "Please Enter ConfirmPassword!", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (!new_password.equals(cnPassword)
        ) {
            Toast.makeText(this, "Confirm Password does not Match!", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseForgotPassword.observe(this) {
            if (it?.code == 200) {
                startActivity(
                    Intent(
                        applicationContext,
                        LoginActivity::class.java
                    )
                )
                finish()
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            } else if (it?.code == 401) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }


    private fun apiCallingForForgotPassword() {
        val userMap: HashMap<String, Any> = prepareDataforOtpVerify()
        viewModel.onForgotPassword(this, userMap)
    }

    private fun prepareDataforOtpVerify(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        if (get_forgot_data.contains("@")) {
            hashMap[MyConstants.kEmail] = get_forgot_data
        } else {
            hashMap[MyConstants.kphoneNumber] = get_forgot_data
        }
        hashMap[MyConstants.kPassword] = binding.editText2.text?.trim().toString()
        return hashMap
    }
    /*  private fun opendialogbox() {
          val dialog = this.let { Dialog(this) }
          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
          dialog.setContentView(R.layout.change_popup)
          dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
          dialog.setCanceledOnTouchOutside(false)
          dialog.show()
          val b: TextView = dialog.findViewById(R.id.dialog_done)

          b.setOnClickListener {
              dialog.dismiss()
              startActivity(Intent(this, LoginActivity::class.java))
              finish()
          }

  //        btDismiss.setOnClickListener {
  //            dialog.dismiss()
  //        }

          val window = dialog.window
          if (window != null) {
              window.setLayout(
                  WindowManager.LayoutParams.MATCH_PARENT,
                  WindowManager.LayoutParams.WRAP_CONTENT
              )
          }
      }*/


    override fun onBackPressed() {
        super.onBackPressed()
    }
}