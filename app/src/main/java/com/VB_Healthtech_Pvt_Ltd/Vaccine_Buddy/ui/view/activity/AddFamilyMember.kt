package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityAddFamilyMemberBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.Enums
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.FileUtils
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.NoOfDoesInfoModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDetails.AskedPackage
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.FamilyMember.FamilyMemberActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleingConsolling.Schedule_Counseling
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.SuggestAsPerBaby.SuggestAsPerBabyActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccinationChart.VaccinationChartActivity
import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import com.catalyist.helper.ErrorUtil
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddFamilyMember : AppCompatActivity() {
    private lateinit var binding: ActivityAddFamilyMemberBinding
    private var url_pdf: String = ""
    private var url_pdf_nicu: String = ""
    private var url_pdf_birth: String = ""
    private val STORAGE_PERMISSION = 123
    private var relation_type = ""
    private lateinit var viewModel: ViewModalLogin
    private lateinit var spinnerRelation: Spinner
    private lateinit var adapterRelation: ArrayAdapter<String>
    private lateinit var spinnerGender: Spinner
    private lateinit var adapterGender: ArrayAdapter<String>
    private lateinit var spinnerDeliveryTpye: Spinner
    private lateinit var adapterDeliveryTpye: ArrayAdapter<String>
    private lateinit var spinnerDeliveryPeriod: Spinner
    private lateinit var adapterDeliveryPeriod: ArrayAdapter<String>
    private lateinit var spinnerAny: Spinner
    private lateinit var adapterAny: ArrayAdapter<String>
    private var age: Int = 0
    private var total_price: Int = 0
    private var type = ""
    var selectDate: String = ""
    var redirectionType = ""
    var new_redirectionType = ""
    var updateFamilyMemberId: String = ""
    private var totalCount: Int = 0
    private lateinit var pkg_details_obj: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage
    private lateinit var vaccine_details_obj: AskedPackage
    lateinit var familymemberObj: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData
    private var noOf_doesInfo_list = ArrayList<NoOfDoesInfoModel>()
    private var ageGroup: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFamilyMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        allSpinerSetup()
        initView()
        lstnr()


    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        redirectionType = intent.getStringExtra("redirection").toString()
        new_redirectionType = intent.getStringExtra("redirection").toString()
        total_price = intent.getIntExtra("total_price", 0)
        println("---total_price$total_price")
        println("---new_redirectionType$new_redirectionType")
        println("---redirectionType$redirectionType")
        ageGroup = intent.getStringExtra("ageGroup").toString()
        ///comming from Filling Activity//
        if (intent != null && !intent.getStringExtra("Pkg").isNullOrBlank()) {
            pkg_details_obj = Gson().fromJson(
                intent.getStringExtra("Pkg"),
                com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage::class.java
            )
            println("---pkg_obj$pkg_details_obj")
        } else if (intent != null && !intent.getStringExtra("Vaccine_Detail").isNullOrBlank()) {
            vaccine_details_obj =
                Gson().fromJson(intent.getStringExtra("Vaccine_Detail"), AskedPackage::class.java)
            println("---vaccine_obj$vaccine_details_obj")
            noOf_doesInfo_list =
                intent.getSerializableExtra("doesInfo") as ArrayList<NoOfDoesInfoModel>
            Log.d("TAG", "onCreateADD: ${noOf_doesInfo_list}")
        }
//     ///comming from Filling Activity//
        totalCount = intent.getIntExtra("totalcount", 0)
        println("---totalCount${totalCount}")
        if (intent != null && !intent.getStringExtra("Obj").isNullOrBlank()) {
            familymemberObj = Gson().fromJson(
                intent.getStringExtra("Obj"),
                com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData::class.java
            )
            updateFamilyMemberId = familymemberObj._id
            relation_type = familymemberObj.relation
            println("-----relation_type$relation_type")

            if (familymemberObj.memberType.equals("KIDS")) {
                if (relation_type.equals("Myself")) {
                    binding.tvReletionHide.visibility = View.VISIBLE
                    binding.tvReletionHide.text = relation_type
                    spinnerRelation.setClickable(false)
                    spinnerRelation.setEnabled(false)
                    binding.addBirth.visibility = View.VISIBLE
                    binding.edName.setText(familymemberObj.fullName)
                    println("----namee${familymemberObj.fullName}")
                    binding.textView71.setText(familymemberObj.dob)
                    binding.edAll.setText(familymemberObj.medicalCondition)
//                    binding.previousTextView.setText(familymemberObj.anyReaction)
                    binding.medicalDis.setText(familymemberObj.medicalDisorder)
                    if (familymemberObj.bithHistory.isNotEmpty()) {
                        url_pdf_birth = familymemberObj.bithHistory
                        binding.pdfBirth.visibility = View.VISIBLE
                        binding.uploadnicBTN3.visibility = View.INVISIBLE
                    } else {
                        binding.pdfBirth.visibility = View.GONE
                        binding.uploadnicBTN3.visibility = View.VISIBLE
                    }
                    if (familymemberObj.nicuDetails.isNotEmpty()) {
                        url_pdf_nicu = familymemberObj.nicuDetails
                        binding.pdfNIC.visibility = View.VISIBLE
                        binding.uploadnicBTN.visibility = View.INVISIBLE
                    } else {
                        binding.pdfBirth.visibility = View.GONE
                        binding.uploadnicBTN.visibility = View.VISIBLE
                    }
                    if (familymemberObj.uploadVaccinationChart.isNotEmpty()) {
                        url_pdf = familymemberObj.uploadVaccinationChart
                        binding.pdfUpload.visibility = View.VISIBLE
                        binding.uploadBTN.visibility = View.INVISIBLE
                    } else {
                        binding.pdfUpload.visibility = View.GONE
                        binding.uploadBTN.visibility = View.VISIBLE
                    }
                    when (familymemberObj.gender) {
                        Enums.Male.toString() -> {
                            spinnerGender.setSelection(1)
                        }
                        Enums.Female.toString() -> {
                            spinnerGender.setSelection(2)
                        }
                        Enums.Other.toString() -> {
                            spinnerGender.setSelection(3)
                        }

                    }
                    when (familymemberObj.deliveryType) {
                        Enums.Natural_Birth.toString() -> {
                            spinnerDeliveryTpye.setSelection(1)
                        }
                        Enums.Vaginal_Birth.toString() -> {
                            spinnerDeliveryTpye.setSelection(2)
                        }
                        Enums.Scheduled_Cesarean.toString() -> {
                            spinnerDeliveryTpye.setSelection(3)
                        }
                        Enums.Unplanned_Cesarean.toString() -> {
                            spinnerDeliveryTpye.setSelection(4)
                        }
                        Enums.Scheduled_Induction.toString() -> {
                            spinnerDeliveryTpye.setSelection(5)
                        }
                        Enums.Other.toString() -> {
                            spinnerDeliveryTpye.setSelection(6)
                        }

                    }
                    when (familymemberObj.deliveryPeriod) {
                        Enums.Full_Time_Delivery.toString() -> {
                            spinnerDeliveryPeriod.setSelection(1)
                        }
                        Enums.Premature_Delivery.toString() -> {
                            spinnerDeliveryPeriod.setSelection(2)
                        }


                    }
                    when (familymemberObj.seizuresAtBirth) {
                        Enums.Yes.toString() -> {
                            spinnerAny.setSelection(1)
                        }
                        Enums.No.toString() -> {
                            spinnerAny.setSelection(2)
                        }
                    }
                } else {
                    spinnerRelation.setClickable(true)
                    spinnerRelation.setEnabled(true)
                    binding.tvReletionHide.visibility = View.GONE
                    when (familymemberObj.relation) {
                        Enums.Son.toString() -> {
                            spinnerRelation.setSelection(1)
                        }
                        Enums.Daughter.toString() -> {
                            spinnerRelation.setSelection(2)
                        }
                        Enums.Father.toString() -> {
                            spinnerRelation.setSelection(3)
                        }
                        Enums.Mother.toString() -> {
                            spinnerRelation.setSelection(4)
                        }
                        Enums.Husband.toString() -> {
                            spinnerRelation.setSelection(5)
                        }
                        Enums.Wife.toString() -> {
                            spinnerRelation.setSelection(6)
                        }
                        Enums.Other.toString() -> {
                            spinnerRelation.setSelection(7)
                        }

                    }
                    binding.addBirth.visibility = View.VISIBLE
                    binding.edName.setText(familymemberObj.fullName)
                    binding.edAnyRection.setText(familymemberObj.previousPediatrician)
                    println("----namee${familymemberObj.fullName}")
                    binding.textView71.setText(familymemberObj.dob)
                    binding.edAll.setText(familymemberObj.medicalCondition)
//                    binding.previousTextView.setText(familymemberObj.anyReaction)
                    binding.medicalDis.setText(familymemberObj.medicalDisorder)
                    if (familymemberObj.bithHistory.isNotEmpty()) {
                        url_pdf_birth = familymemberObj.bithHistory
                        println("---edd_url_pdf_birth$url_pdf_birth")
                        binding.pdfBirth.visibility = View.VISIBLE
                        binding.uploadnicBTN3.visibility = View.INVISIBLE
                    } else {
                        binding.pdfBirth.visibility = View.GONE
                        binding.uploadnicBTN3.visibility = View.VISIBLE
                    }
                    if (familymemberObj.nicuDetails.isNotEmpty()) {
                        url_pdf_nicu = familymemberObj.nicuDetails
                        println("---edd_url_pdf_nicuh$url_pdf_nicu")
                        binding.pdfNIC.visibility = View.VISIBLE
                        binding.uploadnicBTN.visibility = View.INVISIBLE
                    } else {
                        binding.pdfBirth.visibility = View.GONE
                        binding.uploadnicBTN.visibility = View.VISIBLE
                    }
                    if (familymemberObj.uploadVaccinationChart.isNotEmpty()) {
                        url_pdf = familymemberObj.uploadVaccinationChart
                        println("---edd_url_pdf$url_pdf")
                        binding.pdfUpload.visibility = View.VISIBLE
                        binding.uploadBTN.visibility = View.INVISIBLE
                    } else {
                        binding.pdfUpload.visibility = View.GONE
                        binding.uploadBTN.visibility = View.VISIBLE
                    }
                    when (familymemberObj.gender) {
                        Enums.Male.toString() -> {
                            spinnerGender.setSelection(1)
                        }
                        Enums.Female.toString() -> {
                            spinnerGender.setSelection(2)
                        }
                        Enums.Other.toString() -> {
                            spinnerGender.setSelection(3)
                        }

                    }
                    spinnerDeliveryTpye.setSelection(adapterDeliveryTpye.getPosition(familymemberObj.deliveryType))
                    spinnerDeliveryPeriod.setSelection(
                        adapterDeliveryPeriod.getPosition(
                            familymemberObj.deliveryPeriod
                        )
                    )
//                    when (familymemberObj.deliveryType) {
//                        Enums.Natural_Birth.toString() -> {
//                            spinnerDeliveryTpye.setSelection(1)
//                        }
//                        Enums.Vaginal_Birth.toString() -> {
//                            spinnerDeliveryTpye.setSelection(2)
//                        }
//                        Enums.Scheduled_Cesarean.toString() -> {
//                            spinnerDeliveryTpye.setSelection(3)
//                        }
//                        Enums.Unplanned_Cesarean.toString() -> {
//                            spinnerDeliveryTpye.setSelection(4)
//                        }
//                        Enums.Scheduled_Induction.toString() -> {
//                            spinnerDeliveryTpye.setSelection(5)
//                        }
//                        Enums.Other.toString() -> {
//                            spinnerDeliveryTpye.setSelection(6)
//                        }
//
//                    }
//                    when (familymemberObj.deliveryPeriod) {
//                        Enums.Full_Time_Delivery.toString() -> {
//                            spinnerDeliveryPeriod.setSelection(1)
//                        }
//                        Enums.Premature_Delivery.toString() -> {
//                            spinnerDeliveryPeriod.setSelection(2)
//                        }
//
//
//                    }
                    when (familymemberObj.seizuresAtBirth) {
                        Enums.Yes.toString() -> {
                            spinnerAny.setSelection(1)
                        }
                        Enums.No.toString() -> {
                            spinnerAny.setSelection(2)
                        }


                    }

                }


            } else {
                if (relation_type.equals("Myself")) {
                    binding.tvReletionHide.visibility = View.VISIBLE
                    binding.tvReletionHide.text = relation_type
                    spinnerRelation.setClickable(false)
                    spinnerRelation.setEnabled(false)
                    binding.edName.setText(familymemberObj.fullName)
                    binding.textView71.setText(familymemberObj.dob)
                    binding.edAll.setText(familymemberObj.medicalCondition)
                    if (familymemberObj.uploadVaccinationChart.isNotEmpty()) {
                        url_pdf = familymemberObj.uploadVaccinationChart
                        binding.pdfUpload.visibility = View.VISIBLE
                        binding.uploadBTN.visibility = View.INVISIBLE
                    } else {
                        binding.pdfUpload.visibility = View.GONE
                        binding.uploadBTN.visibility = View.VISIBLE
                    }
                    when (familymemberObj.gender) {
                        Enums.Male.toString() -> {
                            spinnerGender.setSelection(1)
                        }
                        Enums.Female.toString() -> {
                            spinnerGender.setSelection(2)
                        }
                        Enums.Other.toString() -> {
                            spinnerGender.setSelection(3)
                        }
                    }
                    when (familymemberObj.deliveryPeriod) {
                        Enums.Full_Time_Delivery.toString() -> {
                            spinnerDeliveryPeriod.setSelection(1)
                        }
                        Enums.Premature_Delivery.toString() -> {
                            spinnerDeliveryPeriod.setSelection(2)
                        }


                    }
                    when (familymemberObj.deliveryType) {
                        Enums.Natural_Birth.toString() -> {
                            spinnerDeliveryTpye.setSelection(1)
                        }
                        Enums.Vaginal_Birth.toString() -> {
                            spinnerDeliveryTpye.setSelection(2)
                        }
                        Enums.Scheduled_Cesarean.toString() -> {
                            spinnerDeliveryTpye.setSelection(3)
                        }
                        Enums.Unplanned_Cesarean.toString() -> {
                            spinnerDeliveryTpye.setSelection(4)
                        }
                        Enums.Scheduled_Induction.toString() -> {
                            spinnerDeliveryTpye.setSelection(5)
                        }
                        Enums.Other.toString() -> {
                            spinnerDeliveryTpye.setSelection(6)
                        }

                    }
                    when (familymemberObj.seizuresAtBirth) {
                        Enums.Yes.toString() -> {
                            spinnerAny.setSelection(1)
                        }
                        Enums.No.toString() -> {
                            spinnerAny.setSelection(2)
                        }


                    }
                    binding.edAnyRection.setText(familymemberObj.previousPediatrician)
                } else {
                    spinnerRelation.setClickable(true)
                    spinnerRelation.setEnabled(true)
                    binding.edAnyRection.setText(familymemberObj.previousPediatrician)
                    binding.tvReletionHide.visibility = View.GONE
                    when (familymemberObj.relation) {
                        Enums.Son.toString() -> {
                            spinnerRelation.setSelection(1)
                        }
                        Enums.Daughter.toString() -> {
                            spinnerRelation.setSelection(2)
                        }
                        Enums.Father.toString() -> {
                            spinnerRelation.setSelection(3)
                        }
                        Enums.Mother.toString() -> {
                            spinnerRelation.setSelection(4)
                        }
                        Enums.Husband.toString() -> {
                            spinnerRelation.setSelection(5)
                        }
                        Enums.Wife.toString() -> {
                            spinnerRelation.setSelection(6)
                        }
                        Enums.Other.toString() -> {
                            spinnerRelation.setSelection(7)
                        }

                    }
                    binding.edName.setText(familymemberObj.fullName)
                    binding.textView71.setText(familymemberObj.dob)
                    binding.edAll.setText(familymemberObj.medicalCondition)
                    if (familymemberObj.uploadVaccinationChart.isNotEmpty()) {
                        url_pdf = familymemberObj.uploadVaccinationChart
                        binding.pdfUpload.visibility = View.VISIBLE
                        binding.uploadBTN.visibility = View.INVISIBLE
                    } else {
                        binding.pdfUpload.visibility = View.GONE
                        binding.uploadBTN.visibility = View.VISIBLE
                    }
                    when (familymemberObj.gender) {
                        Enums.Male.toString() -> {
                            spinnerGender.setSelection(1)
                        }
                        Enums.Female.toString() -> {
                            spinnerGender.setSelection(2)
                        }
                        Enums.Other.toString() -> {
                            spinnerGender.setSelection(3)
                        }
                    }
                }
                binding.addBirth.visibility = View.GONE

            }
            binding.addFamilyToolbar.tvTittle.text = "Update Family Member"
            binding.button6.text = "Update Family Member"
        } else {
            binding.addFamilyToolbar.tvTittle.text = "Add Family Member"
        }


    }

    private fun allSpinerSetup() {

        ///Relation Spinner
        spinnerRelation = binding.relation
        val relationList = resources.getStringArray(R.array.Relation)
        adapterRelation = ArrayAdapter(this, R.layout.spinner_dropdown_item, relationList)
        adapterRelation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRelation.adapter = adapterRelation
        ///Gender Spinner
        spinnerGender = binding.gender
        val genderList = resources.getStringArray(R.array.EditProfile)
        adapterGender = ArrayAdapter(this, R.layout.spinner_dropdown_item, genderList)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapterGender
        ///DeliveryTpye Spinner
        spinnerDeliveryTpye = binding.delivaryType
        val deliveryTypeList = resources.getStringArray(R.array.DeliveryType)
        adapterDeliveryTpye = ArrayAdapter(this, R.layout.spinner_dropdown_item, deliveryTypeList)
        adapterDeliveryTpye.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDeliveryTpye.adapter = adapterDeliveryTpye
        ///DeliveryPeriod Spinner
        spinnerDeliveryPeriod = binding.deliveryPeriod
        val deliveryPeriodList = resources.getStringArray(R.array.DeliveryPeriod)
        adapterDeliveryPeriod =
            ArrayAdapter(this, R.layout.spinner_dropdown_item, deliveryPeriodList)
        adapterDeliveryPeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDeliveryPeriod.adapter = adapterDeliveryPeriod
        ///Any Spinner
        spinnerAny = binding.seizures
        val anyList = resources.getStringArray(R.array.Any)
        adapterAny = ArrayAdapter(this, R.layout.spinner_dropdown_item, anyList)
        adapterAny.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAny.adapter = adapterAny

    }


    private fun lstnr() {
        binding.addFamilyToolbar.ivBack.setOnClickListener {
            finish()
//            if (redirectionType.equals("fill_info")) {
////                startActivity(Intent(this, FillInformationActivity::class.java))
//                finish()
//            } else if (new_redirectionType.equals("schedule")) {
////                startActivity(Intent(this, FillInformationActivity::class.java))
//                finish()
//            } else {
//                onBackPressed()
//            }

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

        binding.button6.setOnClickListener {
            if (redirectionType.equals("add")) {
                if (age < 1 && validationData()) {
                    if (validationData2()) {
                        apiCallingForAddFamilyMember()
                    }

                } else if (validationData()) {
                    apiCallingForAddFamilyMember()
                }

            } else if (new_redirectionType.equals("fill_info")) {
                if (age < 1 && validationData()) {
                    if (validationData2()) {
                        apiCallingForAddFamilyMember()
                    }

                } else if (validationData()) {
                    apiCallingForAddFamilyMember()
                }
            } else if (redirectionType.equals("schedule")) {
                if (age < 1 && validationData()) {
                    if (validationData2()) {
                        apiCallingForAddFamilyMember()
                    }

                } else if (validationData()) {
                    apiCallingForAddFamilyMember()
                }
            } else if (redirectionType.equals("package_suggest")) {
                if (age < 1 && validationData()) {
                    if (validationData2()) {
                        apiCallingForAddFamilyMember()
                    }

                } else if (validationData()) {
                    apiCallingForAddFamilyMember()
                }
            } else if (redirectionType.equals("vaccine_suggest")) {
                if (age < 1 && validationData()) {
                    if (validationData2()) {
                        apiCallingForAddFamilyMember()
                    }

                } else if (validationData()) {
                    apiCallingForAddFamilyMember()
                }
            } else if (redirectionType.equals("vaccineChart")) {
                if (age < 1 && validationData()) {
                    if (validationData2()) {
                        apiCallingForAddFamilyMember()
                    }

                } else if (validationData()) {
                    apiCallingForAddFamilyMember()
                }
            } else {
                if (familymemberObj.memberType.equals("KIDS")) {
                    if (!validationData()) {
                        return@setOnClickListener
                    }
                    if (!validationData2()) {
                        return@setOnClickListener
                    }
                    apiCallingForUpdateFamilyMember()

                } else if (validationData()) {
                    apiCallingForUpdateFamilyMember()
                }
            }

        }

        /*

         if () {
                    if (intent != null && intent.getBooleanExtra("fag", false)) {
//                        if (familymemberObj.memberType.equals("KIDS")) {
//                            apiCallingForUpdateFamilyMember()
//                            binding.addBirth.visibility = View.VISIBLE
//                        } else {
//                            apiCallingForUpdateFamilyMember()
//                            binding.addBirth.visibility = View.GONE
//                        }
                        apiCallingForUpdateFamilyMember()
                    } else {

                    }
                } else {
                    if (intent != null && intent.getBooleanExtra("fag", false)) {
                        if (familymemberObj.memberType.equals("KIDS")) {
                            apiCallingForUpdateFamilyMember()
                            binding.addBirth.visibility = View.VISIBLE
                        } else {
                            apiCallingForUpdateFamilyMember()
                            binding.addBirth.visibility = View.GONE
                        }
                        /// apiCallingForUpdateFamilyMember()
                    }
                }

        else {
                if (intent != null && intent.getBooleanExtra("fag", false)) {
                    apiCallingForUpdateFamilyMember()
                } else {
                    apiCallingForAddFamilyMember()
                }
            }
        * if (intent != null && intent.getBooleanExtra("fag", false)) {
//                        if (familymemberObj.memberType.equals("KIDS")) {
//                            apiCallingForUpdateFamilyMember()
//                            binding.addBirth.visibility = View.VISIBLE
//                        } else {
//                            apiCallingForUpdateFamilyMember()
//                            binding.addBirth.visibility = View.GONE
//                        }
                        apiCallingForUpdateFamilyMember()
                    } else {
                        apiCallingForAddFamilyMember()
                    }
        * */
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())
        binding.textView71.setOnClickListener {
            MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build()).setSelection(Date().time)
                .build()
                .apply {
                    show(supportFragmentManager, this@AddFamilyMember.toString())
                    addOnPositiveButtonClickListener {
                        selectDate =
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                Date(it)
                            )
                        getAge(selectDate)
                        binding.textView71.setText(selectDate)
                    }
                }
        }

    }

    private fun setUpload() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        intent.type = "*/*"
//        val i = Intent.createChooser(intent, "File")
//        startActivityForResult(i, REQUEST_CODE_DOC)
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
            ActivityResultContracts.StartActivityForResult(), {
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

            })

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
        if (SDK_INT >= Build.VERSION_CODES.M) {
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


    /* private fun copyFileToInternalStorage(uri: Uri, newDirName: String): String? {
         val returnCursor: Cursor? = contentResolver.query(
             uri, arrayOf(
                 OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
             ), null, null, null
         )
         val nameIndex: Int = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
         val sizeIndex: Int = returnCursor.getColumnIndex(OpenableColumns.SIZE)
         returnCursor.moveToFirst()
         val name: String = returnCursor.getString(nameIndex)
         val size = java.lang.Long.toString(returnCursor.getLong(sizeIndex))
         if (!isValidType(name)) {
             Toast.makeText(this, "File format should be pdf or docx", Toast.LENGTH_SHORT).show()
             return null
         }
         val output: File
         if (newDirName != "") {
             val dir = File("$filesDir/$newDirName")
             if (!dir.exists()) {
                 dir.mkdir()
             }
             output = File("$filesDir/$newDirName/$name")
         } else {
             output = File("$filesDir/$name")
         }
         try {
             val inputStream: InputStream? = contentResolver.openInputStream(uri)
             val outputStream = FileOutputStream(output)
             var read = 0
             val bufferSize = 1024
             val buffers = ByteArray(bufferSize)
             while (inputStream!!.read(buffers).also { read = it } != -1) {
                 outputStream.write(buffers, 0, read)
             }
             inputStream.close()
             outputStream.close()
         } catch (e: Exception) {
             Log.e("Exception", e.message!!)
         }
         resumeUri = Uri.fromFile(output)
         return output.getPath()
     }*/


    /*  private fun isValidType(path: String): Boolean {
          fileType = ""
          val ret: Boolean
          if (path.endsWith(".pdf")) {
              fileType = "pdf"
              ret = true
          } else if (path.endsWith(".docx")) {
              fileType = "docx"
              ret = true
          } else ret = false
          return ret
      }*/

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseAddFamilyMember.observe(this) {
            if (it?.code == 200) {
                if (redirectionType.equals("add")) {
                    startActivity(Intent(this, FamilyMemberActivity::class.java))
                    finish()
                } else if (new_redirectionType.equals("fill_info")) {
                    if (intent != null && intent.getBooleanExtra("fag", true)) {
                        val intent = Intent(
                            this,
                            FillInformationActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra("Pkg", Gson().toJson(pkg_details_obj))
                        intent.putExtra("package_total_price", total_price)
                        intent.putExtra("totalcount", totalCount)
                        intent.putExtra("ageGroup",ageGroup)
                        intent.putExtra("fag", true)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(
                            this,
                            FillInformationActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra("Obj", Gson().toJson(vaccine_details_obj))
                        intent.putExtra("totalPrice", total_price)
                        intent.putExtra("doesInfo", noOf_doesInfo_list)
                        intent.putExtra("totalcount", totalCount)
                        intent.putExtra("ageGroup",ageGroup)
                        intent.putExtra("fag", false)
                        startActivity(intent)
                        finish()
                    }
                } else if (redirectionType.equals("schedule")) {
                    val intent = Intent(
                        this,
                        Schedule_Counseling::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.putExtra("fag", true)
                    startActivity(intent)
                    finish()
                } else if (redirectionType.equals("package_suggest")) {
                    if (redirectionType.equals("package_suggest")) {
                        val intent = Intent(
                            this,
                            SuggestAsPerBabyActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putExtra("back_from", "back_package_suggest")
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(
                            this,
                            SuggestAsPerBabyActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putExtra("back_from", "back_vaccine_suggest")
                        startActivity(intent)
                        finish()
                    }

                } else if (redirectionType.equals("vaccineChart")) {
                    val intent = Intent(
                        this,
                        VaccinationChartActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.putExtra("back_from", "back_vaccineChart")
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseUpdateFamilyMember.observe(this) {
            if (it?.code == 200) {
                finish()
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForAddFamilyMember() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onAddFamilyMember(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kfullName] = binding.edName.text.trim().toString()
        hashMap[MyConstants.krelation] = binding.relation.selectedItem.toString()
        hashMap[MyConstants.kDob] = binding.textView71.text.trim().toString()
        hashMap[MyConstants.kGender] = binding.gender.selectedItem.toString()
        hashMap[MyConstants.kMedicalCondition] = binding.edAll.text.trim().toString()
        hashMap[MyConstants.kuploadVaccinationChart] = url_pdf
        hashMap[MyConstants.kpreviousPediatrician] = binding.edAnyRection.text.trim().toString()
        if (age < 1) {
            hashMap[MyConstants.kmemberType] = "kids"
            hashMap[MyConstants.kdeliveryType] =
                binding.delivaryType.selectedItem.toString()
            hashMap[MyConstants.kdeliveryPeriod] =
                binding.deliveryPeriod.selectedItem.toString()
            hashMap[MyConstants.knicuDetails] = url_pdf_nicu
            hashMap[MyConstants.kseizuresAtBirth] =
                binding.seizures.selectedItem.toString()
            hashMap[MyConstants.kbithHistory] = url_pdf_birth
//            hashMap[MyConstants.kanyReaction] =
//                binding.previousTextView.text.trim().toString()
            hashMap[MyConstants.kmedicalDisorder] =
                binding.medicalDis.text.trim().toString()

        } else {
            hashMap[MyConstants.kmemberType] = "others"
        }
//        hashMap[MyConstants.kmemberType] = if (age < 1) "kids" else "others"
//        hashMap[MyConstants.kfullName] = binding.edName.text.trim().toString()
//        hashMap[MyConstants.krelation] = binding.relation.selectedItem.toString()
//        hashMap[MyConstants.kDob] = binding.textView71.text.toString()
//        hashMap[MyConstants.kGender] = binding.gender.selectedItem.toString()
//        hashMap[MyConstants.kMedicalCondition] = binding.edAll.text.trim().toString()
//        hashMap[MyConstants.kdeliveryType] =
//            if (age < 1) binding.delivaryType.selectedItem.toString() else null!!
//        hashMap[MyConstants.kdeliveryPeriod] =
//            if (age < 1) binding.deliveryPeriod.selectedItem.toString() else null!!
//        hashMap[MyConstants.knicuDetails] = if (age < 1) "pdf" else null!!
//        hashMap[MyConstants.kseizuresAtBirth] =
//            if (age < 1) binding.seizures.selectedItem.toString() else null!!
//        hashMap[MyConstants.kbithHistory] = if (age < 1) "upload" else null!!
//        hashMap[MyConstants.kanyReaction] =
//            if (age < 1) binding.previousTextView.text.trim().toString() else null!!
//        hashMap[MyConstants.kmedicalDisorder] =
//            if (age < 1) binding.medicalDis.text.trim().toString() else null!!

        return hashMap
    }

    private fun apiCallingForUpdateFamilyMember() {
        val userMap: HashMap<String, Any> = prepareDataForUpdateMember()
        viewModel.onUpdateFamilyMember(this, userMap)
    }

    private fun prepareDataForUpdateMember(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kmemberId] = updateFamilyMemberId
        hashMap[MyConstants.kfullName] = binding.edName.text.trim().toString()
        hashMap[MyConstants.krelation] =
            if (relation_type.equals("Myself")) "Myself" else binding.relation.selectedItem.toString()
        hashMap[MyConstants.kDob] = binding.textView71.text.trim().toString()
        hashMap[MyConstants.kGender] = binding.gender.selectedItem.toString()
        hashMap[MyConstants.kMedicalCondition] = binding.edAll.text.trim().toString()
        hashMap[MyConstants.kuploadVaccinationChart] = url_pdf
        hashMap[MyConstants.kpreviousPediatrician] = binding.edAnyRection.text.trim().toString()
        if (familymemberObj.memberType.equals("KIDS")) {
            hashMap[MyConstants.kmemberId] = updateFamilyMemberId
            hashMap[MyConstants.kmemberType] = "kids"
            hashMap[MyConstants.kdeliveryType] =
                binding.delivaryType.selectedItem.toString()
            hashMap[MyConstants.kdeliveryPeriod] =
                binding.deliveryPeriod.selectedItem.toString()
            hashMap[MyConstants.knicuDetails] = url_pdf_nicu
            hashMap[MyConstants.kseizuresAtBirth] =
                binding.seizures.selectedItem.toString()
            hashMap[MyConstants.kbithHistory] = url_pdf_birth
//            hashMap[MyConstants.kanyReaction] =
//                binding.previousTextView.text.trim().toString()
            hashMap[MyConstants.kmedicalDisorder] =
                binding.medicalDis.text.trim().toString()

        } else {
            hashMap[MyConstants.kmemberType] = "others"
        }
//        hashMap[MyConstants.kmemberId] = updateFamilyMemberId
//        hashMap[MyConstants.kmemberType] = if (age < 1) "kids" else "others"
//        hashMap[MyConstants.kfullName] = binding.edName.text.trim().toString()
//        hashMap[MyConstants.krelation] = binding.relation.selectedItem.toString()
//        hashMap[MyConstants.kDob] = binding.textView71.text.trim().toString()
//        hashMap[MyConstants.kGender] = binding.gender.selectedItem.toString()
//        hashMap[MyConstants.kMedicalCondition] = binding.edAll.text.trim().toString()
//        hashMap[MyConstants.kdeliveryType] =
//            if (age < 1) binding.delivaryType.selectedItem.toString() else null!!
//        hashMap[MyConstants.kdeliveryPeriod] =
//            if (age < 1) binding.deliveryPeriod.selectedItem.toString() else null!!
//        hashMap[MyConstants.knicuDetails] = if (age < 1) "pdf" else null!!
//        hashMap[MyConstants.kseizuresAtBirth] =
//            if (age < 1) binding.seizures.selectedItem.toString() else null!!
//        hashMap[MyConstants.kbithHistory] = if (age < 1) "upload" else null!!
//        hashMap[MyConstants.kanyReaction] =
//            if (age < 1) binding.previousTextView.text.trim().toString() else null!!
//        hashMap[MyConstants.kmedicalDisorder] =
//            if (age < 1) binding.medicalDis.text.trim().toString() else null!!

        return hashMap
    }

    private fun validationData(): Boolean {
        if (relation_type.equals("Myself")) {
            if (binding.edName.text.trim().toString().length < 4) {
                Toast.makeText(
                    applicationContext,
                    "Please Enter Name!!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } /*else if (binding.relation.selectedItemPosition.equals(0)) {
                Toast.makeText(
                    applicationContext,
                    "Please Select Relation!!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }*/ else if (TextUtils.isEmpty(binding.textView71.text?.trim().toString())
            ) {
                Toast.makeText(
                    applicationContext,
                    "Please Select Date!!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (binding.gender.selectedItemPosition.equals(0)) {
                Toast.makeText(
                    applicationContext,
                    "Please Select Gender!!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        } else {
            if (binding.edName.text.trim().toString().length < 4) {
                Toast.makeText(
                    applicationContext,
                    "Please Enter Name!!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (binding.relation.selectedItemPosition.equals(0)) {
                Toast.makeText(
                    applicationContext,
                    "Please Select Relation!!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (TextUtils.isEmpty(binding.textView71.text?.trim().toString())
            ) {
                Toast.makeText(
                    applicationContext,
                    "Please Select Date!!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (binding.gender.selectedItemPosition.equals(0)) {
                Toast.makeText(
                    applicationContext,
                    "Please Select Gender!!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } /*else if (TextUtils.isEmpty(binding.edAll.text?.trim().toString())) {
            Toast.makeText(
                applicationContext,
                "Please Enter Medical Conditions!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }*/ /*else if (TextUtils.isEmpty(binding.previousTextView.text?.trim().toString())) {
            Toast.makeText(
                applicationContext,
                "Please Enter Any Reactions !!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }else if (binding.delivaryType.selectedItemPosition.equals(0)) {
            Toast.makeText(
                applicationContext,
                "Please Select Delivery Type!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.deliveryPeriod.selectedItemPosition.equals(0)
        ) {
            Toast.makeText(
                applicationContext,
                "Please Select Delivery Period!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.seizures.selectedItemPosition.equals(0)) {
            Toast.makeText(
                applicationContext,
                "Please Select Seizures at Birth!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }else if (TextUtils.isEmpty(binding.medicalDis.text?.trim().toString())) {
            Toast.makeText(
                applicationContext,
                "Please Enter Medical Disorder!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }*/
        }

        return true

    }

    private fun validationData2(): Boolean {
        /* if (TextUtils.isEmpty(binding.previousTextView.text?.trim().toString())) {
             Toast.makeText(
                 applicationContext,
                 "Please Enter Any Reactions !!",
                 Toast.LENGTH_SHORT
             ).show()
             return false
         }*/
        if (binding.delivaryType.selectedItemPosition.equals(0)) {
            Toast.makeText(
                applicationContext,
                "Please Select Delivery Type!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.deliveryPeriod.selectedItemPosition.equals(0)
        ) {
            Toast.makeText(
                applicationContext,
                "Please Select Delivery Period!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.seizures.selectedItemPosition.equals(0)) {
            Toast.makeText(
                applicationContext,
                "Please Select Seizures at Birth!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } /*else if (TextUtils.isEmpty(binding.medicalDis.text?.trim().toString())) {
            Toast.makeText(
                applicationContext,
                "Please Enter Medical Disorder!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }*/
        return true

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
            binding.addBirth.visibility = View.VISIBLE
        } else {
            binding.addBirth.visibility = View.GONE
        }

        Log.d("TAG", "getAge: AGE=> $age")
        return age
    }
//
    /*fun selectAge() {
        var c = Calendar.getInstance()
        var cDay = c.get(Calendar.DAY_OF_MONTH)
        var cMonth = c.get(Calendar.MONTH)
        var cYear = c.get(Calendar.YEAR)
        val calendarDi = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cDay = dayOfMonth
                cMonth = month
                cYear = year
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                age = currentYear - cYear

                println("---aggee${age}")
                if (age < 1) {
                    binding.addBirth.visibility = View.VISIBLE
                } else {
                    binding.addBirth.visibility = View.GONE
                }
                date = "$cDay/${cMonth + 1}/$cYear"
                binding.textView71.text = "$cDay/${cMonth + 1}/$cYear"
            },
            cYear,
            cMonth,
            cDay
        )
        calendarDi.show()
    }*/

//    override fun onBackPressed() {
//        super.onBackPressed()
//
//    }
}
