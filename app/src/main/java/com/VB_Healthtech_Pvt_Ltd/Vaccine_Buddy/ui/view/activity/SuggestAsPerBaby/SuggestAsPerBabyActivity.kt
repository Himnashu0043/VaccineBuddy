package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.SuggestAsPerBaby

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivitySuggestAsPerBabyBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.SuggestBaby.PackageSuggestBabyFragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.SuggestBaby.VaccineSuggestBabyFragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class SuggestAsPerBabyActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuggestAsPerBabyBinding
    private var back_from = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestAsPerBabyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        back_from = intent.getStringExtra("back_from").toString()
        initView()
        lstnr()

    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        binding.include5.tvTittle.text = "Suggest as Per Baby's age"
        if (back_from != null && back_from.equals("back_vaccine_suggest")) {
            selectCashColorSet(false, true)
            comitFragemnet(VaccineSuggestBabyFragment())
        } else {
            selectCashColorSet(true, false)
            comitFragemnet(PackageSuggestBabyFragment())
        }


    }

    private fun lstnr() {
        binding.button2.setOnClickListener {
            selectCashColorSet(true, false)
            comitFragemnet(PackageSuggestBabyFragment())
        }
        binding.button4.setOnClickListener {
            selectCashColorSet(false, true)
            comitFragemnet(VaccineSuggestBabyFragment())
        }
        binding.include5.ivBack.setOnClickListener {
            finish()
        }

    }

    fun selectCashColorSet(btn1: Boolean, btn2: Boolean) {
        if (btn1) {
            binding.button2.setTextColor(ContextCompat.getColor(this, R.color.darknavyblue2))
            binding.button2.background = ContextCompat.getDrawable(this, R.drawable.package_edit)
            binding.button4.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.button4.background = ContextCompat.getDrawable(this, R.drawable.vacine_edit)
        } else {
            binding.button2.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.button2.background = ContextCompat.getDrawable(this, R.drawable.package_edit2)
            binding.button4.setTextColor(ContextCompat.getColor(this, R.color.darknavyblue2))
            binding.button4.background = ContextCompat.getDrawable(this, R.drawable.vacine_edit2)
        }

    }

    private fun comitFragemnet(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.suggestFrame, fragment)
            .commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}