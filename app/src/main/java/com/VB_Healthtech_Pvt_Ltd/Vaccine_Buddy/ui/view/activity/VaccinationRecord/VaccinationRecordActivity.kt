package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccinationRecord

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityVaccinationRecordBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.MemberRecord.PackageDoesMemberRecordAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecordModel.StatusMemberRecordModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.MemberRecord.VaccineMemberRecordAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SpinnerAdapterBrands
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord.Record
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecordModel.PackageMemberRecordModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecordModel.VaccineMemberRecordModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.Toaster
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord.AskedRequest
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord.MemberRecordRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetailsPackageDoesModel
import com.bountybunch.helper.startDownload
import com.catalyist.helper.ErrorUtil
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class VaccinationRecordActivity : AppCompatActivity() {
    lateinit var bin: ActivityVaccinationRecordBinding
    private var adpterDose: PackageDoesMemberRecordAdapter? = null
    private var adpterVaccineDose: VaccineMemberRecordAdapter? = null
    private var familyMemberList = ArrayList<String>()
    private var familymemberHashMap: HashMap<String, String> = HashMap()
    private var familymemberHashMapMemberType: HashMap<String, String> = HashMap()
    private var memberId: String = ""
    private var memberType: String = ""
    private var memberRecordList = ArrayList<String>()
    private var mRecord_Id_HashMap: HashMap<String, String> = HashMap()
    private var mRecord_OrderType_HashMap: HashMap<String, String> = HashMap()
    private var mRecordId: String = ""
    private var mRecordOrderType: String = ""
    private lateinit var viewModel: ViewModalLogin
    private var commonsSet: ArrayList<PackageMemberRecordModel> = ArrayList()
    private var commonsSet1: ArrayList<PackageMemberRecordModel> = ArrayList()
    private var vaccineMemberRecordList = ArrayList<VaccineMemberRecordModel>()
    private var testList = ArrayList<AskedRequest>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityVaccinationRecordBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        bin.vaccination.tvTittle.text = "Vaccination Record"
        familyMemberList.add(0, "Select Member")

        apiCallingForFamilyMemberList()
//        bin.does.rcyvaccinedose.layoutManager =
//            LinearLayoutManager(this)
//        adpterDose = DoseDetailsAdapter(this)
//        bin.does.rcyvaccinedose.adapter = adpterDose
//        adpterDose!!.notifyDataSetChanged()
    }

    private fun lstnr() {
        bin.vaccination.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.tvMember.setOnClickListener {
            bin.memberSpinner.performClick()
        }
        bin.tvCustome.setOnClickListener {
            bin.customeSpinner.performClick()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun spinnerForMemberList(memberList: List<String>) {
        val adapter =
            SpinnerAdapterBrands(
                this,
                R.layout.spinner_dropdown_item, memberList
            )
        bin.memberSpinner.adapter = adapter
        // binding.spinner2.setSelection(0, false)
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
            memberId = familymemberHashMap[bin.memberSpinner.selectedItem].toString()
            println("---memberId$memberId")
            memberType =
                familymemberHashMapMemberType.get(bin.memberSpinner.selectedItem.toString())
                    ?: ""
            println("---memberType$memberType")
            if (bin.memberSpinner.selectedItemPosition > 0) {
                memberRecordList.clear()
                memberRecordList.add(0, "Select Order")
                apiCallingForMemberRecordList()
                bin.tvCustome.visibility = View.VISIBLE
                bin.customeSpinner.visibility = View.INVISIBLE
                bin.layout.visibility = View.GONE

            } else {
                bin.tvCustome.visibility = View.GONE
                bin.customeSpinner.visibility = View.GONE

            }
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }

    private fun spinnerForMemberRecordList(memberRecordList: List<String>) {
        val adapter =
            SpinnerAdapterBrands(
                this,
                R.layout.spinner_dropdown_item, memberRecordList
            )
        bin.customeSpinner.adapter = adapter
        bin.customeSpinner.onItemSelectedListener = onItemSelectedStateListenerMemberRecordList

    }

    ///////////spinner program MemberRecordList////
    private var onItemSelectedStateListenerMemberRecordList: AdapterView.OnItemSelectedListener =
        object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                postion: Int,
                l: Long
            ) {
                val itemSelected = adapterView!!.getItemAtPosition(postion) as String
                mRecordOrderType =
                    mRecord_OrderType_HashMap[bin.customeSpinner.selectedItem].toString()
                bin.tvCustome.text = itemSelected
                mRecordId = mRecord_Id_HashMap.get(bin.customeSpinner.selectedItem.toString()) ?: ""
                println("-----mRecordId$mRecordId")
                println("-----mRecordOrderType$mRecordOrderType")
                if (bin.customeSpinner.selectedItemPosition > 0) {
                    bin.layout.visibility = View.VISIBLE
                    mRecordOrderType = testList.get(postion - 1).order_type
                    if (mRecordOrderType == "PACKAGE") {
                        bin.textView123.visibility = View.VISIBLE
                        bin.textView1231.visibility = View.INVISIBLE
                        val img: String = testList.get(postion - 1).itemImage
                        println("=========$img")
                        if (img.isNotEmpty()) {
                            Picasso.get().load(img).into(bin.ivmain)
                            bin.ivmain.visibility = View.VISIBLE
                            bin.ivdummy.visibility = View.INVISIBLE

                        } else {
                            bin.ivdummy.visibility = View.VISIBLE
                            bin.ivmain.visibility = View.INVISIBLE
                        }
                        bin.tvOrderName.text = testList.get(postion - 1).itemName
                        val date = testList.get(postion - 1).createdAt
                        val convert_date = setFormatDate(date)
                        bin.tvOrderDate.text = convert_date
                        bin.tvOrderDes.text = testList.get(postion - 1).description
                        if (testList.get(postion - 1).itemPrice.isEmpty()) {
                            bin.tvOrderprice.text = "₹ ${testList.get(postion - 1).packagePrice}"
                            bin.tvOrderOfferprice.visibility = View.GONE
                            bin.divider14.visibility = View.GONE
                        } else {
                            bin.tvOrderOfferprice.visibility = View.VISIBLE
                            bin.divider14.visibility = View.VISIBLE
                            bin.tvOrderprice.text = "₹ ${testList.get(postion - 1).packagePrice}"
                            bin.tvOrderOfferprice.text =
                                "₹ ${testList.get(postion - 1).itemPrice}"
                        }
                        bin.tvOrderIncludeVaccine.text =
                            testList.get(postion - 1).inclusiveVaccine.toString()
                        bin.tvOrderPendingDoes.text =
                            "${testList.get(postion - 1).pendingDose.toString()} Pending Dosage"
                        bin.tvOrderCompleteDoes.text =
                            "${testList.get(postion - 1).completedDose.toString()} complete Dosage"
                        bin.tvVaccineOrderName.text =
                            testList.get(postion - 1).userData.get(0).fullName
                        val from_or =
                            "${testList.get(postion - 1).slot.get(0).from} - ${
                                testList.get(postion - 1).slot.get(
                                    0
                                ).to
                            }"
                        val slot_date = setFormatDate(testList.get(postion - 1).slotDate)
                        bin.tvVaccineOrderDateTime.text =
                            "${slot_date} , ${testList.get(postion - 1).slotDay} , ${from_or}"
                        if (testList.get(postion - 1).assignedNurse != null) {
                            bin.textView19111.visibility = View.GONE
                            bin.textView191.visibility = View.VISIBLE
                            bin.tvVaccineOrderDoneBy.text =
                                testList.get(postion - 1).assignedNurse
                        } else {
                            bin.textView19111.visibility = View.VISIBLE
                            bin.textView191.visibility = View.INVISIBLE
                            bin.tvVaccineOrderDoneBy.text = "PENDING"
                        }
                        bin.tvName.text = testList.get(postion - 1).userData[0].fullName
                        bin.tvGender.text = testList.get(postion - 1).userData[0].gender
                        bin.dobtext.text = testList.get(postion - 1).userData[0].dob
                        bin.relationtext2.text = testList.get(postion - 1).userData[0].relation
                        bin.tvMedical.text = testList.get(postion - 1).userData[0].medicalCondition
                        bin.tvage.text = "${getAge(testList.get(postion - 1).userData[0].dob)} year"
                        if (testList.get(postion - 1).userData[0].uploadVaccinationChart.isEmpty()) {
                            bin.uploadChatBtn.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn.visibility = View.VISIBLE
                            val url = testList.get(postion - 1).userData[0].uploadVaccinationChart
                            bin.uploadChatBtn.setOnClickListener {
                                Toast.makeText(
                                    this@VaccinationRecordActivity,
                                    "Downloading.....",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startDownload(
                                    url,
                                    this@VaccinationRecordActivity,
                                    "Vaccination chat",
                                    "Vaccination chat"
                                )
                            }
                        }
                        if (testList.get(postion - 1).userData[0].anyReaction.isEmpty()) {
                            bin.tvAnyRePreviousDoes.text = "--"
                        } else {
                            bin.tvAnyRePreviousDoes.text =
                                testList.get(postion - 1).userData[0].anyReaction
                        }
                        bin.childLayout.visibility = View.GONE
                        if (testList.get(postion - 1).userData[0].memberType.equals("KIDS")) {
                            bin.childLayout.visibility = View.VISIBLE
                            bin.tvdelivery.text =
                                "${testList.get(postion - 1).userData[0].deliveryType} , ${
                                    testList.get(
                                        postion - 1
                                    ).userData[0].deliveryPeriod
                                }"
                            bin.tvAnySeizures.text =
                                testList.get(postion - 1).userData[0].seizuresAtBirth
                            if (testList.get(postion - 1).userData[0].nicuDetails.isEmpty()) {
                                bin.uploadChatBtn2.visibility = View.GONE
                            } else {
                                bin.uploadChatBtn2.visibility = View.VISIBLE
                                val url = testList.get(postion - 1).userData[0].nicuDetails
                                bin.uploadChatBtn2.setOnClickListener {
                                    Toast.makeText(
                                        this@VaccinationRecordActivity,
                                        "Downloading.....",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startDownload(
                                        url,
                                        this@VaccinationRecordActivity,
                                        "NICU Records Chat",
                                        "NICU Records Chat"
                                    )
                                }
                            }
                            if (testList.get(postion - 1).userData[0].bithHistory.isEmpty()) {
                                bin.uploadChatBtn3.visibility = View.GONE
                            } else {
                                bin.uploadChatBtn3.visibility = View.VISIBLE
                                val url = testList.get(postion - 1).userData[0].bithHistory
                                bin.uploadChatBtn3.setOnClickListener {
                                    Toast.makeText(
                                        this@VaccinationRecordActivity,
                                        "Downloading.....",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startDownload(
                                        url,
                                        this@VaccinationRecordActivity,
                                        "Birth History Chat",
                                        "Birth History Chat"
                                    )
                                }
                            }
                            bin.tvMedicalDesorder.text =
                                testList.get(postion - 1).userData[0].medicalDisorder

                        }
                        commonSetDoseVaccine(testList.get(postion - 1).record)
                        println("=======${testList.get(postion - 1).record}")
                        bin.does.mainDoesLay.visibility = View.VISIBLE
                        bin.rcyDoes.visibility = View.GONE
                    } else {
                        bin.does.mainDoesLay.visibility = View.GONE
                        bin.rcyDoes.visibility = View.VISIBLE
                        vaccineMemberRecordList.clear()
                        testList.get(postion - 1).record.forEach {
                            if (it.doseInfo.isNotEmpty()) {
                                vaccineMemberRecordList.add(
                                    VaccineMemberRecordModel(
                                        it.status ?: "",
                                        it.doseInfo.get(0)._id ?: "",
                                        it.doseInfo[0].doseNumber ?: "",
                                        it.doseInfo[0].timePeriod ?: ""
                                    )
                                )
                            }
                        }


                        println("------vaccineMemberRecordList$vaccineMemberRecordList")
                        bin.rcyDoes.layoutManager =
                            LinearLayoutManager(this@VaccinationRecordActivity)
                        adpterVaccineDose = VaccineMemberRecordAdapter(
                            this@VaccinationRecordActivity,
                            vaccineMemberRecordList
                        )
                        bin.rcyDoes.adapter = adpterVaccineDose
                        adpterVaccineDose!!.notifyDataSetChanged()
                        bin.textView123.visibility = View.INVISIBLE
                        bin.textView1231.visibility = View.VISIBLE
                        val img: String = testList.get(postion - 1).itemImage
                        if (img != null && img.length > 0) {
                            Picasso.get().load(img).into(bin.ivmain)
                            bin.ivmain.visibility = View.VISIBLE
                            bin.ivdummy.visibility = View.INVISIBLE

                        } else {
                            bin.ivdummy.visibility = View.VISIBLE
                            bin.ivmain.visibility = View.INVISIBLE
                        }
                        bin.tvOrderName.text = testList.get(postion - 1).itemName
                        val date = testList.get(postion - 1).createdAt
                        val convert_date = setFormatDate(date)
                        bin.tvOrderDate.text = convert_date
                        bin.tvOrderDes.text = testList.get(postion - 1).description
                        bin.tvOrderprice.text =
                            "₹ ${testList.get(postion - 1).itemPrice}"
                        bin.divider14.visibility = View.GONE
                        bin.tvOrderOfferprice.visibility = View.GONE
                        bin.tvOrderIncludeVaccine.text =
                            testList.get(postion - 1).noOfDose
                        bin.tvOrderPendingDoes.text =
                            "${testList.get(postion - 1).pendingDose.toString()} Pending Dosage"
                        bin.tvOrderCompleteDoes.text =
                            "${testList.get(postion - 1).completedDose.toString()} complete Dosage"
                        bin.tvVaccineOrderName.text =
                            testList.get(postion - 1).userData.get(0).fullName
                        val from_or =
                            "${testList.get(postion - 1).slot.get(0).from} - ${
                                testList.get(postion - 1).slot.get(
                                    0
                                ).to
                            }"
                        val slot_date = setFormatDate(testList.get(postion - 1).slotDate)
                        bin.tvVaccineOrderDateTime.text =
                            "${slot_date} , ${testList.get(postion - 1).slotDay} , ${from_or}"
                        if (testList.get(postion - 1).assignedNurse != null) {
                            bin.textView19111.visibility = View.GONE
                            bin.textView191.visibility = View.VISIBLE
                            bin.tvVaccineOrderDoneBy.text =
                                testList.get(postion - 1).assignedNurse
                        } else {
                            bin.textView19111.visibility = View.VISIBLE
                            bin.textView191.visibility = View.INVISIBLE
                            bin.tvVaccineOrderDoneBy.text = "PENDING"
                        }
                        bin.tvName.text = testList.get(postion - 1).userData[0].fullName
                        bin.tvGender.text = testList.get(postion - 1).userData[0].gender
                        bin.dobtext.text = testList.get(postion - 1).userData[0].dob
                        bin.relationtext2.text = testList.get(postion - 1).userData[0].relation
                        bin.tvMedical.text = testList.get(postion - 1).userData[0].medicalCondition
                        bin.tvage.text = "${getAge(testList.get(postion - 1).userData[0].dob)} year"
                        if (testList.get(postion - 1).userData[0].uploadVaccinationChart.isEmpty()) {
                            bin.uploadChatBtn.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn.visibility = View.VISIBLE
                            val url = testList.get(postion - 1).userData[0].uploadVaccinationChart
                            bin.uploadChatBtn.setOnClickListener {
                                //    Toast.makeText(this@VaccinationRecordActivity, "Downloading.....", Toast.LENGTH_SHORT).show()
                                startDownload(
                                    url,
                                    this@VaccinationRecordActivity,
                                    "Vaccination chat",
                                    "Vaccination chat"
                                )
                            }
                        }
                        if (testList.get(postion - 1).userData[0].anyReaction.isEmpty()) {
                            bin.tvAnyRePreviousDoes.text = "--"
                        } else {
                            bin.tvAnyRePreviousDoes.text =
                                testList.get(postion - 1).userData[0].anyReaction
                        }
                        bin.childLayout.visibility = View.GONE
                        if (testList.get(postion - 1).userData[0].memberType.equals("KIDS")) {
                            bin.childLayout.visibility = View.VISIBLE
                            bin.tvdelivery.text =
                                "${testList.get(postion - 1).userData[0].deliveryType} , ${
                                    testList.get(
                                        postion - 1
                                    ).userData[0].deliveryPeriod
                                }"
                            bin.tvAnySeizures.text =
                                testList.get(postion - 1).userData[0].seizuresAtBirth
                            if (testList.get(postion - 1).userData[0].nicuDetails.isEmpty()) {
                                bin.uploadChatBtn2.visibility = View.GONE
                            } else {
                                bin.uploadChatBtn2.visibility = View.VISIBLE
                                val url = testList.get(postion - 1).userData[0].nicuDetails
                                bin.uploadChatBtn2.setOnClickListener {
                                    Toast.makeText(
                                        this@VaccinationRecordActivity,
                                        "Downloading.....",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startDownload(
                                        url,
                                        this@VaccinationRecordActivity,
                                        "NICU Records Chat",
                                        "NICU Records Chat"
                                    )
                                }
                            }
                            if (testList.get(postion - 1).userData[0].bithHistory.isEmpty()) {
                                bin.uploadChatBtn3.visibility = View.GONE
                            } else {
                                bin.uploadChatBtn3.visibility = View.VISIBLE
                                val url = testList.get(postion - 1).userData[0].bithHistory
                                bin.uploadChatBtn3.setOnClickListener {
                                    Toast.makeText(
                                        this@VaccinationRecordActivity,
                                        "Downloading.....",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startDownload(
                                        url,
                                        this@VaccinationRecordActivity,
                                        "Birth History Chat",
                                        "Birth History Chat"
                                    )
                                }
                            }
                            bin.tvMedicalDesorder.text =
                                testList.get(postion - 1).userData[0].medicalDisorder
                        }
                    }

                } else {
                    bin.layout.visibility = View.GONE
                }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }


    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseFamilyMemberList.observe(this) {
            if (it?.code == 200) {
                it.askedData.forEach {
                    familyMemberList.add("${it.fullName}(${it.relation})")
                    familymemberHashMap.put("${it.fullName}(${it.relation})", it._id)
                    familymemberHashMapMemberType.put(
                        "${it.fullName}(${it.relation})",
                        it.memberType
                    )
                }
                spinnerForMemberList(familyMemberList)
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseMemberRecord.observe(this) {
            if (it?.code == 200) {
                testList.clear()
                testList.addAll(it.askedRequests)
                it.askedRequests.forEach {
                    memberRecordList.add("${it.itemName} (${it.order_type})")
                    mRecord_Id_HashMap.put("${it.itemName} (${it.order_type})", it._id)
                    mRecord_OrderType_HashMap.put(
                        "${it.itemName} (${it.order_type})",
                        it.order_type
                    )
                }
                spinnerForMemberRecordList(memberRecordList)
                /* if (mRecordOrderType == "PACKAGE") {
                    bin.textView123.visibility = View.VISIBLE
                    bin.textView1231.visibility = View.INVISIBLE
                    val img: String = it.askedRequests[0].itemImage
                    println("=========$img")
                    if (img.isNotEmpty()) {
                        Picasso.get().load(img).into(bin.ivmain)
                        bin.ivmain.visibility = View.VISIBLE
                        bin.ivdummy.visibility = View.INVISIBLE

                    } else {
                        bin.ivdummy.visibility = View.VISIBLE
                        bin.ivmain.visibility = View.INVISIBLE
                    }
                    bin.tvOrderName.text = it.askedRequests[0].itemName
                    val date = it.askedRequests[0].createdAt
                    val convert_date = setFormatDate(date)
                    bin.tvOrderDate.text = convert_date
                    bin.tvOrderDes.text = it.askedRequests[0].description
                    if (it.askedRequests[0].itemPrice.isEmpty()) {
                        bin.tvOrderprice.text = "₹ ${it.askedRequests[0].packagePrice}"
                        bin.tvOrderOfferprice.visibility = View.GONE
                        bin.divider14.visibility = View.GONE
                    } else {
                        bin.tvOrderOfferprice.visibility = View.VISIBLE
                        bin.divider14.visibility = View.VISIBLE
                        bin.tvOrderprice.text = "₹ ${it.askedRequests[0].packagePrice}"
                        bin.tvOrderOfferprice.text =
                            "₹ ${it.askedRequests[0].itemPrice}"
                    }
                    bin.tvOrderIncludeVaccine.text =
                        it.askedRequests[0].inclusiveVaccine.toString()
                    bin.tvOrderPendingDoes.text =
                        "${it.askedRequests[0].pendingDose.toString()} Pending Dosage"
                    bin.tvOrderCompleteDoes.text =
                        "${it.askedRequests[0].completedDose.toString()} complete Dosage"
                    bin.tvVaccineOrderName.text =
                        it.askedRequests[0].userData.get(0).fullName
                    val from_or =
                        "${it.askedRequests[0].slot.get(0).from} - ${
                            it.askedRequests[0].slot.get(
                                0
                            ).to
                        }"
                    val slot_date = setFormatDate(it.askedRequests[0].slotDate)
                    bin.tvVaccineOrderDateTime.text =
                        "${slot_date} , ${it.askedRequests[0].slotDay} , ${from_or}"
                    if (it.askedRequests[0].assignedNurse != null) {
                        bin.textView19111.visibility = View.GONE
                        bin.textView191.visibility = View.VISIBLE
                        bin.tvVaccineOrderDoneBy.text =
                            it.askedRequests[0].assignedNurse
                    } else {
                        bin.textView19111.visibility = View.VISIBLE
                        bin.textView191.visibility = View.INVISIBLE
                        bin.tvVaccineOrderDoneBy.text = "PENDING"
                    }
                    bin.tvName.text = it.askedRequests[0].userData[0].fullName
                    bin.tvGender.text = it.askedRequests[0].userData[0].gender
                    bin.dobtext.text = it.askedRequests[0].userData[0].dob
                    bin.relationtext2.text = it.askedRequests[0].userData[0].relation
                    bin.tvMedical.text = it.askedRequests[0].userData[0].medicalCondition
                    bin.tvage.text = "${getAge(it.askedRequests[0].userData[0].dob)} year"
                    if (it.askedRequests[0].userData[0].uploadVaccinationChart.isEmpty()) {
                        bin.uploadChatBtn.visibility = View.GONE
                    } else {
                        bin.uploadChatBtn.visibility = View.VISIBLE
                        val url = it.askedRequests[0].userData[0].uploadVaccinationChart
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
                    if (it.askedRequests[0].userData[0].anyReaction.isEmpty()) {
                        bin.tvAnyRePreviousDoes.text = "--"
                    } else {
                        bin.tvAnyRePreviousDoes.text = it.askedRequests[0].userData[0].anyReaction
                    }
                    bin.childLayout.visibility = View.GONE
                    if (it.askedRequests[0].userData[0].memberType.equals("KIDS")) {
                        bin.childLayout.visibility = View.VISIBLE
                        bin.tvdelivery.text =
                            "${it.askedRequests[0].userData[0].deliveryType} , ${it.askedRequests[0].userData[0].deliveryPeriod}"
                        bin.tvAnySeizures.text = it.askedRequests[0].userData[0].seizuresAtBirth
                        if (it.askedRequests[0].userData[0].nicuDetails.isEmpty()) {
                            bin.uploadChatBtn2.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn2.visibility = View.VISIBLE
                            val url = it.askedRequests[0].userData[0].nicuDetails
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
                        if (it.askedRequests[0].userData[0].bithHistory.isEmpty()) {
                            bin.uploadChatBtn3.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn3.visibility = View.VISIBLE
                            val url = it.askedRequests[0].userData[0].bithHistory
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
                        bin.tvMedicalDesorder.text = it.askedRequests[0].userData[0].medicalDisorder

                    }
                    commonSetDoseVaccine(it.askedRequests[0].record as ArrayList<Record>)
                    println("=======${it.askedRequests[0].record}")
                    bin.does.mainDoesLay.visibility = View.VISIBLE
                    bin.rcyDoes.visibility = View.GONE
                } else {
                    bin.does.mainDoesLay.visibility = View.GONE
                    bin.rcyDoes.visibility = View.VISIBLE
                    vaccineMemberRecordList.clear()
                    it.askedRequests[0].record.forEach {
                        if (it.doseInfo.isNotEmpty()) {
                            vaccineMemberRecordList.add(
                                VaccineMemberRecordModel(
                                    it.status ?: "",
                                    it.doseInfo.get(0)._id ?: "",
                                    it.doseInfo[0].doseNumber ?: "",
                                    it.doseInfo[0].timePeriod ?: ""
                                )
                            )
                        }
                    }


                    println("------vaccineMemberRecordList$vaccineMemberRecordList")
                    bin.rcyDoes.layoutManager = LinearLayoutManager(this)
                    adpterVaccineDose = VaccineMemberRecordAdapter(this, vaccineMemberRecordList)
                    bin.rcyDoes.adapter = adpterVaccineDose
                    adpterVaccineDose!!.notifyDataSetChanged()
                    bin.textView123.visibility = View.INVISIBLE
                    bin.textView1231.visibility = View.VISIBLE
                    val img: String = it.askedRequests[0].itemImage
                    if (img != null && img.length > 0) {
                        Picasso.get().load(img).into(bin.ivmain)
                        bin.ivmain.visibility = View.VISIBLE
                        bin.ivdummy.visibility = View.INVISIBLE

                    } else {
                        bin.ivdummy.visibility = View.VISIBLE
                        bin.ivmain.visibility = View.INVISIBLE
                    }
                    bin.tvOrderName.text = it.askedRequests[0].itemName
                    val date = it.askedRequests[0].createdAt
                    val convert_date = setFormatDate(date)
                    bin.tvOrderDate.text = convert_date
                    bin.tvOrderDes.text = it.askedRequests[0].description
                    bin.tvOrderprice.text =
                        "₹ ${it.askedRequests[0].itemPrice}"
                    bin.divider14.visibility = View.GONE
                    bin.tvOrderOfferprice.visibility = View.GONE
                    bin.tvOrderIncludeVaccine.text =
                        it.askedRequests[0].noOfDose
                    bin.tvOrderPendingDoes.text =
                        "${it.askedRequests[0].pendingDose.toString()} Pending Dosage"
                    bin.tvOrderCompleteDoes.text =
                        "${it.askedRequests[0].completedDose.toString()} complete Dosage"
                    bin.tvVaccineOrderName.text =
                        it.askedRequests[0].userData.get(0).fullName
                    val from_or =
                        "${it.askedRequests[0].slot.get(0).from} - ${
                            it.askedRequests[0].slot.get(
                                0
                            ).to
                        }"
                    val slot_date = setFormatDate(it.askedRequests[0].slotDate)
                    bin.tvVaccineOrderDateTime.text =
                        "${slot_date} , ${it.askedRequests[0].slotDay} , ${from_or}"
                    if (it.askedRequests[0].assignedNurse != null) {
                        bin.textView19111.visibility = View.GONE
                        bin.textView191.visibility = View.VISIBLE
                        bin.tvVaccineOrderDoneBy.text =
                            it.askedRequests[0].assignedNurse
                    } else {
                        bin.textView19111.visibility = View.VISIBLE
                        bin.textView191.visibility = View.INVISIBLE
                        bin.tvVaccineOrderDoneBy.text = "PENDING"
                    }
                    bin.tvName.text = it.askedRequests[0].userData[0].fullName
                    bin.tvGender.text = it.askedRequests[0].userData[0].gender
                    bin.dobtext.text = it.askedRequests[0].userData[0].dob
                    bin.relationtext2.text = it.askedRequests[0].userData[0].relation
                    bin.tvMedical.text = it.askedRequests[0].userData[0].medicalCondition
                    bin.tvage.text = "${getAge(it.askedRequests[0].userData[0].dob)} year"
                    if (it.askedRequests[0].userData[0].uploadVaccinationChart.isEmpty()) {
                        bin.uploadChatBtn.visibility = View.GONE
                    } else {
                        bin.uploadChatBtn.visibility = View.VISIBLE
                        val url = it.askedRequests[0].userData[0].uploadVaccinationChart
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
                    if (it.askedRequests[0].userData[0].anyReaction.isEmpty()) {
                        bin.tvAnyRePreviousDoes.text = "--"
                    } else {
                        bin.tvAnyRePreviousDoes.text = it.askedRequests[0].userData[0].anyReaction
                    }
                    bin.childLayout.visibility = View.GONE
                    if (it.askedRequests[0].userData[0].memberType.equals("KIDS")) {
                        bin.childLayout.visibility = View.VISIBLE
                        bin.tvdelivery.text =
                            "${it.askedRequests[0].userData[0].deliveryType} , ${it.askedRequests[0].userData[0].deliveryPeriod}"
                        bin.tvAnySeizures.text = it.askedRequests[0].userData[0].seizuresAtBirth
                        if (it.askedRequests[0].userData[0].nicuDetails.isEmpty()) {
                            bin.uploadChatBtn2.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn2.visibility = View.VISIBLE
                            val url = it.askedRequests[0].userData[0].nicuDetails
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
                        if (it.askedRequests[0].userData[0].bithHistory.isEmpty()) {
                            bin.uploadChatBtn3.visibility = View.GONE
                        } else {
                            bin.uploadChatBtn3.visibility = View.VISIBLE
                            val url = it.askedRequests[0].userData[0].bithHistory
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
                        bin.tvMedicalDesorder.text = it.askedRequests[0].userData[0].medicalDisorder
                    }
                }

            }*/ /*else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }*/
            }
        }

        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForFamilyMemberList() {
        viewModel.onFamilyMemberList(this)
    }

    private fun apiCallingForMemberRecordList() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onMemberRecord(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kmemberId] = memberId
        return hashMap
    }

    private fun commonSetDoseVaccine(
        doselist: ArrayList<Record>,

        ) {
        val hashMap: HashMap<String, StatusMemberRecordModel> = HashMap()
        var tt = ""
        for (doseInfo in doselist) {
            tt += doseInfo.vaccines ?: ""
            if (hashMap.containsKey(doseInfo.ageOfBaby)) {
                println("----ttt$tt")
                hashMap.put(
                    doseInfo.ageOfBaby ?: "",
                    StatusMemberRecordModel(
                        doseInfo.status ?: "",
                        doseInfo.completeDate ?: "",
                        doseInfo.nurseName ?: "",
                        tt
                    )

                )
                commonsSet1.add(PackageMemberRecordModel(
                    doseInfo.ageOfBaby ?: "",
                    StatusMemberRecordModel(
                        doseInfo.status ?: "",
                        doseInfo.completeDate ?: "",
                        doseInfo.nurseName ?: "",
                        tt
                    )
                ))
            } else {
                hashMap.put(
                    doseInfo.ageOfBaby ?: "",
                    StatusMemberRecordModel(
                        doseInfo.status ?: "",
                        doseInfo.completeDate ?: "",
                        doseInfo.nurseName ?: "",
                        tt
                    )
                )
                commonsSet1.add(PackageMemberRecordModel(
                    doseInfo.ageOfBaby ?: "",
                    StatusMemberRecordModel(
                        doseInfo.status ?: "",
                        doseInfo.completeDate ?: "",
                        doseInfo.nurseName ?: "",
                        tt
                    )
                ))
            }
        }
        commonsSet.addAll(commonsSet1.getDistinctList())
//        commonsSet.add(
//            PackageMemberRecordModel(
//                doselist.get(0).ageOfBaby ?: "",
//                StatusMemberRecordModel(
//                    doselist[0].status ?: "",
//                    doselist[0].completeDate ?: "",
//                    doselist[0].nurseName ?: "",
//                    tt
//                )
//            )
//        )

        bin.does.rcyvaccinedose.layoutManager =
            LinearLayoutManager(this)
        adpterDose =
            PackageDoesMemberRecordAdapter(
                this,
                commonsSet
            )
        bin.does.rcyvaccinedose.adapter = adpterDose
        adpterDose!!.notifyDataSetChanged()

        Log.e("laxman", "commonSetDoseVaccine:${Gson().toJson(commonsSet)} ")

    }

    private fun List<PackageMemberRecordModel>.getDistinctList(): List<PackageMemberRecordModel> {
        val list = mutableListOf<PackageMemberRecordModel>()
        val distinctDoses = mutableSetOf<String>()

        for (position in count() - 1 downTo 0) {
            val currentItem = get(position)
            if (currentItem.ageofbaby !in distinctDoses) {
                list.add(0, currentItem)
                distinctDoses.add(currentItem.ageofbaby ?: "")
            }
        }
        Log.d("LIST_DISTINCTS", Gson().toJson(list))
        return list
    }
}