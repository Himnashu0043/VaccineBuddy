package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.WishList.PackageListAdpater
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentPackageBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishPackageList.AskedRequest
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AccountCreateActivity
import com.catalyist.helper.ErrorUtil


class PackageFragment : Fragment(), PackageListAdpater.OnClickWishList {
    lateinit var bin: FragmentPackageBinding

    private lateinit var adapter: PackageListAdpater
    private var wishpackagelist = ArrayList<AskedRequest>()
    private lateinit var viewModel: ViewModalLogin
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bin = FragmentPackageBinding.inflate(layoutInflater)
        apiResponse()
        initView()
        lstnr()
        return bin.root
    }

    private fun initView() {
        apiCallingForWishPackageList()
    }

    private fun lstnr() {

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseWishPackageList.observe(requireActivity()) {
            if (it?.code == 200) {
                wishpackagelist.clear()
                wishpackagelist.addAll(it.askedRequests)
                bin.recycler.layoutManager =
                    LinearLayoutManager(requireContext())
                adapter = PackageListAdpater(requireContext(), wishpackagelist, this, this)
                bin.recycler.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseAddFav.observe(requireActivity()) {
            if (it?.code == 200) {
                apiCallingForWishPackageList()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.error.observe(requireActivity()) {
            ErrorUtil.handlerGeneralError(requireContext(), it)

        }
    }

    private fun apiCallingForWishPackageList() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onWishPackageList(requireContext(), userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kType] = "package"
        return hashMap
    }

    var packageId = ""
    override fun onRemoveWishlist(removeId: String) {
        packageId = removeId
        packageDeletePopup()
    }

    override fun onPackageId(msg: AskedRequest) {
        packageId = msg.packageData._id
        startActivity(
            Intent(
                requireContext(),
                PackageDetailsActivity::class.java
            ).putExtra("packageId", packageId)
        )
    }

    private fun apiCallingForAddFav() {
        val userMap: HashMap<String, Any> = prepareDataforRemoveWishlist()
        viewModel.onAddFav(requireContext(), userMap)
    }

    private fun prepareDataforRemoveWishlist(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kpackageId] = packageId
        return hashMap
    }
    private fun packageDeletePopup() {
        val dialog = this.let { Dialog(requireContext()) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.wishlist_delete_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val cont = dialog.findViewById<TextView>(R.id.tvTestWishList)
        val cancel = dialog.findViewById<TextView>(R.id.tvCanclewishlist)
        val ok = dialog.findViewById<TextView>(R.id.tvOkwishList)
        ok.setOnClickListener {
            dialog.dismiss()
            apiCallingForAddFav()
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

    }

}