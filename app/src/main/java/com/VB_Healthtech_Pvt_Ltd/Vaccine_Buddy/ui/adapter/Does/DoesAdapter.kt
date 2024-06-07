package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Does

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.DoesItemsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDetails.DoseInfo
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DoesAdapter(
    val con: Context,
    val doeslist: ArrayList<DoseInfo>,
    val onPress: Does,
    var fromNoti_vaccineIdList: ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.DoseInfo>,
    var notiType: String
) :
    RecyclerView.Adapter<DoesAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: DoesItemsBinding) : RecyclerView.ViewHolder(mView.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(DoesItemsBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mView.tvDoesName.text = doeslist[position].doseNumber
        holder.mView.tvWeekDoes.text = doeslist[position].timePeriod
        println("---adpater$fromNoti_vaccineIdList")
        if (notiType.equals("Custom Vaccine") && notiType != null) {
            for (noti in fromNoti_vaccineIdList) {
                if (doeslist[position].doseNumber.equals(noti.doseNumber)) {
                    holder.mView.checkBox.isChecked = true
                    holder.mView.checkBox.isClickable = false
                    holder.mView.checkBox.setOnClickListener {
                        holder.mView.checkBox.isClickable = false
                        holder.mView.checkBox.isChecked = true
                    }
                } else {
                    holder.mView.checkBox.isClickable = false
                }
            }

        } else {
            holder.mView.checkBox.setOnClickListener {
                if (holder.mView.checkBox.isChecked) {
                    onPress.onDoesChecked(doeslist[position], position)
                } else {
                    onPress.onDoesUnChecked(doeslist[position], position)
                }

            }
        }


    }

    override fun getItemCount(): Int {
        return doeslist.size
    }

    interface Does {
        fun onDoesChecked(msg: DoseInfo, pos: Int)
        fun onDoesUnChecked(msg: DoseInfo, pos: Int)
    }
}