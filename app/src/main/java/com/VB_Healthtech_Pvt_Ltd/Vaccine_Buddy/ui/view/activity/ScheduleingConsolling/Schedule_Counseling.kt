package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleingConsolling

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityScheduleCounselingBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.FileUtils
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddFamilyMember
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduledCounsellingCall.ScheduledCounsellingCallActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Slot.SlotActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.catalyist.helper.ErrorUtil
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Schedule_Counseling : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleCounselingBinding
    private lateinit var viewModel: ViewModalLogin
    private var url_pdf: String = ""
    private var url_pdf_nicu: String = ""
    private var url_pdf_birth: String = ""
    private val STORAGE_PERMISSION = 123
    private var type = ""
    private var familyMemberList = ArrayList<String>()
    private var newfamilyMemberList = ArrayList<AskedData>()
    private var familymemberHashMap: HashMap<String, String> = HashMap()
    private var familymemberHashMapName: HashMap<String, String> = HashMap()
    private lateinit var memberspinner: Spinner
    private lateinit var memberadapter: ArrayAdapter<String>
    private lateinit var relationspinner: Spinner
    private lateinit var relationadapter: ArrayAdapter<String>
    private lateinit var deliveryspinner: Spinner
    private lateinit var deliveryadapter: ArrayAdapter<String>
    private lateinit var deliverPeriodyspinner: Spinner
    private lateinit var deliveryPeriodadapter: ArrayAdapter<String>
    private lateinit var anyspinner: Spinner
    private lateinit var anyadapter: ArrayAdapter<String>
    private lateinit var genderspinner: Spinner
    private lateinit var genderadapter: ArrayAdapter<String>
    private var relatioName: String = ""
    var updateFamilyMemberId: String = ""
    var relation_new = ""
    private var age: Int = 0
    private var g_selecetd_postion: Int = 0
    private var day_id = ""
    private var slot_Day: String = ""
    private var slot_Time: String = ""
    private var slot_date: String = ""
    private var slot_Time_Id: String = ""
    private var back_date_day = ""
    private var back_date_day_slot = ""
    lateinit var member_obj: AskedData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleCounselingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)


    }

    private fun initView() {
        apiCallingForFamilyMemberList()
        binding.scheduleCounsellingToolar.tvTittle.text = "Schedule Counselling"
        day_id = intent.getStringExtra("dayId").toString()
        slot_Day = intent.getStringExtra("day").toString()
        slot_date = intent.getStringExtra("time_selected_date").toString()
        slot_Time = intent.getStringExtra("time").toString()
        slot_Time_Id = intent.getStringExtra("select_vaccine_time_id").toString()
        back_date_day = "${
            intent.getStringExtra("time_selected_date").toString()
        },${intent.getStringExtra("day").toString()},${intent.getStringExtra("time").toString()}"
        back_date_day_slot = intent.getStringExtra("back_date_day").toString()
        println("---s_day_id$day_id")
        println("---s_slot_Day$slot_Day")
        println("---s_slot_date$slot_date")
        println("---s_slot_Timed$slot_Time")
        println("---s_slot_Time_Id$slot_Time_Id")
        println("---s_back_date_day$back_date_day")
        println("---s_back_date_day_slot$back_date_day_slot")
        ///set member Spinner//
        familyMemberList.add(0, "Please Select Member")
        memberspinner = binding.spName
        memberadapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, familyMemberList)
        memberadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        memberspinner.adapter = memberadapter
        ///set member Spinner--End//
        ////relation Spinner
        relationspinner = binding.spRelation
        val relationList = resources.getStringArray(R.array.Relation)
        relationadapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, relationList)
        relationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        relationspinner.adapter = relationadapter
        ////delivery Spinner
        deliveryspinner = binding.delivaryType
        val deliveryTypeList = resources.getStringArray(R.array.DeliveryType)
        deliveryadapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, deliveryTypeList)
        deliveryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        deliveryspinner.adapter = deliveryadapter
        ///DeliveryPeriod Spinner
        deliverPeriodyspinner = binding.deliveryPeriod
        val deliveryPeriodList = resources.getStringArray(R.array.DeliveryPeriod)
        deliveryPeriodadapter =
            ArrayAdapter(this, R.layout.spinner_dropdown_item, deliveryPeriodList)
        deliveryPeriodadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        deliverPeriodyspinner.adapter = deliveryPeriodadapter
        ///Any Spinner
        anyspinner = binding.any
        val anyList = resources.getStringArray(R.array.Any)
        anyadapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, anyList)
        anyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        anyspinner.adapter = anyadapter
        ///Gender Spinner
        genderspinner = binding.spGender
        val genderList = resources.getStringArray(R.array.EditProfile)
        genderadapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, genderList)
        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderspinner.adapter = genderadapter

        memberspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                g_selecetd_postion = position - 1
                if (binding.spName.selectedItemPosition > 0) {
                    relation_new = newfamilyMemberList.get(position - 1).relation

                    if (relation_new.equals("Myself")) {
                        binding.childfilllay.visibility = View.GONE
                        binding.edPreviousPediatrician.visibility = View.GONE
                        binding.prevousView1.visibility = View.GONE
                        binding.relationView.visibility = View.VISIBLE
                        binding.relationView.text = relation_new
                        relationspinner.setClickable(false)
                        relationspinner.setEnabled(false)
                          binding.tvselectslot1.visibility = View.VISIBLE
                        binding.dobview.text = newfamilyMemberList.get(position - 1).dob
                        relationspinner.setSelection(
                            relationadapter.getPosition(
                                newfamilyMemberList.get(
                                    position - 1
                                ).relation
                            )
                        )
                        genderspinner.setSelection(
                            genderadapter.getPosition(
                                newfamilyMemberList.get(
                                    position - 1
                                ).gender
                            )
                        )
                        if (newfamilyMemberList.get(position - 1).uploadVaccinationChart.isNotEmpty()) {
                            url_pdf = newfamilyMemberList.get(position - 1).uploadVaccinationChart
                            println("---schr_url_pdf$url_pdf")
                            binding.pdfUpload.visibility = View.VISIBLE
                            binding.uploadBTN.visibility = View.INVISIBLE
                        } else {
                            binding.pdfUpload.visibility = View.GONE
                            binding.uploadBTN.visibility = View.VISIBLE
                        }
                        binding.edMedical.setText(newfamilyMemberList.get(position - 1).medicalCondition)
                        if (intent != null && !intent.getStringExtra("member_obj_back")
                                .isNullOrBlank()
                        ) {
                            binding.tvselectslot1.visibility = View.GONE
                        }

                    } else {
                        relationspinner.setClickable(true)
                        relationspinner.setEnabled(true)
                        binding.relationView.visibility = View.GONE
                        binding.childfilllay.visibility = View.GONE
                        binding.edPreviousPediatrician.visibility = View.GONE
                        binding.prevousView1.visibility = View.GONE
                        binding.tvselectslot1.visibility = View.VISIBLE
                        binding.edPreviousPediatrician.setText(newfamilyMemberList.get(position - 1).previousPediatrician)
                        if (intent != null && !intent.getStringExtra("member_obj_back")
                                .isNullOrBlank()
                        ) {
                            binding.tvselectslot1.visibility = View.GONE
                        }
                        if (newfamilyMemberList.get(position - 1).uploadVaccinationChart.isNotEmpty()) {
                            url_pdf = newfamilyMemberList.get(position - 1).uploadVaccinationChart
                            binding.pdfUpload.visibility = View.VISIBLE
                            binding.uploadBTN.visibility = View.INVISIBLE
                        } else {
                            binding.pdfUpload.visibility = View.GONE
                            binding.uploadBTN.visibility = View.VISIBLE
                        }
                        binding.dobview.text = newfamilyMemberList.get(position - 1).dob
                        relationspinner.setSelection(
                            relationadapter.getPosition(
                                newfamilyMemberList.get(
                                    position - 1
                                ).relation
                            )
                        )
                        genderspinner.setSelection(
                            genderadapter.getPosition(
                                newfamilyMemberList.get(
                                    position - 1
                                ).gender
                            )
                        )
                        relatioName =
                            familymemberHashMapName.get(binding.spName.selectedItem.toString())
                                ?: ""
                        updateFamilyMemberId =
                            familymemberHashMap[binding.spName.selectedItem.toString()].toString()

                        Log.d("TAG", "onItemSelected: " + updateFamilyMemberId + " " + relatioName)

//                    relationspinner.isEnabled = false
                        binding.edMedical.setText(newfamilyMemberList.get(position - 1).medicalCondition)
                        if (relatioName.equals("KIDS")) {
                            binding.edPreviousPediatrician.visibility = View.VISIBLE
                            binding.prevousView1.visibility = View.VISIBLE
                            binding.childfilllay.visibility = View.VISIBLE
                            binding.edAnyrection.setText(
                                newfamilyMemberList.get(position - 1).anyReaction
                            )
                            deliveryspinner.setSelection(
                                deliveryadapter.getPosition(
                                    newfamilyMemberList.get(
                                        position - 1
                                    ).deliveryType
                                )
                            )
                            deliverPeriodyspinner.setSelection(
                                deliveryPeriodadapter.getPosition(
                                    newfamilyMemberList.get(
                                        position - 1
                                    ).deliveryPeriod
                                )
                            )
                            anyspinner.setSelection(
                                anyadapter.getPosition(
                                    newfamilyMemberList.get(
                                        position - 1
                                    ).seizuresAtBirth
                                )
                            )

                            binding.edMedicalDisorder.setText(
                                newfamilyMemberList.get(position - 1).medicalDisorder
                            )
                            if (newfamilyMemberList.get(position - 1).bithHistory.isNotEmpty()) {
                                url_pdf_birth = newfamilyMemberList.get(position - 1).bithHistory
                                println("---schr_url_pdf_birth$url_pdf_birth")
                                binding.pdfBirth.visibility = View.VISIBLE
                                binding.uploadnicBTN3.visibility = View.INVISIBLE
                            } else {
                                binding.pdfBirth.visibility = View.GONE
                                binding.uploadnicBTN3.visibility = View.VISIBLE
                            }
                            if (newfamilyMemberList.get(position - 1).nicuDetails.isNotEmpty()) {
                                url_pdf_nicu = newfamilyMemberList.get(position - 1).nicuDetails
                                println("---schr_url_pdf_nicuh$url_pdf_nicu")
                                binding.pdfNIC.visibility = View.VISIBLE
                                binding.uploadnicBTN.visibility = View.INVISIBLE
                            } else {
                                binding.pdfBirth.visibility = View.GONE
                                binding.uploadnicBTN.visibility = View.VISIBLE
                            }
                            binding.edPreviousPediatrician.setText(newfamilyMemberList.get(position - 1).previousPediatrician)
                        }
                    }


                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    var selectDate: String = ""
    private fun lstnr() {
        var number= "9650039988"
        binding.ivwhatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${"+91"}$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        binding.scheduleCounsellingToolar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.uploadBTN.setOnClickListener {
            if (checkPermission(STORAGE_PERMISSION)) {
                type = "upt"
                setUpload()
            }

        }
        binding.uploadnicBTN.setOnClickListener {
            if (checkPermission(STORAGE_PERMISSION)) {
                type = "upt_nicu"
                setUpload()

            }
        }
        binding.uploadnicBTN3.setOnClickListener {
            if (checkPermission(STORAGE_PERMISSION)) {
                type = "upt_birth"
                setUpload()

            }
        }
        binding.pdfUpload.setOnClickListener {
            if (checkPermission(STORAGE_PERMISSION)) {
                type = "upt"
                setUpload()
            }
        }
        binding.pdfNIC.setOnClickListener {
            if (checkPermission(STORAGE_PERMISSION)) {
                type = "upt_nicu"
                setUpload()

            }
        }
        binding.pdfBirth.setOnClickListener {
            if (checkPermission(STORAGE_PERMISSION)) {
                type = "upt_birth"
                setUpload()

            }
        }
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())
        binding.dobview.setOnClickListener {
            MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build()).setSelection(Date().time)
                .build()
                .apply {
                    show(supportFragmentManager, this@Schedule_Counseling.toString())
                    addOnPositiveButtonClickListener {
                        selectDate =
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                Date(it)
                            )
                        getAge(selectDate)
                        binding.dobview.setText(selectDate)
                    }
                }
        }
//        binding.dobview.setOnClickListener {
//            ///futrue date disable
//            MaterialDatePicker.Builder.datePicker()
//                .setCalendarConstraints(constraintsBuilder.build()).setSelection(Date().time)
//                .build()
//                .apply {
//                    show(supportFragmentManager, this@Schedule_Counseling.toString())
//                    addOnPositiveButtonClickListener {
//                        selectDate =
//                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
//                                Date(it)
//                            )
//                        getAge(selectDate)
//                        binding.dobview.setText(selectDate)
//
//                    }
//
//                }
//
//        }

        binding.textView22.setOnClickListener() {
            startActivity(
                Intent(this, AddFamilyMember::class.java).putExtra(
                    "redirection",
                    "schedule"
                )
            )

        }
        binding.tvselectslot1.setOnClickListener {
//
                val intent = Intent(this, SlotActivity::class.java)
                intent.putExtra("from", "schedule_counselling")
                intent.putExtra(
                    "member_obj_go",
                    Gson().toJson(newfamilyMemberList[g_selecetd_postion])
                )
                intent.putExtra("schedule_fag", true)
                startActivity(intent)
//                finish()



        }
        binding.ivEdit.setOnClickListener {
            val intent = Intent(this, SlotActivity::class.java)
            intent.putExtra("member_obj_go", Gson().toJson(member_obj))
            intent.putExtra("back_slot_time", slot_Time_Id)
            intent.putExtra("back_selected_date_day", day_id)
            intent.putExtra("back_date_day", back_date_day)
            intent.putExtra("fag", true)
            intent.putExtra("edit", true)
            intent.putExtra("from", "schedule_counselling")
            startActivity(intent)
            finish()
        }
        binding.bookNow.setOnClickListener {
            if (binding.spName.selectedItemPosition > 0) {
                apiCallingForUpdateFamilyMember()
            } else {
                Toast.makeText(this, "Please Select Member", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseFamilyMemberList.observe(this) {
            if (it?.code == 200) {
                newfamilyMemberList.clear()
                newfamilyMemberList.addAll(it.askedData)

                it.askedData.forEach {
                    /*familyMemberList.add("${it.fullName} (${it.relation})")
                    familymemberHashMap.put("${it.fullName} (${it.relation})", it._id)
                    familymemberHashMapName.put("${it.fullName} (${it.relation})", it.memberType)*/
                    familyMemberList.add(it.fullName)
                    familymemberHashMap.put(it.fullName, it._id)
                    familymemberHashMapName.put(it.fullName, it.memberType)
                }
                relation_new = newfamilyMemberList[0].relation
                if (intent != null && !intent.getStringExtra("member_obj_back").isNullOrBlank()) {
                    member_obj = Gson().fromJson(
                        intent.getStringExtra("member_obj_back"),
                        AskedData::class.java
                    )

                    println("-s_relation_new$relation_new")
                    println("---s_member_obj$member_obj")
                    if (relation_new.equals("Myself")) {
                        updateFamilyMemberId = member_obj._id
                        println("------9999${updateFamilyMemberId}")
                        binding.childfilllay.visibility = View.GONE
                        binding.relationView.visibility = View.VISIBLE
                        binding.edPreviousPediatrician.visibility = View.GONE
                        binding.prevousView1.visibility = View.GONE
                        binding.relationView.text = relation_new
                        memberspinner.setSelection(memberadapter.getPosition(member_obj.fullName))
                        relationspinner.setClickable(false)
                        relationspinner.setEnabled(false)
                        binding.dobview.setText(member_obj.dob)
                        relationspinner.setSelection(
                            relationadapter.getPosition(
                                member_obj.relation
                            )
                        )
                        genderspinner.setSelection(
                            genderadapter.getPosition(
                                member_obj.gender
                            )
                        )
                        if (member_obj.uploadVaccinationChart.isNotEmpty()) {
                            url_pdf = member_obj.uploadVaccinationChart
                            println("---schr_url_pdf$url_pdf")
                            binding.pdfUpload.visibility = View.VISIBLE
                            binding.uploadBTN.visibility = View.INVISIBLE
                        } else {
                            binding.pdfUpload.visibility = View.GONE
                            binding.uploadBTN.visibility = View.VISIBLE
                        }
                        if (intent != null && intent.getBooleanExtra("fag", true)) {
                            binding.bookNow.visibility = View.VISIBLE
                            binding.tvselectslot1.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.VISIBLE
                            if (slot_date.isEmpty()) {
                                binding.tvtimeDaySlot.text =
                                    "${back_date_day_slot}"
                            } else {
                                binding.tvtimeDaySlot.text =
                                    "${slot_date}, ${slot_Day}, ${slot_Time}"
                            }
                              binding.tvselectslot1.visibility = View.VISIBLE
                            binding.ivEdit.visibility = View.VISIBLE
                        } else {
                            binding.bookNow.visibility = View.GONE
                            binding.tvselectslot1.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.INVISIBLE
                            binding.ivEdit.visibility = View.INVISIBLE
                        }
                        if (member_obj.memberType.equals("KIDS")) {
                            binding.childfilllay.visibility = View.VISIBLE
                            binding.edPreviousPediatrician.visibility = View.VISIBLE
                            binding.prevousView1.visibility = View.VISIBLE
                            binding.edPreviousPediatrician.setText(
                                member_obj.anyReaction
                            )
                            deliveryspinner.setSelection(
                                deliveryadapter.getPosition(
                                    member_obj.deliveryType
                                )
                            )
                            deliverPeriodyspinner.setSelection(
                                deliveryPeriodadapter.getPosition(
                                    member_obj.deliveryPeriod
                                )
                            )
                            anyspinner.setSelection(
                                anyadapter.getPosition(
                                    member_obj.seizuresAtBirth
                                )
                            )

                            binding.edMedicalDisorder.setText(
                                member_obj.medicalDisorder
                            )
                            if (member_obj.bithHistory.isNotEmpty()) {
                                url_pdf_birth = member_obj.bithHistory
                                println("---schr_url_pdf_birth$url_pdf_birth")
                                binding.pdfBirth.visibility = View.VISIBLE
                                binding.uploadnicBTN3.visibility = View.INVISIBLE
                            } else {
                                binding.pdfBirth.visibility = View.GONE
                                binding.uploadnicBTN3.visibility = View.VISIBLE
                            }
                            if (member_obj.nicuDetails.isNotEmpty()) {
                                url_pdf_nicu = member_obj.nicuDetails
                                println("---schr_url_pdf_nicuh$url_pdf_nicu")
                                binding.pdfNIC.visibility = View.VISIBLE
                                binding.uploadnicBTN.visibility = View.INVISIBLE
                            } else {
                                binding.pdfBirth.visibility = View.GONE
                                binding.uploadnicBTN.visibility = View.VISIBLE
                            }

                        }

                    } else {
                        relationspinner.setClickable(true)
                        relationspinner.setEnabled(true)
                        binding.relationView.visibility = View.GONE
                        binding.childfilllay.visibility = View.GONE
                        binding.edPreviousPediatrician.visibility = View.GONE
                        binding.prevousView1.visibility = View.GONE
                        if (member_obj.uploadVaccinationChart.isNotEmpty()) {
                            url_pdf = member_obj.uploadVaccinationChart
                            println("---schr_url_pdf$url_pdf")
                            binding.pdfUpload.visibility = View.VISIBLE
                            binding.uploadBTN.visibility = View.INVISIBLE
                        } else {
                            binding.pdfUpload.visibility = View.GONE
                            binding.uploadBTN.visibility = View.VISIBLE
                        }
                        if (intent != null && intent.getBooleanExtra("fag", true)) {
                            binding.bookNow.visibility = View.VISIBLE
                            binding.tvselectslot1.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.VISIBLE
                            if (slot_date.isEmpty()) {
                                binding.tvtimeDaySlot.text =
                                    "${back_date_day_slot}"
                            } else {
                                binding.tvtimeDaySlot.text =
                                    "${slot_date}, ${slot_Day}, ${slot_Time}"
                            }
                              binding.tvselectslot1.visibility = View.VISIBLE
                            binding.ivEdit.visibility = View.VISIBLE
                        } else {
                            binding.bookNow.visibility = View.GONE
                            binding.tvselectslot1.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.INVISIBLE
                            binding.ivEdit.visibility = View.INVISIBLE
                        }
                        g_selecetd_postion = memberadapter.getPosition(member_obj.fullName)
                        memberspinner.setSelection(g_selecetd_postion)
                        memberadapter.notifyDataSetChanged()

                        binding.dobview.setText(member_obj.dob)
                        relationspinner.setSelection(
                            relationadapter.getPosition(
                                member_obj.relation
                            )
                        )
                        genderspinner.setSelection(
                            genderadapter.getPosition(
                                member_obj.gender
                            )
                        )
                        if (member_obj.memberType.equals("KIDS")) {
                            binding.childfilllay.visibility = View.VISIBLE
                            binding.edPreviousPediatrician.visibility = View.VISIBLE
                            binding.prevousView1.visibility = View.VISIBLE
                            binding.edPreviousPediatrician.setText(
                                member_obj.previousPediatrician
                            )
                            deliveryspinner.setSelection(
                                deliveryadapter.getPosition(
                                    member_obj.deliveryType
                                )
                            )
                            deliverPeriodyspinner.setSelection(
                                deliveryPeriodadapter.getPosition(
                                    member_obj.deliveryPeriod
                                )
                            )
                            anyspinner.setSelection(
                                anyadapter.getPosition(
                                    member_obj.seizuresAtBirth
                                )
                            )

                            binding.edMedicalDisorder.setText(
                                member_obj.medicalDisorder
                            )
                            if (member_obj.bithHistory.isNotEmpty()) {
                                url_pdf_birth = member_obj.bithHistory
                                println("---schr_url_pdf_birth$url_pdf_birth")
                                binding.pdfBirth.visibility = View.VISIBLE
                                binding.uploadnicBTN3.visibility = View.INVISIBLE
                            } else {
                                binding.pdfBirth.visibility = View.GONE
                                binding.uploadnicBTN3.visibility = View.VISIBLE
                            }
                            if (member_obj.nicuDetails.isNotEmpty()) {
                                url_pdf_nicu = member_obj.nicuDetails
                                println("---schr_url_pdf_nicuh$url_pdf_nicu")
                                binding.pdfNIC.visibility = View.VISIBLE
                                binding.uploadnicBTN.visibility = View.INVISIBLE
                            } else {
                                binding.pdfBirth.visibility = View.GONE
                                binding.uploadnicBTN.visibility = View.VISIBLE
                            }

                        }
                    }
                }
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseUpdateFamilyMember.observe(this) {
            if (it?.code == 200) {
                apiCallingForBookSchedule()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseBookSchedule.observe(this) {
            if (it?.code == 200) {
                showDialog()
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

    private fun apiCallingForUpdateFamilyMember() {
        val userMap: java.util.HashMap<String, Any> = prepareDataForUpdateMember()
        viewModel.onUpdateFamilyMember(this, userMap)
    }

    private fun prepareDataForUpdateMember(): java.util.HashMap<String, Any> {
        val hashMap: java.util.HashMap<String, Any> = java.util.HashMap()
        hashMap[MyConstants.kmemberId] = updateFamilyMemberId
        hashMap[MyConstants.kfullName] = binding.spName.selectedItem.toString()
        hashMap[MyConstants.krelation] =
            if (relation_new.equals("Myself")) "Myself" else binding.spRelation.selectedItem.toString()
        hashMap[MyConstants.kDob] = binding.dobview.text.trim().toString()
        hashMap[MyConstants.kGender] = binding.spGender.selectedItem.toString()
        hashMap[MyConstants.kMedicalCondition] = binding.edMedical.text.trim().toString()
        hashMap[MyConstants.kuploadVaccinationChart] = url_pdf

        if (relatioName.equals("KIDS")) {
            hashMap[MyConstants.kmemberId] = updateFamilyMemberId
            hashMap[MyConstants.kmemberType] = "kids"
            hashMap[MyConstants.kdeliveryType] =
                binding.delivaryType.selectedItem.toString()
            hashMap[MyConstants.kdeliveryPeriod] =
                binding.deliveryPeriod.selectedItem.toString()
            hashMap[MyConstants.knicuDetails] = url_pdf_nicu
            hashMap[MyConstants.kseizuresAtBirth] =
                binding.any.selectedItem.toString()
            hashMap[MyConstants.kbithHistory] = url_pdf_birth
            hashMap[MyConstants.kanyReaction] =
                binding.edAnyrection.text.trim().toString()
            hashMap[MyConstants.kmedicalDisorder] =
                binding.edMedicalDisorder.text.trim().toString()
            hashMap[MyConstants.kpreviousPediatrician] =
                binding.edPreviousPediatrician.text.trim().toString()
        } else {
            hashMap[MyConstants.kmemberType] = "others"
        }

        return hashMap
    }

    private fun apiCallingForBookSchedule() {
        val userMap: java.util.HashMap<String, Any> = prepareDataForBookSchedule()
        viewModel.onBookSchedule(this, userMap)
    }

    private fun prepareDataForBookSchedule(): java.util.HashMap<String, Any> {
        val hashMap: java.util.HashMap<String, Any> = java.util.HashMap()
        hashMap[MyConstants.kmemberId] = updateFamilyMemberId
        hashMap[MyConstants.kpreferredDate] = binding.dobview.text.trim().toString()
        hashMap[MyConstants.kdayId] = day_id
        hashMap[MyConstants.kslotId] = slot_Time_Id


        return hashMap
    }

    fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.book_now_popup)
        val btn: Button = dialog.findViewById(R.id.done_button)
        val txtview: TextView = dialog.findViewById(R.id.txtview)
        btn.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, ScheduledCounsellingCallActivity::class.java).putExtra("from","schedule_counselling"))
            finish()
        }
        txtview.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.show()
    }

    private fun setUpload() {
        val mimeTypes = arrayOf("application/pdf", "application/msword", "application/vnd.ms-excel")
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        try {
            docLauncher.launch(Intent.createChooser(intent, "Select a File to Upload"))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this, "Please install a File Manager.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    var docLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data
                val uri = data!!.data


                //String filePath = GetPath.getPath(ChatActivity.this,uri);
                val filePath: String? =
                    FileUtils.getReadablePathFromUri(applicationContext, uri!!)
                if (filePath != null) uploadToS3(
                    filePath,
                    com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.S3Bucket.DOC
                )
            }

        }

    /* ActivityResultCallback<Any> { result ->
         if (result.getResultCode() === Activity.RESULT_OK) {
             v
         }
     })*/
    private fun uploadToS3(path: String, fileType: String) {
        com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.S3Bucket(
            this,
            path,
            fileType,
            "Sending Photo...",
            object :
                com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.S3Bucket.UploadListener {
                override fun onUploadComplete(url: String) {
                    Log.d("TAG", "onUploadComplete: URL = $url")
                    println("-----file$url")
                    if (type.equals("upt")) {
                        if (url != null) {
                            url_pdf = url
                            println("-----url_pdf$url_pdf")
                            binding.uploadBTN.visibility = View.INVISIBLE
                            binding.pdfUpload.visibility = View.VISIBLE
                        } else {
                            binding.uploadBTN.visibility = View.VISIBLE
                            binding.pdfUpload.visibility = View.GONE
                        }
                    } else if (type.equals("upt_nicu")) {
                        if (url != null) {
                            url_pdf_nicu = url
                            println("-----url_niu$url_pdf")
                            binding.uploadnicBTN.visibility = View.INVISIBLE
                            binding.pdfNIC.visibility = View.VISIBLE
                        } else {
                            binding.uploadnicBTN.visibility = View.VISIBLE
                            binding.pdfNIC.visibility = View.GONE
                        }
                    } else if (type.equals("upt_birth")) {
                        if (url != null) {
                            url_pdf_birth = url
                            println("-----url_birth$url_pdf")
                            binding.uploadnicBTN3.visibility = View.INVISIBLE
                            binding.pdfBirth.visibility = View.VISIBLE
                        } else {
                            binding.uploadnicBTN3.visibility = View.VISIBLE
                            binding.pdfBirth.visibility = View.GONE
                        }
                    }


                }

                override fun onFailure() {
                    //  Toast.makeText(this@AddFamilyMember,"Something went Wrong!",Toast.LENGTH_SHORT).show()
                }
            })


    }

    private fun checkPermission(requestCode: Int): Boolean {
        var ret = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ret = false
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), requestCode
                )
            }
        }
        return ret
    }

    fun getAge(date: String?): Int {
//        var age = 0
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date1 = formatter.parse(date)
            val now = Calendar.getInstance()
            val dob = Calendar.getInstance()
            dob.time = date1
            require(!dob.after(now)) { "Can't be born in the future" }
            val year1 = now[Calendar.YEAR]
            val year2 = dob[Calendar.YEAR]
            age = year1 - year2
            val month1 = now[Calendar.MONTH]
            val month2 = dob[Calendar.MONTH]
            if (month2 > month1) {
                age--
            } else if (month1 == month2) {
                val day1 = now[Calendar.DAY_OF_MONTH]
                val day2 = dob[Calendar.DAY_OF_MONTH]
                if (day2 > day1) {
                    age--
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (age < 1) {
            binding.childfilllay.visibility = View.VISIBLE
        } else {
            binding.childfilllay.visibility = View.GONE
        }

        Log.d("TAG", "getAge: AGE=> $age")
        return age
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}