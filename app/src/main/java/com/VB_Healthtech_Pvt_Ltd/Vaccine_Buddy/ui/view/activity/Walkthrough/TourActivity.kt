package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Walkthrough

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityTourBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.TourAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.TourModal
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.newOtp.NewOtpActivity

class TourActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    lateinit var bin: ActivityTourBinding
    private val walkthroughmodel: ArrayList<TourModal> = ArrayList()
    private val deviceToken = ""

    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, TourActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityTourBinding.inflate(layoutInflater)
        setContentView(bin.root)

        // deviceToken=     getDeviceToken();
        Log.d(ContentValues.TAG, "onCreateToken: $deviceToken")
        PrefrencesHelper.saveTutorialStatus(this, false)
        initView()
        lstnr()
    }

    private fun initView() {
        walkthroughmodel.add(TourModal(R.drawable.tour1))
        walkthroughmodel.add(TourModal(R.drawable.tour2))
        walkthroughmodel.add(TourModal(R.drawable.tour1))

        bin.viewPager.adapter = TourAdapter(this, walkthroughmodel)
        bin.tabLayout.setupWithViewPager(bin.viewPager, true)
        bin.tvNext.setOnClickListener {
            if (bin.viewPager.currentItem != bin.tabLayout.tabCount - 1) {
                bin.viewPager.currentItem = bin.viewPager.currentItem + 1
            } else {
               // startActivity(Intent(this, LoginActivity::class.java))
                startActivity(Intent(this, NewOtpActivity::class.java))

                finish()
            }

        }
        bin.tvSkip.setOnClickListener {
            //startActivity(Intent(this, LoginActivity::class.java))
              startActivity(Intent(this, NewOtpActivity::class.java))

            finish()
        }
        bin.viewPager.addOnPageChangeListener(this)

    }

    private fun lstnr() {


    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (position == bin.tabLayout.tabCount - 1) {
            bin.tvNext.text = "Login"
            bin.tvSkip.visibility = View.GONE
        } else {
            bin.tvNext.text = "Next"
            bin.tvSkip.visibility = View.VISIBLE
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}