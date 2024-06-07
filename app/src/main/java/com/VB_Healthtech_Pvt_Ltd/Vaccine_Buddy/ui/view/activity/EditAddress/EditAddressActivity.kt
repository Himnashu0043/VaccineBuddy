package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.EditAddress

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityEditAddressBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EditAddressActivity : AppCompatActivity() {
    lateinit var binding:ActivityEditAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
    }

    private fun initView() {
        binding.editAddress.tvTittle.text="Edit Address"

    }

    private fun lstnr() {
        binding.editAddress.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.button12.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}