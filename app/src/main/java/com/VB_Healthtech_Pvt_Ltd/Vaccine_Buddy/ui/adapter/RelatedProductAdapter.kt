package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.RelatedProductItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.RelatedPackage.RelatedPackege
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim

class RelatedProductAdapter(val con: Context, val list: ArrayList<RelatedPackege>,val onPress:OnClickItem,  private var onFavPackage:OnClickItem) :
    RecyclerView.Adapter<RelatedProductAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: RelatedProductItemBinding) : RecyclerView.ViewHolder(mView.root) {
        init {
            PushDownAnim.setPushDownAnimTo(mView.mainRelatedProduct)
                .setScale(PushDownAnim.MODE_SCALE, 0.55f)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            RelatedProductItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val img = list[position].packageImage
        if (img != null && img.length > 0) {
            Picasso.get().load(img).into(holder.mView.imageView48)
            holder.mView.lottie.visibility = View.GONE
        } else {
            holder.mView.lottie.visibility = View.VISIBLE
        }
        if (list[position].offerPrice.isNullOrEmpty()) {
            holder.mView.tvPackagePrice.visibility = View.VISIBLE
            holder.mView.divider16.visibility = View.GONE
            holder.mView.tvPackageOfferPrice.visibility = View.GONE
        } else {
            holder.mView.tvPackagePrice.text = "₹" + list[position].price
            holder.mView.tvPackageOfferPrice.text = "₹" + list[position].offerPrice
        }
        holder.mView.tvPackageName.text = list[position].packageName
        holder.mView.tvPackageDes.text = list[position].description
        holder.mView.tvPackagePrice.text = "₹" + list[position].price
        holder.mView.mainRelatedProduct.setOnClickListener {
            onPress.packageclickOn(list[position],position)
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
        fun packageclickOn(msgItem: RelatedPackege, position: Int)
        fun onFavPackage(favid: String)
    }
}