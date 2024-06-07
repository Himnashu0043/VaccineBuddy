package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.About

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityAboutBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.catalyist.helper.ErrorUtil

class AboutActivity : AppCompatActivity() {
    lateinit var binding: ActivityAboutBinding
    private lateinit var viewModel: ViewModalLogin

    //private var about: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this,window)
//         val about = intent.getStringExtra("about")
//        println("---about${about}")
    }

    private fun initView() {

        val about = intent.getStringExtra("about").toString()
        println("---about${about}")
//        println("---about${about}")
        if (about != null && about.equals("getAbout")) {
            binding.aboutToolbar.tvTittle.text = "Abouts"
            apiCallingForAbouts()
        } else if (about.equals("getTerm")) {
            binding.aboutToolbar.tvTittle.text = "Terms And Conditions"
            apiCallingForTerms()
        } else if (about.equals("getPrivacy")) {
            binding.aboutToolbar.tvTittle.text = "Privacy Policy"
            apiCallingForPrivacy()
        } /*else if (about.equals("getContact")) {
            binding.aboutToolbar.tvTittle.text = "Contact Us"
            apiCallingForContact()
        }*/


    }

    private fun lstnr() {
        binding.aboutToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseAbouts.observe(this) {
            if (it?.code == 200) {
               // binding.tvTittlaboute.text = it.staticResult.title
                println("----tittle${it?.staticResult?.title}")
                val htmlAsString = it.staticResult.description
                val htmlAsSpanned = Html.fromHtml(htmlAsString)
                binding.tvDes.text = htmlAsSpanned
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForAbouts() {
        viewModel.onAbout(this, "about")
    }

    private fun apiCallingForTerms() {
        viewModel.onAbout(this, "terms")
    }

    private fun apiCallingForPrivacy() {
        viewModel.onAbout(this, "privacy")
    }

    private fun apiCallingForContact() {
        viewModel.onAbout(this, "contact")
    }
}