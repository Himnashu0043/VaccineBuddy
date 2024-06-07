package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Offer

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityOffersBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.OffersAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Offers.AskedCoupon
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalyist.helper.ErrorUtil

class OffersActivity : AppCompatActivity() {
    lateinit var binding: ActivityOffersBinding
    private var adpater: OffersAdapter? = null
    private lateinit var viewModel: ViewModalLogin
    private var offersList = ArrayList<AskedCoupon>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOffersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        binding.offerToolbar.tvTittle.text = "Offers"
        viewModel.onOffersList(this)

    }

    private fun lstnr() {
        binding.offerToolbar.ivBack.setOnClickListener {
           finish()
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseOffersList.observe(this) {
            if (it?.code == 200) {
                offersList.clear()
                offersList.addAll(it.askedCoupon)
                binding.rcyOffers.layoutManager = LinearLayoutManager(this)
                adpater = OffersAdapter(this, offersList)
                binding.rcyOffers.adapter = adpater
                adpater!!.notifyDataSetChanged()
            } else if (it?.code == 401) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    override fun onBackPressed() {
        super.finish()
    }
}