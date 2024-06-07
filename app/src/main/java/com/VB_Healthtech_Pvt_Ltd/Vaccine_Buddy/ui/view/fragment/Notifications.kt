package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentNotificationsBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.*
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.AskedNotification
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.DoseInfo
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity
import com.catalyist.helper.ErrorUtil
import kotlin.collections.ArrayList

class Notifications : Fragment(), MyAdapter.ClickId {
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var adapter: MyAdapter
    private var notiList = ArrayList<AskedNotification>()
    private lateinit var viewModel: ViewModalLogin
    private var packageId: String? = null
    private var pId: String? = null
    private var vaccineIdList = ArrayList<DoseInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(layoutInflater)
        apiResponse()
        initView()
        lstnr()

        return (binding.root)
    }

    private fun initView() {
        binding.notificationToolbar.tvTittle.text = "Notification"
        viewModel.onNotificationList(requireContext())

    }

    private fun lstnr() {

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseNotification.observe(requireActivity()) {
            if (it?.code == 200) {
                notiList.clear()
                notiList.addAll(it.askedNotification)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                adapter = MyAdapter(requireContext(), notiList, this)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.error.observe(requireActivity())
        {
            ErrorUtil.handlerGeneralError(requireContext(), it)
        }
    }

    override fun onClickId(id: String, notiType: String, msg: AskedNotification) {
        packageId = id
        println("notiPackageId$packageId")
        if (notiType.equals("Custom Package")) {
            startActivity(
                Intent(
                    requireContext(),
                    PackageDetailsActivity::class.java
                ).putExtra("packageId", packageId)
            )
        } else {
            if (vaccineIdList.size > 0) {
                vaccineIdList.clear()
            }
            msg.doseInfo.forEach {
                vaccineIdList.add(it)
            }
            println("-----iiddd$vaccineIdList")
            startActivity(
                Intent(
                    requireContext(),
                    VaccineDetailsActivity::class.java
                ).putExtra("vaccineId", packageId).putExtra("vaccineIdList", vaccineIdList)
                    .putExtra("memberId", msg.memberId).putExtra("notiType", notiType)
            )
        }

    }

    /*private fun dataInitialization() {
        newArrayList = arrayListOf<Modal>()
        imageId = arrayOf(
            R.drawable.ic_red_cart, R.drawable.ic_blue_cart,
            R.drawable.ic_orange_cart, R.drawable.ic_navyblue_cart, R.drawable.ic_darkblue_cart
        )

        heading = arrayOf(
            getString(R.string.order_confirmed),
            getString(R.string.payment_Success),
            getString(R.string.offer_discount),
            getString(R.string.you_got_a_promo_code),
            getString(R.string.order_delivered)
        )

        tv = arrayOf(getString(R.string.itemText))

        time = arrayOf(
            getString(R.string._08_30),
            getString(R.string._08_30),
            getString(R.string.yesterday),
            getString(R.string.monday),
            getString(R.string.september_20)
        )

        for (i in imageId.indices) {
            val modal = Modal(imageId[i], heading[i], tv[i], time[i])
            newArrayList.add(modal)
        }
    }*/
}

