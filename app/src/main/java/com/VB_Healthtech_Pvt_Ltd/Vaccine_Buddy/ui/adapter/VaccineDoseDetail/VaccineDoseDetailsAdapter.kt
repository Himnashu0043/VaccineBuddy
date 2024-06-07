package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.VaccineDoseDetail

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.VaccineDoseItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.DoseVaccineSetModel
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class VaccineDoseDetailsAdapter(
    val con: Context,
    val vac_dose_list: ArrayList<DoseVaccineSetModel>,
    val onPress: onkey
) :
    RecyclerView.Adapter<VaccineDoseDetailsAdapter.MyViewHoldr>() {
    var vaccineNameArr = ArrayList<String>()

    class MyViewHoldr(val mView: VaccineDoseItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHoldr {
        return MyViewHoldr(VaccineDoseItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHoldr, position: Int) {
        println("---testAdd$vac_dose_list")
        holder.mView.textView198.text = vac_dose_list[position].does
        holder.mView.textView199.text =
            vac_dose_list[position].vaccineName
        holder.mView.textView201.setOnClickListener {
            onPress.onMoreClick(vac_dose_list[position].does)
        }
    }

    override fun getItemCount(): Int {
        return vac_dose_list.size

    }

    interface onkey {
        fun onMoreClick(key: String)
    }

}