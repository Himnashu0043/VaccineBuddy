package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.OrderDetailsVaccine

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.OrderDetailsVaccineDoesBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails.AskedRecord
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDoesModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bountybunch.helper.startDownload


class OrderDetailsVaccineDose(
    val con: Context,
    var doesInfo: ArrayList<VaccineDoesModel>,
    var recode_list: ArrayList<AskedRecord>,
    var is_nurse: Boolean,
    var text: TextView
) : RecyclerView.Adapter<OrderDetailsVaccineDose.MyViewHolder>() {


    class MyViewHolder(val mView: OrderDetailsVaccineDoesBinding) :
        RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            OrderDetailsVaccineDoesBinding.inflate(
                LayoutInflater.from(con), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        println("------recode$recode_list")
        println("------is$is_nurse")
        println("------tes$text")
        for (recc in recode_list) {
            if (recc.doseInfo.get(0)._id.equals(doesInfo[position]._id) && recc.status.equals("COMPLETE")) {
                holder.mView.checkBox.isChecked = true
                holder.mView.imageView31.visibility = View.VISIBLE
                holder.mView.imageView31.setOnClickListener {
                    startDownload(
                        recc.certificateLink ?: "",
                        con,
                        "Download Vaccine Certificate",
                        "Download Vaccine Certificate"
                    )
                }
                if (!is_nurse) {
                    text.visibility = View.VISIBLE
                } else {
                    text.visibility = View.GONE
                }
            } else {
                holder.mView.checkBox.isClickable = false

            }
        }
        holder.mView.tvDoesName.text = doesInfo[position].doseNumber
        holder.mView.tvWeekDoes.text = doesInfo[position].timePeriod
        holder.mView.checkBox.isClickable = false
    }

    override fun getItemCount(): Int {
        return doesInfo.size
    }
}