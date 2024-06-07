package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddressSecond


import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivitySecondAddressBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Address.AddressAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.AddressList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddNewAddress
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
import kotlin.collections.ArrayList

class SecondAddressActivity : AppCompatActivity(), AddressAdapter.OnClickItem {
    private lateinit var binding: ActivitySecondAddressBinding
    private lateinit var viewModel: ViewModalLogin
    private var addresslist =
        ArrayList<AskedData>()
    var position: Int = 0
    private var adpterAddKid: AddressAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this,window)
        binding.include2.tvTittle.text = "My Address"


    }

    private fun lstnr() {
        binding.include2.ivBack.setOnClickListener {
            onBackPressed()

        }
        binding.textView140.setOnClickListener {
            startActivity(Intent(this, AddNewAddress::class.java))
            finish()

        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseAddressList.observe(this) {
            if (it?.code == 200) {
                addresslist.clear()
                addresslist.addAll(it.askedData)
                binding.rcyAddkid.layoutManager =
                    LinearLayoutManager(this)
                adpterAddKid = AddressAdapter(this, addresslist, this)
                binding.rcyAddkid.adapter = adpterAddKid
                adpterAddKid!!.notifyDataSetChanged()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.responseDelete.observe(this) {
            if (it?.code == 200) {
                //Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
                addresslist?.removeAt(position)
                adpterAddKid?.notifyItemRemoved(position)
                adpterAddKid?.notifyItemRangeChanged(position, addresslist?.size ?: 0)
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseDefaultAddress.observe(this) {
            if (it?.code == 200) {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
                apiCallingforAddressList()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiCallingforAddressList() {
        viewModel.onAddresseList(this)
    }

    private fun apiCallingForDelete() {
        viewModel.onDelete(this, id)
    }

    private fun apiCallingForDefaultAddress() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onDefaultAddress(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kaddressId] = defaultAddressId

        return hashMap
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun onResume() {
        super.onResume()
        apiCallingforAddressList()
    }

    var id: String = ""
    override fun onDelete(
        msgItem: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.AddressList.AskedData,
        position: Int
    ) {
        id = msgItem._id
        this.position = position
        println("-----iddddd${id}")
        deletePop()
    }

    var defaultAddressId: String = ""
    override fun onDefaultAddress(msgItem: AskedData, position: Int) {
       defaultAddressId = msgItem._id
        apiCallingForDefaultAddress()
    }

    private fun deletePop() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_cancel)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)

        val btDismiss = dialog.findViewById<TextView>(R.id.dialog_newcancel)
        val btSubmit = dialog.findViewById<TextView>(R.id.dialog_ok)

        btSubmit.setOnClickListener {
            dialog.dismiss()
            apiCallingForDelete()
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
}
