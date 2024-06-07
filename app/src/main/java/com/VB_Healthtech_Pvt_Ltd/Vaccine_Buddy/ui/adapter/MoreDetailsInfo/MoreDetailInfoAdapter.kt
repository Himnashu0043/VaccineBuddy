package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.MoreDetailsInfo

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.MoreInfoDetailsItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MoreDoseDetailsModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MoreDetailInfoAdapter(val con: Context, val more_list: ArrayList<MoreDoseDetailsModel>) :
    RecyclerView.Adapter<MoreDetailInfoAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: MoreInfoDetailsItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            MoreInfoDetailsItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val size =more_list[position].vaccineName
        println("----size$size")
        holder.mView.tvVaccineNameMoreinfo.text = more_list[position].vaccineName
        holder.mView.tvPriceMoreinfo.text = "₹ ${more_list[position].price}"
        holder.mView.tvDesMoreinfo.text = more_list[position].description

    }

    override fun getItemCount(): Int {
       return more_list.size
    }
}