package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityPackageDetails2Binding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.ComparisonAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.PricePopupAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.RelatedProductAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.VaccineDoseDetail.VaccineDoseDetailsAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.GPSService
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.DoseVaccineSetModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MoreDoseDetailsModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.DoseInfo
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PriceModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.RelatedPackage.RelatedPackege
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.FillInformationActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Location.SetLocationActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MoreDetailsInfo.MoreDetailInfoActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AccountCreateActivity
import com.catalyist.helper.ErrorUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class PackageDetailsActivity : AppCompatActivity(), RelatedProductAdapter.OnClickItem,
    VaccineDoseDetailsAdapter.onkey, GPSService.OnLocationUpdate {
    lateinit var binding: ActivityPackageDetails2Binding
    private lateinit var viewModel: ViewModalLogin
    private var adapterPricepopup: PricePopupAdapter? = null
    private var adapterRelatedpackage: RelatedProductAdapter? = null
    private var adapterComparision: ComparisonAdapter? = null
    private var pricepopuplist =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage>()
   lateinit var package_data_transfer: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage

    private var relatedpackagelist = ArrayList<RelatedPackege>()
    private var doselist = ArrayList<DoseInfo>()
    private var adpterDose: VaccineDoseDetailsAdapter? = null
    var packageId: String = ""
    private var noofDoes: Int = 0
    var toltalPrice = ""
    private var vaccineNameList = ArrayList<DoseInfo>()
    private var relatedvaccineNameList =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.RelatedPackage.DoseInfo>()
    private var getpackageName: String = ""
    private var getRelatedpackageName: String = ""
    private var package_size: Int = 0
    private var relatedPackage_size: Int = 0
    private var city_Name: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var ageGrorp: String = ""
    var getName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageDetails2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dose.rcyvaccinedose.layoutManager = LinearLayoutManager(this)
        apiResponse()
//        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun initView() {
        ////newCode
        getName = PrefrencesHelper.getFullName(this)
        println("----getName$getName")
       /* if (getName.isNullOrEmpty()) {
            firstTimePopup()
        }*/
        ////newCode
        binding.packageDetailsToolbar.tvTittle.text = getString(R.string.package_details)
        packageId = intent.getStringExtra("packageId").toString()
        println("---packageId${packageId}")
        apiCallingForPackageDetails()
        apiCallingForRelatedPackageList()


    }

    private fun lstnr() {
        binding.packageDetailsToolbar.ivBack.setOnClickListener {
            finish()
        }

        binding.packageDetailsToolbar.constraintLayout32.setOnClickListener {
            startActivity(Intent(this, SetLocationActivity::class.java))
            //finish()
        }
        binding.ivup.setOnClickListener {
            if (binding.ivup.visibility == View.VISIBLE) {
                binding.ivup.visibility = View.GONE
                binding.ivdown.visibility = View.VISIBLE
                binding.consDes.visibility = View.GONE
            }
        }
        binding.ivdown.setOnClickListener {
            if (binding.ivdown.visibility == View.VISIBLE) {
                binding.ivup.visibility = View.VISIBLE
                binding.ivdown.visibility = View.GONE
                binding.consDes.visibility = View.VISIBLE
            }
        }
        binding.textView179.setOnClickListener {
            println("sizeeeeee$package_size")
            println("reelll${relatedvaccineNameList.size}")
            //newCode
            if (getName.isNullOrEmpty()) {
                PrefrencesHelper.getKey(this, "package")
                PrefrencesHelper.getEmailKey(this, "emailKey")
                PrefrencesHelper.savePackageId(this, packageId)
                /* PrefrencesHelper.savePackageId(this, packageId)
                 PrefrencesHelper.savePrice(this, package_total_price.toString())
                 PrefrencesHelper.saveNoDose(this, noofDoes.toString())
                 PrefrencesHelper.saveAge(this, ageGrorp)
                 PrefrencesHelper.savePackageList(this, package_data_transfer!!)
                 PrefrencesHelper.saveFagStatus(this, true)*/
                startActivity(Intent(this, AccountCreateActivity::class.java))
                //newCode
            } else if (package_size < relatedvaccineNameList.size) {
            comparisonPopup()
        } else {
            val intent = Intent(this, FillInformationActivity::class.java)
            intent.putExtra("packageId", packageId)
            intent.putExtra("package_total_price", package_total_price)
            intent.putExtra("Pkg", Gson().toJson(package_data_transfer))
            intent.putExtra("totalcount", noofDoes)
            intent.putExtra("ageGroup", ageGrorp)
            intent.putExtra("fag", true)
            startActivity(intent)
        }

        }
        binding.textView17511.setOnClickListener {
            pricePopup()
        }
        var number = "9650039988"
        binding.ivwhatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${"+91"}$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }


    private fun pricePopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.price_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        val rcy = dialog.findViewById<RecyclerView>(R.id.rvprice)

        rcy.layoutManager = LinearLayoutManager(this)
        adapterPricepopup = PricePopupAdapter(this, pricepopuplist)
        rcy.adapter = adapterPricepopup
        adapterPricepopup!!.notifyDataSetChanged()

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

    }

    private fun comparisonPopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.comparison_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val packageName = dialog.findViewById<TextView>(R.id.tvPackageName)
        val relatedPackageName = dialog.findViewById<TextView>(R.id.tvRelatedPackageName)
        val upgrade = dialog.findViewById<TextView>(R.id.tvupgrade)
        val yes = dialog.findViewById<TextView>(R.id.tvYes)
        packageName.text = getpackageName
        relatedPackageName.text = getRelatedpackageName
        val rcy = dialog.findViewById<RecyclerView>(R.id.rvpcomparison)
        upgrade.setOnClickListener {
            dialog.dismiss()
            startActivity(
                Intent(
                    this, PackageDetailsActivity::class.java
                ).putExtra("packageId", packageId)
            )
            finish()
        }
        yes.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, FillInformationActivity::class.java)
            intent.putExtra("packageId", packageId)
            intent.putExtra("package_total_price", package_total_price)
            intent.putExtra("Pkg", Gson().toJson(package_data_transfer))
            intent.putExtra("totalcount", noofDoes)
            intent.putExtra("fag", true)
            startActivity(intent)

        }
        rcy.layoutManager = LinearLayoutManager(this)
        println("oooo${relatedvaccineNameList.size}")
        adapterComparision = ComparisonAdapter(
            this,
            commonSetDoseVaccine(vaccineNameList),
            commonSetDoseVaccine3(relatedvaccineNameList)
        )
        rcy.adapter = adapterComparision
        adapterComparision!!.notifyDataSetChanged()


        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

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

    var package_total_price: Double = 0.0
    var home_vaccine_count: Double = 0.0
    var home_vaccine_Total: Double = 0.0
    var consulting_count: Int = 0
    var consulting_Total: Int = 0
    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responsePackageDetails.observe(this) {
            if (it?.code == 200) {
                apiCallingForSubCenter()
                package_data_transfer = it.askedPackage
                vaccineNameList = it.askedPackage.doseInfo
                package_size = vaccineNameList.size
                if (it.askedPackage.categoryName != null) {
                    if (it.askedPackage.categoryName.equals("Kids Vaccination")) {
                        binding.dose.textView198.text = "Age of Baby"
                    } else {
                        binding.dose.textView198.text = "Visit"
                    }
                } else {
                    binding.dose.textView198.text = "Visit"
                }
                /*if (it.askedPackage.categoryName.equals("Kids Vaccination")) {
                    binding.dose.textView198.text = "Age"
                } else {
                    binding.dose.textView198.text = "Age of Baby"
                }*/
                println("-----szzzz$package_size")
                if (it.askedPackage.ageGroup.isNotEmpty()) {
                    ageGrorp = it.askedPackage.ageGroup.get(0)
                } else {
                    ageGrorp = ""
                }
                println("---age$ageGrorp")
                getpackageName = it.askedPackage.packageName
                commonSetDoseVaccine1(it.askedPackage.doseInfo)
                val img: String = it.askedPackage.packageImage
                if (img != null && img.length > 0) {
                    Picasso.get().load(img).into(binding.imageView62)
                    binding.lottie.visibility = View.GONE
                } else {
                    binding.lottie.visibility = View.VISIBLE
                }
                val home_Vaccine_fee = it.askedPackage.homeVaccinationFee.toDouble()
                val total_vaccine_fee = it.askedPackage.totalVaccinationFee.toDouble()
                home_vaccine_count = total_vaccine_fee / home_Vaccine_fee
                home_vaccine_Total = home_vaccine_count * home_Vaccine_fee
                if (it.askedPackage.consultingFee == null) {
                    binding.textView17611.visibility = View.GONE
                    binding.textView17711.visibility = View.GONE
                    binding.ivInfo.visibility = View.GONE
                    binding.textView17511.visibility = View.GONE
                } else {
                    val cons_fee = it.askedPackage.consultingFee.toInt()
                    val total_cons_fee = it.askedPackage.totalConsultantFee.toInt()
                    consulting_count = total_cons_fee / cons_fee
                    consulting_Total = consulting_count * cons_fee
                    binding.textView17611.text = (consulting_count).toString() + ("* ₹ ${cons_fee}")
                    binding.textView17711.text = "₹" + consulting_Total

                }


                println("-------${home_vaccine_count}")
                binding.tvNamePD.text = it.askedPackage.packageName
                if (it.askedPackage.subCategoryName == null && it.askedPackage.categoryName == null) {
                    binding.tvDesPD.text = "-"
                } else {
                    if (it.askedPackage.subCategoryName == null) {
                        binding.tvDesPD.text = "${it.askedPackage.categoryName}"
                    } else {
                        binding.tvDesPD.text =
                            "${it.askedPackage.categoryName}-${it.askedPackage.subCategoryName}"
                    }
                }

                /*tvPricePD*/
                binding.tvPricePD.text = "₹" + it.askedPackage.totalPrice
                binding.tvMiniDesPD.text = it.askedPackage.description
                package_total_price = it.askedPackage.totalPrice.toDouble()
                if (it.askedPackage.offerPrice.isEmpty() && it.askedPackage.offerPrice == null) {
                    binding.tvOfferPrice.text = "₹" + it.askedPackage.totalPrice
                } else {
                    binding.tvTotalPrice.text = "₹" + it.askedPackage.totalPrice
                    binding.tvOfferPrice.text = "₹" + it.askedPackage.offerPrice
                    binding.tvBottomTotalPrice.text = "₹" + it.askedPackage.offerPrice
                }

                binding.textView177.text = "₹$toltalPrice"
                home_vaccine_count = String.format(
                    "%.1f", (home_vaccine_count)
                ).toDouble()
                binding.textView1761.text =
                    (home_vaccine_count).toString() + ("* ₹ ${home_Vaccine_fee}").toString()
                binding.textView1771.text = "₹" + home_vaccine_Total

                pricepopuplist.clear()
                pricepopuplist.addAll(listOf(it.askedPackage))
                doselist.clear()
                doselist.addAll(it.askedPackage.doseInfo)
                //Log.e("TAG", "apiResponse: " + commonSetDoseVaccine(doselist).toString())
                adpterDose = VaccineDoseDetailsAdapter(
                    this,
                    commonSetDoseVaccine(it.askedPackage.doseInfo),
                    this,

                    )
                binding.dose.rcyvaccinedose.adapter = adpterDose

                binding.textView163.text = "${it.askedPackage.doseInfo.size} Vaccines / ${
                    commonSetDoseVaccine(
                        it.askedPackage.doseInfo
                    ).size
                } Dosage included"
                noofDoes = commonSetDoseVaccine(it.askedPackage.doseInfo).size
                println("---tttt$noofDoes")
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseRelatedPackageList.observe(this) {
            if (it?.code == 200) {
                if (it.relatedPackege == null) {
                    Toast.makeText(this, "Related Package Not Found!", Toast.LENGTH_SHORT).show()
                    binding.textView178.visibility = View.GONE
                } else {
                    relatedpackagelist.clear()
                    relatedpackagelist.addAll(it.relatedPackege)
                    packageId = it.relatedPackege.get(0)._id

                    getRelatedpackageName = it.relatedPackege.get(0).packageName
                    relatedvaccineNameList = it.relatedPackege.get(0).doseInfo
                    /* if (relatedvaccineNameList.size >  0 ){
                         relatedvaccineNameList.clear()
                     }
                     println("------jj${it.relatedPackege.size}")
                     relatedvaccineNameList = it.relatedPackege.get(0).doseInfo
                     for (hhh in it.relatedPackege) {
                         relatedvaccineNameList.addAll(hhh.doseInfo)
                     }
                     textList.clear()
                     for (kkk in it.relatedPackege.get(0).doseInfo){
                         textList.add(kkk._id)
                     }*/
                    relatedPackage_size = relatedvaccineNameList.size

                    binding.rcyrelated.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    adapterRelatedpackage =
                        RelatedProductAdapter(this, relatedpackagelist, this, this)
                    binding.rcyrelated.adapter = adapterRelatedpackage
                    adapterRelatedpackage!!.notifyDataSetChanged()
                }

            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseAddFav.observe(this) {
            if (it?.code == 200) {
                apiCallingForRelatedPackageList()
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


    private fun commonSetDoseVaccine(doselist: ArrayList<DoseInfo>): ArrayList<DoseVaccineSetModel> {
        val commonsSet: ArrayList<DoseVaccineSetModel> = ArrayList()
        val days: ArrayList<DoseVaccineSetModel> = ArrayList()
        val weeks: ArrayList<DoseVaccineSetModel> = ArrayList()
        val months: ArrayList<DoseVaccineSetModel> = ArrayList()
        val years: ArrayList<DoseVaccineSetModel> = ArrayList()
        val all: ArrayList<DoseVaccineSetModel> = ArrayList()
        val hashMap: HashMap<String, String> = HashMap()

        for (doseInfo in doselist) {
            for (timeDays in doseInfo.dose) {
                if (hashMap.containsKey(timeDays)) {
                    hashMap.put(timeDays, hashMap[timeDays] + "," + doseInfo.vaccineName)
                } else {
                    hashMap.put(timeDays, doseInfo.vaccineName)
                }
            }
        }
        for ((key, value) in hashMap.entries) {
            val time_slipt1 = key.replace("  ", " ")
            val time_slipt = time_slipt1.trim().split(" ")
            if (time_slipt[1].trim().equals("days")) {
                if (time_slipt[0].toInt() < 10) {
                    days.add(DoseVaccineSetModel("0${key}", value))
                } else {
                    days.add(DoseVaccineSetModel(key, value))
                }

                days.sortBy { it.does }
            } else if (time_slipt[1].trim().equals("months")) {
                if (time_slipt[0].toInt() < 10) months.add(DoseVaccineSetModel("0${key}", value))
                else months.add(DoseVaccineSetModel(key, value))
                months.sortBy { it.does }
            } else if (time_slipt[1].trim().equals("weeks")) {
                if (time_slipt[0].toInt() < 10) weeks.add(DoseVaccineSetModel("0$key", value))
                else weeks.add(DoseVaccineSetModel(key, value))
                weeks.sortBy { it.does }
            } else if (time_slipt[1].trim().equals("years")) {
                years.add(DoseVaccineSetModel(key, value))
                years.sortBy { it.does }
            } else if (time_slipt[1].trim().equals("Week")) {
                if (time_slipt[0].toInt() < 10) weeks.add(DoseVaccineSetModel("0$key", value))
                else weeks.add(DoseVaccineSetModel(key, value))
                weeks.sortBy { it.does }
            } else if (time_slipt[1].trim().lowercase().equals("year")) {
                if (time_slipt[0].toInt() < 10) years.add(DoseVaccineSetModel("0$key", value))
                else years.add(DoseVaccineSetModel(key, value))
                years.sortBy { it.does }
            } else if (time_slipt[1].trim().lowercase().equals("month")) {
                if (time_slipt[0].toInt() < 10) months.add(DoseVaccineSetModel("0$key", value))
                else months.add(DoseVaccineSetModel(key, value))
                months.sortBy { it.does }
            } else if (time_slipt[1].trim().lowercase().equals("day")) {
                days.add(DoseVaccineSetModel(key, value))
                days.sortBy { it.does }
            } /*else if (time_slipt[1].trim().equals("")) {
                all.add(DoseVaccineSetModel(key, value))
                all.sortBy { it.does }
            }*/

        }

        /* months.sortBy { it.does }*/

        commonsSet.addAll(days)
        println("day$commonsSet")
        commonsSet.addAll(weeks)
        println("wwwww$commonsSet")
        commonsSet.addAll(months)
        commonsSet.addAll(years)



        return commonsSet

    }

    private fun commonSetDoseVaccine1(doselist: ArrayList<DoseInfo>): ArrayList<PriceModel> {
        val commonsSet: ArrayList<PriceModel> = ArrayList()
        val hashMap: HashMap<String, String> = HashMap()
        for (doseInfo in doselist) {
            for (timeDays in doseInfo.dose) {
                if (hashMap.containsKey(timeDays)) {
                    hashMap.put(timeDays, hashMap[timeDays] + "," + doseInfo.price)
                    println("----hashMap$hashMap")
                } else {
                    hashMap.put(timeDays, doseInfo.price.toString())
                    println("----else_hashMap$hashMap")
                }
            }
        }

        for ((key, value) in hashMap.entries) {
            commonsSet.add(PriceModel(key, value))
            println("----pricecommonsSet$commonsSet")
            val price111 = ArrayList<Int>()
            for (iiii in doselist) {
                for (ooo in iiii.dose) {
                    price111.add(iiii.price)
                    println("------priiiii$price111")
                    toltalPrice = price111.sum().toString()
                }
            }

        }
        return commonsSet

    }

    private fun commonSetDoseVaccine3(doselist: List<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.RelatedPackage.DoseInfo>): ArrayList<DoseVaccineSetModel> {
        val commonsSet: ArrayList<DoseVaccineSetModel> = ArrayList()
        val hashMap: HashMap<String, String> = HashMap()

        for (doseInfo in doselist) {
            for (timeDays in doseInfo.dose) {
                if (hashMap.containsKey(timeDays)) {
                    hashMap.put(timeDays, hashMap[timeDays] + "," + doseInfo.vaccineName)
                    println("----hashMap1$hashMap")
                } else {
                    hashMap.put(timeDays, doseInfo.vaccineName)
                    println("----else_hashMap$1hashMap")
                }
            }
        }

        for ((key, value) in hashMap.entries) {
            commonsSet.add(DoseVaccineSetModel(key, value))
            println("----commonsSet1$commonsSet")
        }
        return commonsSet

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

    private fun apiCallingForPackageDetails() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onPackageDetails(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kpackageId] = packageId
        return hashMap
    }

    private fun apiCallingForRelatedPackageList() {
        val userMap: HashMap<String, Any> = prepareDataforRelatedPackageList()
        viewModel.onRelatedPackageList(this, userMap)
    }

    private fun prepareDataforRelatedPackageList(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kpackageId] = packageId
        return hashMap
    }

    override fun packageclickOn(msgItem: RelatedPackege, position: Int) {
        packageId = msgItem._id
        startActivity(
            Intent(
                this, PackageDetailsActivity::class.java
            ).putExtra("packageId", packageId)
        )
        finish()
    }

    override fun onFavPackage(favid: String) {
        packageId = favid
        println("----packDetailsfavId$packageId")
        apiCallingForAddFav()
    }

    private fun apiCallingForAddFav() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onAddFav(this, userMap)
    }

    override fun onMoreClick(key: String) {
        var str = key.subSequence(0, 1)
        if (str.equals("0")) {
            str = key.substring(1, key.length)
        } else {
            str = key.substring(0, key.length)
        }
        val more_list = ArrayList<MoreDoseDetailsModel>()
        for (get_dose in doselist) {
            if (get_dose.dose.contains(str)) {
                more_list.add(
                    MoreDoseDetailsModel(
                        key, get_dose.vaccineName, get_dose.price.toString(), get_dose.description
                    )
                )

            }

        }
        startActivity(
            Intent(this, MoreDetailInfoActivity::class.java).putExtra(
                "more_details", more_list
            )
        )
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
        binding.packageDetailsToolbar.constraintLayout32.visibility = View.VISIBLE
        binding.packageDetailsToolbar.tvSetLocation.text = city_Name


    }

    override fun onLocationUpdate(latitude: Double, longitude: Double) {

    }

    override fun onResume() {
        super.onResume()
        if (checkPermission()) {
            getLocation()
            getCurrentLocation()
        }
        initView()
    }

    //    fun getnewLocation() {
//        if (checkPermission()) {
//            if (isLocationEnabled()) {
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) == PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    requestPermissions()
//                    //callScreen()
//                    return
//                }
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
//                    val location: Location? = task.result
//                    if (location == null) {
//                        Toast.makeText(this, "Null Recived", Toast.LENGTH_LONG).show()
//                    } else {
//                        Toast.makeText(this, "Recived", Toast.LENGTH_LONG).show()
//                        Toast.makeText(
//                            this,
//                            "${location.latitude} ${location.latitude}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//
//            } else {
//                ////setting open here
//                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
//
//        } else {
/////request permission here
//            requestPermissions()
//
//        }
//
//    }
//
//    private fun isLocationEnabled(): Boolean {
//        var locationManager: LocationManager =
//            getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }
//
//
//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ),
//            PERMISSION_REQUEST_ACCESS_LOCATION
//        )
//    }
//
//    private fun checkPermission(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            return true
//        }
//        return false
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show()
//                getnewLocation()
//                // callScreen()
//            } else {
//                Toast.makeText(this, "Deen", Toast.LENGTH_SHORT).show()
//                // callScreen()
//            }
//        }
//    }
//
//    companion object {
//        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
//    }
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
            PrefrencesHelper.getKey(this, "package")
            PrefrencesHelper.getEmailKey(this, "emailKey")
            PrefrencesHelper.savePackageId(this, packageId)
           /* PrefrencesHelper.savePrice(this, package_total_price.toString())
            PrefrencesHelper.saveNoDose(this, noofDoes.toString())
            PrefrencesHelper.saveAge(this, ageGrorp)*/
            /* PrefrencesHelper.savePackageId(this, packageId)
             PrefrencesHelper.savePrice(this, package_total_price.toString())
             PrefrencesHelper.saveNoDose(this, noofDoes.toString())
             PrefrencesHelper.saveAge(this, ageGrorp)
             PrefrencesHelper.savePackageList(this, package_data_transfer!!)
             PrefrencesHelper.saveFagStatus(this, true)*/
            startActivity(
                Intent(this, AccountCreateActivity::class.java)/*.putExtra(
                    "Pkg",
                    Gson().toJson(package_data_transfer)
                )*/
            )

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