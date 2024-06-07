package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.GridItemListBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SubCategoryList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class KidsAdapter(
    var context: Context,
    var arrayList: ArrayList<AskedData>,
    val onPress: SubCategory
) :
    RecyclerView.Adapter<KidsAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: GridItemListBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            GridItemListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val img: String = arrayList[position].image
        if (img != null && img.length > 0) {
            holder.mView.lottie.visibility = View.VISIBLE
            Picasso.get().load(img).into(holder.mView.imageView14, object : Callback {
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
        holder.mView.tvSubName.text = arrayList[position].subCategoryName
        holder.mView.gridmain.setOnClickListener {
            onPress.onsubCategory_id(arrayList[position], position)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    interface SubCategory {
        fun onsubCategory_id(msg: AskedData, pos: Int)
    }

}
