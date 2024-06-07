package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.vaccineRelated

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.RelatedProductItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class VaccineRelatedProductAdapter(
    val con: Context,
    val vaccine_related_product_list: ArrayList<AskedData>,
    val onPress: OnClickItem,
    private var onFav:OnClickItem


) : RecyclerView.Adapter<VaccineRelatedProductAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: RelatedProductItemBinding) : RecyclerView.ViewHolder(mView.root)

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

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val img: String = vaccine_related_product_list!![position].vaccineImage
        println("---img${img}")
        if (img != null && img.length > 0) {
            Picasso.get().load(img).into(holder.mView.imageView48)
            holder.mView.lottie.visibility = View.GONE
        } else {
            holder.mView.lottie.visibility = View.VISIBLE
        }
        holder.mView.tvPackagePrice.text = "₹" + vaccine_related_product_list[position].price
        holder.mView.divider16.visibility = View.GONE
        holder.mView.tvPackageOfferPrice.visibility = View.GONE
        holder.mView.tvPackageName.text = vaccine_related_product_list[position].vaccineName
        holder.mView.tvPackageDes.text = vaccine_related_product_list[position].description
        holder.mView.mainRelatedProduct.setOnClickListener {
            onPress.vaccineclick(vaccine_related_product_list[position], position)
        }
        if (vaccine_related_product_list[position].isWishList) {
            holder.mView.ivfav.setImageResource(R.drawable.pacimg)
        } else {
            holder.mView.ivfav.setImageResource(R.drawable.group1)

        }

        holder.mView.ivfav.setOnClickListener {
            println("---list${vaccine_related_product_list[position].isWishList}")
            updatelist(position)
            onFav.onFavVaccine(vaccine_related_product_list[position]._id)
        }

    }

    override fun getItemCount(): Int {
        return vaccine_related_product_list.size
    }
    private fun updatelist(pos: Int) {
        for (i in vaccine_related_product_list.indices) {
            if (i == pos)
                vaccine_related_product_list.get(i).isWishList = true
            else
                vaccine_related_product_list.get(i).isWishList = false
        }
        notifyDataSetChanged()
    }
    interface OnClickItem {
        fun vaccineclick(msgItem: AskedData, position: Int)
        fun onFavVaccine(favid: String)
    }
}