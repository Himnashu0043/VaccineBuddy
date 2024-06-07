package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccinationChart

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityVaccinationChartBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SpinnerAdapterBrands
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.VaccinationChart.VaccinationChartAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddFamilyMember
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Package.PackageActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleingConsolling.Schedule_Counseling
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.DoseForAge.Grouped
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.newSchedule.NewScheduleList
import com.catalyist.helper.ErrorUtil

class VaccinationChartActivity : AppCompatActivity() {
    lateinit var bin: ActivityVaccinationChartBinding
    private var adptr: VaccinationChartAdapter? = null
    private var familyMemberList = ArrayList<String>()
    private var memberHashMapID: HashMap<String, String> = HashMap()
    private var newfamilyMemberList = ArrayList<AskedData>()
    private var testData: ArrayList<Grouped> = ArrayList()
    private var newtestData: ArrayList<NewScheduleList.Result> = ArrayList()
    private var memberDOB: String = ""
    private var back_from = ""
    var age = 0
    var gender: String = ""
    private lateinit var viewModel: ViewModalLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityVaccinationChartBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        bin.vaccinechartToolbar.tvTittle.text = "Vaccination Schedule Chart"
        familyMemberList.add(0, "Select Member")
        back_from = intent.getStringExtra("back_from").toString()
        if (back_from.equals("back_vaccineChart")) {
            apiCallingForFamilyMemberList()
        } else {
            apiCallingForFamilyMemberList()
        }

    }

    private fun lstnr() {
        bin.vaccinechartToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.tvMember.setOnClickListener {
            bin.memberSpinner.performClick()
        }
        bin.constraintLayout26.setOnClickListener {
            startActivity(Intent(this, Schedule_Counseling::class.java))

        }
        bin.constraintLayout27.setOnClickListener {
            startActivity(
                Intent(this, PackageActivity::class.java).putExtra(
                    "from",
                    "VaccineFragment"
                )
            )
        }
        bin.buyVaccineLay.setOnClickListener {
            startActivity(
                Intent(this, PackageActivity::class.java).putExtra(
                    "from",
                    "VaccineFragment"
                )
            )
        }
        bin.addMember.setOnClickListener {
            startActivity(
                Intent(this, AddFamilyMember::class.java).putExtra(
                    "redirection",
                    "vaccineChart"
                )
            )

        }
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
            memberDOB = memberHashMapID.get(bin.memberSpinner.selectedItem.toString()).toString()
            println("---memberId$memberDOB")
            age = getAge(memberDOB)

            println("------age$age")
            if (bin.memberSpinner.selectedItemPosition > 0) {
                bin.dobviewBabyage.setText(newfamilyMemberList.get(postion - 1).dob)
                bin.ageviewBabyge.setText("${getAge(newfamilyMemberList.get(postion - 1).dob)} year")
                bin.relationviewBabyage.setText(newfamilyMemberList.get(postion - 1).relation)
                gender = newfamilyMemberList.get(postion - 1).gender
//                apiCallingForDoseForAge()
                apiNew()
//                bin.mainVaccineChart.visibility = View.VISIBLE
//                bin.buyVaccineLay.visibility = View.GONE
            }/* else {
                bin.mainVaccineChart.visibility = View.GONE
                bin.buyVaccineLay.visibility = View.VISIBLE
            }*/
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseFamilyMemberList.observe(this) {
            if (it?.code == 200) {
                newfamilyMemberList.clear()
                newfamilyMemberList.addAll(it.askedData)
                it.askedData.forEach {
                    familyMemberList.add("${it.fullName}(${it.relation})")
                    memberHashMapID.put("${it.fullName}(${it.relation})", it.dob)
                }
                spinnerForMemberList(familyMemberList)
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
       /* viewModel.responseDoseForAge.observe(this) {
            if (it?.code == 200) {
                if (it.grouped.size <= 0) {
                    Toast.makeText(this, "Data not Found!", Toast.LENGTH_SHORT).show()
                    bin.mainVaccineChart.visibility = View.GONE
                    bin.buyVaccineLay.visibility = View.VISIBLE
                } else {
                    bin.mainVaccineChart.visibility = View.VISIBLE
                    bin.buyVaccineLay.visibility = View.GONE
                }
                testData.clear()
                testData.addAll(it.grouped)
                bin.does.rcyvaccinedose.layoutManager =
                    LinearLayoutManager(this)
                adptr =
                    VaccinationChartAdapter(
                        this,
                        testData, memberDOB
                    )
                bin.does.rcyvaccinedose.adapter = adptr
                adptr!!.notifyDataSetChanged()
                // commonSetDoseVaccine(it.grouped as ArrayList<Record>)
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }*/
        viewModel.responseNewSchedule.observe(this) {
            if (it?.code == 200) {
                if (it.result.size <= 0) {
                    Toast.makeText(this, "Data not Found!", Toast.LENGTH_SHORT).show()
                    bin.mainVaccineChart.visibility = View.GONE
                    bin.buyVaccineLay.visibility = View.VISIBLE
                } else {
                    bin.mainVaccineChart.visibility = View.VISIBLE
                    bin.buyVaccineLay.visibility = View.GONE
                }
                newtestData.clear()
                newtestData.addAll(it.result)
                bin.does.rcyvaccinedose.layoutManager =
                    LinearLayoutManager(this)
                adptr =
                    VaccinationChartAdapter(
                        this,
                        newtestData, memberDOB
                    )
                bin.does.rcyvaccinedose.adapter = adptr
                adptr!!.notifyDataSetChanged()
                // commonSetDoseVaccine(it.grouped as ArrayList<Record>)
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

    private fun apiCallingForDoseForAge() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onDoseForAge(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kage] = age * 365
        hashMap[MyConstants.kGender] = gender
        return hashMap
    }

    private fun apiNew() {
        viewModel.onNewSchedulrList(this, age * 365, gender)
    }


    /* private fun commonSetDoseVaccine(
         doselist: ArrayList<Record>,

         ) {
         val hashMap: HashMap<String, StatusMemberRecordModel> = HashMap()
         var tt = ""
         for (doseInfo in doselist) {
             tt += doseInfo.vaccines
             if (hashMap.containsKey(doseInfo.ageOfBaby)) {
                 println("----ttt$tt")
                 hashMap.put(
                     doseInfo.ageOfBaby,
                     StatusMemberRecordModel(
                         doseInfo.status,
                         doseInfo.completeDate,
                         doseInfo.nurseName,
                         tt
                     )

                 )
             } else {
                 hashMap.put(
                     doseInfo.ageOfBaby,
                     StatusMemberRecordModel(
                         doseInfo.status,
                         doseInfo.completeDate,
                         doseInfo.nurseName,
                         tt
                     )
                 )
             }
         }
         commonsSet.add(
             PackageMemberRecordModel(
                 doselist.get(0).ageOfBaby,
                 StatusMemberRecordModel(
                     doselist[0].status,
                     doselist[0].completeDate,
                     doselist[0].nurseName,
                     tt
                 )
             )
         )
         bin.does.rcyvaccinedose.layoutManager =
             LinearLayoutManager(this)
         adptr =
             VaccinationChartAdapter(
                 this,
                 commonsSet
             )
         bin.does.rcyvaccinedose.adapter = adptr
         adptr!!.notifyDataSetChanged()

         Log.e("laxman", "commonSetDoseVaccine:${Gson().toJson(commonsSet)} ")

     }*/

    override fun onBackPressed() {
        super.onBackPressed()
    }
}