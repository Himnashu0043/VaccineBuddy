package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.CustomePackage

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityCustomePackageBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.CustomePackage.CustomePackageAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CustomPackage.AskedPackage
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalyist.helper.ErrorUtil

class CustomePackageActivity : AppCompatActivity(), CustomePackageAdapter.OnClickItem {
    lateinit var binding: ActivityCustomePackageBinding
    private lateinit var adapter: CustomePackageAdapter
    private lateinit var viewModel: ViewModalLogin
    private var customePackagelist = ArrayList<AskedPackage>()
    var customepackageId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomePackageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
    }

    private fun initView() {
        binding.customeToolbar.tvTittle.text = "Custom Package"
        apiCallingForCustomePackageList()

    }

    private fun lstnr() {
        binding.customeToolbar.ivBack.setOnClickListener {
            finish()
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseCustomePackageList.observe(this) {
            if (it?.code == 200) {
                customePackagelist.clear()
                customePackagelist.addAll(it.askedPackage)
                binding.rcyCustomePackage.layoutManager =
                    LinearLayoutManager(this)
                adapter = CustomePackageAdapter(this, customePackagelist, this)
                binding.rcyCustomePackage.adapter = adapter
                adapter!!.notifyDataSetChanged()

            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForCustomePackageList() {
        viewModel.onCustomePackageList(this)
    }

    override fun packageclickOn(msgItem: AskedPackage, position: Int) {
        customepackageId = msgItem._id
        startActivity(
            Intent(
                this,
                PackageDetailsActivity::class.java
            ).putExtra("packageId", customepackageId).putExtra("from_createOwn_Package","create_own_package")
        )
    }
}