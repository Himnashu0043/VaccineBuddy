package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.MainProfileItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MainProfileModal
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainProfileAdapter(
    val con: Context,
    val list: ArrayList<MainProfileModal>,
    private var onPress: OnClickItem
) :
    RecyclerView.Adapter<MainProfileAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: MainProfileItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(MainProfileItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mView.imageView60.setImageResource(list[position].img)
        holder.mView.textView157.text = list[position].name
        holder.mView.mainProfilelay.setOnClickListener {
            onPress.clickOn(list[position],position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickItem {
        fun clickOn(msgItem: MainProfileModal, postion: Int)
    }
}