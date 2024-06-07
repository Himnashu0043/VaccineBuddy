package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.MemberRecord

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.OrderDetailsVaccineDoesBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecordModel.VaccineMemberRecordModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.Toaster

class VaccineMemberRecordAdapter(
    val con: Context,
    val vaccineMRList: ArrayList<VaccineMemberRecordModel>
) :
    RecyclerView.Adapter<VaccineMemberRecordAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: OrderDetailsVaccineDoesBinding) :
        RecyclerView.ViewHolder(mView.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            OrderDetailsVaccineDoesBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (vaccineMRList[position].status.equals("COMPLETE")) {
            holder.mView.checkBox.isChecked = true
            holder.mView.checkBox.isClickable = false
            holder.mView.tvDoesName.text = vaccineMRList[position].doseNumber
            holder.mView.tvWeekDoes.text = vaccineMRList[position].timePeriod
        } else {
            holder.mView.checkBox.isChecked = false
            holder.mView.checkBox.isClickable = false
            holder.mView.tvDoesName.text = vaccineMRList[position].doseNumber
            holder.mView.tvWeekDoes.text = vaccineMRList[position].timePeriod
        }

    }

    override fun getItemCount(): Int {
        return vaccineMRList.size
    }
}