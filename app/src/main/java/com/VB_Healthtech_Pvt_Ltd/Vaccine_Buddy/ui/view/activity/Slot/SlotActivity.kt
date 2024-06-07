package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Slot

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivitySlotBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SlotAdapter.SlotDateAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SlotAdapter.SlotTimeAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.NoOfDoesInfoModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SlotList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SlotList.Slot
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDetails.AskedPackage
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.FillInformationActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleingConsolling.Schedule_Counseling
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getNewFormateCurrentDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getweekDay
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatTime
import com.catalyist.helper.ErrorUtil
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class SlotActivity : AppCompatActivity(), SlotDateAdapter.Date, SlotTimeAdapter.Time {
    lateinit var binding: ActivitySlotBinding
    private lateinit var viewModel: ViewModalLogin
    private var adptrSlotDate: SlotDateAdapter? = null
    private var dateslotlist = ArrayList<AskedData>()
    private var adptrSlotTime: SlotTimeAdapter? = null
    private var timeslotlist = ArrayList<Slot>()
    private var total_vacccine_price: Int = 0
    private var package_total_price: Double = 0.0
    private var totalCount: Int = 0
    lateinit var vaccineDetailsObj: AskedPackage
    lateinit var package_Details_Obj: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage
    private var Selected_time_id: String = ""
    private var time_Selected: String = ""
    private var day_Id: String = ""
    private var dayy: String = ""
    private var time_selected_date: String = ""
    lateinit var member_obj: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData
    private var vaccine_id: String = ""
    private var back_selected_time_id = ""
    private var back_selected_date_day_id = ""
    private var back_date_day = ""
    var isSelectedDate: Boolean = false
    var from = ""
    private var ageGroup: String = ""
    private var noOf_doesInfo_list = ArrayList<NoOfDoesInfoModel>()
    private var from_createOwn_Package: String = ""
    private var currentDate: String? = ""
    private var dateList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentDate = getNewFormateCurrentDate()
        apiResponse()
        initView()
        lstnr()

        if (intent != null && !intent.getStringExtra("member_obj_go").isNullOrBlank()) {
            member_obj = Gson().fromJson(
                intent.getStringExtra("member_obj_go"),
                com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData::class.java
            )
            println("----schh$member_obj")

        }
        ageGroup = intent.getStringExtra("ageGroup").toString()


        if (from.equals("schedule_counselling")) {
            back_selected_time_id = intent.getStringExtra("back_slot_time").toString()
            println("----s_back_selected_time_id$back_selected_time_id")
            back_selected_date_day_id = intent.getStringExtra("back_selected_date_day").toString()
            println("----s_back_selected_date_day_id$back_selected_time_id")
            back_date_day = intent.getStringExtra("back_date_day").toString()
            println("----s_back_date_day$back_date_day")
            isSelectedDate = intent.getBooleanExtra("edit", false)
            println("----isSelectedDate$isSelectedDate")
        } else {
            back_selected_time_id = intent.getStringExtra("back_slot_time").toString()
            println("----s_back_selected_time_id$back_selected_time_id")
            println("----p_back_selected_time_id$back_selected_time_id")
            back_selected_date_day_id = intent.getStringExtra("back_selected_date_day").toString()
            back_date_day = intent.getStringExtra("back_date_day").toString()
            isSelectedDate = intent.getBooleanExtra("edit", false)
            println("----isSelectedDate$isSelectedDate")
            println("----back_selected_date_day_id himmm $back_selected_date_day_id")
            println("----back_date_dayhiii $back_date_day")
        }


    }

    private fun initView() {
        binding.slotToolbar.tvTittle.text = "Slot"
        from = intent.getStringExtra("from").toString()
        println("-----s_from$from")

        CommonUtil.themeSet(this, window)
        apiCallingForSlotList()


        totalCount = intent.getIntExtra("totalcount", 0)
        println("---totalCount${totalCount}")
        vaccine_id = intent.getStringExtra("vaccine_id").toString()
        println("---vaccine_id${vaccine_id}")

        package_total_price = intent.getDoubleExtra("package_total_price", 0.0)
        total_vacccine_price = intent.getIntExtra("totalvaccinePrice", 0)

        Log.e("TAG", "initView:${total_vacccine_price}  ${package_total_price}")
       /* from_createOwn_Package = intent.getStringExtra("from_createOwn_Package").toString()
        println("----from_createOwn_PackageSlot$from_createOwn_Package")*/

        if (from.equals("schedule_counselling")) {
            binding.layout.visibility = View.GONE

        } /*else if (from.equals("schedule_edit_time")) {
            binding.layout.visibility = View.GONE
        } */ else if (intent != null && !intent.getStringExtra("Obj").isNullOrBlank()) {
            vaccineDetailsObj =
                Gson().fromJson(intent.getStringExtra("Obj"), AskedPackage::class.java)
            println("-----onjjj${vaccineDetailsObj}")
            Picasso.get().load(vaccineDetailsObj.vaccineImage).centerCrop().resize(100, 100)
                .into(binding.imageView18)
            binding.textView25.setText(vaccineDetailsObj.vaccineName)
            binding.textView26.setText(vaccineDetailsObj.description)
            binding.tvprice.text = "₹$total_vacccine_price"
            binding.tvOfferPriceSlot.visibility = View.GONE
            binding.divider17.visibility = View.GONE
            noOf_doesInfo_list =
                intent.getSerializableExtra("doesInfo") as ArrayList<NoOfDoesInfoModel>

        } else {
            package_total_price = intent.getDoubleExtra("total_price", 0.0)
            package_Details_Obj =
                Gson().fromJson(
                    intent.getStringExtra("Pkg"),
                    com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage::class.java
                )
            Picasso.get().load(package_Details_Obj.packageImage).centerCrop().resize(100, 100)
                .into(binding.imageView18)
            binding.textView25.setText(package_Details_Obj.packageName)
            binding.textView26.setText(package_Details_Obj.description)
            /*if (from_createOwn_Package.equals("create_own_package")) {
                binding.tvprice.text = "₹${package_Details_Obj.totalPrice}"
                binding.tvOfferPriceSlot.text = "₹${package_Details_Obj.totalPrice}"
                binding.tvOfferPriceSlot.visibility = View.VISIBLE
                binding.divider17.visibility = View.VISIBLE
            } else {*/
            println("csdsfsdvsdvsdvvv$package_Details_Obj")
                binding.tvprice.text = "₹${package_Details_Obj.totalPrice}"
                binding.tvOfferPriceSlot.text = "₹${package_Details_Obj.offerPrice}"
                binding.tvOfferPriceSlot.visibility = View.VISIBLE
                binding.divider17.visibility = View.VISIBLE
           // }

        }

    }

    private fun lstnr() {
        binding.slotToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvConfrmSlot.setOnClickListener {
            if (from.equals("schedule_counselling")) {
                if (!isSelectedDate) {
                    if (dayy.isEmpty()) {
                        Toast.makeText(this, "Please Select Date and Day!", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }
                if (time_Selected.isEmpty()) {
                    Toast.makeText(this, "Please Select Time!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (intent != null && intent.getBooleanExtra("schedule_fag", true)) {
                    val intent = Intent(
                        this,
                        Schedule_Counseling::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.putExtra("time", time_Selected)
                    intent.putExtra("fag", true)
                    intent.putExtra("day", dayy)
                    intent.putExtra("dayId", back_selected_date_day_id)
                    intent.putExtra("time_selected_date", time_selected_date)
                    intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                    intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                    intent.putExtra("back_date_day", back_date_day)
                    startActivity(intent)
                    finish()
                }
            } /*else if (from.equals("schedule_edit_time")) {
                if (!isSelectedDate) {
                    if (dayy.isEmpty()) {
                        Toast.makeText(this, "Please Select Date and Day!", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    if (time_Selected.isEmpty()) {
                        Toast.makeText(this, "Please Select Time!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
                if (intent != null && intent.getBooleanExtra("fag", true)) {
                    val intent = Intent(this, Schedule_Counseling::class.java)
                    intent.putExtra("time", time_Selected)
                    intent.putExtra("fag", true)
                    intent.putExtra("day", dayy)
                    intent.putExtra("dayId", back_selected_date_day_id)
                    intent.putExtra("time_selected_date", time_selected_date)
                    intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                    intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                    intent.putExtra("back_date_day", back_date_day)
                    startActivity(intent)
                    finish()
                }
            }*/ else if (from.equals("first_time")) {
                if (!isSelectedDate) {
                    if (dayy.isEmpty()) {
                        Toast.makeText(this, "Please Select Date and Day!", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    if (time_Selected.isEmpty()) {
                        Toast.makeText(this, "Please Select Time!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
                if (intent != null && intent.getBooleanExtra("fag", true)) {

                    /*if (from_createOwn_Package.equals("create_own_package")) {
                        val intent = Intent(
                            this,
                            FillInformationActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .putExtra(
                                "time",
                                time_Selected
                            )
                        intent.putExtra("day", dayy)
                        intent.putExtra("from_createOwn_Package", "create_own_package")
                        intent.putExtra("dayId", back_selected_date_day_id)
                        intent.putExtra("package_total_price", package_total_price)
                        intent.putExtra(
                            "time_selected_date",
                            time_selected_date
                        )
                        intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                        intent.putExtra("Pkg", Gson().toJson(package_Details_Obj))
                        intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                        intent.putExtra("from_slot", "From_Package")
                        intent.putExtra("back_date_day", back_date_day)
                        intent.putExtra("totalcount", totalCount)
                        intent.putExtra("ageGroup", ageGroup)
                        intent.putExtra("fag", true)
                        startActivity(intent)
                        finish()
                    } else {*/
                        val intent = Intent(
                            this,
                            FillInformationActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .putExtra(
                                "time",
                                time_Selected
                            )
                        intent.putExtra("day", dayy)
                        intent.putExtra("dayId", back_selected_date_day_id)
                        intent.putExtra("package_total_price", package_total_price)
                        intent.putExtra(
                            "time_selected_date",
                            time_selected_date
                        )
                        intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                        intent.putExtra("Pkg", Gson().toJson(package_Details_Obj))
                        intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                        intent.putExtra("from_slot", "From_Package")
                        intent.putExtra("back_date_day", back_date_day)
                        intent.putExtra("totalcount", totalCount)
                        intent.putExtra("ageGroup", ageGroup)
                        intent.putExtra("fag", true)
                        startActivity(intent)
                        finish()
                  //  }

                } else {
                    val intent = Intent(
                        this,
                        FillInformationActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra(
                            "time",
                            time_Selected
                        )
                    intent.putExtra("day", dayy)
                    intent.putExtra("dayId", back_selected_date_day_id)
                    intent.putExtra("totalPrice", total_vacccine_price)
                    intent.putExtra("totalcount", totalCount)
                    intent.putExtra("time_selected_date", time_selected_date)
                    intent.putExtra("Obj", Gson().toJson(vaccineDetailsObj))
                    intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                    intent.putExtra("from_slot", "From_vaccine")
                    intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                    intent.putExtra("back_date_day", back_date_day)
                    intent.putExtra("doesInfo", noOf_doesInfo_list)
                    intent.putExtra("ageGroup", ageGroup)
                    intent.putExtra("fag", false)
                    startActivity(intent)
                    finish()
                }
            } else if (from.equals("edit_time")) {
                println("-----bbbbtime_Selected$time_Selected")

                if (!isSelectedDate) {
                    if (dayy.isEmpty()) {
                        Toast.makeText(this, "Please Select Date and Day!", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }

                if (time_Selected.isEmpty()) {
                    Toast.makeText(this, "Please Select Time!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (intent != null && intent.getBooleanExtra("fag", true)) {
                    /*if (from_createOwn_Package.equals("create_own_package")) {
                        val intent = Intent(
                            this,
                            FillInformationActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .putExtra(
                                "time",
                                time_Selected
                            )
                        intent.putExtra("day", dayy)
                        intent.putExtra("dayId", back_selected_date_day_id)
                        intent.putExtra("package_total_price", package_total_price)
                        intent.putExtra(
                            "time_selected_date",
                            time_selected_date
                        )
                        intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                        intent.putExtra("Pkg", Gson().toJson(package_Details_Obj))
                        intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                        intent.putExtra("from_slot", "From_Package")
                        intent.putExtra("totalcount", totalCount)
                        intent.putExtra("back_date_day", back_date_day)
                        intent.putExtra("from_createOwn_Package", "create_own_package")
                        intent.putExtra("fag", true)
                        intent.putExtra("ageGroup", ageGroup)
                        startActivity(intent)
                        finish()
                    } else {*/
                        val intent = Intent(
                            this,
                            FillInformationActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .putExtra(
                                "time",
                                time_Selected
                            )
                        intent.putExtra("day", dayy)
                        intent.putExtra("dayId", back_selected_date_day_id)
                        intent.putExtra("package_total_price", package_total_price)
                        intent.putExtra(
                            "time_selected_date",
                            time_selected_date
                        )
                        intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                        intent.putExtra("Pkg", Gson().toJson(package_Details_Obj))
                        intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                        intent.putExtra("from_slot", "From_Package")
                        intent.putExtra("totalcount", totalCount)
                        intent.putExtra("back_date_day", back_date_day)
                        intent.putExtra("fag", true)
                        intent.putExtra("ageGroup", ageGroup)
                        startActivity(intent)
                        finish()
                  //  }

                } else {
                    val intent = Intent(
                        this,
                        FillInformationActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra(
                            "time",
                            time_Selected
                        )
                    intent.putExtra("day", dayy)
                    intent.putExtra("dayId", back_selected_date_day_id)
                    intent.putExtra("totalPrice", total_vacccine_price)
                    intent.putExtra("totalcount", totalCount)
                    intent.putExtra("time_selected_date", time_selected_date)
                    intent.putExtra("Obj", Gson().toJson(vaccineDetailsObj))
                    intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                    intent.putExtra("from_slot", "From_vaccine")
                    intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                    intent.putExtra("back_date_day", back_date_day)
                    intent.putExtra("doesInfo", noOf_doesInfo_list)
                    intent.putExtra("ageGroup", ageGroup)
                    intent.putExtra("fag", false)
                    startActivity(intent)
                    finish()
                }
            }
            /* if (!isSelectedDate) {
             if (dayy.isEmpty()) {
                 Toast.makeText(this, "Please Select Date and Day!", Toast.LENGTH_SHORT).show()
                 return@setOnClickListener
             }
             if (time_Selected.isEmpty()) {
                 Toast.makeText(this, "Please Select Time!", Toast.LENGTH_SHORT).show()
                 return@setOnClickListener
             } else {
                 if (intent != null && intent.getBooleanExtra("fag", true)) {
                     val intent = Intent(
                         this,
                         FillInformationActivity::class.java
                     )
                         .putExtra(
                             "time",
                             time_Selected
                         )
                     intent.putExtra("day", dayy)
                     intent.putExtra("dayId", back_selected_date_day_id)
                     intent.putExtra("package_total_price", package_total_price)
                     intent.putExtra(
                         "time_selected_date",
                         time_selected_date
                     )
                     intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                     intent.putExtra("Pkg", Gson().toJson(package_Details_Obj))
                     intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                     intent.putExtra("from_slot", "From_Package")
                     intent.putExtra("back_date_day", back_date_day)
                     intent.putExtra("fag", true)
                     startActivity(intent)
                     finish()
                 } else {
                     val intent = Intent(
                         this,
                         FillInformationActivity::class.java
                     )
                         .putExtra(
                             "time",
                             time_Selected
                         )
                     intent.putExtra("day", dayy)
                     intent.putExtra("dayId", back_selected_date_day_id)
                     intent.putExtra("totalPrice", total_vacccine_price)
                     intent.putExtra("totalcount", totalCount)
                     intent.putExtra("time_selected_date", time_selected_date)
                     intent.putExtra("Obj", Gson().toJson(vaccineDetailsObj))
                     intent.putExtra("select_vaccine_time_id", back_selected_time_id)
                     intent.putExtra("from_slot", "From_vaccine")
                     intent.putExtra("member_obj_back", Gson().toJson(member_obj))
                     intent.putExtra("back_date_day", back_date_day)
                     intent.putExtra("fag", false)
                     startActivity(intent)
                     finish()
                 }
             }

         }*/


        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseSlotList.observe(this) {
            if (it?.code == 200) {
                dateslotlist.clear()
                dateslotlist.addAll(it.askedData)
                /*  if (back_selected_date_day_id == "null") {
                      byDefaultDate = setFormatDate(dateslotlist[0].date)
                  } else {
                      dateslotlist.find { it._id == back_selected_date_day_id }?.let {
                          byDefaultDate = setFormatDate(it.date)
                      }
                  }*/
                /* if (!back_date_day.isEmpty()) {
                     byDefaultDate = back_date_day.split(",").get(0)
                 } else {
                     byDefaultDate = setFormatDate(dateslotlist[0].date)
                 }*/
                binding.rvSlotDate.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                adptrSlotDate = SlotDateAdapter(this, dateslotlist, this, back_selected_date_day_id)
                binding.rvSlotDate.adapter = adptrSlotDate
                adptrSlotDate!!.notifyDataSetChanged()

                timeslotlist.clear()
               /* for (date in it.askedData) {
                    dateList.addAll(listOf(date.date))
                    if (setFormatDate(dateList.toString()) == currentDate) {
                        timeslotlist.addAll(
                            it.askedData.get(
                                getSelectedPosition(
                                    back_selected_date_day_id, dateslotlist
                                )
                            ).slot
                        )
                    } else {

                    }
                }*/
                timeslotlist.addAll(
                    it.askedData.get(
                        getSelectedPosition(
                            back_selected_date_day_id, dateslotlist
                        )
                    ).slot
                )

                binding.rvSlotTime.layoutManager = GridLayoutManager(this, 3)
                if (it.askedData.get(0).day == getweekDay()) {
                    adptrSlotTime = SlotTimeAdapter(
                        this, timeslotlist, this, back_selected_time_id, true
                    )
                } else {
                    adptrSlotTime = SlotTimeAdapter(
                        this, timeslotlist, this, back_selected_time_id, false
                    )
                }
                binding.rvSlotTime.adapter = adptrSlotTime
                adptrSlotTime!!.notifyDataSetChanged()

            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun getSelectedPosition(
        backSelectedDateDayId: String,
        dateslotlist: ArrayList<AskedData>
    ): Int {
        Log.e("TAG", "getSelectedPosition:${backSelectedDateDayId}")
        var position = -1
        for (data in dateslotlist) {
            position++
            if (data._id.equals(backSelectedDateDayId)) {
                Log.d("TAG", "getSelectedPosition: ")
                return position
            }
        }
        return 0
    }

    private fun apiCallingForSlotList() {
        val userMap: java.util.HashMap<String, Any> = prepareDataForAddtoCart()
        viewModel.onSlotList(this, userMap)
    }

    private fun prepareDataForAddtoCart(): java.util.HashMap<String, Any> {
        val hashMap: java.util.HashMap<String, Any> = java.util.HashMap()
        hashMap[MyConstants.kslotFor] =
            if (from.equals("schedule_counselling")) "doctor" else "nurse"
        return hashMap
    }

//    private fun changeTime(day: String) {
//        val userMap: java.util.HashMap<String, Any> = prepareDataForAddtoCart()
//        viewModel.onSlotList(this, userMap)
//    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDateSeleect(
        msg: AskedData,
        position: Int,
        dayId: String,
        day: String,
        date: String
    ) {
        val item = msg.slot.size
        back_selected_date_day_id = dayId
        dayy = day
        time_selected_date = date
        if (item == 0) {
            binding.rvSlotTime.visibility = View.GONE
            Toast.makeText(this, "No Slot Available!", Toast.LENGTH_LONG).show()
        } else {
            println("===============$date")
            binding.rvSlotTime.visibility = View.VISIBLE
            timeslotlist.clear()
            timeslotlist.addAll(msg.slot)
            binding.rvSlotTime.layoutManager = GridLayoutManager(this, 3)
            if (day == getweekDay()) {
                adptrSlotTime = SlotTimeAdapter(
                    this,
                    timeslotlist,
                    this,
                    "${System.currentTimeMillis()}laxman",
                    true
                )
            } else {
                adptrSlotTime = SlotTimeAdapter(
                    this,
                    timeslotlist,
                    this,
                    "${System.currentTimeMillis()}laxman",
                    false
                )
            }

            binding.rvSlotTime.adapter = adptrSlotTime
            adptrSlotTime!!.notifyDataSetChanged()

        }
    }

    override fun onTimeSelect(id: String, time: String) {
        back_selected_time_id = id
        time_Selected = time
        println("-----time_Selected$time_Selected")
    }
}






