package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.SuggestBaby

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentVaccineSuggestBabyBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SpinnerAdapterBrands
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SuggestBabyAdapter.VaccineSuggestBabyAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddFamilyMember
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity
import android.content.Intent
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalyist.helper.ErrorUtil

class VaccineSuggestBabyFragment : Fragment(), VaccineSuggestBabyAdapter.OnClickItem {
    lateinit var binding: FragmentVaccineSuggestBabyBinding
    private lateinit var viewModel: ViewModalLogin
    private var memberList = ArrayList<String>()
    private var vaccineIdHashmap: HashMap<String, String> = HashMap()
    private var vaccineDOBHashmap: HashMap<String, String> = HashMap()
    private var dob: String = ""
    private var explorevaccinelist =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData>()
    private var adapter: VaccineSuggestBabyAdapter? = null
    private var age: Int = 0
    private var vaccineId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVaccineSuggestBabyBinding.inflate(layoutInflater)
        apiResponse()
        initView()
        listner()
        return (binding.root)
    }

    private fun initView() {
        memberList.add(0, "Select Member")
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
                ).putExtra("redirection", "vaccine_suggest")
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
            dob =
                vaccineDOBHashmap.get(binding.memberSpinner.selectedItem.toString())
                    ?: ""
            println("--dob$dob")
            age = getAge(dob)
            if (binding.memberSpinner.selectedItemPosition > 0) {
                binding.sgstRcyVaccine.visibility = View.VISIBLE
                viewModel.onVaccineList(requireContext())
            } else {
                binding.sgstRcyVaccine.visibility = View.GONE
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
                        memberList.add("${it.fullName} (${it.relation})")
                        vaccineIdHashmap.put(it.fullName, it._id)
                        vaccineDOBHashmap.put("${it.fullName} (${it.relation})", it.dob)
                    }

                }
                spinnerForMemberList(memberList)
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseVaccineList.observe(requireActivity()) {
            if (it?.code == 200) {
                if (age <= 1) {
                    explorevaccinelist.clear()
                    it.askedData.forEach {
                        if (it.toAgeDays != null && it.toAgeDays.isNotEmpty()) {
                            if (it.toAgeDays.toInt() <= 365 * 1) {
                                explorevaccinelist.add(it)
                            }
                        }
                    }
                    println("-----test$explorevaccinelist")
//                    for (vaccinedata in it.askedData) {
//                        if (vaccinedata.toAgeDays != null && vaccinedata.toAgeDays.isNotEmpty()) {
//                            if (vaccinedata.toAgeDays.toInt() <= (365 * 1)) {
//                                explorevaccinelist.addAll(it.askedData)
//                            }
//                            println("-----test$explorevaccinelist")
//                        } else {
//                            Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show()
//                        }
//
//                    }
                } else if (age <= 2) {
                    explorevaccinelist.clear()
                    it.askedData.forEach {
                        if (it.toAgeDays != null && it.toAgeDays.isNotEmpty()) {
                            if (it.toAgeDays.toInt() <= 365 * 2) {
                                explorevaccinelist.add(it)
                            }
                        }
                    }
                } else if (age <= 5) {
                    explorevaccinelist.clear()
                    it.askedData.forEach {
                        if (it.toAgeDays != null && it.toAgeDays.isNotEmpty()) {
                            if (it.toAgeDays.toInt() <= 365 * 5) {
                                explorevaccinelist.add(it)
                            }
                        }
                    }
                } else if (age <= 18) {
                    explorevaccinelist.clear()
                    it.askedData.forEach {
                        if (it.toAgeDays != null && it.toAgeDays.isNotEmpty()) {
                            if (it.toAgeDays.toInt() <= 365 * 18) {
                                explorevaccinelist.add(it)
                            }
                        }
                    }
                } else {
                    explorevaccinelist.clear()
                    explorevaccinelist.addAll(it.askedData)

                }
                println("-----exxxp${explorevaccinelist.size}")
                if (explorevaccinelist == null) {
                    Toast.makeText(requireContext(), "Data Not Found!", Toast.LENGTH_SHORT).show()
                }
                binding.sgstRcyVaccine.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter =
                    VaccineSuggestBabyAdapter(requireContext(), explorevaccinelist, this)
                binding.sgstRcyVaccine.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
        viewModel.error.observe(requireActivity()) {
            ErrorUtil.handlerGeneralError(requireContext(), it)

        }
    }

    private fun apiCallingForFamilyMemberList() {
        viewModel.onFamilyMemberList(requireContext())
    }

    override fun vaccineclick(msgItem: AskedData, position: Int) {
        vaccineId = msgItem._id
        startActivity(
            Intent(
                requireContext(),
                VaccineDetailsActivity::class.java
            ).putExtra("vaccineId", vaccineId)
        )
    }


}