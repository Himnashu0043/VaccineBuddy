package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MoreDetailsInfo

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityMoreDetailInfoBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.MoreDetailsInfo.MoreDetailInfoAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MoreDoseDetailsModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

class MoreDetailInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreDetailInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
        CommonUtil.themeSet(this,window)
    }

    private fun initView() {
        val test = intent.getSerializableExtra("more_details") as ArrayList<MoreDoseDetailsModel>
        println("----test$test")
        binding.moreinfoToolbar.tvTittle.text = "Dosage Info"

        binding.tvageMoreInfo.text = test.get(0).does
        binding.rcyMoreInfo.layoutManager = LinearLayoutManager(this)
        val adapter = MoreDetailInfoAdapter(this, test)
        binding.rcyMoreInfo.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun lstnr() {
        binding.moreinfoToolbar.ivBack.setOnClickListener {
            finish()
        }
    }

}