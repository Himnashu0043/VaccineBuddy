package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SuggestBabyAdapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.NewPackagelistItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim

class VaccineSuggestBabyAdapter(
    val con: Context,
    val list: ArrayList<AskedData>,
    private val onPress: OnClickItem,
) :
    RecyclerView.Adapter<VaccineSuggestBabyAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: NewPackagelistItemBinding) : RecyclerView.ViewHolder(mView.root) {
        init {
            PushDownAnim.setPushDownAnimTo(mView.main)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            NewPackagelistItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (list[position].totalStock.toInt() <= 0) {
            holder.mView.tvoutofstock.visibility = View.VISIBLE
        } else {
            holder.mView.tvoutofstock.visibility = View.GONE
        }
        val img: String = list!![position].vaccineImage
        println("---img${img}")
        if (img != null && img.length > 0) {
            Picasso.get().load(img).into(holder.mView.imageView18)
            holder.mView.lottie.visibility = View.GONE
        } else {
            holder.mView.lottie.visibility = View.VISIBLE
        }
        holder.mView.tvPackageSubCat.text = "-"
        holder.mView.tvPackageAllPrice.text = "₹" + list[position].price
        holder.mView.divider13.visibility = View.GONE
        holder.mView.tvPackageOfferPrice.visibility = View.GONE
        holder.mView.tvPackageAllName.text = list[position].vaccineName
        holder.mView.tvPackageAllDes.text = list[position].description
        holder.mView.tvIncludeDose.text = list[position].noOfVaccination
        holder.mView.main.setOnClickListener {
            onPress.vaccineclick(list[position], position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickItem {
        fun vaccineclick(msgItem: AskedData, position: Int)
    }

}