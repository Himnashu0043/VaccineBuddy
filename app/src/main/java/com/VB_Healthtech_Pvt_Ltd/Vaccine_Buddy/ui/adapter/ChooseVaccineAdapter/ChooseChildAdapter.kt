package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.ChooseVaccineAdapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.RcyChildChooseItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.TestAdapter.RcyChooseAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.ModifyItemData

class ChooseChildAdapter(
    val con: Context,
    val list: ArrayList<ModifyItemData>,
    val onPress: Child,
    val parentPos: Int,
    val day: String,
    val daycount: String
) :
    RecyclerView.Adapter<ChooseChildAdapter.MyViewHolder>(),
    RcyChooseAdapter.RcyChooseAdapterListener {


    class MyViewHolder(val mView: RcyChildChooseItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            RcyChildChooseItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position].item_list
        if (item.size > 1) {
            holder.mView.tvName.text = item[0].name
            holder.mView.contranlay11.visibility = View.GONE
            holder.mView.rvSubChild.visibility = View.VISIBLE
            holder.mView.rvSubChild.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = RcyChooseAdapter(
                    context,
                    list[position].item_list,
                    this@ChooseChildAdapter,
                    holder.absoluteAdapterPosition
                )
            }

        } else {
            holder.mView.contranlay11.visibility = View.VISIBLE
            holder.mView.rvSubChild.visibility = View.GONE
            holder.mView.tvName.text = item[0].name
            holder.mView.tvBrandName.text = item[0].brand
            holder.mView.tvprice.text = "₹${item[0].price}"
            holder.mView.radioBtn.isChecked = list[position].item_list[0].isSelectedStatus
            holder.mView.radioBtn.setOnClickListener {
                if (holder.mView.radioBtn.isChecked) {
                    holder.mView.radioBtn.isChecked = true
                    list[position].item_list[0].isSelectedStatus = true
                    onPress.onChildClick(
                        list[position].item_list[0],
                        true,
                        parentPos = parentPos,
                        day,
                        daycount
                    )

                } else {
                    holder.mView.radioBtn.isChecked = false
                    onPress.onChildClick(
                        list[position].item_list[0],
                        false,
                        parentPos,
                        day,
                        daycount
                    )

                    list[position].item_list[0].isSelectedStatus = false
                }


            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface Child {
        fun onChildClick(
            msg: Item,
            checkType: Boolean,
            parentPos: Int,
            days: String,
            countDay: String
        )

        fun subChildClick( msg: Item, parentPos: Int,checkStatue:Boolean)

    }

    override fun onClickChecked(parent: Int, child: Int) {
       onPress.subChildClick(list[parent].item_list[child],parentPos,true)
    }

    override fun onClickUnchecked(parent: Int, child: Int) {
       onPress.subChildClick(list[parent].item_list[child],parentPos,false)

    }
}