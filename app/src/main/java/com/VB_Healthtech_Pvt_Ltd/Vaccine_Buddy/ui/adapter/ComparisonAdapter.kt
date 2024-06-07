package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ComparisionItemsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.DoseVaccineSetModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ComparisonAdapter(
    val con: Context,
    val vac_dose_list: ArrayList<DoseVaccineSetModel>,
    val relatedvaccineNameList: ArrayList<DoseVaccineSetModel>
) :
    RecyclerView.Adapter<ComparisonAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: ComparisionItemsBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            ComparisionItemsBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        println("--------rell$relatedvaccineNameList")
        holder.mView.tvComparison1.text = vac_dose_list[position].vaccineName
        holder.mView.tvComparison2.text = relatedvaccineNameList[position].vaccineName
//        if (relatedvaccineNameList.size != null && relatedvaccineNameList.size > 0) {
//            holder.mView.tvComparison2.text = relatedvaccineNameList[position].vaccineName
//        }

//        if (relatedvaccineNameList[position].vaccineName.isEmpty()) {
//            holder.mView.tvComparison2.text = "--"
//        } else {
//            holder.mView.tvComparison2.text = relatedvaccineNameList[position].vaccineName
//        }


    }

    override fun getItemCount(): Int {
        return vac_dose_list.size
    }
}