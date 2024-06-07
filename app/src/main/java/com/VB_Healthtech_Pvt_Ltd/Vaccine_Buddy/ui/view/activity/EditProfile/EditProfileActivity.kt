package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.EditProfile

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityEditProfileBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.Enums
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.TakeImageWithCrop
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.TakeImageWithCrop.CAMERA_REQUEST
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.TakeImageWithCrop.GALLERY_REQUEST
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.Utils
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.ViewProfile.User
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.catalyist.aws.AWSListner
import com.catalyist.aws.AWSUtils
import com.catalyist.helper.ErrorUtil
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity(), AWSListner {
    lateinit var bin: ActivityEditProfileBinding
    private lateinit var viewModel: ViewModalLogin
    private var imagePath: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(bin.root)


        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        val scList = resources.getStringArray(R.array.EditProfile)
        val spinner = bin.gender
        val scAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, scList)
        spinner.adapter = scAdapter
        val editObj = intent.getSerializableExtra("Obj") as User
        println("-----onjjj${editObj}")

        bin.nameEdit.setText(editObj.name)
        bin.mailEdit.setText(editObj.email)
        bin.phoneEdit.setText(editObj.phoneNumber)
        bin.dobEdit.setText(editObj.dob)
        bin.textView6.setText(editObj.name)
        bin.textView62.setText(editObj.email)
        // bin.lactose.setText(editObj.referralCode)
        bin.phoneEdit.setEnabled(false)
        bin.mailEdit.setEnabled(false)
        bin.lactose.setText(editObj.medicalCondition)

        when (editObj.gender) {
            Enums.Male.toString() -> {
                spinner.setSelection(1)
            }
            Enums.Female.toString() -> {
                spinner.setSelection(2)
            }
            Enums.Other.toString() -> {
                spinner.setSelection(3)
            }

        }
        imagePath = editObj.profilePic
        if (imagePath.isNullOrEmpty()) {
            bin.imageView38.visibility = View.VISIBLE
            bin.dummyImg.visibility = View.INVISIBLE
            bin.lottie.visibility = View.GONE
        } else {
            bin.imageView38.visibility = View.INVISIBLE
            bin.dummyImg.visibility = View.VISIBLE
            Picasso.get().load(editObj.profilePic).centerCrop().resize(100, 100)
                .into(bin.dummyImg)

        }
        bin.editToobar.tvTittle.text = "My Profile"
        bin.commonBtn.tv.text = "Update Profile"
    }

    private fun lstnr() {
        bin.editToobar.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.commonBtn.tv.setOnClickListener {
            if (!isValide()) {
                return@setOnClickListener
            }
            apiCallingForUpdateProfile()
        }
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

        bin.dobEdit.setOnClickListener {
            MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build()).setSelection(Date().time)
                .build()
                .apply {
                    show(supportFragmentManager, this@EditProfileActivity.toString())
                    addOnPositiveButtonClickListener {
                        bin.dobEdit.setText(
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                Date(it)
                            )
                        )
                    }
                }
        }

        bin.imageView69.setOnClickListener {
            showImagePickDialog()
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseUpdateProfile.observe(this) {
            if (it?.code == 200) {
                finish()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForUpdateProfile() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onUpdateProfile(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kName] = Utils.getProperText(bin.nameEdit)
        hashMap[MyConstants.kDob] = Utils.getProperText(bin.dobEdit)
        hashMap[MyConstants.kGender] = bin.gender.selectedItem.toString()
        hashMap[MyConstants.kMedicalCondition] = Utils.getProperText(bin.lactose)
        hashMap[MyConstants.kprofilePic] = imagePath
        return hashMap
    }

    private fun isValide(): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(Utils.getProperText(bin.nameEdit))) {
            Toast.makeText(this, "Please Enter Name!!", Toast.LENGTH_SHORT).show()
            isValid = false

        } else if (bin.gender.selectedItemPosition.equals(0)) {

            Toast.makeText(this, "Please Select Gender!!", Toast.LENGTH_SHORT).show()
            isValid = false

        } /*else if (TextUtils.isEmpty(Utils.getProperText(bin.lactose))) {
            Toast.makeText(this, "Please Enter MedicalCondition!!", Toast.LENGTH_SHORT).show()
            isValid = false

        }*/

        return isValid


    }

    private fun showImagePickDialog() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setMessage("Choose image")
        dialog.setPositiveButton(
            "Gallery"
        ) { _, _ ->
            val intent = Intent(this, TakeImageWithCrop::class.java)
            intent.putExtra("from", "gallery")
            startActivityForResult(intent, GALLERY_REQUEST)
        }
        dialog.setNegativeButton(
            "Camera"
        ) { _, _ ->


            val intent = Intent(this, TakeImageWithCrop::class.java)
            intent.putExtra("from", "camera")
            startActivityForResult(intent, CAMERA_REQUEST)


        }
        dialog.setNeutralButton(
            "Cancel"
        ) { dialog, which -> dialog.dismiss() }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val path = data?.getStringExtra("filePath")
        if (resultCode == RESULT_OK) {
            if (!path.isNullOrEmpty()) {
                AWSUtils(
                    this,
                    path,
                    this
                )
            }
        } else if (requestCode == GALLERY_REQUEST) {
            if (!path.isNullOrEmpty()) {
                AWSUtils(
                    this,
                    path,
                    this
                )
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onAWSLoader(isLoader: Boolean) {
        bin.lottie.visibility = View.VISIBLE
    }

    override fun onAWSSuccess(url: String?) {
        if (url != null) {
            imagePath = url!!
            bin.lottie.visibility = View.GONE
            bin.dummyImg.visibility = View.VISIBLE
            bin.imageView38.visibility = View.INVISIBLE
            Picasso.get().load(url).centerCrop().resize(100, 100)
                .into(bin.dummyImg)
        } else {
            bin.dummyImg.visibility = View.INVISIBLE
            bin.imageView38.visibility = View.VISIBLE
        }
    }

    override fun onAWSError(error: String?) {
        bin.lottie.visibility = View.GONE
        Log.e("error", error ?: "")
    }

    override fun onAWSProgress(progress: Int?) {
        bin.lottie.visibility = View.VISIBLE
        Log.e("progress", progress!!.toString())
    }
}