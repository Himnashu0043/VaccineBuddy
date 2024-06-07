package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.HomeSearchAdapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.SearchItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.NameModal
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.GlobalModel

class HomeSearchAdapter(val con: Context, val test: ArrayList<GlobalModel>,val onPress:TestClick) :
    RecyclerView.Adapter<HomeSearchAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: SearchItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(SearchItemBinding.inflate(LayoutInflater.from(con), parent, false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mView.name.text = test[position].name
        holder.mView.name.setOnClickListener {
            onPress.onTest(test[position],test[position].id)
        }
    }

    override fun getItemCount(): Int {
        return test.size
    }
    interface TestClick{
        fun onTest(msg:GlobalModel,id:String)
    }
}