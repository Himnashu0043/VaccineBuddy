package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.CustomePackage

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.NewPackagelistItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CustomPackage.AskedPackage
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim

class CustomePackageAdapter(val con: Context, val list: ArrayList<AskedPackage>,private var onPress: OnClickItem) :
    RecyclerView.Adapter<CustomePackageAdapter.MyViewHolder>() {
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
            Picasso.get().load(img).into(holder.mView.imageView18)
            holder.mView.lottie.visibility = View.GONE
        } else {
            holder.mView.lottie.visibility = View.VISIBLE
        }
        val does = list[position].doseInfo.size
        println("------does$does")
        holder.mView.tvIncludeDose.text = does.toString()

        holder.mView.tvPackageAllName.text = list[position].packageName

        holder.mView.tvPackageAllDes.text = list[position].description
        holder.mView.tvPackageOfferPrice.visibility = View.GONE
        holder.mView.divider13.visibility = View.GONE
        holder.mView.tvPackageAllPrice.text = "₹" + list[position].totalPrice
        holder.mView.tvPackageSubCat.text =
            "-"
        //        if (list[position].subCategoryName == null) {
//            holder.mView.tvPackageSubCat.text =
//                "${list[position].categoryName}"
//        } else {
//            holder.mView.tvPackageSubCat.text =
//                "${list[position].categoryName} - ${list[position].subCategoryName}"
//        }
//        if (list[position].offerPrice == null) {
//            holder.mView.tvPackageOfferPrice.visibility = View.GONE
//            holder.mView.divider13.visibility = View.GONE
//            holder.mView.tvPackageAllPrice.text = "₹" + list[position].price
//        } else {
//            holder.mView.divider13.visibility = View.VISIBLE
//            holder.mView.tvPackageAllPrice.text = "₹" + list[position].price
//            holder.mView.tvPackageOfferPrice.text = "₹" + list[position].offerPrice
//        }
        holder.mView.main.setOnClickListener {
            onPress.packageclickOn(list[position], position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickItem {
        fun packageclickOn(msgItem: AskedPackage, position: Int)


    }
}