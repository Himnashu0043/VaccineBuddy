package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Order

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.CancelledItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Cancelled.OrderData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CancelledAdapter(
    val con: Context,
    val cancelList: ArrayList<OrderData>,
    val onPress: CancelOrderId
) :
    RecyclerView.Adapter<CancelledAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: CancelledItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(CancelledItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (cancelList[position].order_type.equals("PACKAGE")) {
            val img: String = cancelList[position].itemImage
            holder.mView.textView123.visibility = View.VISIBLE
            holder.mView.textView1231.visibility = View.INVISIBLE
            if (img != null && img.length > 0) {
                Picasso.get().load(img).into(holder.mView.ivmain)
                holder.mView.ivmain.visibility = View.VISIBLE
                holder.mView.ivdummy.visibility = View.INVISIBLE

            } else {
                holder.mView.ivdummy.visibility = View.VISIBLE
                holder.mView.ivmain.visibility = View.INVISIBLE
            }
            holder.mView.tvOrderName.text = cancelList[position].itemName
            val date = cancelList[position].createdAt
            val convert_date = setFormatDate(date)
            holder.mView.tvOrderDate.text = convert_date
            holder.mView.tvOrderDes.text = cancelList[position].description
            if (cancelList[position].itemPrice.isEmpty()) {
                holder.mView.tvOrderprice.text = "₹ ${cancelList[position].packagePrice}"
                holder.mView.tvOrderOfferprice.visibility = View.GONE
                holder.mView.divider14.visibility = View.GONE
            } else {
                holder.mView.tvOrderOfferprice.visibility = View.VISIBLE
                holder.mView.divider14.visibility = View.VISIBLE
                holder.mView.tvOrderprice.text = "₹ ${cancelList[position].packagePrice}"
                holder.mView.tvOrderOfferprice.text = "₹ ${cancelList[position].itemPrice}"
            }
            holder.mView.tvOrderIncludeVaccine.text =
                cancelList[position].inclusiveVaccine.toString()
            holder.mView.tvOrderPendingDoes.text =
                "${cancelList[position].pendingDose.toString()} Pending Dosage"
            holder.mView.tvOrderCompleteDoes.text =
                "${cancelList[position].completedDose.toString()} complete Dosage"
            holder.mView.tvVaccineOrderName.text = cancelList[position].userData.get(0).fullName
            val from_or =
                "${cancelList[position].slot.get(0).from} - ${cancelList[position].slot.get(0).to}"
            val slot_date = setFormatDate(cancelList[position].slotDate)
            holder.mView.tvVaccineOrderDateTime.text =
                "${slot_date} , ${cancelList[position].slotDay} , ${from_or}"
            /*if (cancelList[position].assignedNurse != null) {
                holder.mView.textView19111.visibility = View.GONE
                holder.mView.textView191.visibility = View.VISIBLE
                holder.mView.tvVaccineOrderDoneBy.text = cancelList[position].assignedNurse
            } else {
                holder.mView.textView19111.visibility = View.VISIBLE
                holder.mView.textView191.visibility = View.INVISIBLE
                holder.mView.tvVaccineOrderDoneBy.text = "PENDING"
            }*/
            holder.mView.tvPaymentMode.text = cancelList[position].paymentDetails.paymentType
            holder.mView.tvPaymentStatus.text = cancelList[position].itemStatus
        } else {
            val price: Double =
                (cancelList[position].itemPrice.toDouble() + cancelList[position].homeVaccineFee.toDouble()) * cancelList[position].noOfDose.toDouble()
            val cal: Double = (price.toInt() * cancelList[position].discount.toDouble() / 100)
            val per: Double = price.toInt() - cal
            if (!cancelList[position].offerPrice.equals("")) {
                val offer_price = per - cancelList[position].offerPrice.toInt()
                holder.mView.tvTotal.text = "₹ ${offer_price}"
            } else {
                holder.mView.tvTotal.text = "₹ ${per}"
            }
            holder.mView.tvPaymentMode.text = cancelList[position].paymentDetails.paymentType
            holder.mView.tvPaymentStatus.text = cancelList[position].itemStatus
            holder.mView.textView123.visibility = View.INVISIBLE
            holder.mView.textView1231.visibility = View.VISIBLE
            val img: String = cancelList[position].itemImage
            if (img != null && img.length > 0) {
                Picasso.get().load(img).into(holder.mView.ivmain)
                holder.mView.ivdummy.visibility = View.INVISIBLE
                holder.mView.ivmain.visibility = View.VISIBLE

            } else {
                holder.mView.ivdummy.visibility = View.VISIBLE
                holder.mView.ivmain.visibility = View.INVISIBLE
            }
            holder.mView.tvOrderName.text = cancelList[position].itemName
            val date = cancelList[position].createdAt
            val convert_date = setFormatDate(date)
            holder.mView.tvOrderDate.text = convert_date
            holder.mView.tvOrderDes.text = cancelList[position].description
            holder.mView.tvOrderOfferprice.visibility = View.GONE
            holder.mView.divider14.visibility = View.GONE
            holder.mView.tvOrderprice.text = "₹ ${cancelList[position].itemPrice}"
            holder.mView.tvOrderIncludeVaccine.text =
                cancelList[position].noOfDose.toString()
            holder.mView.tvOrderPendingDoes.text =
                "${cancelList[position].pendingDose.toString()} Pending Dosage"
            holder.mView.tvOrderCompleteDoes.text =
                "${cancelList[position].completedDose.toString()} complete Dosage"
            holder.mView.tvVaccineOrderName.text = cancelList[position].userData.get(0).fullName
            val from_or =
                "${cancelList[position].slot.get(0).from} - ${cancelList[position].slot.get(0).to}"
            val slot_date = setFormatDate(cancelList[position].slotDate)
            holder.mView.tvVaccineOrderDateTime.text =
                "${slot_date} , ${cancelList[position].slotDay} , ${from_or}"
            holder.mView.tvVaccineOrderDoneBy.text = "PENDING"
            /* if (cancelList[position].assignedNurse != null) {
                 holder.mView.textView19111.visibility = View.GONE
                 holder.mView.textView191.visibility = View.VISIBLE
                 holder.mView.tvVaccineOrderDoneBy.text = cancelList[position].assignedNurse
             } else {
                 holder.mView.textView19111.visibility = View.VISIBLE
                 holder.mView.textView191.visibility = View.INVISIBLE
                 holder.mView.tvVaccineOrderDoneBy.text = "PENDING"
             }*/

        }
        holder.mView.cancelledMainItem.setOnClickListener {
//            con.startActivity(Intent(con, OrderDetailsActivity::class.java))
            onPress.onCancelOrderId(cancelList[position])
        }
    }

    override fun getItemCount(): Int {
        return cancelList.size

    }

    interface CancelOrderId {
        fun onCancelOrderId(msg: OrderData)
    }
}