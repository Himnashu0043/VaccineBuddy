package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.TestAdapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.RcyChildChooseItemBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.Item

class RcyChooseAdapter(
    val context: Context,
    val itemList: ArrayList<Item>,
    val listener: RcyChooseAdapterListener,
    val parent: Int
) :
    RecyclerView.Adapter<RcyChooseAdapter.ViewHolder>() {
    var mSelectedPo = -1

    interface RcyChooseAdapterListener {
        fun onClickChecked(parent: Int, child: Int)
        fun onClickUnchecked(parent: Int, child: Int)
    }

    class ViewHolder(val view: RcyChildChooseItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RcyChildChooseItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.tvName.visibility = View.GONE
        holder.view.rvSubChild.visibility = View.GONE
        holder.view.tvBrandName.visibility = View.VISIBLE
        holder.view.tvprice.visibility = View.VISIBLE
        holder.view.tvBrandName.text = itemList[position].brand
        holder.view.tvprice.text = itemList[position].price

        if (position == mSelectedPo) {
            holder.view.contranlay11.isSelected = true
            holder.view.radioBtn.isChecked = true
        } else {
            holder.view.contranlay11.isSelected = false
            holder.view.radioBtn.isChecked = false

        }

        holder.view.radioBtn.setOnClickListener {
            mSelectedPo = holder.absoluteAdapterPosition
            if (holder.view.radioBtn.isChecked) {
                listener.onClickUnchecked(parent, holder.absoluteAdapterPosition)
            } else {
                listener.onClickChecked(parent, holder.absoluteAdapterPosition)
            }
            notifyItemRangeChanged(0, itemList.size)
        }

    }


    override fun getItemCount(): Int {
        return itemList!!.size
    }

}