package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SubCategory

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.GridItemListBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SubCategoryList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim

class SubCategoryAdapter(val con: Context,val sublist:ArrayList<AskedData>, val onPress: SubCategory) :
    RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder>() {
   inner class MyViewHolder(val mView: GridItemListBinding) : RecyclerView.ViewHolder(mView.root){
        init {

            PushDownAnim.setPushDownAnimTo(mView.gridmain)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(GridItemListBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val img: String = sublist!![position].image
        if (img != null && img.length > 0) {
            Picasso.get().load(img).into(holder.mView.imageView14)
            holder.mView.lottie.visibility = View.GONE
        } else {
            holder.mView.lottie.visibility = View.VISIBLE
        }
        holder.mView.tvSubName.text = sublist[position].subCategoryName
        holder.mView.gridmain.setOnClickListener {
            onPress.onsubCategory_id(sublist[position], position)
        }
    }

    override fun getItemCount(): Int {
        return sublist.size

    }
    interface SubCategory {
        fun onsubCategory_id(msg: AskedData, pos: Int)
    }
}