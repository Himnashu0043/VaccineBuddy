package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MyProfile

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityMainProfileBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.MainProfileAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MainProfileModal
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.About.AboutActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddressSecond.SecondAddressActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ContactUs.ContactUsActivtity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.CustomePackage.CustomePackageActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.FamilyMember.FamilyMemberActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.KidsGrowth.KidsGrowthRecordActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduledCounsellingCall.ScheduledCounsellingCallActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccinationRecord.VaccinationRecordActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.*
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.newOtp.NewOtpActivity
import com.catalyist.helper.CustomLoader
import com.catalyist.helper.ErrorUtil
import com.squareup.picasso.Picasso

class MainProfileActivity : AppCompatActivity(), MainProfileAdapter.OnClickItem {
    lateinit var bin: ActivityMainProfileBinding
    private var adaptermainProfile: MainProfileAdapter? = null
    private var mainprofileList = ArrayList<MainProfileModal>()
    private lateinit var viewModel: ViewModalLogin
    var referenceCode = ""
    var websiteLink: String =
        "https://play.google.com/store/apps/details?id=com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMainProfileBinding.inflate(layoutInflater)
        setContentView(bin.root)

        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this, window)

        bin.profiletoolbar.tvTittle.text = "Profiles"
        mainprofileList.add(MainProfileModal("My Profile", R.drawable.ic_path_5559))
        mainprofileList.add(MainProfileModal("Family member", R.drawable.ic_members))
        mainprofileList.add(MainProfileModal("My Addresses", R.drawable.ic_address))
        mainprofileList.add(
            MainProfileModal(
                "Vaccination Records",
                R.drawable.ic_vacination_record
            )
        )
        mainprofileList.add(MainProfileModal("Custom Package", R.drawable.ic_kids_growth))
        mainprofileList.add(MainProfileModal("Kids Growth Records", R.drawable.ic_kids_growth))
        mainprofileList.add(MainProfileModal("Counselling Call", R.drawable.ic_call))
        mainprofileList.add(MainProfileModal("Settings", R.drawable.ic_settings))
        mainprofileList.add(MainProfileModal("About Us", R.drawable.ic_about_us))
        mainprofileList.add(MainProfileModal("Terms & Conditions", R.drawable.ic_terms_cod))
        mainprofileList.add(MainProfileModal("Privacy Policy", R.drawable.ic_pivacy))
        mainprofileList.add(MainProfileModal("Contact Us", R.drawable.ic_contact))
        mainprofileList.add(MainProfileModal("Invite Friends", R.drawable.ic_invite))
        mainprofileList.add(MainProfileModal("Logout", R.drawable.ic_logout))




        bin.rcyMyProfile.layoutManager =
            LinearLayoutManager(this)
        adaptermainProfile = MainProfileAdapter(this, mainprofileList, this)
        bin.rcyMyProfile.adapter = adaptermainProfile
        adaptermainProfile!!.notifyDataSetChanged()

    }

    private fun lstnr() {
        bin.profiletoolbar.ivcross.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun opendialogbox() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.logout_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val btDismiss = dialog.findViewById<TextView>(R.id.dialog_cancel)
        val btSubmit = dialog.findViewById<TextView>(R.id.dialog_submit)
        val dialogText = dialog.findViewById<TextView>(R.id.dialog_text)
//        dialogText.setText(R.string.are_you_sure_want_to_logout)

        btSubmit.setOnClickListener {
//           onLogOut()
            dialog.dismiss()

            PrefrencesHelper.getSaveToken(this, "")
            PrefrencesHelper.saveLoginStatus(this, false)
            PrefrencesHelper.getEmailKey(this, "")
            PrefrencesHelper.getKey(this, "")
            PrefrencesHelper.saveFullName(this, "")
            PrefrencesHelper.savePackageId(this, "")
            PrefrencesHelper.savePrice(this, "")
            PrefrencesHelper.saveNoDose(this, "")
            PrefrencesHelper.saveAge(this, "")

            PrefrencesHelper.saveFagStatus(this, false)
            startActivity(
                Intent(this, NewOtpActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finishAffinity()
        }

        btDismiss.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        CustomLoader.hideLoader()
        viewModel.responseViewProfile.observe(this) {
            if (it?.code == 200) {
                referenceCode = it.user.myRefferral
                CustomLoader.hideLoader()

                bin.tvEmailMainProfile.text = it.user.email
                if (it.user.profilePic.isNullOrEmpty()) {
                    bin.dummy2Img.visibility = View.VISIBLE
                    bin.imageView59.visibility = View.INVISIBLE
                } else {
                    Picasso.get().load(it.user.profilePic).centerCrop().resize(100, 100)
                        .into(bin.imageView59)
                    bin.dummy2Img.visibility = View.INVISIBLE
                    bin.imageView59.visibility = View.VISIBLE
                }
                if (it.user.name.isNullOrEmpty()) {
                    bin.tvMainProileName.text = "Hi,Guest"
                    firstTimePopup()
                } else {
                    bin.tvMainProileName.text = it.user.name
                }
            } else if (it?.code == 401) {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    fun onViewProfileAPI() {
        viewModel.onViewProfile(this)
    }

    fun sendData() {
        val sendData =
            "Application link : ${websiteLink}\nReference Code :${referenceCode}, IOS users ${"http://vaccinebuddy.app.link/rTTm37MjMvb"}\nIOS Reference Code :${referenceCode}"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT, sendData
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    override fun clickOn(msgItem: MainProfileModal, postion: Int) {
        if (postion == 0) {
            startActivity(Intent(this, MyProfileActivity::class.java))

        } else if (postion == 1) {
            startActivity(Intent(this, FamilyMemberActivity::class.java))

        } else if (postion == 2) {
            startActivity(Intent(this, SecondAddressActivity::class.java))

        } else if (postion == 3) {
            startActivity(Intent(this, VaccinationRecordActivity::class.java))

        } else if (postion == 4) {
            startActivity(Intent(this, CustomePackageActivity::class.java))

        } else if (postion == 5) {
            startActivity(Intent(this, KidsGrowthRecordActivity::class.java))

        } else if (postion == 6) {
            startActivity(Intent(this, ScheduledCounsellingCallActivity::class.java))

        } else if (postion == 7) {
            startActivity(Intent(this, SettingsActivity::class.java))

        } else if (postion == 8) {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getAbout"))

        } else if (postion == 9) {

            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getTerm"))

        } else if (postion == 10) {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("about", "getPrivacy"))

        } else if (postion == 11) {
            startActivity(Intent(this, ContactUsActivtity::class.java))

        } else if (postion == 12) {
            sendData()
        } else if (postion == 13) {
            opendialogbox()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun firstTimePopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.first_time_enter_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val cont = dialog.findViewById<TextView>(R.id.tvcont)
        val may = dialog.findViewById<TextView>(R.id.mayBelater)
        cont.setOnClickListener {
            dialog.dismiss()
            PrefrencesHelper.getKey(this, "profile")
            PrefrencesHelper.getEmailKey(this, "emailKey")
            startActivity(Intent(this, AccountCreateActivity::class.java))
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

    override fun onResume() {
        super.onResume()
        onViewProfileAPI()
    }
}