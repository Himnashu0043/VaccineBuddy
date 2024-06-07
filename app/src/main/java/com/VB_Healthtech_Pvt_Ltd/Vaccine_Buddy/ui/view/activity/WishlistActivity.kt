package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityWishlistBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.PackageFragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.VaccineFragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class WishlistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishlistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this,window)
        binding.include4.tvTittle.text = "Wishlist"
        selectCashColorSet(true, false)
        comitFragemnet(PackageFragment())
    }

    private fun lstnr() {
        binding.include4.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.button2.setOnClickListener{
            selectCashColorSet(true, false)
            comitFragemnet(PackageFragment())
        }



        binding.button4.setOnClickListener{
            selectCashColorSet(false, true)
            comitFragemnet(VaccineFragment())

        }
    }

    private fun comitFragemnet(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
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
        super.onBackPressed()
    }
}