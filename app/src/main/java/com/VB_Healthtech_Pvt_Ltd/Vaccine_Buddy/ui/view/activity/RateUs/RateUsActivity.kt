package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.RateUs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityRateUsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.OrderDetailsVaccine.OrderDetailsVaccineDose
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.StatusPackageModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDoesModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.OrderDetails.OrderDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import com.bountybunch.helper.startDownload
import com.catalyist.helper.ErrorUtil
import com.squareup.picasso.Picasso

class RateUsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRateUsBinding
    private lateinit var viewModel: ViewModalLogin
    var nurseId: String = ""
    var rating1: RatingBar? = null
    var orderId: String = ""
    var ratingReview: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)


    }

    private fun initView() {
        binding.rateToolbar.tvTittle.text = "Rate Service"
        binding.tvSubmit.tv.text = "Submit"
        nurseId = intent.getStringExtra("nurseId").toString()
        println("------nurseId$nurseId")
        orderId = intent.getStringExtra("orderId").toString()
        println("----orderId$orderId")
        binding.rating.setOnRatingBarChangeListener(RatingBar.OnRatingBarChangeListener { ratingBar, fl, b ->
            val a = binding.rating.rating
            val b = a.toInt()
            ratingReview = b.toString()
            println("----rrr$ratingReview")
            binding.tvRating.text = "${ratingReview}.0 rating"
        })

    }

    private fun lstnr() {
        binding.rateToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvSubmit.tv.setOnClickListener {
            onBackPressed()
        }
        binding.tvSubmit.tv.setOnClickListener {
            apiCallingForRatingReview()
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseRatingReview.observe(this) {
            if (it?.message.equals("Request Successful")) {
                startActivity(
                    Intent(this, OrderDetailsActivity::class.java).putExtra(
                        "orderId",
                        orderId
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                finish()
            }

        }
        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    private fun apiCallingForRatingReview() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onRatingReview(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.knurseId] = nurseId
        hashMap[MyConstants.kcontent] = binding.editTextTextPersonName.text.trim().toString()
        hashMap[MyConstants.kratingNumber] = ratingReview
        hashMap[MyConstants.korderId] = orderId
        return hashMap
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}