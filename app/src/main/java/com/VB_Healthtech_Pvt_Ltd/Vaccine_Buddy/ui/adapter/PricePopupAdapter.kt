package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.PricePopupItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PricePopupAdapter(val con: Context, val pricepopupList: ArrayList<AskedPackage>) :
    RecyclerView.Adapter<PricePopupAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: PricePopupItemBinding) : RecyclerView.ViewHolder(mView.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(PricePopupItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data2 = ""
        for (data in pricepopupList[position].includedConsultant) {
            data2 = data2 + data + "\n\n"
        }
        holder.mView.textView24.text = data2


    }

    override fun getItemCount(): Int {
        return pricepopupList.size
    }
}