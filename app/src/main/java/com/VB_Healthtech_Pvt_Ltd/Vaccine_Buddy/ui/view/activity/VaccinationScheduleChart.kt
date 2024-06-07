package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityVaccinationScheduleChartBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class VaccinationScheduleChart : AppCompatActivity()
{
    private lateinit var binding:ActivityVaccinationScheduleChartBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityVaccinationScheduleChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}