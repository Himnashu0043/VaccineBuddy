package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ExplorePackagesHomeItemBinding
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

class ExploreVaccineAdapter(
    val con: Context,
    val list: ArrayList<AskedData>,
    private val onPress: OnClickItem,
    private var onFav: OnClickItem
) :
    RecyclerView.Adapter<ExploreVaccineAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: ExplorePackagesHomeItemBinding) :
        RecyclerView.ViewHolder(mView.root) {
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
            ExplorePackagesHomeItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val img: String = list!![position].vaccineImage
        println("---img${img}")
        if (list[position].totalStock.toInt() <= 0) {
            holder.mView.tvoutofstock.visibility = View.VISIBLE
        } else {
            holder.mView.tvoutofstock.visibility = View.GONE
        }
        if (img != null && img.length > 0) {
            holder.mView.lottie.visibility = View.VISIBLE
            Picasso.get().load(img).into(holder.mView.imageView48, object : Callback {
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
        holder.mView.tvPackageOfferPrice.visibility = View.GONE
        holder.mView.divider13.visibility = View.GONE
        holder.mView.tvPackagePrice.text = "₹" + list[position].price
        holder.mView.tvCategorySub.text = "-"
        holder.mView.tvPackageName.text = list[position].vaccineName
        holder.mView.tvPackageDes.text = list[position].description
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

