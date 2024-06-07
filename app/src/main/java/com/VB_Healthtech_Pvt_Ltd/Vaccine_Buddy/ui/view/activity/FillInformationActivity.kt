package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityFillInformationBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.FileUtils
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.NoOfDoesInfoModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDetails.AskedPackage
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MyCart.MyCartActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Slot.SlotActivity
import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FillInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillInformationBinding
    private lateinit var viewModel: ViewModalLogin
    private var familymemberHashMap: HashMap<String, String> = HashMap()
    private var familymemberHashMapName: HashMap<String, String> = HashMap()
    private var familyMemberList = ArrayList<String>()
    private var newfamilyMemberList = ArrayList<AskedData>()
    private var mmnewfamilyMemberList = ArrayList<AskedData>()
    private var age: Int = 0
    private var url_pdf: String = ""
    private var url_pdf_nicu: String = ""
    private var url_pdf_birth: String = ""
    private val STORAGE_PERMISSION = 123
    private var type = ""
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
    lateinit var vaccineDetailsObj: AskedPackage
    lateinit var packageDetailsObj: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage
    var updateFamilyMemberId: String = ""
    private var relatioName: String = ""
    private var packageNewId: String = ""
    private var total_vacccine_price: Int = 0
    private var package_total_price: Double = 0.0
    private var totalCount: Int = 0
    private var vaccine_id: String = ""
    private var rrrvaccine_id: String = ""
    private var rev_package_id: String = ""
    private var vacccine_slot_Day: String = ""
    private var vacccine_slot_Time: String = ""
    private var vacccine_select_Time_Id: String = ""
    private var vacccine_day_Id: String = ""
    private var package_slot_Day: String = ""
    private var pacakge_slot_Time: String = ""
    private var package_select_Time_Id: String = ""
    private var package_day_Id: String = ""
    private var fromvaccine: String = ""
    lateinit var member_go: AskedData
    private var g_selecetd_postion: Int = 0
    private var time_selected_date = ""
    private var relation = ""
    private var back_selecet_date_day = ""
    private var back_date_day = ""
    private var back_date_day_slot = ""
    private var noOf_doesInfo_list = ArrayList<NoOfDoesInfoModel>()
    private var fromNotiMemberId: String = ""
    private var fromNotiType: String = ""
    private var ageGroup: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
        packageNewId = intent.getStringExtra("packageId").toString()
        println("---packageNewId${packageNewId}")
        package_total_price = intent.getDoubleExtra("package_total_price", 0.0)
        println("---package_total_price${package_total_price}")

        total_vacccine_price = intent.getIntExtra("totalPrice", 0)
        println("---total_vacccine_price${total_vacccine_price}")
        vaccine_id = intent.getStringExtra("vaccineId").toString()
        println("---vaccine_Id${vaccine_id}")
        fromvaccine = intent.getStringExtra("from_slot").toString()
        println("-----frommmVaccine${fromvaccine}")

        ageGroup = intent.getStringExtra("ageGroup").toString()
        println("----ageePack$ageGroup")
        binding.eligibleview.text = "You are Eligible to schedule $ageGroup dose."
        ////From Noti
        fromNotiType = intent.getStringExtra("fromNoti").toString()
        fromNotiMemberId = intent.getStringExtra("memberId").toString()
        if (fromNotiType != null && fromNotiType.equals("customVaccine")) {
            println("-----notiMember$fromNotiMemberId")
            println("-----notitype$fromNotiType")
            if (intent != null && !intent.getStringExtra("Obj").isNullOrBlank()) {
                vaccineDetailsObj =
                    Gson().fromJson(intent.getStringExtra("Obj"), AskedPackage::class.java)
                println("-----onjjj${vaccineDetailsObj}")
                rrrvaccine_id = vaccineDetailsObj._id
                println("----rrrrr${rrrvaccine_id}")
                Picasso.get().load(vaccineDetailsObj.vaccineImage).centerCrop().resize(100, 100)
                    .into(binding.imageView18)
                binding.textView25.setText(vaccineDetailsObj.vaccineName)
                binding.textView26.setText(vaccineDetailsObj.description)
                binding.iiii.text = "₹$total_vacccine_price"
                binding.tvofferprice.visibility = View.GONE
                binding.divider15.visibility = View.GONE
                noOf_doesInfo_list =
                    intent.getSerializableExtra("doesInfo") as ArrayList<NoOfDoesInfoModel>
                Log.d("TAG", "onCreate: ${noOf_doesInfo_list}")
                totalCount = intent.getIntExtra("totalcount", 0)
                println("---vaccinetotalcount${totalCount}")
            }
        }



        if (fromvaccine != null && fromvaccine.equals("From_vaccine")) {
            total_vacccine_price = intent.getIntExtra("totalPrice", 0)
            println("---rtotal_vacccine_price${total_vacccine_price}")
            vacccine_day_Id = intent.getStringExtra("dayId").toString()
            println("---vaccine_Day_id${vacccine_day_Id}")
            vacccine_slot_Day = intent.getStringExtra("day").toString()
            println("---vaccine_day${vacccine_slot_Day}")
            vacccine_slot_Time = intent.getStringExtra("time").toString()

            println("---vaccine_time${vacccine_slot_Time}")

            vacccine_select_Time_Id = intent.getStringExtra("select_vaccine_time_id").toString()
            println("---vaccine_select_time_ID${vacccine_select_Time_Id}")
            time_selected_date = intent.getStringExtra("time_selected_date").toString()
            back_date_day = "${
                intent.getStringExtra("time_selected_date").toString()
            } , ${intent.getStringExtra("day").toString()}, ${
                intent.getStringExtra("time").toString()
            }"

            println("---vaccine_time_selected_date${time_selected_date}")
            back_selecet_date_day = "$time_selected_date , $vacccine_slot_Day"
            println("-----back_selecet_date_day$back_selecet_date_day")
            println("--back_date_day$back_date_day")
            back_date_day_slot = intent.getStringExtra("back_date_day").toString()
            println("---back_date_day_slot$back_date_day_slot")
            totalCount = intent.getIntExtra("totalcount", 0)
            println("---vaaatotalcount${totalCount}")

        } else {
            package_total_price = intent.getDoubleExtra("package_total_price", 0.0)
            println("---package_total_price${package_total_price}")
            package_day_Id = intent.getStringExtra("dayId").toString()
            println("---package_Day_id${package_day_Id}")
            package_slot_Day = intent.getStringExtra("day").toString()
            println("---package_day${package_slot_Day}")
            pacakge_slot_Time = intent.getStringExtra("time").toString()

            println("---package_time${pacakge_slot_Time}")
            package_select_Time_Id = intent.getStringExtra("select_vaccine_time_id").toString()
            println("---package_select_time_ID${package_select_Time_Id}")
            time_selected_date = intent.getStringExtra("time_selected_date").toString()
            println("---package_time_selected_date${time_selected_date}")
            back_selecet_date_day = "$time_selected_date , $package_slot_Day"
            println("-----back_selecet_date_day$back_selecet_date_day")
            back_date_day = "${
                intent.getStringExtra("time_selected_date").toString()
            } , ${intent.getStringExtra("day").toString()} ,${
                intent.getStringExtra("time").toString()
            }"
            println("--back_date_day$back_date_day")
            back_date_day_slot = intent.getStringExtra("back_date_day").toString()
            println("---back_date_day_slot$back_date_day_slot")
            totalCount = intent.getIntExtra("totalcount", 0)
            println("---Pcakagetotalcount${totalCount}")
        }


        if (fromvaccine != null && fromvaccine.equals("From_vaccine")) {
            binding.continue2.visibility = View.VISIBLE
            binding.tvselectslot.visibility = View.GONE
            binding.tvtimeDaySlot.visibility = View.VISIBLE
            binding.tvslotData.visibility = View.VISIBLE
            binding.ivEdit.visibility = View.VISIBLE
        } else if (fromvaccine != null && fromvaccine.equals("From_Package")) {
            binding.continue2.visibility = View.VISIBLE
            binding.tvselectslot.visibility = View.GONE
            binding.tvtimeDaySlot.visibility = View.VISIBLE
            binding.ivEdit.visibility = View.VISIBLE
            binding.tvslotData.visibility = View.VISIBLE

        } else {
            binding.continue2.visibility = View.GONE
            //binding.tvselectslot.visibility = View.VISIBLE
            binding.tvslotData.visibility = View.GONE
            binding.tvtimeDaySlot.visibility = View.GONE
            binding.ivEdit.visibility = View.GONE
        }
        if (intent != null && !intent.getStringExtra("Obj").isNullOrBlank()) {
            vaccineDetailsObj =
                Gson().fromJson(intent.getStringExtra("Obj"), AskedPackage::class.java)
            println("-----onjjj${vaccineDetailsObj}")
            rrrvaccine_id = vaccineDetailsObj._id
            println("----rrrrr${rrrvaccine_id}")
            Picasso.get().load(vaccineDetailsObj.vaccineImage).centerCrop().resize(100, 100)
                .into(binding.imageView18)
            binding.textView25.setText(vaccineDetailsObj.vaccineName)
            binding.textView26.setText(vaccineDetailsObj.description)
            binding.iiii.text = "₹$total_vacccine_price"
            binding.tvofferprice.visibility = View.GONE
            binding.divider15.visibility = View.GONE
            noOf_doesInfo_list =
                intent.getSerializableExtra("doesInfo") as ArrayList<NoOfDoesInfoModel>
            Log.d("TAG", "onCreate: ${noOf_doesInfo_list}")
            totalCount = intent.getIntExtra("totalcount", 0)
            println("---vaccinetotalcount${totalCount}")
        } else if (intent != null && !intent.getStringExtra("Pkg").isNullOrBlank()) {
            packageDetailsObj = Gson().fromJson(
                intent.getStringExtra("Pkg"),
                com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage::class.java
            )
            println("-----pckjjj${packageDetailsObj}")
            rev_package_id = packageDetailsObj._id
            println("----rr_package_id${rev_package_id}")
            Picasso.get().load(packageDetailsObj.packageImage).centerCrop().resize(100, 100)
                .into(binding.imageView18)
            binding.textView25.setText(packageDetailsObj.packageName)
            binding.textView26.setText(packageDetailsObj.description)

            binding.iiii.text = "₹${packageDetailsObj.totalPrice}"
            binding.tvofferprice.visibility = View.VISIBLE
            binding.divider15.visibility = View.VISIBLE
            binding.tvofferprice.text = "₹${packageDetailsObj.offerPrice}"


            totalCount = intent.getIntExtra("totalcount", 0)
            println("---Packagetotalcount${totalCount}")


        }


    }

    var relation_new = ""
    private fun initView() {
        apiCallingForFamilyMemberList()
        binding.fillToolbar.tvTittle.text = "Filling Information"
        familyMemberList.add(0, "Please Select Member")
        memberspinner = binding.nameview
        memberadapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, familyMemberList)
        memberadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        memberspinner.adapter = memberadapter
        ////relation Spinner
        relationspinner = binding.relation
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
        genderspinner = binding.genderSp
        val genderList = resources.getStringArray(R.array.EditProfile)
        genderadapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, genderList)

        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderspinner.adapter = genderadapter

        memberspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                g_selecetd_postion = position - 1
                if (binding.nameview.selectedItemPosition > 0) {
                    relation_new = newfamilyMemberList.get(position - 1).relation
                    println("----relation$relation")
                    if (relation_new.equals("Myself")) {
                        binding.tvReletionHide.visibility = View.VISIBLE
                        binding.tvReletionHide.text = relation_new
                        binding.tvselectslot.visibility = View.VISIBLE
                        binding.childfilllay.visibility = View.GONE
                        relationspinner.setClickable(false)
                        relationspinner.setEnabled(false)
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
                        if (intent != null && !intent.getStringExtra("member_obj_back")
                                .isNullOrBlank()
                        ) {
                            binding.tvselectslot.visibility = View.GONE
                        } else {
                            binding.tvselectslot.visibility = View.VISIBLE
                        }
                        if (newfamilyMemberList.get(position - 1).uploadVaccinationChart.isNotEmpty()) {
                            url_pdf = newfamilyMemberList.get(position - 1).uploadVaccinationChart
                            println("---schr_url_pdf$url_pdf")
                            binding.pdfUpload.visibility = View.VISIBLE
                            binding.uploadBTN.visibility = View.INVISIBLE
                        } else {
                            binding.pdfUpload.visibility = View.GONE
                            binding.uploadBTN.visibility = View.VISIBLE
                        }
                        binding.edAnyRection.setText(newfamilyMemberList.get(position - 1).anyReaction)
                    } else {
                        relationspinner.setClickable(true)
                        relationspinner.setEnabled(true)
                        binding.tvReletionHide.visibility = View.GONE
                        binding.childfilllay.visibility = View.GONE
                        if (intent != null && !intent.getStringExtra("member_obj_back")
                                .isNullOrBlank()
                        ) {
                            binding.tvselectslot.visibility = View.GONE
                        } else {
                            binding.tvselectslot.visibility = View.VISIBLE
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
                            familymemberHashMapName.get(binding.nameview.selectedItem.toString())
                                ?: ""
                        updateFamilyMemberId =
                            familymemberHashMap[binding.nameview.selectedItem.toString()].toString()

                        Log.d("TAG", "onItemSelected: " + updateFamilyMemberId + " " + relatioName)

//                    relationspinner.isEnabled = false
                        binding.edMedical.setText(newfamilyMemberList.get(position - 1).medicalCondition)
                        binding.edAnyRection.setText(newfamilyMemberList.get(position - 1).anyReaction)
                        if (relatioName.equals("KIDS")) {
                            binding.childfilllay.visibility = View.VISIBLE
//                            binding.previousTextView.setText(
//                                newfamilyMemberList.get(position - 1).previousPediatrician
//                            )
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

                            binding.loremView.text =
                                newfamilyMemberList.get(position - 1).medicalDisorder
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
        binding.fillToolbar.ivBack.setOnClickListener {
            onBackPressed()
            //  finish()
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
        binding.textView22.setOnClickListener {
            if (intent != null && intent.getBooleanExtra("fag", true)) {
                val intent = Intent(this, AddFamilyMember::class.java)
                intent.putExtra("Pkg", Gson().toJson(packageDetailsObj))
                intent.putExtra("redirection", "fill_info")
                intent.putExtra("total_price", package_total_price)
                intent.putExtra("totalcount", totalCount)
                intent.putExtra("ageGroup", ageGroup)
                intent.putExtra("fag", true)
                startActivity(intent)

                //  finish()

            } else {
                if (fromNotiType != null && fromNotiType.equals("customVaccine")) {
                    val intent = Intent(this, AddFamilyMember::class.java)
                    intent.putExtra("Vaccine_Detail", Gson().toJson(vaccineDetailsObj))
                    intent.putExtra("redirection", "fill_info")
                    intent.putExtra("total_price", total_vacccine_price)
                    intent.putExtra("fag", false)
                    intent.putExtra("ageGroup", ageGroup)
                    intent.putExtra("totalcount", totalCount)
                    intent.putExtra("doesInfo", noOf_doesInfo_list)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, AddFamilyMember::class.java)
                    intent.putExtra("Vaccine_Detail", Gson().toJson(vaccineDetailsObj))
                    intent.putExtra("redirection", "fill_info")
                    intent.putExtra("total_price", total_vacccine_price)
                    intent.putExtra("fag", false)
                    intent.putExtra("ageGroup", ageGroup)
                    intent.putExtra("totalcount", totalCount)
                    intent.putExtra("doesInfo", noOf_doesInfo_list)
                    startActivity(intent)
                }

                //finish()
            }

        }
        binding.ivEdit.setOnClickListener {
            if (intent != null && intent.getBooleanExtra("fag", true)) {
                val intent = Intent(this, SlotActivity::class.java)
                intent.putExtra("Pkg", Gson().toJson(packageDetailsObj))
                intent.putExtra("package_total_price", package_total_price)
                intent.putExtra("member_obj_go", Gson().toJson(member_go))
                intent.putExtra("back_slot_time", package_select_Time_Id)
                intent.putExtra("back_selected_date_day", package_day_Id)
                intent.putExtra("back_date_day", back_date_day)
                intent.putExtra("totalcount", totalCount)
                intent.putExtra("fag", true)
                intent.putExtra("ageGroup", ageGroup)
                intent.putExtra("edit", true)
                intent.putExtra("from", "edit_time")
                startActivity(intent)
                finish()
            } else {
                if (fromNotiType != null && fromNotiType.equals("customVaccine")) {
                    val intent = Intent(this, SlotActivity::class.java)
                    intent.putExtra("Obj", Gson().toJson(vaccineDetailsObj))
                    intent.putExtra("totalvaccinePrice", total_vacccine_price)
                    intent.putExtra("vaccine_id", vaccine_id)
                    intent.putExtra("totalcount", totalCount)
                    intent.putExtra("back_selected_date_day", vacccine_day_Id)
                    intent.putExtra("back_slot_time", vacccine_select_Time_Id)
                    intent.putExtra("back_date_day", back_date_day)
                    intent.putExtra("doesInfo", noOf_doesInfo_list)
                    intent.putExtra(
                        "member_obj_go", Gson().toJson(member_go)
                    )
                    intent.putExtra("fag", false)
                    intent.putExtra("ageGroup", ageGroup)
                    intent.putExtra("edit", true)
                    intent.putExtra("from", "edit_time")
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, SlotActivity::class.java)
                    intent.putExtra("Obj", Gson().toJson(vaccineDetailsObj))
                    intent.putExtra("totalvaccinePrice", total_vacccine_price)
                    intent.putExtra("vaccine_id", vaccine_id)
                    intent.putExtra("totalcount", totalCount)
                    intent.putExtra("back_selected_date_day", vacccine_day_Id)
                    intent.putExtra("back_slot_time", vacccine_select_Time_Id)
                    intent.putExtra("back_date_day", back_date_day)
                    intent.putExtra("doesInfo", noOf_doesInfo_list)
                    intent.putExtra(
                        "member_obj_go", Gson().toJson(member_go)
                    )
                    intent.putExtra("fag", false)
                    intent.putExtra("edit", true)
                    intent.putExtra("from", "edit_time")
                    intent.putExtra("ageGroup", ageGroup)
                    startActivity(intent)
                    finish()
                }

            }

        }

        binding.tvselectslot.setOnClickListener {
            if (intent != null && intent.getBooleanExtra("fag", true)) {
                val intent = Intent(this, SlotActivity::class.java)
                intent.putExtra("Pkg", Gson().toJson(packageDetailsObj))
                intent.putExtra("package_total_price", package_total_price)
                intent.putExtra("totalcount", totalCount)
                intent.putExtra("ageGroup", ageGroup)
                intent.putExtra("from", "first_time")
                intent.putExtra(
                    "member_obj_go", Gson().toJson(newfamilyMemberList[g_selecetd_postion])
                )
                intent.putExtra("fag", true)
                startActivity(intent)

            } else {
                if (fromNotiType != null && fromNotiType.equals("customVaccine")) {
                    val intent = Intent(this, SlotActivity::class.java)
                    intent.putExtra("Obj", Gson().toJson(vaccineDetailsObj))
                    intent.putExtra("totalvaccinePrice", total_vacccine_price)
                    intent.putExtra("vaccine_id", vaccine_id)
                    intent.putExtra("totalcount", totalCount)
                    intent.putExtra("ageGroup", ageGroup)
                    intent.putExtra("from", "first_time")
                    intent.putExtra(
                        "member_obj_go", Gson().toJson(newfamilyMemberList[g_selecetd_postion])
                    )
                    intent.putExtra("doesInfo", noOf_doesInfo_list)
                    intent.putExtra("fag", false)
                    startActivity(intent)
                    Log.e("TAG", "lstnr:2 ${total_vacccine_price}")

                } else {
                    val intent = Intent(this, SlotActivity::class.java)
                    intent.putExtra("Obj", Gson().toJson(vaccineDetailsObj))
                    intent.putExtra("totalvaccinePrice", total_vacccine_price)
                    intent.putExtra("vaccine_id", vaccine_id)
                    intent.putExtra("totalcount", totalCount)
                    intent.putExtra("ageGroup", ageGroup)
                    intent.putExtra("from", "first_time")
                    intent.putExtra(
                        "member_obj_go", Gson().toJson(newfamilyMemberList[g_selecetd_postion])
                    )
                    intent.putExtra("doesInfo", noOf_doesInfo_list)
                    intent.putExtra("fag", false)
                    startActivity(intent)
                    Log.e("TAG", "lstnr:3 ${total_vacccine_price}")

                }

                //finish()
            }

        }

        binding.continue2.setOnClickListener {
            if (binding.nameview.selectedItemPosition > 0) {
                apiCallingForUpdateFamilyMember()
            } else {
                Toast.makeText(this, "Please Select Member", Toast.LENGTH_SHORT).show()
            }

        }
        binding.dobview.setOnClickListener {
            MaterialDatePicker.Builder.datePicker().setSelection(Date().time).build().apply {
                show(supportFragmentManager, this@FillInformationActivity.toString())
                addOnPositiveButtonClickListener {
                    selectDate = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(
                        Date(it)
                    )
                    getAge(selectDate)
                    binding.dobview.setText(selectDate)
                }
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
                    /* familyMemberList.add("${it.fullName} (${it.relation})")
                     familymemberHashMap.put("${it.fullName} (${it.relation})", it._id)
                     familymemberHashMapName.put("${it.fullName} (${it.relation})", it.memberType)*/
                    familyMemberList.add(it.fullName)
                    familymemberHashMap.put(it.fullName, it._id)
                    familymemberHashMapName.put(it.fullName, it.memberType)
                }
                /////from Noti
                if (fromNotiType != null && fromNotiType.equals("customVaccine")) {
                    for (kk in newfamilyMemberList) {
                        if (kk._id.equals(fromNotiMemberId)) {
                            memberspinner.setSelection(memberadapter.getPosition(kk.fullName))
                            memberspinner.setEnabled(false)
                            if (fromvaccine != null && fromvaccine.equals("From_vaccine")) {
                                binding.continue2.visibility = View.VISIBLE
                                binding.tvselectslot.visibility = View.GONE
                                binding.tvtimeDaySlot.visibility = View.VISIBLE
                                if (time_selected_date.isEmpty()) {
                                    binding.tvtimeDaySlot.text = "${back_date_day_slot}"
                                } else {
                                    binding.tvtimeDaySlot.text =
                                        "${time_selected_date}, ${vacccine_slot_Day}, ${vacccine_slot_Time}"
                                }
                                binding.tvslotData.visibility = View.VISIBLE
                                binding.ivEdit.visibility = View.VISIBLE
                            } else if (fromvaccine != null && fromvaccine.equals("From_Package")) {
                                binding.continue2.visibility = View.VISIBLE
                                binding.tvselectslot.visibility = View.GONE
                                binding.tvtimeDaySlot.visibility = View.VISIBLE
                                binding.ivEdit.visibility = View.VISIBLE
                                if (time_selected_date.isEmpty()) {
                                    binding.tvtimeDaySlot.text = "${back_date_day_slot}"
                                } else {
                                    binding.tvtimeDaySlot.text =
                                        "${time_selected_date}, ${package_slot_Day}, ${pacakge_slot_Time}"
                                }
                                binding.tvslotData.visibility = View.VISIBLE
                            } else {
                                binding.continue2.visibility = View.GONE
                                binding.tvslotData.visibility = View.GONE
                                binding.tvtimeDaySlot.visibility = View.GONE
                                binding.ivEdit.visibility = View.GONE
                            }
                        }
                    }
                }

                relation_new = newfamilyMemberList[0].relation
                println("------reeeeeeee$relation_new")

                if (intent != null && !intent.getStringExtra("member_obj_back").isNullOrBlank()) {
                    member_go = Gson().fromJson(
                        intent.getStringExtra("member_obj_back"), AskedData::class.java
                    )
                    println("------memback$member_go")
                    relation_new = member_go.relation
                    println("------reeeeeeee$relation_new")


                    if (relation_new.equals("Myself")) {
                        updateFamilyMemberId = member_go._id
                        println("-----000000yyyy$updateFamilyMemberId")
                        binding.childfilllay.visibility = View.GONE
                        binding.tvReletionHide.visibility = View.VISIBLE
                        binding.tvReletionHide.text = relation_new
                        binding.edAnyRection.setText(member_go.anyReaction)
                        /* memberspinner.setSelection(memberadapter.getPosition("${member_go.fullName}(${member_go.relation}"))*/
                        memberspinner.setSelection(memberadapter.getPosition(member_go.fullName))
                        relationspinner.setClickable(false)
                        relationspinner.setEnabled(false)
                        binding.dobview.setText(member_go.dob)
                        relationspinner.setSelection(
                            relationadapter.getPosition(
                                member_go.relation
                            )
                        )
                        genderspinner.setSelection(
                            genderadapter.getPosition(
                                member_go.gender
                            )
                        )
                        if (member_go.uploadVaccinationChart.isNotEmpty()) {
                            url_pdf = member_go.uploadVaccinationChart
                            println("---schr_url_pdf$url_pdf")
                            binding.pdfUpload.visibility = View.VISIBLE
                            binding.uploadBTN.visibility = View.INVISIBLE
                        } else {
                            binding.pdfUpload.visibility = View.GONE
                            binding.uploadBTN.visibility = View.VISIBLE
                        }
                        if (fromvaccine != null && fromvaccine.equals("From_vaccine")) {
                            binding.continue2.visibility = View.VISIBLE
                            binding.tvselectslot.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.VISIBLE
                            if (time_selected_date.isEmpty()) {
                                binding.tvtimeDaySlot.text = "${back_date_day_slot}"
                            } else {
                                binding.tvtimeDaySlot.text =
                                    "${time_selected_date}, ${vacccine_slot_Day}, ${vacccine_slot_Time}"
                            }
                            binding.tvslotData.visibility = View.VISIBLE
                            binding.ivEdit.visibility = View.VISIBLE
                        } else if (fromvaccine != null && fromvaccine.equals("From_Package")) {
                            binding.continue2.visibility = View.VISIBLE
                            binding.tvselectslot.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.VISIBLE
                            binding.ivEdit.visibility = View.VISIBLE
                            if (time_selected_date.isEmpty()) {
                                binding.tvtimeDaySlot.text = "${back_date_day_slot}"
                            } else {
                                binding.tvtimeDaySlot.text =
                                    "${time_selected_date}, ${package_slot_Day}, ${pacakge_slot_Time}"
                            }
                            binding.tvslotData.visibility = View.VISIBLE
                        } else {
                            binding.continue2.visibility = View.GONE
                            binding.tvslotData.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.GONE
                            binding.ivEdit.visibility = View.GONE
                        }
                        if (member_go.memberType.equals("KIDS")) {
                            binding.childfilllay.visibility = View.VISIBLE
//                            binding.previousTextView.setText(
//                                member_go.previousPediatrician
//                            )
                            deliveryspinner.setSelection(
                                deliveryadapter.getPosition(
                                    member_go.deliveryType
                                )
                            )
                            deliverPeriodyspinner.setSelection(
                                deliveryPeriodadapter.getPosition(
                                    member_go.deliveryPeriod
                                )
                            )
                            anyspinner.setSelection(
                                anyadapter.getPosition(
                                    member_go.seizuresAtBirth
                                )
                            )

                            binding.loremView.setText(
                                member_go.medicalDisorder
                            )
                            if (member_go.bithHistory.isNotEmpty()) {
                                url_pdf_birth = member_go.bithHistory
                                println("---schr_url_pdf_birth$url_pdf_birth")
                                binding.pdfBirth.visibility = View.VISIBLE
                                binding.uploadnicBTN3.visibility = View.INVISIBLE
                            } else {
                                binding.pdfBirth.visibility = View.GONE
                                binding.uploadnicBTN3.visibility = View.VISIBLE
                            }
                            if (member_go.nicuDetails.isNotEmpty()) {
                                url_pdf_nicu = member_go.nicuDetails
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
                        binding.tvReletionHide.visibility = View.GONE
                        binding.childfilllay.visibility = View.GONE
                        binding.edAnyRection.setText(member_go.anyReaction)
                        if (fromvaccine != null && fromvaccine.equals("From_vaccine")) {
                            binding.continue2.visibility = View.VISIBLE
                            binding.tvselectslot.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.VISIBLE
                            if (time_selected_date.isEmpty()) {
                                binding.tvtimeDaySlot.text = "${back_date_day_slot}"
                            } else {
                                binding.tvtimeDaySlot.text =
                                    "${time_selected_date}, ${vacccine_slot_Day}, ${vacccine_slot_Time}"
                            }
                            binding.tvslotData.visibility = View.VISIBLE
                            binding.ivEdit.visibility = View.VISIBLE
                        } else if (fromvaccine != null && fromvaccine.equals("From_Package")) {
                            binding.continue2.visibility = View.VISIBLE
                            binding.tvselectslot.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.VISIBLE
                            binding.ivEdit.visibility = View.VISIBLE
                            if (time_selected_date.isEmpty()) {
                                binding.tvtimeDaySlot.text = "${back_date_day_slot}"
                            } else {
                                binding.tvtimeDaySlot.text =
                                    "${time_selected_date}, ${package_slot_Day}, ${pacakge_slot_Time}"
                            }
                            binding.tvslotData.visibility = View.VISIBLE
                        } else {
                            binding.continue2.visibility = View.GONE
                            binding.tvslotData.visibility = View.GONE
                            binding.tvtimeDaySlot.visibility = View.GONE
                            binding.ivEdit.visibility = View.GONE
                        }
                        g_selecetd_postion = memberadapter.getPosition(member_go.fullName)
                        memberspinner.setSelection(g_selecetd_postion)
                        memberadapter.notifyDataSetChanged()

                        binding.dobview.setText(member_go.dob)
                        relationspinner.setSelection(
                            relationadapter.getPosition(
                                member_go.relation
                            )
                        )
                        genderspinner.setSelection(
                            genderadapter.getPosition(
                                member_go.gender
                            )
                        )
                        if (member_go.uploadVaccinationChart.isNotEmpty()) {
                            url_pdf = member_go.uploadVaccinationChart
                            println("---schr_url_pdf$url_pdf")
                            binding.pdfUpload.visibility = View.VISIBLE
                            binding.uploadBTN.visibility = View.INVISIBLE
                        } else {
                            binding.pdfUpload.visibility = View.GONE
                            binding.uploadBTN.visibility = View.VISIBLE
                        }
                        if (member_go.memberType.equals("KIDS")) {
                            binding.childfilllay.visibility = View.VISIBLE
//                            binding.previousTextView.setText(
//                                member_go.previousPediatrician
//                            )
                            deliveryspinner.setSelection(
                                deliveryadapter.getPosition(
                                    member_go.deliveryType
                                )
                            )
                            deliverPeriodyspinner.setSelection(
                                deliveryPeriodadapter.getPosition(
                                    member_go.deliveryPeriod
                                )
                            )
                            anyspinner.setSelection(
                                anyadapter.getPosition(
                                    member_go.seizuresAtBirth
                                )
                            )

                            binding.loremView.setText(
                                member_go.medicalDisorder
                            )
                            if (member_go.bithHistory.isNotEmpty()) {
                                url_pdf_birth = member_go.bithHistory
                                println("---schr_url_pdf_birth$url_pdf_birth")
                                binding.pdfBirth.visibility = View.VISIBLE
                                binding.uploadnicBTN3.visibility = View.INVISIBLE
                            } else {
                                binding.pdfBirth.visibility = View.GONE
                                binding.uploadnicBTN3.visibility = View.VISIBLE
                            }
                            if (member_go.nicuDetails.isNotEmpty()) {
                                url_pdf_nicu = member_go.nicuDetails
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
//                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseUpdateFamilyMember.observe(this) {
            if (it?.code == 200) {
                if (fromvaccine.equals("From_vaccine")) {
                    apiCallingForAddtoCart()
                    val intent = Intent(this, MyCartActivity::class.java)
                    intent.putExtra("from", "vaccine")
                    startActivity(intent)
                    //  startActivity(Intent(this, MyCartActivity::class.java).putExtra("from","From_vaccine"))
                    finish()
                } else {
                    apiCallingForAddtoCart()
                    startActivity(Intent(this, MyCartActivity::class.java))
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

    private fun apiCallingForAddtoCart() {
        val userMap: java.util.HashMap<String, Any> = prepareDataForAddtoCart()
        viewModel.onAddtoCart(this, userMap)
    }

    private fun prepareDataForAddtoCart(): java.util.HashMap<String, Any> {
        val hashMap: java.util.HashMap<String, Any> = java.util.HashMap()

        if (fromvaccine != null && fromvaccine.equals("From_Package")) {
            hashMap[MyConstants.kmemberId] = updateFamilyMemberId
            hashMap[MyConstants.kpackageId] = rev_package_id
            hashMap[MyConstants.kdayId] = package_day_Id
            hashMap[MyConstants.ksheduleId] = package_select_Time_Id
            hashMap[MyConstants.knoOfDose] = totalCount
        } else if (fromvaccine != null && fromvaccine.equals("From_vaccine")) {
            hashMap[MyConstants.kmemberId] = updateFamilyMemberId
            hashMap[MyConstants.kvaccineId] = rrrvaccine_id
            hashMap[MyConstants.kdayId] = vacccine_day_Id
            hashMap[MyConstants.ksheduleId] = vacccine_select_Time_Id
            hashMap[MyConstants.knoOfDose] = totalCount
            hashMap[MyConstants.kdoseInfo] = noOf_doesInfo_list
        }

        return hashMap
    }

    private fun apiCallingForUpdateFamilyMember() {
        val userMap: java.util.HashMap<String, Any> = prepareDataForUpdateMember()
        viewModel.onUpdateFamilyMember(this, userMap)
    }

    private fun prepareDataForUpdateMember(): java.util.HashMap<String, Any> {
        updateFamilyMemberId =
            familymemberHashMap[binding.nameview.selectedItem.toString()].toString()

        val hashMap: java.util.HashMap<String, Any> = java.util.HashMap()
        hashMap[MyConstants.kmemberId] = updateFamilyMemberId
        hashMap[MyConstants.kfullName] = binding.nameview.selectedItem.toString()
        hashMap[MyConstants.krelation] =
            if (relation_new.equals("Myself")) "Myself" else binding.relation.selectedItem.toString()
        hashMap[MyConstants.kDob] = binding.dobview.text.trim().toString()
        hashMap[MyConstants.kGender] = binding.genderSp.selectedItem.toString()
        hashMap[MyConstants.kMedicalCondition] = binding.edMedical.text.trim().toString()
        hashMap[MyConstants.kuploadVaccinationChart] = url_pdf
        hashMap[MyConstants.kanyReaction] = binding.edAnyRection.text.trim().toString()
        if (relatioName.equals("KIDS")) {
            hashMap[MyConstants.kmemberId] = updateFamilyMemberId
            hashMap[MyConstants.kmemberType] = "kids"
            hashMap[MyConstants.kdeliveryType] = binding.delivaryType.selectedItem.toString()
            hashMap[MyConstants.kdeliveryPeriod] = binding.deliveryPeriod.selectedItem.toString()
            hashMap[MyConstants.knicuDetails] = url_pdf_nicu
            hashMap[MyConstants.kseizuresAtBirth] = binding.any.selectedItem.toString()
            hashMap[MyConstants.kbithHistory] = url_pdf_birth
//            hashMap[MyConstants.kpreviousPediatrician] =
//                binding.previousTextView.text.trim().toString()
            hashMap[MyConstants.kmedicalDisorder] = binding.loremView.text.trim().toString()

        } else {
            hashMap[MyConstants.kmemberType] = "others"
        }

        return hashMap
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
                this, "Please install a File Manager.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    var docLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            val uri = data!!.data

            val filePath: String? = FileUtils.getReadablePathFromUri(applicationContext, uri!!)
            if (filePath != null) uploadToS3(
                filePath, com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.S3Bucket.DOC
            )
        }

    }

    /* ActivityResultCallback<Any> { result ->
         if (result.getResultCode() === Activity.RESULT_OK) {
             v
         }
     })*/

    private fun uploadToS3(path: String, fileType: String) {
        com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.S3Bucket(this,
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
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun getAge(date: String?): Int {
//        var age = 0
        try {
            val formatter = SimpleDateFormat("yyyy/MM/dd")
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

    override fun onResume() {
        super.onResume()
        if (intent != null && !intent.getStringExtra("Obj").isNullOrBlank()) {
            vaccineDetailsObj =
                Gson().fromJson(intent.getStringExtra("Obj"), AskedPackage::class.java)
            println("-----onjjj${vaccineDetailsObj}")
            Picasso.get().load(vaccineDetailsObj.vaccineImage).centerCrop().resize(100, 100)
                .into(binding.imageView18)
            binding.textView25.setText(vaccineDetailsObj.vaccineName)
            binding.textView26.setText(vaccineDetailsObj.description)
            binding.iiii.text = "₹$total_vacccine_price"
        } else if (intent != null && !intent.getStringExtra("Pkg").isNullOrBlank()) {
            packageDetailsObj = Gson().fromJson(
                intent.getStringExtra("Pkg"),
                com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage::class.java
            )
            println("-----pckjjj${packageDetailsObj}")
            rev_package_id = packageDetailsObj._id
            println("----rr_package_id${rev_package_id}")
            Picasso.get().load(packageDetailsObj.packageImage).centerCrop().resize(100, 100)
                .into(binding.imageView18)
            binding.textView25.setText(packageDetailsObj.packageName)
            binding.textView26.setText(packageDetailsObj.description)
            binding.iiii.text = "₹${packageDetailsObj.totalPrice}"

        }

    }
}
