package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Location

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivitySetLocationBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.GPSService
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*


class SetLocationActivity : AppCompatActivity() {

    lateinit var binding: ActivitySetLocationBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    private lateinit var viewModel: ViewModalLogin

    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS,Place.Field.LAT_LNG)
    val token: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()
    private var from: String = ""
    private var mLat:String=""
    private var mLong:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Places.initialize(applicationContext, getString(R.string.google_play_api))

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        CommonUtil.themeSet(this, window)
        from = intent.getStringExtra("from").toString()
        println("-----From$from")
        listener()
        initView()
    }

    @SuppressLint("MissingPermission", "")
    private fun getLocation() {
            mFusedLocationClient.lastLocation.addOnCompleteListener(this) {
                val location: Location = it.result
                if (location != null) {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val list: List<Address> =
                        geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        ) as List<Address>
                    this.getSharedPreferences(MyConstants.PREF_NAME, Context.MODE_PRIVATE).edit() {
                        putString(
                            MyConstants.ktypeLATITUDE,
                            GPSService.mLastLocation!!.latitude.toString()
                        )
                        putString(
                            MyConstants.ktypeLONGITUDE,
                            GPSService.mLastLocation!!.longitude.toString()
                        )
                        apply()
                    }

                    mLat = GPSService.mLastLocation!!.latitude.toString()
                    mLong=GPSService.mLastLocation!!.longitude.toString()

                    finish()


                }
            }
    }
    private fun initView() {
        CommonUtil.themeSet(this, window)
        binding.setlocationToolbar.tvTittle.text = "Set Location"
        binding.setlocationToolbar.constraintLayout32.visibility = View.GONE
    }

    private fun listener() {
        binding.setlocationToolbar.ivBack.setOnClickListener {
            finish()
        }

        binding.placeTextview.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields
            ).setCountry("IN").setTypeFilter(TypeFilter.ADDRESS)
                .build(this@SetLocationActivity)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

        binding.getCurrentLocation.setOnClickListener {
            getLocation()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data)

                Log.i(TAG, "Place:12 " + place.name + ", " + place.id)
                binding.placeTextview.setText(place.name)
                this.getSharedPreferences(MyConstants.PREF_NAME, Context.MODE_PRIVATE).edit() {
                    place.name?.let {
                        putString(MyConstants.kAddressNew, place.name)
                        putString(MyConstants.kAddress, place.name)
                    }
                    place.latLng?.let {
                        putString(MyConstants.ktypeLATITUDE, place.latLng.latitude.toString())
                        println("--lat${place.latLng.latitude}")
                    }
                    place.latLng?.let {
                        putString(MyConstants.ktypeLONGITUDE, place.latLng.longitude.toString())
                        println("--long${place.latLng.longitude}")
                    }

                    mLat =place.latLng.latitude.toString()
                    mLong=place.latLng.longitude.toString()
                    val geocoder = Geocoder(this@SetLocationActivity, Locale.getDefault())
                    val list: List<Address> = geocoder.getFromLocation(
                        place.latLng.latitude.toDouble(),
                        place.latLng.longitude.toDouble(),
                        1
                    ) as List<Address>
                    putString(
                        MyConstants.ktypeLATITUDE,
                        mLat
                    )
                    putString(
                        MyConstants.ktypeLONGITUDE,
                        mLong
                    )
                    putString(MyConstants.kstate, list?.get(0)?.adminArea)
                    putString(MyConstants.kcity, list?.get(0)?.locality)
                    putString(MyConstants.kisoCode, list?.get(0)?.postalCode)
                    apply()


                }
            }

            finish()

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status: Status = Autocomplete.getStatusFromIntent(data)
            Log.i(TAG, status.statusMessage!!)

        } else {
            Log.i(TAG, "statusMessage!!")
        }

    }
}

