package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.PriceDetailsItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PriceDetailsModal
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PriceDetailsAdapter(val con: Context, val list: ArrayList<PriceDetailsModal>) :
    RecyclerView.Adapter<PriceDetailsAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: PriceDetailsItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            PriceDetailsItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mView.textView175.text = list[position].name
        holder.mView.textView176.text = list[position].rate
        holder.mView.textView177.text = list[position].price

    }

    override fun getItemCount(): Int {
        return list.size
    }
}