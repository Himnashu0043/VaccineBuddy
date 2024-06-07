package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.BabyFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentBabyPackageBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Package.BabyPackageAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Package.PackageActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalyist.helper.CustomLoader
import com.catalyist.helper.ErrorUtil
import java.util.*
import kotlin.collections.ArrayList


class BabyPackageFragment : Fragment(), BabyPackageAdapter.OnClickItem {
    lateinit var bin: FragmentBabyPackageBinding
    private lateinit var adapter: BabyPackageAdapter
    private lateinit var viewModel: ViewModalLogin
    private var packagecategorylist = ArrayList<AskedData>()
    private lateinit var activity: PackageActivity
    var packageId: String = ""
    var subCategory_id: String = ""
    var categoryId: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bin = FragmentBabyPackageBinding.inflate(layoutInflater)
        activity = getActivity() as PackageActivity
        Log.e("TAG", "onCreateView: " + activity.subCategory_id)
        subCategory_id = activity.subCategory_id

        activity = getActivity() as PackageActivity
        Log.e("TAG", "onCreateView:Cattt " + activity.categoryId)
        categoryId = activity.categoryId


        apiResponse()
        initView()
        lstnr()
        return bin.root
    }

    private fun initView() {
        if (!subCategory_id.equals("null")) {
            apiCallingForSubCategoryPackageList()
        } else if (!categoryId.equals("null")) {
            apiCallingForSubCategoryPackageList()
        } else {
            apiCallingForPackageALlList()
        }

    }

    private fun lstnr() {
        bin.seach.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var text = p0.toString().trim()
                if (text.isNotEmpty()) {
                    if (text.length > 0) {
                        var tempFilterList = packagecategorylist.filter {
                            it.packageName.lowercase(Locale.ROOT)
                                .contains(text.lowercase(Locale.ROOT))
                        }
                        bin.recycler.layoutManager =
                            LinearLayoutManager(requireContext())
                        adapter = BabyPackageAdapter(
                            requireContext(),
                            tempFilterList as ArrayList<AskedData>, this@BabyPackageFragment,this@BabyPackageFragment
                        )
                        bin.recycler.adapter = adapter
                        adapter!!.notifyDataSetChanged()
                        if (tempFilterList.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Data Not Found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    bin.recycler.layoutManager =
                        LinearLayoutManager(requireContext())
                    adapter = BabyPackageAdapter(
                        requireContext(),
                        packagecategorylist,
                        this@BabyPackageFragment,this@BabyPackageFragment
                    )
                    bin.recycler.adapter = adapter
                    adapter!!.notifyDataSetChanged()

                }
            }
        })
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        CustomLoader.showLoader(requireActivity())
        viewModel.responsePackageList.observe(requireActivity()) {
            if (it?.code == 200) {
                CustomLoader.hideLoader()
                packagecategorylist.clear()
                packagecategorylist.addAll(it.askedData)
                bin.recycler.layoutManager =
                    LinearLayoutManager(requireContext())
                adapter = BabyPackageAdapter(requireContext(), packagecategorylist, this, this)
                bin.recycler.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseSubCategoryPackageList.observe(requireActivity()) {
            if (it?.code == 200) {
                CustomLoader.hideLoader()
                packagecategorylist.clear()
                packagecategorylist.addAll(it.askedData)
                bin.recycler.layoutManager =
                    LinearLayoutManager(requireContext())
                adapter = BabyPackageAdapter(requireContext(), packagecategorylist, this, this)
                bin.recycler.adapter = adapter
                adapter!!.notifyDataSetChanged()
                if (packagecategorylist.isEmpty()) {
                    Toast.makeText(requireContext(), "Data not Found!", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseAddFav.observe(requireActivity()) {
            if (it?.code == 200) {
                apiCallingForPackageALlList()
//                if (packageId.isEmpty()) {
//                    viewModel.onVaccineList(requireContext())
//                } else {
//                    viewModel.onPackageList(requireContext())
//                }

                // Toast.makeText(requireContext(), "ADD to Fav!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(requireActivity()) {
            ErrorUtil.handlerGeneralError(requireContext(), it)

        }
    }

    private fun apiCallingForPackageALlList() {
        viewModel.onPackageList(requireContext())
    }

    private fun apiCallingForSubCategoryPackageList() {
        viewModel.onPackageSubCategoryList(
            requireContext(),
            if (!subCategory_id.equals("null")) subCategory_id else "",
            if (!categoryId.equals("null")) categoryId else ""
        )
    }

    override fun packageclickOn(msgItem: AskedData, position: Int) {
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

    private fun apiCallingForAddFav() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onAddFav(requireContext(), userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kpackageId] = packageId

        return hashMap
    }
}