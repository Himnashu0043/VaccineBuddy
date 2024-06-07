package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleHistory

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityScheduleHistoryBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.DoesDetailsSecond.DoesDetailSecondAdpater
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails.AddressData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails.AskedRecord
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bountybunch.helper.startDownload
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ScheduleHistoryActivity : AppCompatActivity() {
    lateinit var bin: ActivityScheduleHistoryBinding
    private var adpterDose: DoesDetailSecondAdpater? = null
    private lateinit var askedDataObj: AskedData
    private var askedRecordList = ArrayList<AskedRecord>()
    private var addressList = ArrayList<AddressData>()
    private var age: Int = 0
    var from = ""
    var from_schedule: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityScheduleHistoryBinding.inflate(layoutInflater)
        setContentView(bin.root)
        askedDataObj = Gson().fromJson(intent.getStringExtra("asked_data"), AskedData::class.java)
        println("------asked$askedDataObj")
        askedRecordList = intent.getSerializableExtra("record") as ArrayList<AskedRecord>
        println("------comemm$askedRecordList")
        addressList = intent.getSerializableExtra("address") as ArrayList<AddressData>
        println("------addresss$addressList")
        age = intent.getIntExtra("age", 0)
        println("----age$age")
        from_schedule = intent.getStringExtra("schedule_dis").toString()
        from = intent.getStringExtra("from").toString()
        println("----schecomplete_from$from")
        bin.tvage.text = "$age year"

        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
        if (from_schedule.equals("schedule_dis")){
            bin.include7.tvTotal.visibility = View.INVISIBLE
            bin.include7.textView120.visibility = View.INVISIBLE
        }
    }

    private fun initView() {
        bin.toolbarDesignHistory.tvTittle.text = "Schedule History"
        bin.tvOrderId.text = "#${askedDataObj.order_number}"
        bin.include7.tvNameOrderDetails.text = askedDataObj.itemName
        val img = askedDataObj.itemImage
        if (img.isNotEmpty()) {
            bin.include7.ivdummyimg.visibility = View.INVISIBLE
            bin.include7.imageView36.visibility = View.VISIBLE
            Picasso.get().load(img).into(bin.include7.imageView36)
        } else {
            bin.include7.ivdummyimg.visibility = View.VISIBLE
            bin.include7.imageView36.visibility = View.INVISIBLE
        }
        if (askedDataObj.itemPrice.isEmpty()) {
            bin.include7.tvPriceOrderDetails.text = "₹ ${askedDataObj.packagePrice}"
            bin.include7.tvOfferPriceOrderDetails.visibility = View.INVISIBLE
            bin.include7.divider19.visibility = View.GONE
        } else {
            bin.include7.divider19.visibility = View.VISIBLE
            bin.include7.tvPriceOrderDetails.text = "₹ ${askedDataObj.packagePrice}"
            bin.include7.tvOfferPriceOrderDetails.text = "₹ ${askedDataObj.itemPrice}"

        }
        bin.include7.tvDesOrderDetails.text = askedDataObj.description
        bin.include7.tvIncludeVaaOrderDetails.text =
            askedDataObj.inclusiveVaccine.toString()
        bin.include7.tvCompleteDoesOrderDetails.text =
            "${askedDataObj.completedDose} Complete Dosage"
        bin.include7.tvPendingDoesOrderDetails.text =
            "${askedDataObj.pendingDose} Pending Dosage"
        if (askedDataObj.userData[0].anyReaction.isEmpty()) {
            bin.tvAnyRePreviousDoes.text = "--"
        } else {
            bin.tvAnyRePreviousDoes.text = askedDataObj.userData[0].anyReaction
        }
        bin.tvName.text = askedDataObj.userData[0].fullName
//        bin.tvage.text = "0 year"
        bin.tvGender.text = askedDataObj.userData[0].gender
        bin.dobtext.text = askedDataObj.userData[0].dob
        bin.relationtext2.text = askedDataObj.userData[0].relation
        bin.tvMedical.text = askedDataObj.userData[0].medicalCondition
        if (askedDataObj.userData[0].uploadVaccinationChart.isEmpty()) {
            bin.uploadChatBtn.visibility = View.GONE
        } else {
            bin.uploadChatBtn.visibility = View.VISIBLE
            val url = askedDataObj.userData[0].uploadVaccinationChart
            bin.uploadChatBtn.setOnClickListener {
                Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT).show()
                startDownload(
                    url,
                    this,
                    "Vaccination chat",
                    "Vaccination chat"
                )
            }
        }
        if (askedDataObj.userData[0].anyReaction.isEmpty()) {
            bin.tvAnyRePreviousDoes.text = "--"
        } else {
            bin.tvAnyRePreviousDoes.text = askedDataObj.userData[0].anyReaction
        }
        if (askedDataObj.assignedNurse != null) {
//            bin.include7.tvForOrderDetails.text = askedDataObj.assignedNurse
            if (from.equals("complete")) {
                bin.include7.textView1871.visibility = View.VISIBLE
                bin.include7.textView187.visibility = View.INVISIBLE
                bin.include7.tvForOrderDetails.text = askedDataObj.assignedNurse
            } else {
                bin.include7.textView1871.visibility = View.INVISIBLE
                bin.include7.textView187.visibility = View.VISIBLE
                bin.include7.tvFor1OrderDetails.text = askedDataObj.assignedNurse
            }
        } else {
            bin.include7.tvFor1OrderDetails.text = "NA"
        }
        bin.childLayout.visibility = View.GONE
        if (askedDataObj.userData[0].memberType.equals("KIDS")) {
            bin.childLayout.visibility = View.VISIBLE
            bin.tvdelivery.text =
                "${askedDataObj.userData[0].deliveryType} , ${askedDataObj.userData[0].deliveryPeriod}"
            bin.tvAnySeizures.text = askedDataObj.userData[0].seizuresAtBirth
            if (askedDataObj.userData[0].nicuDetails.isEmpty()) {
                bin.uploadChatBtn2.visibility = View.GONE
            } else {
                bin.uploadChatBtn2.visibility = View.VISIBLE
                val url = askedDataObj.userData[0].nicuDetails
                bin.uploadChatBtn2.setOnClickListener {
                    Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT).show()
                    startDownload(
                        url,
                        this,
                        "NICU Records Chat",
                        "NICU Records Chat"
                    )
                }
            }
            if (askedDataObj.userData[0].bithHistory.isEmpty()) {
                bin.uploadChatBtn3.visibility = View.GONE
            } else {
                bin.uploadChatBtn3.visibility = View.VISIBLE
                val url = askedDataObj.userData[0].bithHistory
                bin.uploadChatBtn3.setOnClickListener {
                    Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT).show()
                    startDownload(
                        url,
                        this,
                        "Birth History Chat",
                        "Birth History Chat"
                    )
                }
            }
            bin.tvMedicalDesorder.text = askedDataObj.userData[0].medicalDisorder
        }
        bin.rcySchedule.layoutManager =
            LinearLayoutManager(this)
        adpterDose = DoesDetailSecondAdpater(this, askedRecordList, addressList)
        bin.rcySchedule.adapter = adpterDose
        adpterDose!!.notifyDataSetChanged()

    }

    private fun lstnr() {
        bin.toolbarDesignHistory.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.textView204.setOnClickListener {
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
        val btSubmit = dialog.findViewById<TextView>(R.id.dialogConfrom)
        val dialogText = dialog.findViewById<TextView>(R.id.tvPrice)
        dialogText.setText("₹ ${askedDataObj.itemPrice}")

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