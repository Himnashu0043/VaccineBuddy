package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter


import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ExploreItemHomeBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CategoryList.AskedData

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim
import java.lang.Exception

class ExploreHomeAdapter(
    val con: Context,
    val list: ArrayList<AskedData>,
    private var onPress: OnClickItem
) :
    RecyclerView.Adapter<ExploreHomeAdapter.MyViewHolder>() {
    inner class MyViewHolder(val mView: ExploreItemHomeBinding) :
        RecyclerView.ViewHolder(mView.root) {
        init {
            PushDownAnim.setPushDownAnimTo(mView.mainCate)
                .setScale(PushDownAnim.MODE_SCALE, 0.55f)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(ExploreItemHomeBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val img: String = list!![position].image
        if (img != null && img.length > 0) {
            holder.mView.lottie.visibility = View.VISIBLE
            Picasso.get().load(img).into(holder.mView.ivImg, object : Callback {
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
        holder.mView.tvName.text = list[position].categoryName
        holder.mView.mainCate.setOnClickListener {
            //con.startActivity(Intent(con, SubCategoryActivity::class.java))
            onPress.clickOn(list[position], position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickItem {
        fun clickOn(msgItem: AskedData, position: Int)


    }
}