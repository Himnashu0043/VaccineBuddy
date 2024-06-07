package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ChooseVaccine

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityChooseVaccineBabyBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.ChooseVaccineAdapter.ChooseChildAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.ChooseVaccineAdapter.ChooseVaccineAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.ExplorePackageAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SpinnerAdapterBrands
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.Grouped
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.Item
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.SendData.TestData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import com.catalyist.helper.ErrorUtil
import com.google.gson.Gson

class ChooseVaccineBabyActivity : AppCompatActivity(), ChooseChildAdapter.Child,
    ChooseVaccineAdapter.Parent {
    lateinit var bin: ActivityChooseVaccineBabyBinding
    private var categoryAdapter: ChooseVaccineAdapter? = null
    var parentSelectionCount: Int = 0
    var homeVaccinationPrice1: Int = 0
    var homeVaccinationFee: Int = 0
    var sumOfVaccinePrice: Int = 0
    var sumOfVaccinePrice2: Int = 0
    private var subCategoryList = ArrayList<String>()
    private var subCategoryHashMapID: HashMap<String, String> = HashMap()
    private var subCategoryId: String = ""
    private lateinit var viewModel: ViewModalLogin
    var categoryId: String = "6321875f0a77c07dc659ea55"
    var selected_Name: String = ""
    var vaccineID: String = ""
    var vaccineName: String = ""
    var vaccinePrice: String = ""
    private var testData = ArrayList<TestData>()
    private var amountList = ArrayList<Int>()
    private var packageId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityChooseVaccineBabyBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        bin.rcychoose.layoutManager = LinearLayoutManager(this)
        bin.choosetoolbar.tvTittle.text = "Create your own package"
        subCategoryList.add(0, "Select SubCategory")
        apiforSubCategoryList()
    }

    private fun spinnerForSubCategoryList(memberList: List<String>) {
        val adapter =
            SpinnerAdapterBrands(
                this,
                R.layout.spinner_dropdown_item, memberList
            )
        bin.memberSpinner.adapter = adapter
        bin.memberSpinner.onItemSelectedListener = onItemSelectedStateListenerMemberList

    }

    ///////////spinner program SubCategoryList////
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
            selected_Name = itemSelected
            println("-----name$selected_Name")
            subCategoryId =
                subCategoryHashMapID.get(bin.memberSpinner.selectedItem.toString()).toString()
            println("---memberId$subCategoryId")
            if (bin.memberSpinner.selectedItemPosition > 0) {
                apiforCreateOwnPackageList()
                bin.rcychoose.visibility = View.VISIBLE
            } else {
                bin.rcychoose.visibility = View.GONE
            }
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }

    private fun lstnr() {

        bin.choosetoolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.textView179.setOnClickListener {
            if (parentSelectionCount < 1) {
                Toast.makeText(this, "Please Create Own Package!", Toast.LENGTH_SHORT).show()
            } else {
                createOwnPackagePopup()
            }

        }
        bin.tvMember.setOnClickListener {
            bin.memberSpinner.performClick()
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this)[ViewModalLogin::class.java]
        viewModel.responseSubcategoryList.observe(this) {
            if (it?.code == 200) {
                it.askedData.forEach {
                    subCategoryList.add(it.subCategoryName)
                    subCategoryHashMapID.put(it.subCategoryName, it._id)
                }
                spinnerForSubCategoryList(subCategoryList)
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseCreateOwnPackageList.observe(this) {
            if (it?.code == 200) {
                println("laxman_____$it")
                if (it.grouped.size <= 0) {
                    Toast.makeText(this, "Data not Found !", Toast.LENGTH_SHORT).show()
                    bin.rcychoose.visibility = View.GONE
                } else {
                    bin.rcychoose.visibility = View.VISIBLE
                    homeVaccinationFee = it.grouped.get(0).items.get(0).homeVaccinationFee.toInt()

                    categoryAdapter = ChooseVaccineAdapter(
                        this,
                        getModifiedList(it.grouped),

                        this,
                        this
                    )
                    bin.rcychoose.adapter = categoryAdapter
                    categoryAdapter!!.notifyDataSetChanged()
                }

            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseAddCreateOwnPackage.observe(this) {
            if (it?.code == 200) {
                packageId = it.addPackage._id
                startActivity(
                    Intent(this, PackageDetailsActivity::class.java).putExtra(
                        "packageId",
                        packageId
                    )
                )
                finish()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
            Toast.makeText(this, "Data not Found !", Toast.LENGTH_SHORT).show()
            bin.rcychoose.visibility = View.GONE

        }
    }

    private fun getModifiedList(grouped: List<Grouped>): ArrayList<Grouped> {
        val modifyList = ArrayList<Grouped>()
        for (data in grouped) {
            data.isSelectStatus = false
            amountList.add(0)
        }
        modifyList.addAll(grouped)

        print("getModifiedList${Gson().toJson(modifyList)}")
        return modifyList

    }

    private fun apiforSubCategoryList() {
        viewModel.onSubcategoryList(this, categoryId)
    }

    private fun apiforCreateOwnPackageList() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onCreateOwnPackageList(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.ksubCategoryId] = subCategoryId
        return hashMap
    }

    private fun apiforAddCreateOwnPackage() {
        val userMap: HashMap<String, Any> = prepareDataforAddCreateOwnPackage()
        viewModel.onAddCreateOwnPackage(this, userMap)
    }

    private fun prepareDataforAddCreateOwnPackage(): HashMap<String, Any> {
        homeVaccinationPrice1 = parentSelectionCount * homeVaccinationFee
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kpackageName] = "Custom Package"
        hashMap[MyConstants.kprice] =
            sumOfVaccinePrice
        //single price ke liye
        /*  sumOfVaccinePrice - (parentSelectionCount * homeVaccinationFee)*/
        hashMap[MyConstants.kdescription] = "Custom package with customize Vaccines"
        hashMap[MyConstants.khomeVaccinationFee] = homeVaccinationFee
        hashMap[MyConstants.kimage] =
            "https://cdn.the-scientist.com/assets/articleNo/67152/aImg/36068/vax-thumb-l.png"
        hashMap[MyConstants.kdoseInfo] = testData
        hashMap[MyConstants.ktotalVaccinationFee] = parentSelectionCount * homeVaccinationFee
        hashMap[MyConstants.ktotalPrice] = sumOfVaccinePrice
        return hashMap
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun createOwnPackagePopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.create_own_package_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val packagePrice11 = dialog.findViewById<TextView>(R.id.tvPackage1Price)
        val homeVaccinationPrice = dialog.findViewById<TextView>(R.id.tvhomePrice)
        val totalPrice = dialog.findViewById<TextView>(R.id.tvtotalPrice)
        val cancel = dialog.findViewById<TextView>(R.id.tvcancel)
        val ok = dialog.findViewById<TextView>(R.id.tvOk)
        packagePrice11.setText("₹ ${sumOfVaccinePrice2 - (parentSelectionCount * homeVaccinationFee)}")
        homeVaccinationPrice.setText("₹ ${homeVaccinationFee * parentSelectionCount}")
        totalPrice.setText(bin.tvBotomPrice.text.toString())
        ok.setOnClickListener {
            dialog.dismiss()
            apiforAddCreateOwnPackage()
        }
        cancel.setOnClickListener {
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

    // child click
    override fun onChildClick(
        msg: Item,
        checkType: Boolean,
        parentPos: Int,
        days: String,
        countDay: String
    ) {
        val testDoes = ArrayList<Any>()
        val testDoesAgeDay = ArrayList<Any>()
        testDoesAgeDay.add(countDay)
        testDoes.add(days)
        var sumCal = 0
        homeVaccinationPrice1 = parentSelectionCount * homeVaccinationFee
        vaccineID = msg.id
        vaccineName = msg.name
        vaccinePrice = msg.price
        if (checkType) {
            if (vaccinePrice.isNotEmpty()) {
                sumCal = amountList[parentPos] + vaccinePrice.trim().toInt()
                amountList[parentPos] = sumCal
                setAmountList()
            }
            testData.add(
                TestData(
                    vaccineID,
                    vaccineName,
                    vaccinePrice,
                    msg.description,
                    testDoes,
                    testDoesAgeDay
                )
            )
        } else {
            if (vaccinePrice.isNotEmpty()) {
                amountList[parentPos] = amountList[parentPos] - vaccinePrice.trim().toInt()
                setAmountList()
            }

            testData.remove(
                TestData(
                    vaccineID,
                    vaccineName,
                    vaccinePrice,
                    msg.description,
                    testDoes,
                    testDoesAgeDay
                )
            )
        }


    }

    override fun subChildClick(msg: Item, parentPos: Int,checkStatue:Boolean) {
        if (checkStatue){
          /*  testData.remove(
                TestData(
                    vaccineID,
                    vaccineName,
                    vaccinePrice,
                    msg.description,
                    testDoes,
                    testDoesAgeDay
                )
            )*/
        }else{
          /*  testData.remove(
                TestData(
                    vaccineID,
                    vaccineName,
                    vaccinePrice,
                    msg.description,
                    testDoes,
                    testDoesAgeDay
                )
            )*/
        }

    }

    private fun setAmountList() {
        var sumOfVaccinePrice1 = 0
        for (amount in amountList) {
            sumOfVaccinePrice1 += amount
        }
        sumOfVaccinePrice = sumOfVaccinePrice1
        sumOfVaccinePrice2 = sumOfVaccinePrice1
        bin.tvBotomPrice.text = "₹ ${sumOfVaccinePrice1}"
    }

    override fun onParentClick(age: String, normalAge: String, checkType: Boolean, position: Int) {
        if (checkType) {
            parentSelectionCount += 1
            amountList[position] = homeVaccinationFee
        } else {
            amountList[position] = 0
            parentSelectionCount -= 1
//            testDoes.removeAt(position)
//            testDoesAgeDay.removeAt(position)
            categoryAdapter!!.notifyItemChanged(position)
            setAmountList()

        }


    }


}



