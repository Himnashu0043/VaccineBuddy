package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.DoesDetailsSecond

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.DoesDetailsSecondItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails.AddressData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails.AskedRecord
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DoesDetailSecondAdpater(
    val con: Context,
    val doesList: ArrayList<AskedRecord>,
    val addressList: ArrayList<AddressData>
) :
    RecyclerView.Adapter<DoesDetailSecondAdpater.MyViewHolder>() {
    class MyViewHolder(val mView: DoesDetailsSecondItemBinding) :
        RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            DoesDetailsSecondItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        println("---${addressList}")
        holder.mView.tvAgeOfBirth1.text = doesList[position].ageOfBaby
        holder.mView.tvInsuions1.text =
            doesList[position].vaccines.toString().replace("[", "").replace("]", "")
        if (doesList[position].status.equals("COMPLETE")) {
            holder.mView.tvStatus1.text =
                "${doesList[position].status} on ${doesList[position].completeDate} by ${doesList[position].nurseName} (nurse)"
        } else {
            holder.mView.tvStatus1.text = doesList[position].status
        }
        if (doesList[position].status.equals("Pending")) {
            holder.mView.tvVaccinationDoneBy.text = "--"
            holder.mView.tvVisitAddress.text = "--"
        } else {
            holder.mView.tvVaccinationDoneBy.text = doesList[position].nurseName
            holder.mView.tvVisitAddress.text =
                "${addressList[0].streetAddress} , ${addressList[0].city} , ${addressList[0].state} , ${addressList[0].zipCode}"
        }

    }

    override fun getItemCount(): Int {
        return doesList.size
    }
}