package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityAddNewAddressBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.AddressList.AskedData

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddressSecond.SecondAddressActivity
import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.GPSService
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.Utils
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.Validator
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Location.SetLocationActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import com.catalyist.helper.ErrorUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson

import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddNewAddress : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewAddressBinding
    private lateinit var viewModel: ViewModalLogin
    var countryHashMap = HashMap<String, String>()
    var countryLst = ArrayList<String>()
    var cityLst = ArrayList<String>()
    var updateid: String = ""
    private lateinit var spinner1: Spinner
    private lateinit var adapter1: ArrayAdapter<String>
    lateinit var addressObj: AskedData
    private lateinit var citySpinner: Spinner
    private lateinit var cityAdapter: ArrayAdapter<String>
    private var intentCMData: Boolean = false
    private var intentCMMMMData: Boolean = false
    var editId: String = ""
    var from = ""
    private var cart_total_price: Double? = 0.0
    private var cart_id_list = ArrayList<String>()
    var discountPercentage: Double? = 0.0
    var walletAmount: Double? = 0.0
    var discamount: Double? = 0.0
    private var city_Name: String = ""
    private var state_Name: String = ""
    private var address_Name: String = ""
    private var pinCode: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        from = intent.getStringExtra("from").toString()
        println("-----from$from")
        if (from.equals("address_fill")) {
            cart_total_price = intent.getDoubleExtra("cart_price", 0.0)
            println("-----add_cart$cart_total_price")
            cart_id_list = intent.getSerializableExtra("cart_id_list") as ArrayList<String>
            println("-----add_cart$cart_id_list")
            discountPercentage = intent.getDoubleExtra("discount", 0.0)
            println("------discountPercentage$discountPercentage")
            walletAmount = intent.getDoubleExtra("walletAmount", 0.0)
            println("------walletAmount$walletAmount")
            discamount = intent.getDoubleExtra("discountAmount", 0.0)
            println("------discamount$discamount")
        }

        /*onStateListAPI()
        intentCMMMMData = (intent != null && intent.getBooleanExtra("fag", false))
        intentCMData = (intent != null && intent.getBooleanExtra("fag", false))*/
        val scList =
            resources.getStringArray(com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R.array.AddressType)
        val spinner = binding.tvAddressTypeSp
        val scAdapter = ArrayAdapter(
            this, com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R.layout.spinner_dropdown_item, scList
        )
        spinner.adapter = scAdapter

        /* citySpinner = binding.tvCitySp
         cityAdapter = ArrayAdapter(
             this,
             R.layout.simple_spinner_item,
             cityLst
         )
         cityAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
         citySpinner.adapter = cityAdapter*/



        if (intent != null && !intent.getStringExtra("Obj").isNullOrBlank()) {
            addressObj = Gson().fromJson(intent.getStringExtra("Obj"), AskedData::class.java)
            editId = addressObj._id
            binding.edName.setText(addressObj.contactPersonName)
            binding.edPhone.setText(addressObj.phoneNumber)
            binding.edEmail.setText(addressObj.streetAddress)
            binding.edPincode.setText(addressObj.zipCode)
            binding.checkBox2.isChecked = addressObj.defaultAddress
            binding.edPlat.setText(addressObj.flat_no)
            binding.edTower.setText(addressObj.tower)
            when (addressObj.addressType) {
                "Home" -> {
                    spinner.setSelection(1)
                }
                "Office" -> {
                    spinner.setSelection(2)
                }

            }
            binding.addnewAddress.tvTittle.text = "Edit Address"
            binding.button12.text = "Update Address"
        } else {
            binding.addnewAddress.tvTittle.text = "Add New Address"

        }
    }

    private fun initView() {
        /*   binding.tvStateSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
               override fun onItemSelected(
                   parent: AdapterView<*>?,
                   view: View?,
                   position: Int,
                   id: Long
               ) {

                   if (binding.tvStateSp.selectedItemPosition > 0) {
                       apiCallingForCity()
                   }
               }

               override fun onNothingSelected(parent: AdapterView<*>?) {

               }

           }*/

    }

    private fun lstnr() {
        binding.addnewAddress.ivBack.setOnClickListener {
//            onBackPressed()
            finish()

        }
        binding.edEmail.setOnClickListener {
            startActivity(
                Intent(this, SetLocationActivity::class.java)
            )
        }
        binding.button12.setOnClickListener {
            if (validationData()) {
                if (intent != null && intent.getBooleanExtra("fag", false)) {
                    apiCallingForUpdateAddress()
                } else {
                    apiCallingForAddAddress()
                }
            }
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        /*viewModel.responseStateList.observe(this) {
            if (it?.code == 200) {
                countryLst.clear()
                countryLst.add(0, "Please Select State")
                it?.states?.forEach {
                    countryHashMap[it?.name] = it.isoCode
                    countryLst.add(it?.name)
                }
                spinner1 = binding.tvStateSp
                adapter1 = ArrayAdapter(this, R.layout.simple_spinner_item, countryLst)
                adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                spinner1.adapter = adapter1
                if (intentCMData) {
                    println("=====${addressObj.state}====")
                    spinner1.setSelection(adapter1.getPosition(addressObj.state))
                    intentCMData = false
                }
                cityLst.clear()
                cityLst.add(0, "Please Select City")
                cityAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseCityList.observe(this) {
            if (it?.code == 200) {
                cityLst.clear()
                cityLst.add(0, "Please Select City")
                it?.cities?.forEach {
                    cityLst.add(it?.name ?: "")
                }

                if (intentCMMMMData) {
                    println("=====city${addressObj.city}====")
                    citySpinner.setSelection(cityAdapter.getPosition(addressObj.city))
                    intentCMMMMData = false
                }
                cityAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }*/
        viewModel.responseAddAddress.observe(this) {
            if (it?.code == 200) {
                updateid = it.addAddress._id
                if (from.equals("address_fill")) {
                    startActivity(
                        Intent(this, AddressActivity::class.java).putExtra(
                            "from", "back_address_fill"
                        ).putExtra("cart_price", cart_total_price)
                            .putExtra("cart_id_list", cart_id_list)
                            .putExtra("walletAmount", walletAmount)
                            .putExtra("discount", discountPercentage)
                            .putExtra("discountAmount", discamount)
                    )
                    finish()
                } else {
                    startActivity(
                        Intent(this, SecondAddressActivity::class.java)
                    )
                    finish()
                }
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseUpdateAddress.observe(this) {
            if (it?.code == 200) {
                finish()
//                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }



        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    /*  fun onStateListAPI() {
          viewModel.onStateList(this)
      }

      private fun apiCallingForCity() {
          val userMap: HashMap<String, Any> = prepareData()
          viewModel.onCityList(this, userMap)
      }

      private fun prepareData(): HashMap<String, Any> {
          val hashMap: HashMap<String, Any> = HashMap()
          hashMap[MyConstants.kstateCode] =
              countryHashMap[binding.tvStateSp.selectedItem.toString()].toString()
          return hashMap
      }*/

    private fun apiCallingForAddAddress() {
        val userMap: HashMap<String, Any> = prepareDataofAddress()
        viewModel.onAddAddress(this, userMap)
    }

    private fun prepareDataofAddress(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kcontactPersonName] = binding.edName.text.trim().toString()
        hashMap[MyConstants.kphoneNumber] = binding.edPhone.text.trim().toString()
        hashMap[MyConstants.kstreetAddress] = binding.edEmail.text.trim().toString()
        hashMap[MyConstants.kzipCode] = pinCode
        hashMap[MyConstants.kstate] = state_Name
        hashMap[MyConstants.kcity] = city_Name
        hashMap[MyConstants.kaddressType] = binding.tvAddressTypeSp.selectedItem.toString()
        hashMap[MyConstants.klat] = latitude
        hashMap[MyConstants.klong] = longitude
        hashMap[MyConstants.ktower] = binding.edTower.text.trim().toString()
        hashMap[MyConstants.kflat_no] = binding.edPlat.text.trim().toString()
//        hashMap[MyConstants.kisoCode] =
//            pinCode
        hashMap[MyConstants.kdefaultAddress] = if (binding.checkBox2.isChecked) true else false

        return hashMap
    }

    private fun apiCallingForUpdateAddress() {
        val userMap: HashMap<String, Any> = prepareDataofUpdateAddress()
        viewModel.onUpdateAddress(this, userMap)
    }

    private fun prepareDataofUpdateAddress(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kaddressId] = editId
        println("----eddit${editId}")
        hashMap[MyConstants.kcontactPersonName] = binding.edName.text.trim().toString()
        hashMap[MyConstants.kphoneNumber] = binding.edPhone.text.trim().toString()
        hashMap[MyConstants.kstreetAddress] = binding.edEmail.text.trim().toString()
        hashMap[MyConstants.kzipCode] = pinCode
        hashMap[MyConstants.kstate] = state_Name
        hashMap[MyConstants.kcity] = city_Name
        hashMap[MyConstants.kaddressType] = binding.tvAddressTypeSp.selectedItem.toString()
        hashMap[MyConstants.klat] = latitude
        hashMap[MyConstants.klong] = longitude
        hashMap[MyConstants.ktower] = binding.edTower.text.trim().toString()
        hashMap[MyConstants.kflat_no] = binding.edPlat.text.trim().toString()
        /*hashMap[MyConstants.kisoCode] =
            countryHashMap[binding.tvStateSp.selectedItem.toString()].toString()*/
        hashMap[MyConstants.kdefaultAddress] = if (binding.checkBox2.isChecked) true else false

        return hashMap
    }

    private fun validationData(): Boolean {
        if (binding.edName.text.trim().toString().length < 4) {
            Toast.makeText(
                applicationContext, "Please Enter Name!!", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (TextUtils.isEmpty(binding.edPhone.text.trim().toString())) {
            Toast.makeText(
                applicationContext, "Please Enter Mobile Number!!", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.edPhone.text.trim().toString().length < 10 || binding.edPhone.text.trim()
                .toString().length > 12
        ) {
            Toast.makeText(
                applicationContext, "Please Enter Valid Mobile Number!!", Toast.LENGTH_SHORT
            ).show()
            return false
        } /*else if (TextUtils.isEmpty(binding.edEmail.text.trim().toString())) {
            Toast.makeText(
                applicationContext, "Please Enter Street Address!!", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (!Validator.isValidEmail(Utils.getProperText(binding.edEmail))) {
            // Toaster.toast("Invalid Email Address!!")
            Toast.makeText(this, "Invalid Email Address!!", Toast.LENGTH_SHORT).show()
            return false

        }*/ /*else if (binding.tvStateSp.selectedItemPosition.equals(0)) {
            Toast.makeText(
                applicationContext,
                "Please Select State!!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.tvCitySp.selectedItemPosition.equals(0)) {
            Toast.makeText(
                applicationContext,
                "Please Select City!!",
                Toast.LENGTH_SHORT
            ).show()
        }*/
        else if (TextUtils.isEmpty(binding.edPlat.text?.trim().toString())) {
            Toast.makeText(
                applicationContext, "Please Enter Plat and Flat No.!!", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (TextUtils.isEmpty(binding.edTower.text?.trim().toString())) {
            Toast.makeText(
                applicationContext, "Please Enter Tower and Apartment No.!!", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (TextUtils.isEmpty(binding.edPincode.text?.trim().toString())) {
            Toast.makeText(
                applicationContext, "Please Enter Pincode!!", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.edPincode.text.trim().toString().length < 6) {
            Toast.makeText(
                applicationContext, "Please Enter Valid Pincode!!", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.tvAddressTypeSp.selectedItemPosition.equals(0)) {
            Toast.makeText(
                applicationContext, "Please Select Address Type!!", Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true

    }

    override fun onBackPressed() {
        super.onBackPressed()

    }


    fun getCurrentLocation() {
        val editor = this?.getSharedPreferences(MyConstants.PREF_NAME, MODE_PRIVATE)
        city_Name = editor!!.getString(MyConstants.kcity, "").toString()
        address_Name = editor.getString(MyConstants.kAddressNew, "").toString()
        state_Name = editor.getString(MyConstants.kstate, "").toString()
        pinCode = editor.getString(MyConstants.kisoCode, "").toString()
        // latitude = editor.getString(MyConstants.kLATITUDE, "")!!.toDouble()
        //longitude = editor.getString(MyConstants.kLONGITUDE, "")!!.toDouble()
        latitude = editor.getString(MyConstants.ktypeLATITUDE, "")!!.toDouble()
        longitude = editor.getString(MyConstants.ktypeLONGITUDE, "")!!.toDouble()
        println("--c$city_Name")
        println("--a$address_Name")
        println("--s$state_Name")
        println("--p$pinCode")
        println("--latt$latitude")
        println("--longttt$longitude")
        binding.edEmail.setText(address_Name)
        binding.tvStateSp.setText(state_Name)
        binding.tvCitySp.setText(city_Name)
        binding.edPincode.setText(pinCode)

    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
    }

}
