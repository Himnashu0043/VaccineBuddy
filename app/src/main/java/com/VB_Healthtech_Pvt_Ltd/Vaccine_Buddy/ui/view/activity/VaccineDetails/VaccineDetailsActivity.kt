package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityProductDetailsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Does.DoesAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.vaccineRelated.VaccineRelatedProductAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.GPSService
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.NoOfDoesInfoModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDetails.AskedPackage
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDetails.DoseInfo
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.FillInformationActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Location.SetLocationActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AccountCreateActivity
import com.catalyist.helper.CustomLoader
import com.catalyist.helper.ErrorUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class VaccineDetailsActivity : AppCompatActivity(), DoesAdapter.Does,
    VaccineRelatedProductAdapter.OnClickItem, GPSService.OnLocationUpdate {
    lateinit var bin: ActivityProductDetailsBinding
    private lateinit var viewModel: ViewModalLogin
    private var adapterRelatedpackage: VaccineRelatedProductAdapter? = null
    private var relatedpackagelist =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData>()
    private var adpterDoes: DoesAdapter? = null
    private var doeslist = ArrayList<DoseInfo>()
    private var totalCount: Int = 0

    lateinit var datatransfer: AskedPackage
    private var totalprice: Int = 0
    private var sendtotalprice: Int = 0
    private var totalVacinePrice: Int = 0
    private var totalHomeVacinePrice: Int = 0
    private var costVaccinePrice: Int = 0
    private var homeVaccinePrice: Int = 0
    private var dose_size = ""
    var vaccineId: String = ""
    private var totalofStock: Int = 0
    private var notiType: String = ""
    val noOf_doesInfo_list = ArrayList<NoOfDoesInfoModel>()
    private var city_Name: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var fromNoti_vaccineId_list =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.DoseInfo>()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var test = ArrayList<DoseInfo>()
    var fromNoti_memberId: String = ""
    private var ageGrorp: String = ""
    var getName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
//        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        notiType = intent.getStringExtra("notiType").toString()
        println("----notitype$notiType")
        if (notiType.equals("Custom Vaccine") && notiType != null) {
            fromNoti_vaccineId_list =
                intent.getSerializableExtra("vaccineIdList") as ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.DoseInfo>
            fromNoti_memberId = intent.getStringExtra("memberId").toString()

        }
    }

    private fun initView() {
        ////newCode
        getName = PrefrencesHelper.getFullName(this)
        println("----getName$getName")
        /* if (getName.isNullOrEmpty()) {
             firstTimePopup()
         }*/
        bin.productDetailsToolbar.tvTittle.text = getString(R.string.vaccine_details)
        vaccineId = intent.getStringExtra("vaccineId").toString()
        println("---vaccineId${vaccineId}")
        apiCallingForVaccineDetails()
        viewModel.onVaccineList(this)


    }

    private fun lstnr() {
        bin.productDetailsToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        bin.productDetailsToolbar.constraintLayout32.setOnClickListener {
            startActivity(Intent(this, SetLocationActivity::class.java))
        }
        bin.ivup.setOnClickListener {
            if (bin.ivup.visibility == View.VISIBLE) {
                bin.ivup.visibility = View.GONE
                bin.ivdown.visibility = View.VISIBLE
                bin.consDes.visibility = View.GONE
            }
        }
        bin.ivdown.setOnClickListener {
            if (bin.ivdown.visibility == View.VISIBLE) {
                bin.ivup.visibility = View.VISIBLE
                bin.ivdown.visibility = View.GONE
                bin.consDes.visibility = View.VISIBLE
            }
        }
        bin.ivup1.setOnClickListener {
            if (bin.ivup1.visibility == View.VISIBLE) {
                bin.ivup1.visibility = View.GONE
                bin.ivdown1.visibility = View.VISIBLE
                bin.consBenifits.visibility = View.GONE
            }
        }
        bin.ivdown1.setOnClickListener {
            if (bin.ivdown1.visibility == View.VISIBLE) {
                bin.ivup1.visibility = View.VISIBLE
                bin.ivdown1.visibility = View.GONE
                bin.consBenifits.visibility = View.VISIBLE
            }
        }
        bin.ivup11.setOnClickListener {
            if (bin.ivup11.visibility == View.VISIBLE) {
                bin.ivup11.visibility = View.GONE
                bin.ivdown11.visibility = View.VISIBLE
                bin.consPrecautions.visibility = View.GONE
            }
        }
        bin.ivdown11.setOnClickListener {
            if (bin.ivdown11.visibility == View.VISIBLE) {
                bin.ivup11.visibility = View.VISIBLE
                bin.ivdown11.visibility = View.GONE
                bin.consPrecautions.visibility = View.VISIBLE
            }
        }
        bin.textView179.setOnClickListener {
            if (totalCount <= 0) {
                Toast.makeText(this, "Please Select Dose!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (totalofStock <= 0) {
                Toast.makeText(this, "This vaccine is out of stock!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (getName.isNullOrEmpty()) {
                PrefrencesHelper.getKey(this, "vaccine")
                PrefrencesHelper.getEmailKey(this, "emailKey")
                PrefrencesHelper.savePackageId(this, vaccineId)
                 /*PrefrencesHelper.savePackageId(this, packageId)
                 PrefrencesHelper.savePrice(this, package_total_price.toString())
                 PrefrencesHelper.saveNoDose(this, noofDoes.toString())
                 PrefrencesHelper.saveAge(this, ageGrorp)
                 PrefrencesHelper.savePackageList(this, package_data_transfer!!)
                 PrefrencesHelper.saveFagStatus(this, true)*/
                startActivity(Intent(this, AccountCreateActivity::class.java))
            } else if (notiType.equals("Custom Vaccine") && notiType != null) {
                val intent = Intent(
                    this, FillInformationActivity::class.java
                )
                intent.putExtra("fromNoti", "customVaccine")
                intent.putExtra("totalcount", totalCount)
                intent.putExtra("vaccineId", vaccineId)
                intent.putExtra("totalPrice", sendtotalprice)
                intent.putExtra("Obj", Gson().toJson(datatransfer))
                intent.putExtra("memberId", fromNoti_memberId)
                intent.putExtra("fag", false)
                intent.putExtra("ageGroup", ageGrorp)
                intent.putExtra("doesInfo", noOf_doesInfo_list)
                startActivity(intent)
            } else {
                val intent = Intent(
                    this, FillInformationActivity::class.java
                )
                intent.putExtra("totalcount", totalCount)
                intent.putExtra("vaccineId", vaccineId)
                intent.putExtra("totalPrice", sendtotalprice)
                intent.putExtra("Obj", Gson().toJson(datatransfer))
                intent.putExtra("fag", false)
                intent.putExtra("ageGroup", ageGrorp)
                intent.putExtra("doesInfo", noOf_doesInfo_list)
                startActivity(intent)
            }


        }
        var number = "9650039988"
        bin.ivwhatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${"+91"}$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseVaccineDetails.observe(this) {
            if (it?.code == 200) {
                apiCallingForSubCenter()
                if (it.askedPackage.ageGroup.isNotEmpty()) {
                    ageGrorp = it.askedPackage.ageGroup.get(0)
                } else {
                    ageGrorp = ""
                }

                sendtotalprice = it.askedPackage.price.toInt()
                datatransfer = it.askedPackage
                totalofStock = it.askedPackage.totalStock.toInt()
                costVaccinePrice = it.askedPackage.price.toInt()
                homeVaccinePrice = it.askedPackage.homeVaccinationFee.toInt()
                test = it.askedPackage.doseInfo as ArrayList<DoseInfo>
                val img: String = it.askedPackage.vaccineImage
                if (img != null && img.length > 0) {
                    Picasso.get().load(img).into(bin.imageView62)
                    bin.lottie.visibility = View.GONE
                } else {
                    bin.lottie.visibility = View.VISIBLE
                }
                bin.tvNameProductDetails.text = it.askedPackage.vaccineName
//                bin.tvDesProductDetails.text = it.askedPackage.categoryName
                bin.tvMiniProductDetails.text = it.askedPackage.description
                bin.tvBenifits.text = it.askedPackage.benifits
                bin.tvPre.text = it.askedPackage.precautions
                bin.tvPriceProductDetails.text = "₹" + it.askedPackage.price
                bin.tvDesProductDetails.text = "--"
                doeslist.clear()
                doeslist.addAll(it.askedPackage.doseInfo)
                dose_size = doeslist.size.toString()

                println("-----size$dose_size")
                bin.tvDoesSize.text = "/ dose"
                bin.rcyDoes.layoutManager = LinearLayoutManager(this)
                adpterDoes = DoesAdapter(this, doeslist, this, fromNoti_vaccineId_list, notiType)
                bin.rcyDoes.adapter = adpterDoes
                adpterDoes!!.notifyDataSetChanged()
                if (notiType.equals("Custom Vaccine") && notiType != null) {
                    bin.total1.visibility = View.VISIBLE
                    totalCount += fromNoti_vaccineId_list.size
                    totalVacinePrice = costVaccinePrice * totalCount
                    totalHomeVacinePrice = homeVaccinePrice * totalCount
                    /* + totalHomeVacinePrice  ye total price main add tha ab hta diya h*/
                    totalprice = totalVacinePrice + totalHomeVacinePrice
                    bin.textView176.text = "${totalCount} * ${costVaccinePrice}"
                    bin.textView1761.text = "${totalCount} * ${homeVaccinePrice}"
                    bin.textView177.text = "₹${totalVacinePrice}"
                    bin.textView1771.text = "₹${totalHomeVacinePrice}"
                    bin.tvTotalPriceProductDetails.text = "₹$totalprice"
                    var offerPrice: Double =
                        totalprice.toDouble() - it.askedPackage.offerPrice.toDouble()
                    bin.tvOfferPrice.text = "₹$offerPrice"
                    bin.tvBottomPrice.text = "₹$offerPrice"
                    for (jj in it.askedPackage.doseInfo) {
                        for (ll in fromNoti_vaccineId_list) {
                            if (jj.doseNumber.equals(ll.doseNumber)) {
                                noOf_doesInfo_list.add(
                                    NoOfDoesInfoModel(
                                        jj._id,
                                        jj.doseNumber,
                                        jj.timePeriod
                                    )
                                )
                            }
                        }

                    }
                    println("----testst$noOf_doesInfo_list")

                } else {
                    bin.total1.visibility = View.GONE
                }
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseVaccineList.observe(this) {
            if (it?.code == 200) {
                CustomLoader.hideLoader()
                relatedpackagelist.clear()
                relatedpackagelist.addAll(it.askedData)
                if (relatedpackagelist.isEmpty()) {
                    bin.textView178.visibility = View.GONE
                }

                bin.rcyrelated.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                adapterRelatedpackage =
                    VaccineRelatedProductAdapter(this, relatedpackagelist, this, this)
                bin.rcyrelated.adapter = adapterRelatedpackage
                adapterRelatedpackage!!.notifyDataSetChanged()

            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.responseAddFav.observe(this) {
            if (it?.code == 200) {
                viewModel.onVaccineList(this)
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseSubCenter.observe(this) {
            if (it?.code == 200) {
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) {
            subCenterPopup()
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForVaccineDetails() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onVaccineDetails(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kvaccineId] = vaccineId
        return hashMap
    }

    private fun apiCallingForAddFav() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onAddFav(this, userMap)
    }

    private fun apiCallingForSubCenter() {
        val userMap: HashMap<String, Any> = prepareDataforSubCenter()
        viewModel.onSubCenter(this, userMap)
    }

    private fun prepareDataforSubCenter(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.klat] = latitude
        hashMap[MyConstants.klong] = longitude
        return hashMap
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDoesChecked(msg: DoseInfo, pos: Int) {
        println(costVaccinePrice)
        totalCount = totalCount + 1
        totalVacinePrice = costVaccinePrice * totalCount
        totalHomeVacinePrice = homeVaccinePrice * totalCount
        /* + totalHomeVacinePrice  ye total price main add tha ab hta diya h*/
        totalprice = totalVacinePrice + totalHomeVacinePrice
        bin.textView176.text = "${totalCount} * ${costVaccinePrice}"
        bin.textView1761.text = "${totalCount} * ${homeVaccinePrice}"
        bin.textView177.text = "₹${totalVacinePrice}"
        bin.textView1771.text = "₹${totalHomeVacinePrice}"
        bin.tvTotalPriceProductDetails.text = "₹$totalprice"
        bin.tvBottomPrice.text = "₹$totalprice"
        println("----total${totalCount}")
        val id = msg._id
        println("---doesInfo${id}")

        noOf_doesInfo_list.add(
            NoOfDoesInfoModel(
                _id = msg._id, doseNumber = msg.doseNumber, timePeriod = msg.timePeriod
            )
        )
        println("----tetstst$noOf_doesInfo_list")


    }

    override fun onDoesUnChecked(msg: DoseInfo, pos: Int) {
        if (noOf_doesInfo_list.contains(
                NoOfDoesInfoModel(
                    _id = msg._id, doseNumber = msg.doseNumber, timePeriod = msg.timePeriod
                )
            )
        ) {
            noOf_doesInfo_list.remove(
                NoOfDoesInfoModel(
                    _id = msg._id, doseNumber = msg.doseNumber, timePeriod = msg.timePeriod
                )
            )
        }
        println("----reetetstst$noOf_doesInfo_list")
        if (totalCount > 1) {
            totalCount = totalCount - 1
            totalVacinePrice = costVaccinePrice * totalCount
            totalHomeVacinePrice = homeVaccinePrice * totalCount
            totalprice = totalVacinePrice + totalHomeVacinePrice
            bin.textView176.text = "${totalCount} X ${costVaccinePrice}"
            bin.textView1761.text = "${totalCount} X ${homeVaccinePrice}"
            bin.textView177.text = "${totalVacinePrice}"
            bin.textView1771.text = "${totalHomeVacinePrice}"
            bin.tvTotalPriceProductDetails.text = "₹" + totalprice
            bin.tvBottomPrice.text = "₹" + totalprice
            println("----totalsss${totalCount}")

        } else {
            bin.textView176.text = "-"
            bin.textView1761.text = "-"
            bin.textView177.text = "-"
            bin.textView1771.text = "-"
            bin.tvTotalPriceProductDetails.text = "₹ 0"
            bin.tvBottomPrice.text = "₹ 0"
            totalCount = 0
        }
    }

    override fun vaccineclick(msgItem: AskedData, position: Int) {
        vaccineId = msgItem._id
        startActivity(
            Intent(
                this, VaccineDetailsActivity::class.java
            ).putExtra("vaccineId", vaccineId)
        )
    }

    override fun onFavVaccine(favid: String) {
        vaccineId = favid
        apiCallingForAddFav()
    }

    private fun getLocation() {
        GPSService(this, this)
    }

    private fun checkPermission(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun getCurrentLocation() {
        val editor = this?.getSharedPreferences(MyConstants.PREF_NAME, MODE_PRIVATE)
        city_Name = editor!!.getString(MyConstants.kAddress, "").toString()
        latitude = editor!!.getString(MyConstants.kLATITUDE, "")!!.toDouble()
        longitude = editor!!.getString(MyConstants.kLONGITUDE, "")!!.toDouble()
        /* Toast.makeText(this, "latitude${latitude}", Toast.LENGTH_SHORT).show()
         Toast.makeText(this, "longitude${longitude}", Toast.LENGTH_SHORT).show()
         Toast.makeText(this, "Test${city_Name}", Toast.LENGTH_SHORT).show()*/
        bin.productDetailsToolbar.constraintLayout32.visibility = View.VISIBLE
        bin.productDetailsToolbar.tvSetLocation.text = city_Name


    }

    private fun subCenterPopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.subcenter_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val yes = dialog.findViewById<TextView>(R.id.tvYes1)
        val cancel = dialog.findViewById<TextView>(R.id.cancel)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        yes.setOnClickListener {
            dialog.dismiss()
        }
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

    }

    override fun onResume() {
        super.onResume()
        if (checkPermission()) {
            getLocation()
            getCurrentLocation()
        }
        initView()
    }

    override fun onLocationUpdate(latitude: Double, longitude: Double) {

    }

    private fun firstTimePopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.first_time_enter_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val cont = dialog.findViewById<TextView>(R.id.tvcont)
        val may = dialog.findViewById<TextView>(R.id.mayBelater)
        cont.setOnClickListener {
            dialog.dismiss()
            PrefrencesHelper.getKey(this, "vaccine")
            PrefrencesHelper.getEmailKey(this, "emailKey")
            /* PrefrencesHelper.savePackageId(this, packageId)
             PrefrencesHelper.savePrice(this, package_total_price.toString())
             PrefrencesHelper.saveNoDose(this, noofDoes.toString())
             PrefrencesHelper.saveAge(this, ageGrorp)
             PrefrencesHelper.savePackageList(this, package_data_transfer!!)
             PrefrencesHelper.saveFagStatus(this, true)*/
            startActivity(Intent(this, AccountCreateActivity::class.java))
        }
        may.setOnClickListener {
            dialog.dismiss()
        }
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

    }

}
