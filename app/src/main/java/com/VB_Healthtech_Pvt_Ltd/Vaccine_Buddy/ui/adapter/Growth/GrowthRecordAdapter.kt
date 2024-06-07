package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Growth

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.GrowthItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.KidsGrowths.AskedRequest
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GrowthRecordAdapter(val con: Context, val growthsList: ArrayList<AskedRequest>) :
    RecyclerView.Adapter<GrowthRecordAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: GrowthItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(GrowthItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val convertDate = setFormatDate(growthsList[position].date)
        holder.mView.tvDate.text = convertDate
        holder.mView.tvWeights.text = "${growthsList[position].weight} Kg"
        holder.mView.tvHeights.text = "${growthsList[position].hight} Cm"

    }

    override fun getItemCount(): Int {
        return growthsList.size
    }
}