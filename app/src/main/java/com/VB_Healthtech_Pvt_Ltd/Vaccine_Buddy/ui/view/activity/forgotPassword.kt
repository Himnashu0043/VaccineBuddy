package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityForgotPasswordBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.catalyist.helper.ErrorUtil
import java.util.regex.Matcher
import java.util.regex.Pattern

class forgotPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private var forgot_data: String? = ""
    private lateinit var viewModel: ViewModalLogin
    val MobilePattern: String = "[0-9]{10}"
    var ps: Pattern = Pattern.compile("^[a-zA-Z ]+$")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)

    }

    private fun initView() {
        binding.btn.tv.text = "Next"

    }

    private fun lstnr() {
        binding.imageView3.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btn.tv.setOnClickListener {
            if (!isValideField()) {
                return@setOnClickListener
            } else {
                apiCallingForCheckUser()
            }
        }

    }

    /* private fun opendialogbox() {
         val dialog = this.let { Dialog(this) }
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
         dialog.setContentView(R.layout.dialog_forgot_popup)
         dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         dialog.setCanceledOnTouchOutside(false)
         dialog.show()
         val btDismiss = dialog.findViewById<TextView>(R.id.dialog_cancel)
         val btSubmit = dialog.findViewById<TextView>(R.id.dialog_submit)
         val dialogText = dialog.findViewById<TextView>(R.id.dialog_text)
 //        dialogText.setText(R.string.are_you_sure_want_to_logout)

         btSubmit.setOnClickListener {
             dialog.dismiss()
             startActivity(Intent(this, VerificationActivity::class.java))
             finish()
         }

         val window = dialog.window
         if (window != null) {
             window.setLayout(
                 WindowManager.LayoutParams.MATCH_PARENT,
                 WindowManager.LayoutParams.WRAP_CONTENT
             )
         }
     }*/
    var otpemail: String = ""

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseCommonModel.observe(this) {
            if (it?.code == 200) {
                if (forgot_data!!.contains("@")) {
                    Toast.makeText(this, "This email is not register with us!", Toast.LENGTH_SHORT)
                        .show()
                } /*else if (bs == false) {
                    Log.d("TAG", "apiResponse: Himanshu")
                    Toast.makeText(
                        this,
                        "Pleaese",
                        Toast.LENGTH_SHORT
                    ).show()
                }*/ else {
                    Toast.makeText(
                        this,
                        "This Phone Number is not register with us!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (it?.code == 409) {
                if (forgot_data!!.contains("@")) {
                    apiCallingForOtpEmail()
                } else {
                    var intent = Intent(applicationContext, PhoneVerifyActivity::class.java)
                    println("--forgot_mobile${otpemail}")
                    intent.putExtra("forgot_data", forgot_data)
                    intent.putExtra("forgot_otp", otpemail)
                    intent.putExtra("from", "forgot")
                    startActivity(intent)
                }
            }
        }
        viewModel.responseOtpEmail.observe(this) {
            if (it?.code == 200) {
                otpemail = it.user.otp
                println("--forgot_otp${otpemail}")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                var intent = Intent(applicationContext, PhoneVerifyActivity::class.java)
                intent.putExtra("forgot_data", forgot_data)
                intent.putExtra("forgot_otp", otpemail)
                intent.putExtra("from", "forgot")
                startActivity(intent)
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
        hashMap[MyConstants.kEmail] = binding.forgottextfield.text.trim().toString()
        return hashMap
    }

    private fun apiCallingForCheckUser() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onCheckUser(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        if (forgot_data!!.contains("@")) {
            hashMap[MyConstants.kEmail] = binding.forgottextfield.text.trim().toString()
        } else {
            hashMap[MyConstants.kphoneNumber] = binding.forgottextfield.text.toString().trim()
        }

        return hashMap
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    private fun isValideField(): Boolean {
        forgot_data = binding.forgottextfield.text?.trim().toString()
        println("-----pass$forgot_data")
        var ms: Matcher = ps.matcher(forgot_data)
        var bs: Boolean = ms.matches()
        var isValid = true
        if (TextUtils.isEmpty(binding.forgottextfield.text?.trim().toString())) {
            Toast.makeText(
                this,
                "Please enter your registered email or phone number!",
                Toast.LENGTH_SHORT
            ).show()
            isValid = false
        } else if (bs == true) {
            Toast.makeText(
                this,
                "Please Enter Valid Email!",
                Toast.LENGTH_SHORT
            ).show()
            isValid = false
        }
        return isValid

    }


}
