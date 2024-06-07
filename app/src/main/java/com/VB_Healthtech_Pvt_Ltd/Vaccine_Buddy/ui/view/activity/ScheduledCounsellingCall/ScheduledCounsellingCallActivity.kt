package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduledCounsellingCall


import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityScheduleCounsellingCallBinding

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SchedulingCall.SchedulingCallAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalyist.helper.ErrorUtil
import kotlin.collections.ArrayList

class ScheduledCounsellingCallActivity : AppCompatActivity() {
    lateinit var bin: ActivityScheduleCounsellingCallBinding
    private var adptercall: SchedulingCallAdapter? = null
    private lateinit var viewModel: ViewModalLogin
    private var call_schedule_list =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CallSchedule.AskedData>()
    private var from = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityScheduleCounsellingCallBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        from = intent.getStringExtra("from").toString()
        println("----from$from")
        initView()
        lstnr()
    }

    private fun initView() {
        viewModel.onCallScheduleList(this)
        CommonUtil.themeSet(this, window)
        bin.include12.tvTittle.text = "Scheduled Counselling"


    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseCallScheduleList.observe(this) {
            if (it?.code == 200) {

                //binding.lottie.visibility = View.GONE
                call_schedule_list.clear()
                call_schedule_list.addAll(it.askedData)
                bin.rcyschedule.layoutManager =
                    LinearLayoutManager(this)
                adptercall = SchedulingCallAdapter(this, call_schedule_list)
                bin.rcyschedule.adapter = adptercall
                adptercall!!.notifyDataSetChanged()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun lstnr() {
        bin.include12.ivBack.setOnClickListener {
            if (from.equals("schedule_counselling")) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                onBackPressed()
            }

        }

    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }
}