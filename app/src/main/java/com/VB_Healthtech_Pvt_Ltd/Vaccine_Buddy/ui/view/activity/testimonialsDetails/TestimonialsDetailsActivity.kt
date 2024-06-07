package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.testimonialsDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityTestimonialsDetailsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Testimonial.AskedRequest
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import com.squareup.picasso.Picasso

class TestimonialsDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestimonialsDetailsBinding
    private var list :AskedRequest?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestimonialsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CommonUtil.themeSet(this, window)
        initView()
        lstnr()
    }

    private fun initView() {
        binding.tessToolbar.tvTittle.text ="Testimonial Details"
        list = intent.getSerializableExtra("list") as AskedRequest
        Picasso.get().load(list!!.image).into(binding.ivdummy)
        binding.tvTesName.text = list!!.name
        binding.tvTesCEO.text = list!!.designation
        binding.tvTesDes.text = list!!.reviewContent

    }

    private fun lstnr() {
        binding.tessToolbar.ivBack.setOnClickListener {
            finish()
        }

    }
}