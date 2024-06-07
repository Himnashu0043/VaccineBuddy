package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.GridItemListBinding
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

class CategoryAdapter(var context: Context, var arrayList: ArrayList<AskedData>, private var onPress:OnClickItem) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHOlder>() {
    class MyViewHOlder(val mView: GridItemListBinding) : RecyclerView.ViewHolder(mView.root){
        init {

            PushDownAnim.setPushDownAnimTo(mView.gridmain)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHOlder {
        return MyViewHOlder(GridItemListBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHOlder, position: Int) {
        val img: String = arrayList!![position].image
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
//        val test = arrayList[position].categoryName.length
//        println("----test$test")
        holder.mView.tvSubName.text = arrayList[position].categoryName
        holder.mView.gridmain.setOnClickListener {
            onPress.clickOn(arrayList[position],position)

        }
    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
    interface OnClickItem {
        fun clickOn(msgItem: AskedData, position: Int)
    }

}