package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Slot

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivitySecondSlotBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SecondSlotActivity : AppCompatActivity() {
    lateinit var binding :ActivitySecondSlotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondSlotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
    }

    private fun initView() {

    }

    private fun lstnr() {

    }
}