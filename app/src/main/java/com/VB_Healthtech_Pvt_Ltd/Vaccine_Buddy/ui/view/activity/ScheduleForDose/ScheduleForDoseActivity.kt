package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleForDose

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityScheduleForDoseBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.OrderDetails.DoseDetailsAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView

class ScheduleForDoseActivity : AppCompatActivity() {
    lateinit var bin:ActivityScheduleForDoseBinding
    private var adpterDose: DoseDetailsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityScheduleForDoseBinding.inflate(layoutInflater)
        setContentView(bin.root)
        initView()
        lstnr()
    }

    private fun initView() {
        bin.scheduleForToolbar.tvTittle.text ="Schedule For Dosage"
        bin.commonBtn.tv.text ="Book Now"
//        bin.doesLay.rcyvaccinedose.layoutManager =
//            LinearLayoutManager(this)
//        adpterDose = DoseDetailsAdapter(this)
//        bin.doesLay.rcyvaccinedose.adapter = adpterDose
//        adpterDose!!.notifyDataSetChanged()
    }

    private fun lstnr() {
        bin.scheduleForToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.cancelView.setOnClickListener {
            opendialogbox()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun opendialogbox() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_cancel_pop)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val btDismiss = dialog.findViewById<TextView>(R.id.dialog_cancel)
        val btSubmit = dialog.findViewById<TextView>(R.id.dialog_submit)
        val dialogText = dialog.findViewById<TextView>(R.id.dialog_text)
//        dialogText.setText(R.string.are_you_sure_want_to_logout)

        btSubmit.setOnClickListener {
            dialog.dismiss()
            opendialogbox1()
        }

        btDismiss.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }
    private fun opendialogbox1() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_submited)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
//        val btDismiss = dialog.findViewById<TextView>(R.id.dialog_cancel)
        val btSubmit = dialog.findViewById<TextView>(R.id.dialog_submit)
//        val dialogText = dialog.findViewById<TextView>(R.id.dialog_text)
//        dialogText.setText(R.string.are_you_sure_want_to_logout)

        btSubmit.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, MainActivity::class.java))
        }

//        btDismiss.setOnClickListener {
//            dialog.dismiss()
//        }

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }
}