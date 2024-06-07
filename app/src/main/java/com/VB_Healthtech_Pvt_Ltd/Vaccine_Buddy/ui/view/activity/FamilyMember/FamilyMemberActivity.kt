package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.FamilyMember

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityScheduleCounsellingForMemberBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.FamilyMember.FamilyMemberAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddFamilyMember
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalyist.helper.ErrorUtil

class FamilyMemberActivity : AppCompatActivity(), FamilyMemberAdapter.OnClickItem {
    private lateinit var binding: ActivityScheduleCounsellingForMemberBinding
    private var adpterScheduleing: FamilyMemberAdapter? = null
    private lateinit var viewModel: ViewModalLogin
    private var familyMemberList = ArrayList<AskedData>()
    var positionDeleteFm: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleCounsellingForMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()


    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        binding.scheduleToolbaar.tvTittle.text = "Family Members"
        apiCallingForFamilyMemberList()

    }

    private fun lstnr() {
        binding.scheduleToolbaar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.addmember.setOnClickListener {
            val intent = Intent(this, AddFamilyMember::class.java)
            intent.putExtra("redirection", "add")
            startActivity(intent)
            finish()
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseFamilyMemberList.observe(this) {
            if (it?.code == 200) {
                if (familyMemberList.size < 10) {
                    binding.threeMemberView.text = "0" + familyMemberList.size + " Family Member"
                } else {
                    binding.threeMemberView.text = familyMemberList.size.toString()
                }
                familyMemberList.clear()
                familyMemberList.addAll(it.askedData)
                binding.rcyschedulCus.layoutManager =
                    LinearLayoutManager(this)
                adpterScheduleing = FamilyMemberAdapter(this, familyMemberList, this)
                binding.rcyschedulCus.adapter = adpterScheduleing
                adpterScheduleing!!.notifyDataSetChanged()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseDeleteFamilyMember.observe(this) {
            if (it?.code == 200) {
                if (familyMemberList.size > 0) {
                    binding.threeMemberView.text =
                        (familyMemberList.size - 1).toString() + " Family Member"

                } else {
                    binding.threeMemberView.text = "0 Family Member"

                }
                familyMemberList?.removeAt(positionDeleteFm)
                adpterScheduleing?.notifyItemRemoved(positionDeleteFm)
                adpterScheduleing?.notifyItemRangeChanged(
                    positionDeleteFm,
                    familyMemberList?.size ?: 0
                )
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingForFamilyMemberList() {
        viewModel.onFamilyMemberList(this)
    }

    private fun apiCallingForDeleteFamilyMember() {
        viewModel.onDeleteFamilyMember(this, familyMemberDeleteId)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        apiCallingForFamilyMemberList()
    }

    private fun openPopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_cancel)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)

        val btDismiss = dialog.findViewById<TextView>(R.id.dialog_newcancel)
        val btok = dialog.findViewById<TextView>(R.id.dialog_ok)

        btok.setOnClickListener {
            dialog.dismiss()
            apiCallingForDeleteFamilyMember()
        }

        btDismiss.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    var familyMemberDeleteId: String = ""
    override fun onDelete(msgItem: AskedData, position: Int) {
        familyMemberDeleteId = msgItem._id
        positionDeleteFm = position
        openPopup()
    }

}