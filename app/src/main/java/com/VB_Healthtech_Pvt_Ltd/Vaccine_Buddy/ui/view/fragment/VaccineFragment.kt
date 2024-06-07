package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentVaccineBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.WishList.VaccineAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity
import com.catalyist.helper.ErrorUtil


class VaccineFragment : Fragment(), VaccineAdapter.OnClickWishList {
    lateinit var bin: FragmentVaccineBinding
    private lateinit var adapter: VaccineAdapter
    private var wishvacccinelist =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishVaccineList.AskedRequest>()
    private lateinit var viewModel: ViewModalLogin
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bin = FragmentVaccineBinding.inflate(layoutInflater)
        apiResponse()
        initView()
        lstnr()
        // Inflate the layout for this fragment
        return bin.root
    }

    private fun initView() {
        apiCallingForWishVaccineList()
    }

    private fun lstnr() {

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseWishVaccineList.observe(requireActivity()) {
            if (it?.code == 200) {
                wishvacccinelist.clear()
                wishvacccinelist.addAll(it.askedRequests)
                bin.recycler2.layoutManager =
                    LinearLayoutManager(requireContext())
                adapter = VaccineAdapter(requireContext(), wishvacccinelist, this,this)
                bin.recycler2.adapter = adapter
                adapter!!.notifyDataSetChanged()

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseAddFav.observe(requireActivity()) {
            if (it?.code == 200) {
                apiCallingForWishVaccineList()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(requireActivity()) {
            ErrorUtil.handlerGeneralError(requireContext(), it)

        }
    }

    private fun apiCallingForWishVaccineList() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onWishVaccineList(requireContext(), userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kType] = "vaccine"
        return hashMap
    }

    var vaccineId = ""
    override fun onRemoveWishlist(removeId: String) {
        vaccineId = removeId
        vaccineDeletePopup()
    }

    override fun onVaccineId(msg: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishVaccineList.AskedRequest) {
        vaccineId = msg.vaccineData._id
        startActivity(
            Intent(
                requireContext(),
                VaccineDetailsActivity::class.java
            ).putExtra("vaccineId", vaccineId)
        )
    }

    private fun apiCallingForAddFav() {
        val userMap: HashMap<String, Any> = prepareDataforRemoveWishlist()
        viewModel.onAddFav(requireContext(), userMap)
    }

    private fun prepareDataforRemoveWishlist(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kvaccineId] = vaccineId
        return hashMap
    }

    private fun vaccineDeletePopup() {
        val dialog = this.let { Dialog(requireContext()) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.wishlist_delete_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val cont = dialog.findViewById<TextView>(R.id.tvTestWishList)
        val cancel = dialog.findViewById<TextView>(R.id.tvCanclewishlist)
        val ok = dialog.findViewById<TextView>(R.id.tvOkwishList)
        cont.text ="Are you sure you want to remove\n vaccine from Wishlist"
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


