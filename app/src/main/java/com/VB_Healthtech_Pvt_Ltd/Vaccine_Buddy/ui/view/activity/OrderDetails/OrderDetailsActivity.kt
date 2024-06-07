package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.OrderDetails

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityOrderDetailsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.OrderDetails.DoseDetailsAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.OrderDetailsVaccine.OrderDetailsVaccineDose
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails.*
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetailsPackageDoesModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.StatusPackageModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDoesModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleHistory.ScheduleHistoryActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.DoseVaccineSetModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.RateUs.RateUsActivity
import com.bountybunch.helper.startDownload
import com.catalyist.helper.ErrorUtil
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.math.log

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var bin: ActivityOrderDetailsBinding
    private var adpterDose: DoseDetailsAdapter? = null
    private lateinit var viewModel: ViewModalLogin
    private lateinit var doesInfoVaccine_Adpater: OrderDetailsVaccineDose
    private var doesInfoVaccine_List = ArrayList<VaccineDoesModel>()
    private var order_type = ""
    private var itemId = ""
    private var askedRecordDataList = ArrayList<AskedRecord>()
    private lateinit var askedData: AskedData
    private var addressList = ArrayList<AddressData>()
    private var commonsSet: ArrayList<OrderDetailsPackageDoesModel> = ArrayList()
    private var status = ArrayList<StatusPackageModel>()
    private var item_price: Double = 0.0
    private var diff_year: Int = 0
    var from = ""
    var payment_status: Boolean = true
    private var payment_type1: String = ""
    var approvedStatus: Boolean = true
    var invoiceLink = ""
    var recode_list = ArrayList<AskedRecord>()
    var vaccine_dose_list = ArrayList<VaccineDoesModel>()
    var total_price_cancel_vaccine_pop: Double = 0.0
    var total_dis_cancel_vaccine_pop: Double = 0.0
    var total_cancel_vaccine_pop: Double = 0.0
    var total_noofcount_cancel_vaccine_pop: Double = 0.0
    var total_gst_amount_cancel_vaccine_pop: Double = 0.0
    var total_selected_vaccine_cancel_vaccine_pop: Double = 0.0
    var is_nurse: Boolean = false
    var nurseId: String = ""
    var orderId1: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
        /*try {
            val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now()
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            current_year = current.getYear()
            println("---current_yearAdd$current_year")
        } catch (e: Exception) {
            e.toString()
        }*/
        from = intent.getStringExtra("from").toString()
        println("----complete_from$from")

    }

    private fun initView() {
        bin.include6.tvTittle.text = "Order Details"
        bin.commonBtn.tv.text = "Re-Order"
        apiCallingForOrderDetails()


    }

    private fun lstnr() {
        bin.include6.ivDownload.setOnClickListener {
            startDownload(invoiceLink, this, "Order Invoice", "Order Invoice")
        }
        bin.include6.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.tvPreView.setOnClickListener {
            startActivity(
                Intent(this, RateUsActivity::class.java).putExtra("nurseId", nurseId)
                    .putExtra("orderId", orderId1).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }

        bin.tvSchedulHistory.setOnClickListener {
            val intent = Intent(this, ScheduleHistoryActivity::class.java)
            intent.putExtra("asked_data", Gson().toJson(askedData))
            intent.putExtra("record", askedRecordDataList)
            intent.putExtra("address", addressList)
            intent.putExtra("age", diff_year)
            intent.putExtra("schedule_dis", "schedule_dis")
            intent.putExtra("from", "complete")
            startActivity(intent)

        }
        bin.commonBtn.tv.setOnClickListener {
            println("---cliclordid$itemId")
            if (order_type.equals("PACKAGE")) {
                startActivity(
                    Intent(
                        this, PackageDetailsActivity::class.java
                    ).putExtra("packageId", itemId)
                )
            } else {
                startActivity(
                    Intent(
                        this, VaccineDetailsActivity::class.java
                    ).putExtra("vaccineId", itemId)
                )
            }

        }
        bin.textView204.setOnClickListener {
            opendialogbox()
        }
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
        if (!payment_status) {
            dialogText.setText("₹ 0")
        } else {
            dialogText.setText("₹ $item_price")
        }
        btSubmit.setOnClickListener {
            dialog.dismiss()
            apiCallingForCancelOrder()
            // opendialogbox1()
        }

        btDismiss.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    /* private fun opendialogbox1() {
         val dialog = this.let { Dialog(this) }
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
         dialog.setContentView(R.layout.dialog_submited)
         dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         dialog.setCanceledOnTouchOutside(false)
         dialog.show()
         val btSubmit = dialog.findViewById<TextView>(R.id.dialog_submit)

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
     }*/

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseOrderDetails.observe(this) {
            if (it?.code == 200) {
                println("---is_nurse$is_nurse")
                if (it.is_nurse == null) {
                    is_nurse = true
                } else if (it.is_nurse) {
                    is_nurse = true
                } else {
                    is_nurse = false
                }

                askedData = it.askedData
                order_type = it.askedData.order_type
                println("----order$order_type")
                itemId = it.askedData.itemId
                println("---item$itemId")
                askedRecordDataList.addAll(it.askedRecord)
                addressList = it.askedData.addressData
                payment_status = it.askedData.paymentStatus
                payment_type1 = it.askedData.paymentDetails.paymentType
                println("------pppp$payment_type1")
                approvedStatus = it.askedData.approvedStatus
                invoiceLink = it.askedData.invoiceLink ?: ""



                if (from.equals("complete")) {
                    bin.textView204.visibility = View.INVISIBLE
                    if (!it.askedData.invoiceLink.isNullOrEmpty()) {
                        bin.include6.ivDownload.visibility = View.VISIBLE
                    }

                    it.askedData.let {
                        if (!it.packageCertificateLink.isNullOrEmpty()) {
                            bin.commonBtn1.tv.visibility = View.VISIBLE
                            bin.commonBtn1.tv.text = "Download Certificate"
                            var certi_url = it.packageCertificateLink ?: ""
                            bin.commonBtn1.tv.setOnClickListener {
                                startDownload(
                                    certi_url,
                                    this,
                                    "Download Package Certificate",
                                    "Download Package Certificate"
                                )

                            }

                        } else {
                            bin.commonBtn1.root.visibility = View.GONE
                        }

                    }
                    /*if (!it.askedRecord.get(0).nurseId.isNullOrEmpty()) {
                        nurseId = it.askedRecord.get(0).nurseId ?: ""
                    }*/
                } else if (from.equals("cancel")) {
                    bin.textView204.visibility = View.INVISIBLE
                    bin.commonBtn1.root.visibility = View.GONE
                } else {
                    if (payment_type1.equals("CASH")) {
                        if (approvedStatus == false) {
                            bin.include6.ivDownload.visibility = View.GONE
                        } else {
                            bin.include6.ivDownload.visibility = View.VISIBLE
                        }
                    } else {
                        bin.include6.ivDownload.visibility = View.VISIBLE
                    }
                    bin.textView204.visibility = View.VISIBLE
                    it.askedData.let {
                        if (!it.packageCertificateLink.isNullOrEmpty()) {
                            bin.commonBtn1.tv.visibility = View.VISIBLE
                            bin.commonBtn1.tv.text = "Download Certificate"
                            var certi_url = it.packageCertificateLink ?: ""
                            bin.commonBtn1.tv.setOnClickListener {
                                startDownload(
                                    certi_url,
                                    this,
                                    "Download Package Certificate",
                                    "Download Package Certificate"
                                )

                            }

                        } else {
                            bin.commonBtn1.root.visibility = View.GONE
                        }
                    }
//                    for (id in it.askedRecord) {
//                        if (id.status == "COMPLETE") {
//                            if (!id.nurseId.isNullOrEmpty()) {
//                                nurseId = id.nurseId
//                            }
//                        }
//                        break
//                    }
//                    println("======$nurseId")
//                    it.askedRecord.forEach {
//                        if (!it.nurseId.isNullOrEmpty()) {
//                            nurseId = it.nurseId ?: ""
//                        }
//
//                    }


                }
                /* val total_price: Double =
                     it.askedData.itemPrice.toDouble() - it.askedData.discountAmount.toDouble() - it.askedData.gstAmount.toDouble()
                 var price: Int = 0
                 val price_list = ArrayList<Int>()
                 it.askedRecord.get(0).inclusion.forEach {
                     price_list.add(it.price.toInt())
                     price = price_list.sum()
                 }
                 println("----itemppp$price")
                 if (it.askedData.homeVaccineFee.isEmpty()) {
                     item_price =
                         total_price + price.toDouble() + (0 * it.askedData.completedDose)
                     println("----item$item_price")
                     //item_price = it.askedData.itemPrice.toInt() - it.askedData.gstAmount.toDouble()
                 } else {
                     item_price =
                         total_price + price.toDouble() + (it.askedData.homeVaccineFee.toDouble() * it.askedData.completedDose)
                     println("----item$item_price")
                     //item_price = it.askedData.itemPrice.toInt() - it.askedData.gstAmount.toDouble()
                 }*/

                diff_year = getAge(it.askedData.userData[0].dob)
                bin.tvage.text = "${getAge(it.askedData.userData[0].dob)} years"

                if (it.askedData.order_type.equals("PACKAGE")) {
                    val dis_cal: Double =
                        it.askedData.itemPrice.toDouble() * it.askedData.discount.toDouble() / 100
                    var total_price: Double = it.askedData.itemPrice.toDouble() - dis_cal
                    bin.include7.tvTotal.text = "₹$total_price"
                    println("--tootal$total_price")

                    total_price = total_price - it.askedData.gstAmount.toDouble()

                    /* if (it.askedRecord.get(0).status.equals("COMPLETE")) {*/
                    /*val total_price1: Double =
                        it.askedData.itemPrice.toDouble() - dis_cal - it.askedData.gstAmount.toDouble()
                    println("-----tttt$total_price1")*/
                    var price: Int = 0
                    val price_list = ArrayList<Int>()
                    it.askedRecord.get(0).inclusion.forEach {
                        price_list.add(it.price.toInt())
                        price = price_list.sum()
                    }
                    println("----itemppp$price")
                    if (!it.askedData.homeVaccineFee.isEmpty()) {
                        item_price =
                            price.toDouble() + (it.askedItem.homeVaccinationFee.toDouble() * it.askedData.completedDose)
                        println("----item$item_price")

                    }
                    if (total_price < item_price) {
                        item_price = 0.0

                    } else {
                        item_price = String.format("%.2f", total_price - item_price).toDouble()
                        println("-----klklll$item_price")
                    }
                    //}
                    /*else {
                       val total_popup_price: Double =
                           it.askedData.itemPrice.toDouble() - it.askedData.discountAmount.toDouble() - it.askedData.gstAmount.toDouble()
                       item_price = total_popup_price
                   }*/

                    for (stt in it.askedRecord) {
                        status.add(
                            StatusPackageModel(
                                ageOfBaby = stt.ageOfBaby,
                                status = stt.status,
                                completeDate = stt.completeDate,
                                nurseName = stt.nurseName,
                                nurseId = stt.nurseId
                            )
                        )
                    }
                    println("-----stst$status")

                    bin.tvOrderId.text = "#${it.askedData.order_number}"
                    bin.include7.tvNameOrderDetails.text = it.askedData.itemName
                    bin.include7.textView123.visibility = View.VISIBLE
                    bin.include7.textView1231.visibility = View.INVISIBLE
                    val img = it.askedData.itemImage
                    if (img.isNotEmpty()) {
                        bin.include7.ivdummyimg.visibility = View.INVISIBLE
                        bin.include7.imageView36.visibility = View.VISIBLE
                        Picasso.get().load(img).into(bin.include7.imageView36)
                    } else {
                        bin.include7.ivdummyimg.visibility = View.VISIBLE
                        bin.include7.imageView36.visibility = View.INVISIBLE
                    }
                    if (it.askedData.itemPrice.isEmpty()) {
                        bin.include7.tvPriceOrderDetails.text = "₹ ${it.askedData.packagePrice}"
                        bin.include7.tvOfferPriceOrderDetails.visibility = View.INVISIBLE
                        bin.include7.divider19.visibility = View.GONE
                    } else {
                        bin.include7.divider19.visibility = View.VISIBLE
                        bin.include7.tvPriceOrderDetails.text = "₹ ${it.askedData.packagePrice}"
                        bin.include7.tvOfferPriceOrderDetails.text =
                            "₹ ${it.askedData.itemPrice}"
                    }
                    bin.include7.tvDesOrderDetails.text = it.askedData.description
                    bin.include7.tvIncludeVaaOrderDetails.text =
                        it.askedData.inclusiveVaccine.toString()
                    bin.include7.tvCompleteDoesOrderDetails.text =
                        "${it.askedData.completedDose} Complete Dosage"
                    bin.include7.tvPendingDoesOrderDetails.text =
                        "${it.askedData.pendingDose} Pending Dosage"
                    if (it.askedData.assignedNurse != null) {
                        if (from.equals("complete")) {
                            bin.include7.textView1871.visibility = View.VISIBLE
                            bin.include7.textView187.visibility = View.INVISIBLE
                            bin.include7.tvForOrderDetails.text = it.askedData.assignedNurse
                        } else {
                            bin.include7.textView1871.visibility = View.INVISIBLE
                            bin.include7.textView187.visibility = View.VISIBLE
                            bin.include7.tvFor1OrderDetails.text = it.askedData.assignedNurse
                        }

                    } else {
                        bin.include7.tvFor1OrderDetails.text = "NA"
                    }

                    bin.tvName.text = it.askedData.userData[0].fullName

                    bin.tvGender.text = it.askedData.userData[0].gender
                    bin.dobtext.text = it.askedData.userData[0].dob
                    bin.relationtext2.text = it.askedData.userData[0].relation
                    bin.tvMedical.text = it.askedData.userData[0].medicalCondition
                    if (it.askedData.userData[0].uploadVaccinationChart.isEmpty()) {
                        bin.uploadChatBtn.visibility = View.GONE
                    } else {
                        bin.uploadChatBtn.visibility = View.VISIBLE
                        val url = it.askedData.userData[0].uploadVaccinationChart
                        bin.uploadChatBtn.setOnClickListener {
                            //Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT).show()
                            startDownload(
                                url, this, "Vaccination chat", "Vaccination chat"
                            )
                        }
                    }
                    if (it.askedData.userData[0].anyReaction.isEmpty()) {
                        bin.tvAnyRePreviousDoes.text = "--"
                    } else {
                        bin.tvAnyRePreviousDoes.text = it.askedData.userData[0].anyReaction
                    }
                    bin.childLayout.visibility = View.GONE
                    if (it.askedData.userData[0].memberType.equals("KIDS")) {
                        bin.childLayout.visibility = View.VISIBLE
                        bin.tvdelivery.text =
                            "${it.askedData.userData[0].deliveryType} , ${it.askedData.userData[0].deliveryPeriod}"
                        bin.tvAnySeizures.text = it.askedData.userData[0].seizuresAtBirth
                        if (it.askedData.userData[0].nicuDetails.isEmpty()) {
                            bin.uploadChatBtn2.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn2.visibility = View.VISIBLE
                            val url = it.askedData.userData[0].nicuDetails
                            bin.uploadChatBtn2.setOnClickListener {
                                Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT)
                                    .show()
                                startDownload(
                                    url, this, "NICU Records Chat", "NICU Records Chat"
                                )
                            }
                        }
                        if (it.askedData.userData[0].bithHistory.isEmpty()) {
                            bin.uploadChatBtn3.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn3.visibility = View.VISIBLE
                            val url = it.askedData.userData[0].bithHistory
                            bin.uploadChatBtn3.setOnClickListener {
                                Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT)
                                    .show()
                                startDownload(
                                    url, this, "Birth History Chat", "Birth History Chat"
                                )
                            }
                        }
                        bin.tvMedicalDesorder.text = it.askedData.userData[0].medicalDisorder
                    }
                    bin.rcyDoes.visibility = View.INVISIBLE
                    bin.doesLay.mainDoesLay.visibility = View.VISIBLE
                    bin.tvSchedulHistory.visibility = View.VISIBLE
                    commonSetDoseVaccine(it.askedItem.doseInfo, status)
                    for (id in it.askedRecord) {
                        if (id.status == "COMPLETE") {
                            if (!id.nurseId.isNullOrEmpty()) {
                                nurseId = id.nurseId
                            }
                        }
                    }
                    println("======$nurseId")

                } else {
                    bin.commonBtn1.root.visibility = View.GONE
                    recode_list = it.askedRecord
                    vaccine_dose_list.clear()
                    val price: Double =
                        (it.askedData.itemPrice.toDouble() + it.askedData.homeVaccineFee.toDouble()) * it.askedData.noOfDose.toDouble()
                    val cal: Double = (price.toInt() * it.askedData.discount.toDouble() / 100)
                    val per: Double = price.toInt() - cal

                    if (!it.askedData.offerPrice.equals("")) {
                        val offer_price = per - it.askedData.offerPrice.toInt()
                        bin.include7.tvTotal.text = "₹ ${offer_price}"
                    } else {
                        bin.include7.tvTotal.text = "₹ ${per}"
                    }

                    for (does in it.askedData.doseRecord) {
                        vaccine_dose_list.add(
                            VaccineDoesModel(
                                does._id, does.doseNumber, does.timePeriod
                            )
                        )
                        println("------does$vaccine_dose_list")
                    }
                    var total_count: Int = 0
                    for (ii in it.askedRecord) {
                        if (ii.status.equals("COMPLETE")) {
                            nurseId = ii.nurseId ?: ""
                            total_count++
                            vaccine_dose_list.add(
                                VaccineDoesModel(
                                    ii.doseInfo.get(0)._id,
                                    ii.doseInfo.get(0).doseNumber,
                                    ii.doseInfo.get(0).timePeriod
                                )
                            )
                        }
                        println("----total$total_count")
                        println("------adddoes$vaccine_dose_list")
                        println("------nurseId$nurseId")
                    }

                    total_price_cancel_vaccine_pop =
                        it.askedData.itemPrice.toDouble() + it.askedData.homeVaccineFee.toDouble()
                    total_dis_cancel_vaccine_pop =
                        total_price_cancel_vaccine_pop * it.askedData.discount.toDouble() / 100
                    total_cancel_vaccine_pop =
                        total_price_cancel_vaccine_pop - total_dis_cancel_vaccine_pop
                    total_noofcount_cancel_vaccine_pop =
                        total_cancel_vaccine_pop * (vaccine_dose_list.size)
                    total_gst_amount_cancel_vaccine_pop =
                        total_noofcount_cancel_vaccine_pop * 100 / 105

                    total_selected_vaccine_cancel_vaccine_pop =
                        (it.askedData.itemPrice.toDouble() + it.askedData.homeVaccineFee.toDouble()) * total_count.toDouble()

                    if (total_gst_amount_cancel_vaccine_pop > total_selected_vaccine_cancel_vaccine_pop) {
                        item_price = String.format(
                            "%.1f",
                            (total_gst_amount_cancel_vaccine_pop - total_selected_vaccine_cancel_vaccine_pop)
                        ).toDouble()
                    } else if (total_gst_amount_cancel_vaccine_pop < total_selected_vaccine_cancel_vaccine_pop) {
                        item_price = 0.0
                    }
                    println("---iiiiiiiiihhh$item_price")

                    bin.rcyDoes.visibility = View.VISIBLE
                    bin.doesLay.mainDoesLay.visibility = View.INVISIBLE
                    bin.tvSchedulHistory.visibility = View.INVISIBLE
                    doesInfoVaccine_List.clear()
                    doesInfoVaccine_List.addAll(vaccine_dose_list)
                    bin.rcyDoes.layoutManager = LinearLayoutManager(this)
                    doesInfoVaccine_Adpater = OrderDetailsVaccineDose(
                        this, doesInfoVaccine_List, recode_list, is_nurse, bin.tvPreView
                    )
                    bin.rcyDoes.adapter = doesInfoVaccine_Adpater
                    doesInfoVaccine_Adpater!!.notifyDataSetChanged()
                    bin.tvOrderId.text = "#${it.askedData.order_number}"
                    bin.include7.tvNameOrderDetails.text = it.askedData.itemName
                    val img = it.askedData.itemImage
                    if (img.isNotEmpty()) {
                        bin.include7.ivdummyimg.visibility = View.INVISIBLE
                        bin.include7.imageView36.visibility = View.VISIBLE
                        Picasso.get().load(img).into(bin.include7.imageView36)
                    } else {
                        bin.include7.ivdummyimg.visibility = View.VISIBLE
                        bin.include7.imageView36.visibility = View.INVISIBLE
                    }
                    bin.include7.tvOfferPriceOrderDetails.visibility = View.GONE
                    bin.include7.divider19.visibility = View.GONE
                    bin.include7.tvPriceOrderDetails.text = "₹ ${it.askedData.itemPrice}"
                    bin.include7.tvDesOrderDetails.text = it.askedData.description
                    bin.include7.textView123.visibility = View.INVISIBLE
                    bin.include7.textView1231.visibility = View.VISIBLE
                    bin.include7.tvIncludeVaaOrderDetails.text = it.askedData.noOfDose
                    bin.include7.tvCompleteDoesOrderDetails.text =
                        "${it.askedData.completedDose} Complete Dosage"
                    bin.include7.tvPendingDoesOrderDetails.text =
                        "${it.askedData.pendingDose} Pending Dosage"
                    if (it.askedData.userData[0].anyReaction.isEmpty()) {
                        bin.tvAnyRePreviousDoes.text = "--"
                    } else {
                        bin.tvAnyRePreviousDoes.text = it.askedData.userData[0].anyReaction
                    }
                    if (it.askedData.assignedNurse != null) {
                        if (from.equals("complete")) {
                            bin.include7.textView1871.visibility = View.VISIBLE
                            bin.include7.textView187.visibility = View.INVISIBLE
                            bin.include7.tvForOrderDetails.text = it.askedData.assignedNurse
                        } else {
                            bin.include7.textView1871.visibility = View.INVISIBLE
                            bin.include7.textView187.visibility = View.VISIBLE
                            bin.include7.tvFor1OrderDetails.text = it.askedData.assignedNurse
                        }
                    } else {
                        bin.include7.tvFor1OrderDetails.text = "NA"
                    }
                    bin.tvName.text = it.askedData.userData[0].fullName
                    // bin.tvage.text = "0 year"
                    bin.tvGender.text = it.askedData.userData[0].gender
                    bin.dobtext.text = it.askedData.userData[0].dob
                    bin.relationtext2.text = it.askedData.userData[0].relation
                    bin.tvMedical.text = it.askedData.userData[0].medicalCondition
                    if (it.askedData.userData[0].uploadVaccinationChart.isEmpty()) {
                        bin.uploadChatBtn.visibility = View.GONE
                    } else {
                        bin.uploadChatBtn.visibility = View.VISIBLE
                        val url = it.askedData.userData[0].uploadVaccinationChart
                        bin.uploadChatBtn.setOnClickListener {
                            //Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT).show()
                            startDownload(
                                url, this, "Vaccination chat", "Vaccination chat"
                            )
                        }
                    }
                    if (it.askedData.userData[0].anyReaction.isEmpty()) {
                        bin.tvAnyRePreviousDoes.text = "--"
                    } else {
                        bin.tvAnyRePreviousDoes.text = it.askedData.userData[0].anyReaction
                    }
                    bin.childLayout.visibility = View.GONE
                    if (it.askedData.userData[0].memberType.equals("KIDS")) {
                        bin.childLayout.visibility = View.VISIBLE
                        bin.tvdelivery.text =
                            "${it.askedData.userData[0].deliveryType} , ${it.askedData.userData[0].deliveryPeriod}"
                        bin.tvAnySeizures.text = it.askedData.userData[0].seizuresAtBirth
                        if (it.askedData.userData[0].nicuDetails.isEmpty()) {
                            bin.uploadChatBtn2.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn2.visibility = View.VISIBLE
                            val url = it.askedData.userData[0].nicuDetails
                            bin.uploadChatBtn2.setOnClickListener {
                                //Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT).show()
                                startDownload(
                                    url, this, "NICU Records Chat", "NICU Records Chat"
                                )
                            }
                        }
                        if (it.askedData.userData[0].bithHistory.isEmpty()) {
                            bin.uploadChatBtn3.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn3.visibility = View.VISIBLE
                            val url = it.askedData.userData[0].bithHistory
                            bin.uploadChatBtn3.setOnClickListener {
                                // Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT).show()
                                startDownload(
                                    url, this, "Birth History Chat", "Birth History Chat"
                                )
                            }
                        }
                        bin.tvMedicalDesorder.text = it.askedData.userData[0].medicalDisorder
                    }
                }


            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseCancelOrder.observe(this) {
            if (it?.code == 200) {
                startActivity(
                    Intent(this, MainActivity::class.java).putExtra(
                        "back", "back_to_home"
                    )
                )
                finish()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    private fun apiCallingForOrderDetails() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onOrderDetails(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val orderId = intent.getStringExtra("orderId").toString()
        orderId1 = orderId
        val hashMap: HashMap<String, Any> = HashMap()
        println("---has$orderId")
        hashMap[MyConstants.korderId] = orderId
        return hashMap
    }

    private fun apiCallingForCancelOrder() {
        val userMap: HashMap<String, Any> = prepareDataForCancelOrder()
        viewModel.onCancelOrder(this, userMap)
    }

    private fun prepareDataForCancelOrder(): HashMap<String, Any> {
        val orderId = intent.getStringExtra("orderId").toString()
        val hashMap: HashMap<String, Any> = HashMap()
        println("---has$orderId")
        hashMap[MyConstants.korderId] = orderId
        hashMap[MyConstants.krefundAmount] = item_price
        return hashMap
    }

    var days: ArrayList<OrderDetailsPackageDoesModel> = ArrayList()
    var weeks: ArrayList<OrderDetailsPackageDoesModel> = ArrayList()
    var months: ArrayList<OrderDetailsPackageDoesModel> = ArrayList()
    var years: ArrayList<OrderDetailsPackageDoesModel> = ArrayList()
    private fun commonSetDoseVaccine(
        doselist: ArrayList<DoseInfo>,
        askedRecord: ArrayList<StatusPackageModel>,
    ) {

        val hashMap: HashMap<String, String> = HashMap()
        for (doseInfo in doselist) {
            for (timeDays in doseInfo.dose) {
                if (hashMap.containsKey(timeDays)) {
                    hashMap.put(timeDays, hashMap[timeDays] + "," + doseInfo.vaccineName)
                } else {
                    hashMap.put(timeDays, doseInfo.vaccineName)
                }
            }
        }

        for ((key, value) in hashMap.entries) {
            commonsSet.add(
                OrderDetailsPackageDoesModel(
                    key, value, StatusPackageModel("", "", "", "", "")

                )
            )
            commonsSet.sortBy { it.dose }
        }
        println("----cmm1${Gson().toJson(commonsSet)}")
        var commonsSet2: ArrayList<OrderDetailsPackageDoesModel> = ArrayList()

        try {

            var isAddedStatus = true

            for (datt in commonsSet) {
                for (statusList1 in askedRecord) {
                    if (datt.dose.equals(statusList1.ageOfBaby) && statusList1.status.equals(
                            "COMPLETE"
                        )
                    ) {
                        nurseId = statusList1.nurseId.toString()
                        isAddedStatus = false
                        val time_slipt1 = datt.dose!!.replace("  ", " ")
                        val time_slipt = time_slipt1.trim().split(" ")
                        if (time_slipt[1].trim().equals("days")) {
                            if (time_slipt[0].toInt() < 10) {
                                days.add(
                                    OrderDetailsPackageDoesModel(
                                        "0${datt.dose}", datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            } else {
                                days.add(
                                    OrderDetailsPackageDoesModel(
                                        "${datt.dose}", datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            }

                            days.sortBy { it.dose }
                        } else if (time_slipt[1].trim().equals("months")) {
                            if (time_slipt[0].toInt() < 10) {
                                months.add(
                                    OrderDetailsPackageDoesModel(
                                        "0${datt.dose}", datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            } else months.add(
                                OrderDetailsPackageDoesModel(
                                    datt.dose, datt.vaccineName, StatusPackageModel(
                                        statusList1.ageOfBaby,
                                        statusList1.status,
                                        statusList1.completeDate,
                                        statusList1.nurseName,
                                        statusList1.nurseId
                                    )
                                )
                            )
                            months.sortBy { it.dose }
                        } else if (time_slipt[1].trim().lowercase().equals("month")) {
                            if (time_slipt[0].toInt() < 10) {
                                months.add(
                                    OrderDetailsPackageDoesModel(
                                        "0${datt.dose}", datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            } else {
                                months.add(
                                    OrderDetailsPackageDoesModel(
                                        datt.dose, datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            }
                            months.sortBy { it.dose }
                        } else if (time_slipt[1].trim().equals("years")) {
                            if (time_slipt[0].toInt() < 10) {
                                years.add(
                                    OrderDetailsPackageDoesModel(
                                        "0${datt.dose}", datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            } else {
                                years.add(
                                    OrderDetailsPackageDoesModel(
                                        datt.dose, datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            }
                            years.sortBy { it.dose }
                        } else if (time_slipt[1].trim().lowercase().equals("weeks")) {
                            if (time_slipt[0].toInt() < 10) {
                                weeks.add(
                                    OrderDetailsPackageDoesModel(
                                        "0${datt.dose}", datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            } else {
                                weeks.add(
                                    OrderDetailsPackageDoesModel(
                                        datt.dose, datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            }
                            weeks.sortBy { it.dose }
                        } else if (time_slipt[1].trim().equals("Week")) {
                            if (time_slipt[0].toInt() < 10) {
                                weeks.add(
                                    OrderDetailsPackageDoesModel(
                                        "0${datt.dose}", datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            } else {
                                weeks.add(
                                    OrderDetailsPackageDoesModel(
                                        datt.dose, datt.vaccineName, StatusPackageModel(
                                            statusList1.ageOfBaby,
                                            statusList1.status,
                                            statusList1.completeDate,
                                            statusList1.nurseName,
                                            statusList1.nurseId
                                        )
                                    )
                                )
                            }
                            weeks.sortBy { it.dose }
                        }
                        /*  commonsSet2.add(
                              OrderDetailsPackageDoesModel(
                                  datt.dose, datt.vaccineName, StatusPackageModel(
                                      statusList1.ageOfBaby,
                                      statusList1.status,
                                      statusList1.completeDate,
                                      statusList1.nurseName
                                  )
                              )
                          )*/
                    }

                }
                println("----cmm2$commonsSet2")
                if (isAddedStatus) {
                    val time_slipt1 = datt.dose!!.replace("  ", " ")
                    val time_slipt = time_slipt1!!.trim().split(" ")

                    if (time_slipt[1].trim().equals("days")) {
                        if (time_slipt[0].toInt() < 10) {
                            days.add(
                                OrderDetailsPackageDoesModel(
                                    "0${datt.dose}",
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        } else {
                            days.add(
                                OrderDetailsPackageDoesModel(
                                    datt.dose,
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        }
                        days.sortBy { it.dose }
                        println("----dydyydy${Gson().toJson(days)}")
                    } else if (time_slipt[1].trim().equals("months")) {
                        if (time_slipt[0].toInt() < 10) {
                            months.add(
                                OrderDetailsPackageDoesModel(
                                    "0${datt.dose}",
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        } else months.add(
                            OrderDetailsPackageDoesModel(
                                datt.dose,
                                datt.vaccineName,
                                StatusPackageModel("", "", "", "", "")
                            )
                        )
                        months.sortBy { it.dose }
                    } else if (time_slipt[1].trim().lowercase().equals("month")) {
                        if (time_slipt[0].toInt() < 10) {
                            months.add(
                                OrderDetailsPackageDoesModel(
                                    "0${datt.dose}",
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        } else {
                            months.add(
                                OrderDetailsPackageDoesModel(
                                    datt.dose,
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        }
                        months.sortBy { it.dose }
                    } else if (time_slipt[1].trim().equals("years")) {
                        if (time_slipt[0].toInt() < 10) {
                            years.add(
                                OrderDetailsPackageDoesModel(
                                    "0${datt.dose}",
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        } else
                            years.add(
                                OrderDetailsPackageDoesModel(
                                    datt.dose,
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        years.sortBy { it.dose }
                    } else if (time_slipt[1].trim().lowercase().equals("weeks")) {
                        if (time_slipt[0].toInt() < 10) {
                            weeks.add(
                                OrderDetailsPackageDoesModel(
                                    "0${datt.dose}",
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        } else {
                            weeks.add(
                                OrderDetailsPackageDoesModel(
                                    datt.dose,
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        }
                        weeks.sortBy { it.dose }
                    } else if (time_slipt[1].trim().equals("Week")) {
                        if (time_slipt[0].toInt() < 10) {
                            weeks.add(
                                OrderDetailsPackageDoesModel(
                                    "0${datt.dose}",
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        } else {
                            weeks.add(
                                OrderDetailsPackageDoesModel(
                                    datt.dose,
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        }
                        weeks.sortBy { it.dose }
                    } else if (time_slipt[1].trim().equals("year", true)) {
                        if (time_slipt[0].toInt() < 10) {
                            years.add(
                                OrderDetailsPackageDoesModel(
                                    "0${datt.dose}",
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        } else {
                            years.add(
                                OrderDetailsPackageDoesModel(
                                    datt.dose,
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "", "")
                                )
                            )
                        }
                        weeks.sortBy { it.dose }
                    }
                    /*else {
                            commonsSet2.add(
                                OrderDetailsPackageDoesModel(
                                    datt.dose,
                                    datt.vaccineName,
                                    StatusPackageModel("", "", "", "")
                                )
                            )
                        }*/
                }
//                commonsSet2.addAll(days.getDistinctList())
//                println("----dd${Gson().toJson(days)}")
//                commonsSet2.addAll(weeks.getDistinctList())
//                println("----wwwddjf${Gson().toJson(weeks)}")
//                commonsSet2.addAll(months.getDistinctList())
//                println("----m$${Gson().toJson(months)}")
//                commonsSet2.addAll(years.getDistinctList())
//                println("----y${Gson().toJson(years)}")

                /*commonsSet2.add(
                        OrderDetailsPackageDoesModel(
                            datt.dose,
                            datt.vaccineName,
                            StatusPackageModel("", "", "", "")
                        )
                    )*/

//                val hashset = HashSet<OrderDetailsPackageDoesModel>()
//                hashset.addAll(commonsSet2)
//                commonsSet2.clear()
//                commonsSet2.addAll(hashset)
//                commonsSet2.sortBy { it.dose }

                isAddedStatus = true
                println("----cmm3${Gson().toJson(commonsSet2)}")


            }

            commonsSet2.addAll(days.getDistinctList())
            println("----dd${Gson().toJson(days)}")
            commonsSet2.addAll(weeks.getDistinctList())
            println("----wwwddjf${Gson().toJson(weeks)}")
            commonsSet2.addAll(months.getDistinctList())
            println("----m$${Gson().toJson(months)}")
            commonsSet2.addAll(years.getDistinctList())
            println("----y${Gson().toJson(years)}")

        } catch (e: Exception) {
            e.printStackTrace()
        }



        bin.doesLay.rcyvaccinedose.layoutManager = LinearLayoutManager(this)
        adpterDose = DoseDetailsAdapter(
            this, commonsSet2, is_nurse, bin.tvPreView
        )
        bin.doesLay.rcyvaccinedose.adapter = adpterDose
        adpterDose!!.notifyDataSetChanged()

        Log.e("laxman", "commonSetDoseVaccine:${Gson().toJson(commonsSet)} ")

    }

    private fun List<OrderDetailsPackageDoesModel>.getDistinctList(): List<OrderDetailsPackageDoesModel> {
        val list = mutableListOf<OrderDetailsPackageDoesModel>()
        val distinctDoses = mutableSetOf<String>()

        for (position in count() - 1 downTo 0) {
            val currentItem = get(position)
            if (currentItem.dose !in distinctDoses) {
                list.add(0, currentItem)
                distinctDoses.add(currentItem.dose ?: "")
            }
        }
        Log.d("LIST_DISTINCTS", Gson().toJson(list))
        return list
    }
}