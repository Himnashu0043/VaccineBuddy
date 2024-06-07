package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.OffersItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Offers.AskedCoupon
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MyCart.MyCartActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thekhaeng.pushdownanim.PushDownAnim

class OffersAdapter(val con: Context, val offerlist: ArrayList<AskedCoupon>) :
    RecyclerView.Adapter<OffersAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: OffersItemBinding) : RecyclerView.ViewHolder(mView.root){
        init {
            PushDownAnim.setPushDownAnimTo(mView.main)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(OffersItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mView.tvOfferName.text = offerlist[position].couponTitle
        holder.mView.tvOffersDes.text = offerlist[position].description
        holder.mView.tvApplyName.text = offerlist[position].couponCode
        holder.mView.tvOfferPers.text = "${offerlist[position].discountPercentage}%"
        holder.mView.tvOffersDate.text = offerlist[position].duration
        holder.mView.textView122.setOnClickListener {
            con.startActivity(
                Intent(con, MyCartActivity::class.java).putExtra(
                    "coupenCode",
                    "${offerlist[position].couponCode}"
                )
                    .putExtra("discount", "${offerlist[position].discountPercentage}").setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }

    override fun getItemCount(): Int {
        return offerlist.size
    }

}