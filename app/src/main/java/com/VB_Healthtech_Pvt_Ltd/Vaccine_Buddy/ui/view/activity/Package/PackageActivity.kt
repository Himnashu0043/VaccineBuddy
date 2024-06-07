package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Package

import android.annotation.SuppressLint
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityPackageBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ChooseVaccine.ChooseVaccineBabyActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.BabyFragment.BabyPackageFragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.BabyFragment.BabyVaccineFragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PackageActivity : AppCompatActivity() {
    lateinit var binding: ActivityPackageBinding
    var subCategory_id: String = ""
    var categoryId: String = ""
    val fragmentBabyPackage = BabyPackageFragment()
    val fragmentBabyVaccine = BabyVaccineFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
    }

    private fun initView() {
        val data = intent.getStringExtra("from")
        println("dddd${data}")
        subCategory_id = intent.getStringExtra("subCategory_id").toString()
        println("------subCategory_id$subCategory_id")
        categoryId = intent.getStringExtra("categoryId").toString()
        println("------categoryId$categoryId")
        if (data != null && data.equals("VaccineFragment")) {
            binding.include5.tvcreate.visibility = View.GONE
            selectCashColorSet(false, true)
            binding.include5.tvTittle.text = "Vaccination"
            comitFragemnet(fragmentBabyVaccine)
        } else {
            selectCashColorSet(true, false)
            comitFragemnet(fragmentBabyPackage)
            binding.include5.tvcreate.visibility = View.VISIBLE
        }
    }

    private fun lstnr() {
        binding.include5.ivBack.setOnClickListener {
            finish()
        }

        binding.button2.setOnClickListener {
            binding.include5.tvTittle.text = "Package"
            binding.include5.tvcreate.visibility = View.VISIBLE
            selectCashColorSet(true, false)
            comitFragemnet(fragmentBabyPackage)
        }
        binding.button4.setOnClickListener {
            binding.include5.tvTittle.text = "Vaccination"
            binding.include5.tvcreate.visibility = View.GONE
            selectCashColorSet(false, true)
            comitFragemnet(fragmentBabyVaccine)

        }
//        binding.textView167.setOnClickListener {
//            startActivity(Intent(this, SuggestAsPerBabyActivity::class.java))
//            //finish()
//        }
        binding.include5.tvcreate.setOnClickListener {
            startActivity(Intent(this, ChooseVaccineBabyActivity::class.java))
            // finish()
        }

    }

    @SuppressLint("CommitTransaction")
    private fun comitFragemnet(fragment: Fragment) {
        /* val fragment11 = supportFragmentManager.saveFragmentInstanceState(fragment)
         if (fragment11 != null) {
             supportFragmentManager.beginTransaction().remove(fragment)
         }*/
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout1, fragment)
            .commitAllowingStateLoss()
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

    override fun onBackPressed() {
        finish()
    }
}