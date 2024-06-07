package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.OrderDetails

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.DoesDetailsItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetailsPackageDoesModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoseDetailsAdapter(
    val con: Context,
    val list: ArrayList<OrderDetailsPackageDoesModel>,
    val is_nurse: Boolean,
    var text: TextView
) : RecyclerView.Adapter<DoseDetailsAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: DoesDetailsItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(DoesDetailsItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            println("------is$is_nurse")
            println("------tes$text")
            holder.mView.tvAgeOfBirth.text = list[position].dose
            holder.mView.tvInsuions.text = list[position].vaccineName
            if (list[position].statusObj!!.status.equals("COMPLETE")) {
                holder.mView.tvStatus.text =
                    "${list[position].statusObj!!.status} on ${list[position].statusObj!!.completeDate} by ${list[position].statusObj!!.nurseName} (nurse)"
                if (!is_nurse) {
                    text.visibility = View.VISIBLE
                } else {
                    text.visibility = View.GONE
                }
            } else {
                holder.mView.tvStatus.text = "Pending"
                //text.visibility = View.GONE
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}