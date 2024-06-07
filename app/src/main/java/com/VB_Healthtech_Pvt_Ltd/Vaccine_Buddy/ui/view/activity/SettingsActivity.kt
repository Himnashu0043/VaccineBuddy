package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivitySettingsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.SecondChangePassword.SeconChangePasswordActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.catalyist.helper.ErrorUtil

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: ViewModalLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()


    }

    private fun initView() {
        binding.include3.tvTittle.text = "Setting"
        CommonUtil.themeSet(this, window)


    }

    private fun lstnr() {
        binding.include3.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.textView66.setOnClickListener {
            startActivity(Intent(this, SeconChangePasswordActivity::class.java))

        }
        binding.pushNotiBtn.setOnClickListener {
            viewModel.onSetNotification(this)
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseSetNotification.observe(this) {
            if (it?.code == 200) {
                if (it.is_notification) {
                    Toast.makeText(this, "Notification On", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Notification Off", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }
}