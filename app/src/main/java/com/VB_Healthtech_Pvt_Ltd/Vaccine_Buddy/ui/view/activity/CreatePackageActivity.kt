package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityCreatePackageBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.PackageFragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.VaccineFragment
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class CreatePackageActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityCreatePackageBinding
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePackageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectCashColorSet(true,false)
        comitFragemnet(PackageFragment())

        binding.button2.setOnClickListener()
        {

            selectCashColorSet(true,false)
            comitFragemnet(PackageFragment())
        }
        binding.button4.setOnClickListener()
        {
            selectCashColorSet(false,true)
            comitFragemnet(VaccineFragment())

        }
        binding.textView54.setOnClickListener()
        {
            startActivity(Intent(this,WishlistActivity::class.java))
        }

        binding.imageView28.setOnClickListener()
        {
            onBackPressed()
        }

//        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
//
//        val adapter = PageAdapter(supportFragmentManager)
//        binding.viewpager.adapter = adapter
//
//        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//
//        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab)
//            {
//                viewPager?.currentItem = tab.position
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//
//            }
//        })
//
//    }
//
//    private fun PagerAdapter(
//        createPackageActivity: CreatePackageActivity,
//        supportFragmentManager: FragmentManager,
//        tabCount: Int
//    ) {

    }

    private fun comitFragemnet(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commitAllowingStateLoss()
    }


    fun selectCashColorSet( btn1: Boolean ,btn2:Boolean) {
//        binding.tvCash.setTextColor(ContextCompat.getColor(this, R.color.white))
//        binding.tvCash.background = ContextCompat.getDrawable(this, R.drawable.rec_green_10r)
//        binding.bank.setTextColor(ContextCompat.getColor(this, R.color.black_light))
//        binding.bank.background = ContextCompat.getDrawable(this, R.drawable.rec_ring_gray2)
        if (btn1){
            binding.button2.setTextColor(ContextCompat.getColor(this, R.color.darknavyblue2))
            binding.button2.background = ContextCompat.getDrawable(this, R.drawable.package_edit)


            binding.button4.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.button4.background = ContextCompat.getDrawable(this, R.drawable.vacine_edit)

        } else{
            binding.button2.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.button2.background = ContextCompat.getDrawable(this, R.drawable.package_edit2)

            binding.button4.setTextColor(ContextCompat.getColor(this, R.color.darknavyblue2))
            binding.button4.background = ContextCompat.getDrawable(this, R.drawable.vacine_edit2)
        }

    }
}

