package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import android.annotation.SuppressLint
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.TestimonialItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Testimonial.AskedRequest
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.testimonialsDetails.TestimonialsDetailsActivity
import com.squareup.picasso.Picasso

class TestimonialsAdapter(val con: Context, val tem_list: ArrayList<AskedRequest>) :
    RecyclerView.Adapter<TestimonialsAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: TestimonialItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(TestimonialItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val img = tem_list[position].image
        if (img != null && img.length > 0) {
            holder.mView.ivMain.visibility = View.VISIBLE
            Picasso.get().load(img).into(holder.mView.ivMain)
            holder.mView.ivdummy.visibility = View.INVISIBLE

        } else {
            holder.mView.ivMain.visibility = View.INVISIBLE
            holder.mView.ivdummy.visibility = View.VISIBLE
        }
        holder.mView.tvTesName.text = tem_list[position].name
        holder.mView.tvTesCEO.text = tem_list[position].designation
        if (tem_list[position].reviewContent.length > 40) {
            holder.mView.tvReadMore.visibility = View.VISIBLE
        } else {
            holder.mView.tvReadMore.visibility = View.GONE
        }
        holder.mView.tvTesDes.text = tem_list[position].reviewContent
//        holder.mView.imageView51.setImageResource(tem_list[position].img)

        if (position == 0) {
            holder.mView.mainTest.setBackgroundResource(R.drawable.pink_bg)
            holder.mView.tvTesCEO.setTextColor(Color.parseColor("#D41C5B"))
        } else if (position == 1) {
            holder.mView.mainTest.setBackgroundResource(R.drawable.blue_bg)
            holder.mView.tvTesCEO.setTextColor(Color.parseColor("#2795D3"))
        }
        holder.itemView.setOnClickListener {
            con.startActivity(
                Intent(con, TestimonialsDetailsActivity::class.java).putExtra(
                    "list",
                    tem_list[position]
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return tem_list.size
    }
}