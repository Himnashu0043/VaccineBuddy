package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.MemberRecord

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.DoesDetailsItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecordModel.PackageMemberRecordModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PackageDoesMemberRecordAdapter(
    val con: Context,
    val list: ArrayList<PackageMemberRecordModel>
) : RecyclerView.Adapter<PackageDoesMemberRecordAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: DoesDetailsItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            DoesDetailsItemBinding.inflate(
                LayoutInflater.from(
                    con
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.mView.tvAgeOfBirth.text = list[position].ageofbaby
        holder.mView.tvInsuions.text =
            list[position].statusObj!!.vaccineName.replace("[", "").replace("]", "")
        if (list[position].statusObj!!.status.equals("COMPLETE")) {
            holder.mView.tvStatus.text =
                "${list[position].statusObj!!.status} on ${list[position].statusObj!!.completeDate} by ${list[position].statusObj!!.nurseName} (nurse)"

        } else {
            holder.mView.tvStatus.text = "Pending"
        }
        println("========List${list[position]}")

    }

    override fun getItemCount(): Int {
        return list.size
    }
}