package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Package

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.NewPackagelistItemBinding
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

class BabyPackageAdapter(
    val con: Context,
    val list: ArrayList<AskedData>,
    private var onPress: OnClickItem,
    private var onFav: OnClickItem
) :
    RecyclerView.Adapter<BabyPackageAdapter.MyViewHolder>() {
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
        val img: String = list!![position].packageImage
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
        val does = list[position].doseInfo.size
        println("------does$does")
        holder.mView.tvIncludeDose.text = does.toString()
        println("---sub${list[position].categoryName} ${list[position].subCategoryName}")
        if (list[position].categoryName.equals("Kids Vaccination")) {
            /*if (list[position].subCategoryName != null) {
                holder.mView.tvnewSub.visibility = View.VISIBLE
                val subCat = list[position].subCategoryName!!.replace("vaccination", "")
                holder.mView.tvnewSub.text = subCat
                holder.mView.tvPackageAllName.text = list[position].packageName
                holder.mView.tvPackageSubCat.text = list[position].description
                holder.mView.tvPackageAllDes.visibility = View.GONE
            } else {
                holder.mView.tvnewSub.visibility = View.INVISIBLE
            }*/
            /*holder.mView.tvnewSub.visibility = View.VISIBLE
            val subCat = list[position].subCategoryName!!.replace("vaccination", "")
            holder.mView.tvnewSub.text = subCat
            holder.mView.tvPackageAllName.text = list[position].packageName
            holder.mView.tvPackageSubCat.text = list[position].description
            holder.mView.tvPackageAllDes.visibility = View.GONE*/
             list[position].subCategoryName?.let {
                 holder.mView.tvnewSub.visibility = View.VISIBLE
                 val subCat = it.lowercase().replace("vaccination", "").uppercase()
                 holder.mView.tvnewSub.text = subCat
                 holder.mView.tvPackageAllName.text = list[position].packageName
                 holder.mView.tvPackageSubCat.text = list[position].description
                 holder.mView.tvPackageAllDes.visibility = View.GONE
             } ?: run {
                 holder.mView.tvnewSub.visibility = View.INVISIBLE
             }

        } else {
            if (list[position].subCategoryName != null) {
                holder.mView.tvPackageAllName.text =
                    "${list[position].categoryName} - ${list[position].subCategoryName}"
            } else {
                holder.mView.tvPackageAllName.text =
                    "${list[position].categoryName}"
            }
            holder.mView.tvPackageSubCat.text = list[position].packageName
            holder.mView.tvPackageAllDes.text = list[position].description
            holder.mView.tvnewSub.visibility = View.INVISIBLE
        }
        if (list[position].offerPrice == null) {
            holder.mView.tvPackageOfferPrice.visibility = View.GONE
            holder.mView.divider13.visibility = View.GONE
            holder.mView.tvPackageAllPrice.text = "₹" + list[position].price
        } else {
            holder.mView.divider13.visibility = View.VISIBLE
            holder.mView.tvPackageAllPrice.text = "₹" + list[position].price
            holder.mView.tvPackageOfferPrice.text = "₹" + list[position].offerPrice
        }
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
            onFav.onFavPackage(list[position]._id)
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
        fun packageclickOn(msgItem: AskedData, position: Int)
        fun onFavPackage(favid: String)
    }
}