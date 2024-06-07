package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentHomeBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.*
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.HomeSearchAdapter.HomeSearchAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CategoryList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.GlobalModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Testimonial.AskedRequest
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AccountCreateActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AllCategories.AllCategoriesActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Kids.KidsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MyProfile.MainProfileActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Package.PackageActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleingConsolling.Schedule_Counseling
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.SubCategory.SubCategoryActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccinationChart.VaccinationChartActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.WishlistActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil.DELAY_MS
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil.PERIOD_MS
import com.catalyist.helper.CustomLoader
import com.catalyist.helper.ErrorUtil
import com.squareup.picasso.Picasso
import java.util.*

class HomeFragment : Fragment(), ExploreHomeAdapter.OnClickItem, ExplorePackageAdapter.OnClickItem,
    ExploreVaccineAdapter.OnClickItem, HomeViewPagerAdapter.OnClick, HomeSearchAdapter.TestClick {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ViewModalLogin
    private var adapterExplore: ExploreHomeAdapter? = null
    private var explorelist = ArrayList<AskedData>()
    private var adapterExplorepackage: ExplorePackageAdapter? = null
    private var explorepackagelist =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageList.AskedData>()
    private var adapterExploreVaccine: ExploreVaccineAdapter? = null
    private var explorevaccinelist =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData>()
    private var adapterTestm: TestimonialsAdapter? = null
    private var testlist = ArrayList<AskedRequest>()
    private var viewPagerlist =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.BannerList.AskedData>()
    private var currentPos = 0
    val handler = Handler()
    var categoryId: String = ""
    var packageId: String = ""
    var vaccineId: String = ""
    var test = ArrayList<AskedRequest>()
    private var type_list = ArrayList<String>()
    private var nameAdapter: HomeSearchAdapter? = null
    private var testListSeacrch = ArrayList<GlobalModel>()
    private var globalModelList = ArrayList<GlobalModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        apiResponse()

        initView()
        listner()
        return (binding.root)
    }

    private fun initView() {
        viewModel.onGlobalSearch(requireContext())
        viewModel.onCategoryList(requireContext())
        viewModel.onPackageList(requireContext())
        viewModel.onVaccineList(requireContext())
        viewModel.onBannerList(requireContext())
        viewModel.onTestimonialList(requireContext())

    }

    private fun listner() {
        binding.imageView26.setOnClickListener {
            val intent = Intent(activity, MainProfileActivity::class.java)
            startActivity(intent)
        }
        binding.imageView19.setOnClickListener {
            val intent = Intent(activity, MainProfileActivity::class.java)
            startActivity(intent)
        }
        binding.dummyHomeImg.setOnClickListener {
            val intent = Intent(activity, MainProfileActivity::class.java)
            startActivity(intent)
        }
        binding.buttonOwn.setOnClickListener {
            startActivity(Intent(requireContext(), VaccinationChartActivity::class.java))
        }
        binding.viewBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AllCategoriesActivity::class.java))
        }
        binding.imageView25.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }
        val number = "9650039988"
        binding.ivwhatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${"+91"}$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val text = p0.toString().trim()
                if (text.isNotEmpty()) {
                    binding.rcySearch.visibility = View.VISIBLE
                    val tempFilterList = testListSeacrch.filter {
                        it.name.lowercase(Locale.ROOT)
                            .contains(text.lowercase(Locale.ROOT))
                    }
                    binding.rcySearch.layoutManager = LinearLayoutManager(requireContext())
                    nameAdapter = HomeSearchAdapter(
                        requireContext(),
                        tempFilterList as ArrayList<GlobalModel>, this@HomeFragment
                    )
                    binding.rcySearch.adapter = nameAdapter
                    nameAdapter!!.notifyItemRangeInserted(0, tempFilterList.size)

                    if (tempFilterList.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Data Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    binding.rcySearch.visibility = View.GONE
                    /* binding.rcySearch.layoutManager = LinearLayoutManager(requireContext())
                     nameAdapter = HomeSearchAdapter(
                         requireContext(),
                         testListSeacrch, this@HomeFragment
                     )
                     binding.rcySearch.adapter = nameAdapter
                     nameAdapter!!.notifyDataSetChanged()*/

                }
            }

        })



        binding.imageview1011.setOnClickListener {
            startActivity(Intent(requireContext(), Schedule_Counseling::class.java))
        }

        binding.viewBtn1.setOnClickListener {
            startActivity(
                Intent(requireContext(), PackageActivity::class.java).putExtra(
                    "from",
                    "PackageFragment"
                )
            )
        }
        binding.viewBtn11.setOnClickListener {
            startActivity(
                Intent(requireContext(), PackageActivity::class.java).putExtra(
                    "from",
                    "VaccineFragment"
                )
            )
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseCategoryList.observe(requireActivity()) {
            if (it?.code == 200) {
                explorelist.clear()
                explorelist.addAll(it.askedData)
                binding.recyclerviewHomeFrag.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapterExplore = ExploreHomeAdapter(requireContext(), explorelist, this)
                binding.recyclerviewHomeFrag.adapter = adapterExplore
                adapterExplore!!.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responsePackageList.observe(requireActivity()) {
            if (it?.code == 200) {
                explorepackagelist.clear()
                explorepackagelist.addAll(it.askedData.take(8))
                binding.rcyPackages.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapterExplorepackage =
                    ExplorePackageAdapter(
                        requireContext(),
                        explorepackagelist, this, this
                    )
                binding.rcyPackages.adapter = adapterExplorepackage
                adapterExplorepackage!!.notifyDataSetChanged()
            }
        }

        viewModel.responseVaccineList.observe(requireActivity()) {
            if (it?.code == 200) {
                explorevaccinelist.clear()
                explorevaccinelist.addAll(it.askedData.take(8))
                binding.rcyVaccine.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapterExploreVaccine =
                    ExploreVaccineAdapter(requireContext(), explorevaccinelist, this, this)
                binding.rcyVaccine.adapter = adapterExploreVaccine
                adapterExploreVaccine!!.notifyDataSetChanged()
            }
        }
        viewModel.responseBannerList.observe(requireActivity()) {
            if (it?.code == 200) {
                viewPagerlist.clear()
                viewPagerlist.addAll(it.askedData)
                binding.viewPager1.adapter =
                    HomeViewPagerAdapter(requireContext(), viewPagerlist, this)
                binding.tabLayout1.setupWithViewPager(binding.viewPager1, true)
                val runnable = Runnable {
                    if (currentPos == it.askedData.size - 1) currentPos = 0
                    else currentPos++
                    if (binding.viewPager1 != null) {
                        binding.viewPager1.setCurrentItem(currentPos, true)
                    }
                }

                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        handler.post(runnable)
                    }
                }, DELAY_MS, PERIOD_MS)
            }
        }
        viewModel.responseViewProfile.observe(requireActivity()) {
            if (it?.code == 200) {
                CustomLoader.hideLoader()
                if (it.user.profilePic.isNullOrEmpty()) {
                    binding.dummyHomeImg.visibility = View.VISIBLE
                    binding.imageView26.visibility = View.INVISIBLE

                } else {
                    Picasso.get().load(it.user.profilePic).centerCrop().resize(100, 100)
                        .into(binding.imageView26)
                    binding.dummyHomeImg.visibility = View.INVISIBLE
                    binding.imageView26.visibility = View.VISIBLE
                    binding.textView40.text = it.user.name
                }
                if (!it.user.name.isNullOrEmpty()) {
                    binding.textView40.text = it.user.name
                    PrefrencesHelper.saveFullName(context, it.user.name)
                } else {
                    binding.textView40.text = "Hi,Guest"
                    firstTimePopup()
                }

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseTestimonialList.observe(requireActivity()) {
            if (it?.code == 200) {
                CustomLoader.hideLoader()
                testlist.clear()
                testlist.addAll(it.askedRequests)
                if (testlist.isEmpty()) {
                    binding.textView10611.visibility = View.GONE
                }
                binding.rcyTest.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapterTestm = TestimonialsAdapter(requireContext(), testlist)
                binding.rcyTest.adapter = adapterTestm
                adapterTestm!!.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseGlobalSearch.observe(requireActivity()) {
            if (testListSeacrch.size > 0) {
                testListSeacrch.clear()
            }
            if (it?.message == "Request Successful") {
                for (cat_data in it.categoryData.askedData) {
//                    type_list.add(cat_data.categoryName)
                    testListSeacrch.add(
                        GlobalModel(
                            it.categoryData.key,
                            cat_data.categoryName,
                            cat_data._id
                        )
                    )
                }
                for (cat_data in it.subCategoryData.askedData1) {
//                    type_list.add(cat_data.subCategoryName)
                    testListSeacrch.add(
                        GlobalModel(
                            it.subCategoryData.key,
                            cat_data.subCategoryName,
                            cat_data._id

                        )
                    )
                }
                for (cat_data in it.packageData.askedData2) {
                    //type_list.clear()
                    val test = "${cat_data.packageName} (${cat_data.categoryName})"
                    testListSeacrch.add(
                        GlobalModel(
                            it.packageData.key,
                            test,
                            cat_data._id

                        )
                    )
                }
                for (cat_data in it.vacineData.askedData3) {
                    // type_list.add(cat_data.vaccineName)
                    testListSeacrch.add(
                        GlobalModel(
                            it.vacineData.key,
                            cat_data.vaccineName,
                            cat_data._id,


                            )
                    )
                }
                /* for (cat_data in it.categoryData.askedData) {
                     type_list.add(cat_data.categoryName)
                     globalModelList.add(
                         GlobalModel(
                             it.categoryData.key,
                             cat_data.categoryName,
                             cat_data._id
                         )
                     )
                 }
                 for (cat_data in it.subCategoryData.askedData1) {
                     type_list.add(cat_data.subCategoryName)
                     globalModelList.add(
                         GlobalModel(
                             it.subCategoryData.key,
                             cat_data.subCategoryName,
                             cat_data._id

                         )
                     )
                 }
                 for (cat_data in it.packageData.askedData2) {
                      //type_list.clear()
                     val test = "${cat_data.packageName} (${cat_data.categoryName})"
                     println("------test$test")
                     type_list.add(test)
                     globalModelList.add(
                         GlobalModel(
                             it.packageData.key,
                             test,
                             cat_data._id

                         )
                     )
                 }
                 for (cat_data in it.vacineData.askedData3) {
                     type_list.add(cat_data.vaccineName)
                     globalModelList.add(
                         GlobalModel(
                             it.vacineData.key,
                             cat_data.vaccineName,
                             cat_data._id,


                             )
                     )
                     println("======type_list$globalModelList")
                 }*/

                binding.rcySearch.layoutManager = LinearLayoutManager(requireContext())
                nameAdapter = HomeSearchAdapter(requireContext(), testListSeacrch, this)
                binding.rcySearch.adapter = nameAdapter
                nameAdapter!!.notifyDataSetChanged()

                /*   val adapter = ArrayAdapter(
                       requireContext(),
                       android.R.layout.simple_spinner_dropdown_item,
                       type_list
                   )
                   binding.searchEdit.setAdapter(adapter)*/
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseAddFav.observe(requireActivity()) {
            if (it?.code == 200) {
                if (packageId.isEmpty()) {
                    viewModel.onVaccineList(requireContext())
                } else {
                    viewModel.onPackageList(requireContext())
                }

                // Toast.makeText(requireContext(), "ADD to Fav!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(requireActivity())
        {
            ErrorUtil.handlerGeneralError(requireContext(), it)
        }
    }

    override fun clickOn(msgItem: AskedData, position: Int) {
        categoryId = explorelist.get(position)._id
        if (msgItem.subCategoryCount.isEmpty()) {
            startActivity(
                Intent(
                    requireContext(),
                    PackageActivity::class.java
                ).putExtra("categoryId", categoryId)
            )
        } else {
            startActivity(
                Intent(
                    requireContext(),
                    SubCategoryActivity::class.java
                ).putExtra("categoryId", categoryId)
            )
        }
    }

    override fun packageclickOn(
        msgItem: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageList.AskedData,
        position: Int
    ) {
        packageId = msgItem._id
        startActivity(
            Intent(
                requireContext(),
                PackageDetailsActivity::class.java
            ).putExtra("packageId", packageId)
        )
    }

    override fun onFavPackage(favid: String) {
        packageId = favid
        println("----favId$packageId")
        apiCallingForAddFav()

    }

    override fun vaccineclick(
        msgItem: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData,
        position: Int
    ) {
        vaccineId = msgItem._id
        startActivity(
            Intent(
                requireContext(),
                VaccineDetailsActivity::class.java
            ).putExtra("vaccineId", vaccineId)
        )
    }

    override fun onFavVaccine(favid: String) {
        vaccineId = favid
        apiCallingForAddFav()
    }

    override fun onBannerClick(position: Int, bannerType: String, id: String) {
        if (bannerType.equals("Category")) {
            startActivity(
                Intent(
                    requireContext(),
                    SubCategoryActivity::class.java
                ).putExtra("categoryId", id)
            )
        } else if (bannerType.equals("Package")) {
            startActivity(
                Intent(
                    requireContext(),
                    PackageDetailsActivity::class.java
                ).putExtra("packageId", id)
            )
        } else if (bannerType.equals("Vaccine")) {
            startActivity(
                Intent(
                    requireContext(),
                    VaccineDetailsActivity::class.java
                ).putExtra("vaccineId", id)
            )
        }
    }

    private fun apiCallingForAddFav() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onAddFav(requireContext(), userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        if (packageId.isEmpty()) {
            hashMap[MyConstants.kvaccineId] = vaccineId
        } else {
            hashMap[MyConstants.kpackageId] = packageId
        }
        return hashMap
    }

    override fun onTest(msg: GlobalModel, id: String) {
        println("----iddddSer$id")
        binding.searchEdit.setText(msg.name)
        binding.rcySearch.visibility = View.INVISIBLE
        val name = msg.name
        if (msg.name.equals(name)) {
            if (msg.key.equals("CATEGORY")) {
                val id = msg.id
                println("-----id$id")
                if (name.equals("Travel Vaccination")) {
                    startActivity(
                        Intent(requireContext(), PackageActivity::class.java).putExtra(
                            "from",
                            "PackageFragment"
                        ).putExtra("categoryId", id)

                    )
                } else {
                    startActivity(
                        Intent(
                            requireContext(),
                            KidsActivity::class.java
                        ).putExtra("categoryId", id)

                    )
                }
            } else if (msg.key.equals("SUBCATEGORY")) {
                val id = msg.id
                startActivity(
                    Intent(
                        requireContext(),
                        PackageActivity::class.java
                    ).putExtra("subCategory_id", id)

                )

            } else if (msg.key.equals("VACCINE")) {
                val id = msg.id
                startActivity(
                    Intent(
                        requireContext(),
                        VaccineDetailsActivity::class.java
                    ).putExtra("vaccineId", id)
                )

            } else if (msg.key.equals("PACKAGE")) {
                val idSS = msg.id
                println("----idss$idSS")
                startActivity(
                    Intent(
                        requireContext(),
                        PackageDetailsActivity::class.java
                    )
                )


            }

        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewProfile(requireContext())
        binding.rcySearch.visibility = View.INVISIBLE
    }

    private fun firstTimePopup() {
        val dialog = this.let { Dialog(requireContext()) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.first_time_enter_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val cont = dialog.findViewById<TextView>(R.id.tvcont)
        val may = dialog.findViewById<TextView>(R.id.mayBelater)
        cont.setOnClickListener {
            dialog.dismiss()
            PrefrencesHelper.getKey(requireContext(), "home")
            PrefrencesHelper.getEmailKey(requireContext(), "emailKey")
            startActivity(Intent(requireContext(), AccountCreateActivity::class.java))
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
