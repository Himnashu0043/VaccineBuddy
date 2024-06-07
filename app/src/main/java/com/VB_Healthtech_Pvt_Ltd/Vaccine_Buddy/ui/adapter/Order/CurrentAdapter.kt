package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Order

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.CurrentItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Current.OrderData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CurrentAdapter(
    val con: Context,
    val current_list: ArrayList<OrderData>,
    val onPress: OrderId
) :
    RecyclerView.Adapter<CurrentAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: CurrentItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CurrentItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (current_list[position].order_type.equals("PACKAGE")) {
            val dis_cal: Double =
                current_list[position].itemPrice.toDouble() * current_list[position].discount / 100
            val total_price: Double =
                current_list[position].itemPrice.toDouble() - dis_cal
            holder.mView.tvTotal.text = "₹$total_price"

            val img: String = current_list[position].itemImage
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
            holder.mView.tvOrderName.text = current_list[position].itemName
            val date = current_list[position].createdAt
            val convert_date = setFormatDate(date)
            holder.mView.tvOrderDate.text = convert_date
            holder.mView.tvOrderDes.text = current_list[position].description
            if (current_list[position].itemPrice.isEmpty()) {
                holder.mView.tvOrderprice.text = "₹ ${current_list[position].packagePrice}"
                holder.mView.tvOrderOfferprice.visibility = View.GONE
                holder.mView.divider14.visibility = View.GONE
            } else {
                holder.mView.tvOrderOfferprice.visibility = View.VISIBLE
                holder.mView.divider14.visibility = View.VISIBLE
                holder.mView.tvOrderprice.text = "₹ ${current_list[position].packagePrice}"
                holder.mView.tvOrderOfferprice.text = "₹ ${current_list[position].itemPrice}"
            }
            holder.mView.tvOrderIncludeVaccine.text =
                current_list[position].inclusiveVaccine.toString()
            holder.mView.tvPaymentMode.text = current_list[position].paymentDetails.paymentType
            /* holder.mView.tvPaymentStatus.text = current_list[position].itemStatus*/
            holder.mView.tvOrderPendingDoes.text =
                "${current_list[position].pendingDose.toString()} Pending Dosage"
            holder.mView.tvOrderCompleteDoes.text =
                "${current_list[position].completedDose.toString()} complete Dosage"
            holder.mView.tvVaccineOrderName.text = current_list[position].userData.get(0).fullName
            val from_or =
                "${current_list[position].slot.get(0).from} - ${current_list[position].slot.get(0).to}"
            val slot_date = setFormatDate(current_list[position].slotDate)
            holder.mView.tvVaccineOrderDateTime.text =
                "${slot_date} , ${current_list[position].slotDay} , ${from_or}"
            if (current_list[position].assignedNurse != null) {
                holder.mView.textView19111.visibility = View.GONE
                holder.mView.textView191.visibility = View.VISIBLE
                holder.mView.tvVaccineOrderDoneBy.text = current_list[position].assignedNurse

            } else {
                holder.mView.textView19111.visibility = View.VISIBLE
                holder.mView.textView191.visibility = View.INVISIBLE
                holder.mView.tvVaccineOrderDoneBy.text = "PENDING"
            }
            if (current_list[position].paymentStatus) {
                holder.mView.tvPaymentStatus.text = "Complete"
            } else {
                holder.mView.tvPaymentStatus.text = "PENDING"
            }

        } else {
            holder.mView.tvPaymentMode.text = current_list[position].paymentDetails.paymentType
            /*  holder.mView.tvPaymentStatus.text = current_list[position].itemStatus*/

            val price: Double =
                (current_list[position].itemPrice.toDouble() + current_list[position].homeVaccineFee.toDouble()) * current_list[position].noOfDose.toDouble()
            val cal: Double = (price.toInt() * current_list[position].discount / 100)
            val per: Double = price.toInt() - cal
            if (!current_list[position].offerPrice.equals("")) {
                val offer_price = per - current_list[position].offerPrice.toInt()
                holder.mView.tvTotal.text = "₹ ${offer_price}"
            } else {
                holder.mView.tvTotal.text = "₹ ${per}"
            }


            holder.mView.tvOrderprice.text = "₹${current_list[position].itemPrice}"
            holder.mView.textView123.visibility = View.INVISIBLE
            holder.mView.textView1231.visibility = View.VISIBLE
            val img: String = current_list[position].itemImage
            if (img != null && img.length > 0) {
                Picasso.get().load(img).into(holder.mView.ivmain)
                holder.mView.ivdummy.visibility = View.INVISIBLE
                holder.mView.ivmain.visibility = View.VISIBLE
            } else {
                holder.mView.ivdummy.visibility = View.VISIBLE
                holder.mView.ivmain.visibility = View.INVISIBLE
            }
            holder.mView.tvOrderName.text = current_list[position].itemName
            val date = current_list[position].createdAt
            val convert_date = setFormatDate(date)
            holder.mView.tvOrderDate.text = convert_date
            holder.mView.tvOrderDes.text = current_list[position].description
            holder.mView.tvOrderOfferprice.visibility = View.GONE
            holder.mView.divider14.visibility = View.GONE

            holder.mView.tvOrderIncludeVaccine.text =
                current_list[position].noOfDose.toString()
            holder.mView.tvOrderPendingDoes.text =
                "${current_list[position].pendingDose.toString()} Pending Dosage"
            holder.mView.tvOrderCompleteDoes.text =
                "${current_list[position].completedDose.toString()} complete Dosage"
            holder.mView.tvVaccineOrderName.text = current_list[position].userData.get(0).fullName
            val from_or =
                "${current_list[position].slot.get(0).from} - ${current_list[position].slot.get(0).to}"
            val slot_date = setFormatDate(current_list[position].slotDate)
            holder.mView.tvVaccineOrderDateTime.text =
                "${slot_date} , ${current_list[position].slotDay} , ${from_or}"
            holder.mView.tvVaccineOrderDoneBy.text = "PENDING"
            if (current_list[position].assignedNurse != null) {
                holder.mView.textView19111.visibility = View.GONE
                holder.mView.textView191.visibility = View.VISIBLE
                holder.mView.tvVaccineOrderDoneBy.text = current_list[position].assignedNurse
            } else {
                holder.mView.textView19111.visibility = View.VISIBLE
                holder.mView.textView191.visibility = View.INVISIBLE
                holder.mView.tvVaccineOrderDoneBy.text = "PENDING"
            }
            if (current_list[position].paymentStatus) {
                holder.mView.tvPaymentStatus.text = "Complete"
            } else {
                holder.mView.tvPaymentStatus.text = "PENDING"
            }

        }
        holder.mView.currentItemMain.setOnClickListener {
            onPress.onOrderId(current_list[position])
        }
    }

    override fun getItemCount(): Int {
        return current_list.size

    }

    interface OrderId {
        fun onOrderId(msg: OrderData)
    }
}