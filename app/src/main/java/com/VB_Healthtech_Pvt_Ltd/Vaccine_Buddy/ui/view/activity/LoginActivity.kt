package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityLoginBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants.*
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.About.AboutActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.newOtp.NewOtpActivity
import com.catalyist.helper.ErrorUtil
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {
    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 1991
        fun getIntent(context: Context?): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: ViewModalLogin
    var ps: Pattern = Pattern.compile("^[a-zA-Z ]+$")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
        println("------$packageName")


    }

    private fun initView() {
        apiResponse()
        /// binding.textView2.animate().translationX(-430f).setDuration(3000).setStartDelay(0)
        val logout_email_phone = PrefrencesHelper.getEmail_Phone(this)
        val logout_pass = PrefrencesHelper.getLogin_pass(this)
        if (!logout_email_phone.isNullOrEmpty()) {
            binding.editText.setText(logout_email_phone)
            binding.editText2.setText(logout_pass)
            binding.checkBox.isChecked = true
        }
    }

    private fun lstnr() {
        binding.forgotextview.setOnClickListener {
            val intent = Intent(this, forgotPassword::class.java)
            startActivity(intent)
            finish()
        }
        binding.textView7.setOnClickListener {
            val intent = Intent(this, AccountCreateActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginButton.setOnClickListener {
            val email_phone = binding.editText.text!!.trim().toString()
            val password = binding.editText2.text!!.trim().toString()

            if (binding.checkBox.isChecked) {
                PrefrencesHelper.saveEmail_Phone(this, email_phone)
                PrefrencesHelper.saveLogin_pass(this, password)
            } else {
                PrefrencesHelper.saveEmail_Phone(this, "")
                PrefrencesHelper.saveLogin_pass(this, "")
            }
            if (!isValide()) {
                return@setOnClickListener
            }

            apiCallingForLogin()
        }


        binding.tvTermCondition.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getTerm"))
        }
        binding.tvprivate.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getPrivacy"))
        }
        binding.tvLoginWithOtp.setOnClickListener {
            startActivity(Intent(this, NewOtpActivity::class.java))
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseLogin.observe(this) {
            if (it?.code == 200) {
                PrefrencesHelper.getSaveToken(this, it.token)
                PrefrencesHelper.saveLoginStatus(this, true)
                PrefrencesHelper.saveRefCode(this, it.activeUser.myRefferral)
                startActivity(MainActivity.getIntent(this))

                finish()
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            } else if (it?.code == 401) {
                Toast.makeText(this, "Please enter Valid Credentials!", Toast.LENGTH_SHORT).show()
            } else if (it?.code == 404) {
                Toast.makeText(
                    this,
                    "Your Credential is not Register with us!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForLogin() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.userLogin(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        val deviceToken = PrefrencesHelper.setSaveDeviceID(this)
        println("devide----${deviceToken}")
        hashMap[kemailPhone] = binding.editText.text!!.trim().toString()
        hashMap[kPassword] = binding.editText2.text!!.trim().toString()
        hashMap[kDeviceToken] = deviceToken
        hashMap[kdeviceType] = "android"
        return hashMap
    }

    private fun isValide(): Boolean {
        var ms: Matcher = ps.matcher(binding.editText.text.toString())
        var bs: Boolean = ms.matches()
        var isValid = true
        if (Patterns.PHONE.matcher(binding.editText.text.toString()).matches()) {
            if (binding.editText.text!!.trim()
                    .toString().length < 8 || (binding.editText.text!!.trim()
                    .toString().length > 13)
            ) {
                Toast.makeText(this, "Please Enter Valid Phone Number", Toast.LENGTH_LONG).show()
                isValid = false
            }
        }
        if (TextUtils.isEmpty(binding.editText.text!!.trim().toString())) {
            // Toaster.toast("Please Enter Email Id!!")
            Toast.makeText(
                this,
                "Please enter registered email id or phone no.!!",
                Toast.LENGTH_SHORT
            )
                .show()
            isValid = false

        } /*else if (!Validator.isValidEmail(Utils.getProperText(binding.editText))) {
            // Toaster.toast("Invalid Email Address!!")
            Toast.makeText(this, "Invalid Email Address!!", Toast.LENGTH_SHORT).show()
            isValid = false

        }*/
//        else if(binding.checkBox.isChecked){
//            isValid = false
//            Toast.makeText(this, "Please Select !!", Toast.LENGTH_SHORT).show()
//        }
        else if (TextUtils.isEmpty(binding.editText2.text!!.trim().toString())) {
            //CommonUtil.showSnackBar(this,"Please Enter Password!!")
            Toast.makeText(this, "Please Enter Password!!", Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}
