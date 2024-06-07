package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.KidsGrowth

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityKidsGrowthRecordBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Growth.GrowthRecordAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SpinnerAdapterBrands
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.KidsGrowths.AskedRequest
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bountybunch.helper.startDownload
import com.catalyist.helper.ErrorUtil
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate


class KidsGrowthRecordActivity : AppCompatActivity() {
    lateinit var bin: ActivityKidsGrowthRecordBinding
    private var adpterGrowth: GrowthRecordAdapter? = null
    private lateinit var viewModel: ViewModalLogin
    private var familyMemberList = ArrayList<String>()
    private var familymemberHashMapID: HashMap<String, String> = HashMap()
    private var memberId: String = ""
    private var growthsList = ArrayList<AskedRequest>()
    // private var chart: LineChart? = null

    // variable for our bar chart
    var barChart: LineChart? = null

    // variable for our bar data.
    var barData: LineData? = null

    // variable for our bar data set.
    var barDataSet: LineDataSet? = null

    // array list for storing entries.
    var barEntriesArrayList: ArrayList<Entry>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityKidsGrowthRecordBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        bin.include10.tvTittle.text = "Kids Growth Record"
        familyMemberList.add(0, "Select Member")
        apiCallingForFamilyMemberList()
        barChart = bin.lineChart
        /// getBarEntries()



    }

    private fun lstnr() {
        bin.include10.ivBack.setOnClickListener {
            finish()
        }
        bin.tvMember.setOnClickListener {
            bin.memberSpinner.performClick()
        }

    }

    private fun getBarEntries() {
        barEntriesArrayList = ArrayList()
        var xAxix: Float = 0f
        var yAxix: Float = 0f
        for (growth in growthsList) {
            xAxix += 2f
            yAxix = growth.hight.toFloat()
            barEntriesArrayList!!.add(BarEntry(xAxix, yAxix))
        }
        barDataSet = LineDataSet(barEntriesArrayList, "Growth Record (Vaccine Buddy)")
        barDataSet!!.setDrawValues(false)
        barDataSet!!.setDrawFilled(true)
        barDataSet!!.lineWidth = 3f
        barDataSet!!.fillColor = R.color.gray
        barDataSet!!.fillAlpha = R.color.red
        barData = LineData(barDataSet)
        barChart!!.data = barData
        barDataSet!!.setColors(*ColorTemplate.MATERIAL_COLORS)
        barDataSet!!.valueTextColor = Color.BLACK
        barDataSet!!.valueTextSize = 16f
        barChart!!.description.isEnabled = false

    }

    private fun spinnerForMemberList(memberList: List<String>) {
        val adapter =
            SpinnerAdapterBrands(
                this,
                R.layout.spinner_dropdown_item, memberList
            )
        bin.memberSpinner.adapter = adapter
        bin.memberSpinner.onItemSelectedListener = onItemSelectedStateListenerMemberList

    }

    ///////////spinner program MemberList////
    private var onItemSelectedStateListenerMemberList: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            adapterView: AdapterView<*>?,
            view: View,
            postion: Int,
            l: Long
        ) {
            val itemSelected = adapterView!!.getItemAtPosition(postion) as String
            bin.tvMember.text = itemSelected
            memberId = familymemberHashMapID.get(bin.memberSpinner.selectedItem.toString()) ?: ""
            println("---memberId$memberId")
            if (bin.memberSpinner.selectedItemPosition > 0) {
                apiCallingForKidsGrowths()
            }
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseFamilyMemberList.observe(this) {
            if (it?.code == 200) {
                it.askedData.forEach {
                    if (it.memberType.equals("KIDS")) {
                        familyMemberList.add("${it.fullName} (${it.relation})")
                        familymemberHashMapID.put("${it.fullName} (${it.relation})", it._id)
                    }
                }
                spinnerForMemberList(familyMemberList)
                println("-----tttttt$familyMemberList")
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseKidsGrowthsRecord.observe(this) {
            if (it?.code == 200) {
                bin.mainlay.visibility = View.VISIBLE
                bin.tvName.text = it.memberData.fullName
                bin.tvGender.text = it.memberData.gender
                bin.dobtext.text = it.memberData.dob
                bin.tvage.text = "${getAge(it.memberData.dob)} year"
                bin.relationtext2.text = it.memberData.relation
                bin.tvMedical.text = it.memberData.medicalCondition
                if (it.memberData.uploadVaccinationChart.isEmpty()) {
                    bin.uploadChatBtn.visibility = View.GONE
                } else {
                    bin.uploadChatBtn.visibility = View.VISIBLE
                    val url = it.memberData.uploadVaccinationChart
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
                if (it.memberData.anyReaction.isEmpty()) {
                    bin.tvAnyRePreviousDoes.text = "--"
                } else {
                    bin.tvAnyRePreviousDoes.text = it.memberData.anyReaction
                }
                bin.tvdelivery.text =
                    "${it.memberData.deliveryType} , ${it.memberData.deliveryPeriod}"
                bin.tvAnySeizures.text = it.memberData.seizuresAtBirth
                if (it.memberData.nicuDetails.isEmpty()) {
                    bin.uploadChatBtn2.visibility = View.GONE
                } else {
                    bin.uploadChatBtn2.visibility = View.VISIBLE
                    val url = it.memberData.nicuDetails
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
                if (it.memberData.bithHistory.isEmpty()) {
                    bin.uploadChatBtn3.visibility = View.GONE
                } else {
                    bin.uploadChatBtn3.visibility = View.VISIBLE
                    val url = it.memberData.bithHistory
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
                bin.tvMedicalDesorder.text = it.memberData.medicalDisorder
                growthsList.clear()
                growthsList.addAll(it.askedRequests)
                if (growthsList.isEmpty()){
                    bin.textView215.visibility = View.GONE
                    bin.chart.visibility = View.GONE
                    bin.lineChart.visibility = View.GONE
                }else{
                    println("====$growthsList")
                    bin.lineChart.visibility = View.VISIBLE
                    bin.textView215.visibility = View.VISIBLE
                    bin.chart.visibility = View.VISIBLE
                    bin.rcygrowth.layoutManager =
                        LinearLayoutManager(this)
                    adpterGrowth = GrowthRecordAdapter(this, growthsList)
                    bin.rcygrowth.adapter = adpterGrowth
                    adpterGrowth!!.notifyDataSetChanged()
                    getBarEntries()
                }

            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForFamilyMemberList() {
        viewModel.onFamilyMemberList(this)
    }

    private fun apiCallingForKidsGrowths() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onKidsGrowthsRecord(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kmemberId] = memberId
        return hashMap
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}