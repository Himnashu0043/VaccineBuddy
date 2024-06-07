package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.SuggestBaby

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentPackageSuggestBabyBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SpinnerAdapterBrands
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SuggestBabyAdapter.PackageSuggestBabyAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddFamilyMember
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.catalyist.helper.CustomLoader
import com.catalyist.helper.ErrorUtil


class PackageSuggestBabyFragment : Fragment(), PackageSuggestBabyAdapter.OnClickItem {
    lateinit var binding: FragmentPackageSuggestBabyBinding
    private lateinit var viewModel: ViewModalLogin
    private var adpater: PackageSuggestBabyAdapter? = null
    private var packageMemberList = ArrayList<String>()
    private var hashMapId: HashMap<String, String> = HashMap()
    private var hashmapdob: HashMap<String, String> = HashMap()
    private var dob: String = ""
    private var age: Int = 0
    var subCategory_id: String = ""
    var categoryId: String = ""
    private var packageId: String = ""
    private var packagecategorylist = ArrayList<AskedData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPackageSuggestBabyBinding.inflate(layoutInflater)
        apiResponse()
        initView()
        listner()
        return (binding.root)
    }

    private fun initView() {
        packageMemberList.add(0, "Select Member")
        apiCallingForFamilyMemberList()


    }

    private fun listner() {
        binding.tvMember.setOnClickListener {
            binding.memberSpinner.performClick()
        }
        binding.tvAddNewMember.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    AddFamilyMember::class.java
                ).putExtra("redirection", "package_suggest")
            )
        }

    }

    private fun spinnerForMemberList(memberList: List<String>) {
        val adapter =
            SpinnerAdapterBrands(
                requireActivity(),
                R.layout.spinner_dropdown_item, memberList
            )
        binding.memberSpinner.adapter = adapter
        binding.memberSpinner.onItemSelectedListener = onItemSelectedStateListenerMemberList

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
            binding.tvMember.text = itemSelected
            dob = hashmapdob.get(binding.memberSpinner.selectedItem.toString()).toString()
            age = getAge(dob)
            println("----age$age")
            Log.d("TAG", "onItemSelected: $dob")
            if (binding.memberSpinner.selectedItemPosition > 0) {
                apiCallingForSubCategoryPackageList()
                binding.sgstRcyPackage.visibility = View.VISIBLE
            } else {
                binding.sgstRcyPackage.visibility = View.GONE
            }
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseFamilyMemberList.observe(requireActivity()) {
            if (it?.code == 200) {
                it.askedData.forEach {
                    if (it.memberType.equals("KIDS")) {
                        packageMemberList.add("${it.fullName} (${it.relation})")
                        hashMapId.put(it.fullName, it._id)
                        hashmapdob.put("${it.fullName} (${it.relation})", it.dob)
                    }

                }
                println("---familymemberHashMapMemberType$hashmapdob")
                spinnerForMemberList(packageMemberList)
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseSubCategoryPackageList.observe(requireActivity()) {
            if (it?.code == 200) {
                CustomLoader.hideLoader()
                packagecategorylist.clear()
                packagecategorylist.addAll(it.askedData)
                binding.sgstRcyPackage.layoutManager = LinearLayoutManager(requireContext())
                adpater = PackageSuggestBabyAdapter(requireContext(), packagecategorylist, this)
                binding.sgstRcyPackage.adapter = adpater
                adpater!!.notifyDataSetChanged()
                if (packagecategorylist.isEmpty()) {
                    Toast.makeText(requireContext(), "Data not Found!", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(requireActivity()) {
            ErrorUtil.handlerGeneralError(requireActivity(), it)

        }
    }

    private fun apiCallingForFamilyMemberList() {
        viewModel.onFamilyMemberList(requireContext())
    }

    private fun apiCallingForSubCategoryPackageList() {
        viewModel.onNewPackageSubCategoryList(
            requireContext(),
            if (age <= 1) "636e29769bdb0f57342eabdf"
            else if (age <= 2) "636e28acd026915667ca17cd"
            else if (age <= 5) "636e29e89bdb0f57342eabfa"
            else if (age <= 18) "636e2a2f9bdb0f57342eac00"
            else "636e2a529bdb0f57342eac0a"
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

}

