package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.SecondChangePassword

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivitySecondChangePasswordBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.LoginActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.catalyist.helper.ErrorUtil
import java.util.HashMap

class SeconChangePasswordActivity : AppCompatActivity() {
    lateinit var bin: ActivitySecondChangePasswordBinding
    private lateinit var viewModel: ViewModalLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivitySecondChangePasswordBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        bin.addcontactToolbar.tvTittle.text = "Change Password"
        bin.btn.tv.text = "Change Password"
        CommonUtil.themeSet(this, window)

    }

    private fun lstnr() {
        bin.addcontactToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.btn.tv.setOnClickListener {
            if (!isValideField()) {
                return@setOnClickListener
            }
            apiCallingForChangePassword()
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseChangePassword.observe(this) {
            if (it?.code == 200) {
                startActivity(
                    Intent(
                        applicationContext,
                        LoginActivity::class.java
                    )
                )
                PrefrencesHelper.getSaveToken(this, "")
                PrefrencesHelper.saveLoginStatus(this, false)
                finish()
            } else if (it?.code == 401) {
                Toast.makeText(this, "Please Enter Valid Old Password!!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }


    private fun apiCallingForChangePassword() {
        val userMap: HashMap<String, Any> = prepareDataforChangePassword()
        viewModel.onChangePassword(this, userMap)
    }

    private fun prepareDataforChangePassword(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.koldPassword] = bin.edOldPwd.text!!.trim().toString()
        hashMap[MyConstants.knewPassword] = bin.edCnfrmPwd.text!!.trim().toString()
        return hashMap
    }

    private fun isValideField(): Boolean {
        val createpassword: String = bin.edCrtPwd.text?.trim().toString()
        val cnfrmPassword: String = bin.edCnfrmPwd.text?.trim().toString()
        var isValid = true
        if (TextUtils.isEmpty(bin.edOldPwd.text?.trim().toString())) {
            Toast.makeText(this, "Please Enter Old Password!", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (TextUtils.isEmpty(bin.edCrtPwd.text?.trim().toString())) {
            Toast.makeText(this, "Please Enter New Password!", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (TextUtils.isEmpty(bin.edCnfrmPwd.text?.trim().toString())) {
            Toast.makeText(this, "Please Enter Confirm Password!", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (!createpassword.equals(cnfrmPassword)
        ) {
            Toast.makeText(this, "New and Confirm Password does not Match!", Toast.LENGTH_SHORT)
                .show()
            isValid = false
        }
        return isValid

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}