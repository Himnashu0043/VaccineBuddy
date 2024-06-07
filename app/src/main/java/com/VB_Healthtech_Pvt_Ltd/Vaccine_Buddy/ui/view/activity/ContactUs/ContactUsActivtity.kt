package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ContactUs

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityContactUsActivtityBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class ContactUsActivtity : AppCompatActivity()
{
    private lateinit var binding:ActivityContactUsActivtityBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsActivtityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
        CommonUtil.themeSet(this,window)
    }

    private fun initView() {
        binding.contactToolbar.tvTittle.text="Contact Us"
    }

    private fun lstnr() {
        binding.contactToolbar.ivBack.setOnClickListener {
            onBackPressed()
          }
        binding.imageView43.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:9650039988")
            startActivity(intent)
        }
        binding.imageView44.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_EMAIL, "info@vaccine-buddy.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            //intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.")
            startActivity(Intent.createChooser(intent, "Send Email"))
        }
        binding.imageView45.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.vaccine-buddy.com/")
            startActivity(openURL)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}