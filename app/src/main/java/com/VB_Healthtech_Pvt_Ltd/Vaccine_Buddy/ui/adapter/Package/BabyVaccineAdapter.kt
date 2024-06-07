package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Package

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.NewPackagelistItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim
import java.lang.Exception

class BabyVaccineAdapter(
    val con: Context,
    val list: ArrayList<AskedData>,
    private val onPress: OnClickItem,
    private val onFav:OnClickItem
) :
    RecyclerView.Adapter<BabyVaccineAdapter.MyViewHolder>() {
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
            holder.mView.lottie.visibility = View.VISIBLE
            Picasso.get().load(img).into(holder.mView.imageView18, object : Callback {
                override fun onSuccess() {
                    holder.mView.lottie.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    holder.mView.lottie.visibility = View.VISIBLE
                }

            })
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
        if (list[position].isWishList) {
            holder.mView.ivfav.setImageResource(R.drawable.pacimg)
        } else {
            holder.mView.ivfav.setImageResource(R.drawable.group1)

        }

        holder.mView.ivfav.setOnClickListener {
            println("---list${list[position].isWishList}")
            updatelist(position)
            onFav.onFavVaccine(list[position]._id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    private fun updatelist(pos: Int) {
        for (i in list.indices) {
            if (i == pos)
                list.get(i).isWishList = true
            else
                list.get(i).isWishList = false
        }
        notifyDataSetChanged()
    }

    interface OnClickItem {
        fun vaccineclick(msgItem: AskedData, position: Int)
        fun onFavVaccine(favid: String)
    }
}