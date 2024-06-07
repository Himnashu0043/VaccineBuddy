package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ExplorePackagesHomeItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim
import java.lang.Exception

class ExplorePackageAdapter(
    val con: Context,
    val list: ArrayList<AskedData>,
    private var onPress: OnClickItem,
    private var onFavPackage: OnClickItem
) :
    RecyclerView.Adapter<ExplorePackageAdapter.MyViewHolder>() {
    inner class MyViewHolder(val mView: ExplorePackagesHomeItemBinding) :
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

        val img: String = list[position].packageImage
        if (img != null && img.length > 0) {
            holder.mView.lottie.visibility = View.VISIBLE
            Picasso.get().load(img).into(holder.mView.imageView48, object : Callback {
                override fun onSuccess() {
                    holder.mView.lottie.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    holder.mView.lottie.visibility = View.GONE
                }

            })
        } else {
            holder.mView.lottie.visibility = View.VISIBLE
        }

        if (list[position].subCategoryName == null) {
            holder.mView.tvPackageName.text =
                "${list[position].categoryName}"
        } else {
            holder.mView.tvPackageName.text =
                "${list[position].categoryName} - ${list[position].subCategoryName}"
        }

        holder.mView.tvCategorySub.text = list[position].packageName

        /* if (list[position].categoryName == null) {
             holder.mView.tvCategorySub.text = "--"
         } else {
             if (list[position].subCategoryName == null) {
                 holder.mView.tvCategorySub.text =
                     "${list[position].categoryName}"
             } else {
                 holder.mView.tvCategorySub.text =
                     "${list[position].categoryName} - ${list[position].subCategoryName}"
             }
         }*/


        if (list[position].offerPrice.isNullOrEmpty()) {
            holder.mView.tvPackagePrice.visibility = View.VISIBLE
            holder.mView.divider13.visibility = View.GONE
            holder.mView.tvPackageOfferPrice.visibility = View.GONE
        } else {
            holder.mView.tvPackagePrice.text = "₹" + list[position].price
            holder.mView.tvPackageOfferPrice.text = "₹" + list[position].offerPrice
        }
        holder.mView.tvPackageDes.text = list[position].description
        holder.mView.main.setOnClickListener {
            onPress.packageclickOn(list[position], position)
        }
        if (list[position].isWishList) {
            holder.mView.ivfav.setImageResource(R.drawable.pacimg)
        } else {
            holder.mView.ivfav.setImageResource(R.drawable.group1)

        }

        holder.mView.ivfav.setOnClickListener {
            println("---list${list[position].isWishList}")
            updatelist(position)
            onFavPackage.onFavPackage(list[position]._id)
        }

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

    override fun getItemCount(): Int {
        return list.size

    }

    interface OnClickItem {
        fun packageclickOn(msgItem: AskedData, position: Int)
        fun onFavPackage(favid: String)

    }
}