package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Order

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.CompletedItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CompletedAdapter(
    val con: Context,
    val curren1111t_list: ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Completed.OrderData>,
    val onPress: CompleteOrderId
) :
    RecyclerView.Adapter<CompletedAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: CompletedItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(CompletedItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (curren1111t_list[position].order_type.equals("PACKAGE")) {
            holder.mView.textView123.visibility = View.VISIBLE
            holder.mView.textView1231.visibility = View.INVISIBLE
            val img: String = curren1111t_list[position].itemImage
            if (img != null && img.length > position) {
                Picasso.get().load(img).into(holder.mView.ivmain)
                holder.mView.ivmain.visibility = View.VISIBLE
                holder.mView.ivdummy.visibility = View.INVISIBLE

            } else {
                holder.mView.ivdummy.visibility = View.VISIBLE
                holder.mView.ivmain.visibility = View.INVISIBLE
            }
            holder.mView.tvOrderName.text = curren1111t_list[position].itemName
            val date = curren1111t_list[position].createdAt
            val convert_date = setFormatDate(date)
            holder.mView.tvOrderDate.text = convert_date
            holder.mView.tvOrderDes.text = curren1111t_list[position].description
            if (curren1111t_list[position].itemPrice.isEmpty()) {
                holder.mView.tvOrderprice.text = "₹ ${curren1111t_list[position].packagePrice}"
                holder.mView.tvOrderOfferprice.visibility = View.GONE
                holder.mView.divider14.visibility = View.GONE
            } else {
                holder.mView.tvOrderOfferprice.visibility = View.VISIBLE
                holder.mView.divider14.visibility = View.VISIBLE
                holder.mView.tvOrderprice.text = "₹ ${curren1111t_list[position].packagePrice}"
                holder.mView.tvOrderOfferprice.text = "₹ ${curren1111t_list[position].itemPrice}"
            }
            holder.mView.tvOrderIncludeVaccine.text =
                curren1111t_list[position].inclusiveVaccine.toString()
            holder.mView.tvOrderPendingDoes.text =
                "${curren1111t_list[position].pendingDose.toString()} Pending Dosage"
            holder.mView.tvOrderCompleteDoes.text =
                "${curren1111t_list[position].completedDose.toString()} complete Dosage"
            if (curren1111t_list[position].assignedNurse != null) {
                holder.mView.tvCompletedFor.text = curren1111t_list[position].assignedNurse
            } else {
                holder.mView.tvCompletedFor.text = "NA"
            }
        } else {
            holder.mView.textView123.visibility = View.INVISIBLE
            holder.mView.textView1231.visibility = View.VISIBLE
            val img: String = curren1111t_list[position].itemImage
            if (img != null && img.length > position) {
                Picasso.get().load(img).into(holder.mView.ivmain)
                holder.mView.ivdummy.visibility = View.INVISIBLE
                holder.mView.ivmain.visibility = View.VISIBLE

            } else {
                holder.mView.ivdummy.visibility = View.VISIBLE
                holder.mView.ivmain.visibility = View.INVISIBLE
            }
            holder.mView.tvOrderName.text = curren1111t_list[position].itemName
            val date = curren1111t_list[position].createdAt
            val convert_date = setFormatDate(date)
            holder.mView.tvOrderDate.text = convert_date
            holder.mView.tvOrderDes.text = curren1111t_list[position].description
            holder.mView.tvOrderOfferprice.visibility = View.GONE
            holder.mView.divider14.visibility = View.GONE
            holder.mView.tvOrderprice.text = "₹ ${curren1111t_list[position].itemPrice}"
            holder.mView.tvOrderIncludeVaccine.text =
                curren1111t_list[position].noOfDose.toString()
            holder.mView.tvOrderPendingDoes.text =
                "${curren1111t_list[position].pendingDose.toString()} Pending Dosage"
            holder.mView.tvOrderCompleteDoes.text =
                "${curren1111t_list[position].completedDose.toString()} complete Dosage"
            if (curren1111t_list[position].assignedNurse != null) {
                holder.mView.tvCompletedFor.text = curren1111t_list[position].assignedNurse
            } else {
                holder.mView.tvCompletedFor.text = "NA"
            }


        }
        holder.mView.completedMainItem.setOnClickListener {
            onPress.onCompleteOrderId(curren1111t_list[position])
        }
    }

    override fun getItemCount(): Int {
        return curren1111t_list.size

    }

    interface CompleteOrderId {
        fun onCompleteOrderId(msg: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Completed.OrderData)
    }
}