package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment.BabyFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentBabyVaccineBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Package.BabyVaccineAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.SuggestAsPerBaby.SuggestAsPerBabyActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalyist.helper.CustomLoader
import com.catalyist.helper.ErrorUtil
import java.util.*
import kotlin.collections.ArrayList


class BabyVaccineFragment : Fragment(), BabyVaccineAdapter.OnClickItem {
    lateinit var bin: FragmentBabyVaccineBinding
    private lateinit var adapter: BabyVaccineAdapter
    private var vaccineAlllist =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData>()
    private lateinit var viewModel: ViewModalLogin
    var vaccineId: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bin = FragmentBabyVaccineBinding.inflate(layoutInflater)
        apiResponse()
        initView()
        lstnr()
        return bin.root
    }

    private fun initView() {
        apiCallingForVaccineAllList()
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

                        var tempFilterList = vaccineAlllist.filter {
                            it.vaccineName.lowercase(Locale.ROOT)
                                .contains(text.lowercase(Locale.ROOT))
                        }
                        bin.recycler.layoutManager =
                            LinearLayoutManager(requireContext())
                        adapter = BabyVaccineAdapter(
                            requireContext(),
                            tempFilterList as ArrayList<AskedData>, this@BabyVaccineFragment,this@BabyVaccineFragment
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
                    adapter = BabyVaccineAdapter(
                        requireContext(),
                        vaccineAlllist,
                        this@BabyVaccineFragment,this@BabyVaccineFragment
                    )
                    bin.recycler.adapter = adapter
                    adapter.notifyDataSetChanged()

                }
            }
        })
        bin.tvclickHere.setOnClickListener {
            startActivity(Intent(requireContext(), SuggestAsPerBabyActivity::class.java))
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        CustomLoader.showLoader(requireActivity())
        viewModel.responseVaccineList.observe(requireActivity()) {
            if (it?.code == 200) {
                CustomLoader.hideLoader()
                vaccineAlllist.clear()
                vaccineAlllist.addAll(it.askedData)
//                bin.textView53.text = "${vaccineAlllist.size} Results"
                bin.recycler.layoutManager =
                    LinearLayoutManager(requireContext())
                adapter = BabyVaccineAdapter(requireContext(), vaccineAlllist, this,this)
                bin.recycler.adapter = adapter
                adapter.notifyDataSetChanged()

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseAddFav.observe(requireActivity()) {
            if (it?.code == 200) {
                apiCallingForVaccineAllList()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(requireActivity()) {
            ErrorUtil.handlerGeneralError(requireContext(), it)

        }
    }

    private fun apiCallingForVaccineAllList() {
        viewModel.onVaccineList(requireContext())
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

    override fun onFavVaccine(favid: String) {
        vaccineId = favid
        println("----favId$vaccineId")
        apiCallingForAddFav()
    }
    private fun apiCallingForAddFav() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onAddFav(requireContext(), userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kvaccineId] = vaccineId
        return hashMap
    }

}