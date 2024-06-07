package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.FragmentMyOrderBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Order.CancelledAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Order.CompletedAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Order.CurrentAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Current.OrderData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.OrderDetails.OrderDetailsActivity
import com.catalyist.helper.ErrorUtil
import java.util.*

class MyOrderFragment : Fragment(), CurrentAdapter.OrderId, CompletedAdapter.CompleteOrderId,CancelledAdapter.CancelOrderId {
    lateinit var bin: FragmentMyOrderBinding
    private lateinit var viewModel: ViewModalLogin
    private var current: String = "Current"
    private var completed: String = "Completed"
    private var cancelled: String = "Cancelled"
    private var orderList = ArrayList<OrderData>()
    private var completedList =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Completed.OrderData>()
    private var adpterCurrent: CurrentAdapter? = null
    private var adpterCompleted: CompletedAdapter? = null
    private var adpterCancelled: CancelledAdapter? = null
    private var cancelList =
        ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Cancelled.OrderData>()
    private var orderId = ""
    private var serach_type: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentMyOrderBinding.inflate(layoutInflater)
        apiResponse()
        initView()
        lstnr()
        return bin.root
    }

    private fun initView() {
        bin.include.tvTittle.text = "My Orders"
//        bin.include.ivBack.visibility = View.GONE
        chnageBtn(true, false, false)
        viewModel.onOrderList(requireContext(), "pending")

    }

    private fun lstnr() {
        bin.textView193.setOnClickListener {
            chnageBtn(true, false, false)
            viewModel.onOrderList(requireContext(), "pending")
        }
        bin.textView194.setOnClickListener {
            chnageBtn(false, true, false)
            viewModel.onOrderCompletedList(requireContext(), "complete")
            println("test")
        }
        bin.textView195.setOnClickListener {
            chnageBtn(false, false, true)
            viewModel.onOrderCancelList(requireContext(), "cancel")
        }
        var number = "9650039988"
        bin.ivwhatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${"+91"}$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        bin.seach.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var text = p0.toString().trim()
                if (text.isNotEmpty()) {
                    if (serach_type) {
                        if (text.length > 0) {
                            var tempFilterList = orderList.filter {
                                it.itemName.lowercase(Locale.ROOT)
                                    .contains(text.lowercase(Locale.ROOT))
                            }
                            adpterCurrent = CurrentAdapter(
                                requireContext(),
                                tempFilterList as ArrayList<OrderData>,
                                this@MyOrderFragment
                            ).apply {
                                bin.rcyorder.layoutManager =
                                    LinearLayoutManager(requireContext())
                                bin.rcyorder.adapter = this
                            }
                            if (tempFilterList.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "Data Not Found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else if (serach_type) {
                        if (text.length > 0) {
                            var tempFilterList = completedList.filter {
                                it.itemName.lowercase(Locale.ROOT)
                                    .contains(text.lowercase(Locale.ROOT))
                            }
                            adpterCompleted = CompletedAdapter(
                                requireContext(), completedList, this@MyOrderFragment
                            ).apply {
                                bin.rcyorder.layoutManager =
                                    LinearLayoutManager(requireContext())
                                bin.rcyorder.adapter = this

                            }
                            if (tempFilterList.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "Data Not Found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else if (serach_type) {
                        if (text.length > 0) {
                            var tempFilterList = cancelList.filter {
                                it.itemName.lowercase(Locale.ROOT)
                                    .contains(text.lowercase(Locale.ROOT))
                            }
                            adpterCancelled = CancelledAdapter(
                                requireContext(), cancelList, this@MyOrderFragment
                            ).apply {
                                bin.rcyorder.layoutManager =
                                    LinearLayoutManager(requireContext())
                                bin.rcyorder.adapter = this
                            }
                            if (tempFilterList.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "Data Not Found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        if (serach_type) {
                            adpterCurrent = CurrentAdapter(
                                requireContext(), orderList, this@MyOrderFragment
                            ).apply {
                                bin.rcyorder.layoutManager =
                                    LinearLayoutManager(requireContext())
                                bin.rcyorder.adapter = this


                            }
                        } else if (serach_type) {
                            adpterCompleted = CompletedAdapter(
                                requireContext(), completedList, this@MyOrderFragment
                            ).apply {
                                bin.rcyorder.layoutManager =
                                    LinearLayoutManager(requireContext())
                                bin.rcyorder.adapter = this

                            }
                        } else {
                            adpterCancelled = CancelledAdapter(
                                requireContext(), cancelList, this@MyOrderFragment
                            ).apply {
                                bin.rcyorder.layoutManager =
                                    LinearLayoutManager(requireContext())
                                bin.rcyorder.adapter = this
                            }
                        }


                    }
                }

            }
        })

    }

    @SuppressLint("ResourceType")
    private fun chnageBtn(name: Boolean, name1: Boolean, name2: Boolean) {
        if (name) {
            serach_type = name
            println("========name$name")
            bin.textView195.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            bin.textView195.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.vacine_edit)
            bin.textView194.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            bin.textView194.background =
                ContextCompat.getDrawable(requireContext(), R.color.darknavyblue2)

            bin.textView193.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.darknavyblue2
                )
            )
            bin.textView193.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.package_edit)
            adpterCurrent = CurrentAdapter(
                requireContext(), orderList, this
            ).apply {
                bin.rcyorder.layoutManager =
                    LinearLayoutManager(requireContext())
                bin.rcyorder.adapter = this


            }
        } else if (name1) {
            serach_type = name1
            println("========name1$name1")
            bin.textView195.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            bin.textView195.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.vacine_edit)
            bin.textView194.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.darknavyblue2
                )
            )
            bin.textView194.background =
                ContextCompat.getDrawable(requireContext(), R.color.white)
            bin.textView193.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            bin.textView193.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.package_edit2)
            adpterCompleted = CompletedAdapter(
                requireContext(), completedList, this
            ).apply {
                bin.rcyorder.layoutManager =
                    LinearLayoutManager(requireContext())
                bin.rcyorder.adapter = this

            }
        } else {
            serach_type = name2
            println("========name2$name2")
            bin.textView195.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.darknavyblue2
                )
            )
            bin.textView195.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.vacine_edit2)
            bin.textView194.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            bin.textView194.background =
                ContextCompat.getDrawable(requireContext(), R.color.darknavyblue2)
            bin.textView193.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            bin.textView193.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.package_edit2)

            adpterCancelled = CancelledAdapter(
                requireContext(), cancelList,this
            ).apply {
                bin.rcyorder.layoutManager =
                    LinearLayoutManager(requireContext())
                bin.rcyorder.adapter = this
            }
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseOrder.observe(requireActivity()) {
            if (it?.code == 200) {
                orderList.clear()
                orderList.addAll(it.orderData)
                adpterCurrent!!.notifyDataSetChanged()

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
            viewModel.responseOrderCompleted.observe(requireActivity()) {
                if (it?.code == 200) {
                    completedList.clear()
                    completedList.addAll(it.orderData)
                    adpterCompleted!!.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.responseOrderCancel.observe(requireActivity()) {
            if (it?.code == 200) {
                cancelList.clear()
                cancelList.addAll(it.orderData)
                adpterCancelled!!.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(requireActivity()) {
            ErrorUtil.handlerGeneralError(requireContext(), it)
        }
    }

    override fun onOrderId(msg: OrderData) {
        orderId = msg._id
        println("----ord_id$orderId")
        startActivity(
            Intent(requireContext(), OrderDetailsActivity::class.java).putExtra(
                "orderId",
                orderId
            )
        )
    }

    override fun onCompleteOrderId(msg: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Completed.OrderData) {
        orderId = msg._id
        println("-----commOrderId$orderId")
        startActivity(
            Intent(requireContext(), OrderDetailsActivity::class.java).putExtra(
                "orderId",
                orderId
            ).putExtra("from", "complete")
        )
    }

    override fun onCancelOrderId(msg: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Cancelled.OrderData) {
        orderId = msg._id
        println("-----cancelOrderId$orderId")
        startActivity(
            Intent(requireContext(), OrderDetailsActivity::class.java).putExtra(
                "orderId",
                orderId
            ).putExtra("from", "cancel")
        )
    }


}