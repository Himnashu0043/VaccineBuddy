package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.EditFamilyMenber

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityEditFamilyMemberBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EditFamilyMemberActivity : AppCompatActivity() {
    lateinit var binding:ActivityEditFamilyMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFamilyMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
    }

    private fun initView() {
        binding.editFamilyMemberToolbar.tvTittle.text ="Edit Family Member"


    }

    private fun lstnr() {
        binding.button6.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}