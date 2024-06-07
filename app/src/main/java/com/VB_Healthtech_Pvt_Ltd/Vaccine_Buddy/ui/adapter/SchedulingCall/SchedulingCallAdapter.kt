package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SchedulingCall

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ScheduleCallItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CallSchedule.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bountybunch.helper.startDownload

class SchedulingCallAdapter(val con: Context, val call_list: ArrayList<AskedData>) :
    RecyclerView.Adapter<SchedulingCallAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: ScheduleCallItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            ScheduleCallItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }


    private var diff_year: Int = 0
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = call_list.get(position)
//        try {
//            val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                LocalDateTime.now()
//            } else {
//                TODO("VERSION.SDK_INT < O")
//            }
//            current_year = current.getYear()
//            println("---current_yearAdd$current_year")
//        } catch (e: Exception) {
//            e.toString()
//        }
//        val currentDate = LocalDate.parse(item.userData.get(0).dob)
//        year = currentDate.year
//        println("-----uuuuuuu$year")
//        diff_year = current_year - year
//        println("-----diff_year$diff_year")

        val slotDay = item.slotDay
        val date = item.sheduledDate
        val convert_date = setFormatDate(date)
        val from = item.sheduledSlot.get(0).from
        val to = item.sheduledSlot.get(0).to
        val from_to = "$from - $to"
        val req_date = item.requestedDate
        val req_convert_date = setFormatDate(req_date)

        if (call_list[position].memberType.equals("KIDS")) {
            println("-----kkkdiff_year$diff_year")
            holder.mView.constraintLayout11.visibility = View.VISIBLE
            if (slotDay.isNullOrEmpty()) {
                holder.mView.tvTimeDayCallScheduleItem.text = "-"
            } else {
                holder.mView.tvTimeDayCallScheduleItem.text = "$convert_date,$slotDay,$from_to"
            }
            if (item.userData.get(0).uploadDocument.isEmpty()) {
                holder.mView.textView108.visibility = View.GONE
            } else {
                holder.mView.textView108.visibility = View.VISIBLE
                holder.mView.textView108.setOnClickListener {
                    Toast.makeText(con, "Downloading...", Toast.LENGTH_SHORT).show()
                    startDownload(
                        item.userData.get(0).uploadDocument,
                        con,
                        "Vaccination Chat",
                        "Vaccination Chart"
                    )
                }
            }
            if (item.userData.get(0).nicuDetails.isEmpty()) {
                holder.mView.uploadChatBtn2.visibility = View.GONE
            } else {
                holder.mView.uploadChatBtn2.visibility = View.VISIBLE
                holder.mView.uploadChatBtn2.setOnClickListener {
                    Toast.makeText(con, "Downloading...", Toast.LENGTH_SHORT).show()
                    startDownload(
                        item.userData.get(0).nicuDetails,
                        con,
                        "NICU Chat",
                        "NICU Chart"
                    )
                }
            }
            if (item.userData.get(0).bithHistory.isEmpty()) {
                holder.mView.uploadChatBtn3.visibility = View.GONE
            } else {
                holder.mView.uploadChatBtn3.visibility = View.VISIBLE
                holder.mView.uploadChatBtn3.setOnClickListener {
                    Toast.makeText(con, "Downloading...", Toast.LENGTH_SHORT).show()
                    startDownload(
                        item.userData.get(0).bithHistory,
                        con,
                        "Birth History Chat",
                        "Birth History Chart"
                    )
                }
            }


            holder.mView.tvage.text = "${getAge(item.userData.get(0).dob)} years"
            holder.mView.tvstatusCallScheduleItem.text = item.callStatus
            holder.mView.tvNameCallScheduleItem.text = item.userData.get(0).fullName
            holder.mView.tvGenderCallScheduleItem.text = item.userData.get(0).gender
            holder.mView.tvDoBCallScheduleItem.text = item.userData.get(0).dob
            holder.mView.tvRelationCallScheduleItem.text = item.userData.get(0).relation
            if (item.userData.get(0).medicalCondition.isNullOrEmpty()) {
                holder.mView.tvMedicalCallScheduleItem.text = "-"
            } else {
                holder.mView.tvMedicalCallScheduleItem.text =
                    item.userData.get(0).medicalCondition
            }
            if (item.userData.get(0).previousPediatrician.isNullOrEmpty()) {
               // holder.mView.tvPrevoiusCallScheduleItem.text = "-"
                holder.mView.tvPrevoiusCallScheduleItem.visibility = View.GONE
                holder.mView.textView116.visibility = View.GONE
            } else {
                holder.mView.tvPrevoiusCallScheduleItem.visibility = View.VISIBLE
                holder.mView.textView116.visibility = View.VISIBLE
                holder.mView.tvPrevoiusCallScheduleItem.text =
                    item.userData.get(0).previousPediatrician
            }
            if (item.userData.get(0).anyReaction.isNullOrEmpty()) {
                holder.mView.textView30.visibility = View.GONE
                holder.mView.tvAnyReaction.visibility = View.GONE
               // holder.mView.tvAnyReaction.text = "-"

            } else {
                holder.mView.textView30.visibility = View.VISIBLE
                holder.mView.tvAnyReaction.visibility = View.VISIBLE
                holder.mView.tvAnyReaction.text =
                    item.userData.get(0).anyReaction
            }
            val delivery_type = item.userData.get(0).deliveryType
            val delivery_period = item.userData.get(0).deliveryPeriod
            if (delivery_type.isNullOrEmpty() && delivery_period.isNullOrEmpty()) {
                holder.mView.tvDelivery.text = "-"
            } else {
                holder.mView.tvDelivery.text = "$delivery_type ," + "$delivery_period"
            }
            holder.mView.tvAnySeizures.text = item.userData.get(0).seizuresAtBirth
            if (item.userData.get(0).medicalDisorder.isNullOrEmpty()) {
                holder.mView.tvmedicalDisorder.text = "-"
            } else {
                holder.mView.tvmedicalDisorder.text = item.userData.get(0).medicalDisorder
            }

            if (item.confirmStatus.equals("COMPLETED")) {
                holder.mView.tvCallTimeScheduleDate.text = "$req_convert_date"
            } else {
                holder.mView.textView111.visibility = View.GONE
                holder.mView.tvCallTimeScheduleDate.visibility = View.GONE
                //holder.mView.tvCallTimeScheduleDate.text = "--"
            }
        } else {
            holder.mView.constraintLayout11.visibility = View.GONE
            println("-----ooodiff_year$diff_year")
            holder.mView.tvage.text = "${getAge(item.userData.get(0).dob)} years"
            if (slotDay.isNullOrEmpty()) {
                holder.mView.tvTimeDayCallScheduleItem.text = "-"
            } else {
                holder.mView.tvTimeDayCallScheduleItem.text = "$convert_date,$slotDay,$from_to"
            }
            if (item.userData.get(0).uploadDocument.isEmpty()) {
                holder.mView.textView108.visibility = View.GONE
            } else {
                holder.mView.textView108.visibility = View.VISIBLE
                holder.mView.textView108.setOnClickListener {
                    Toast.makeText(con, "Downloading...", Toast.LENGTH_SHORT).show()
                    startDownload(
                        item.userData.get(0).uploadDocument,
                        con,
                        "Vaccination Chat",
                        "Vaccination Chart"
                    )
                }
            }
            holder.mView.tvstatusCallScheduleItem.text = item.callStatus
            holder.mView.tvNameCallScheduleItem.text = item.userData.get(0).fullName
            holder.mView.tvGenderCallScheduleItem.text = item.userData.get(0).gender
            holder.mView.tvDoBCallScheduleItem.text = item.userData.get(0).dob
            holder.mView.tvRelationCallScheduleItem.text = item.userData.get(0).relation
            if (item.userData.get(0).medicalCondition.isNullOrEmpty()) {
                holder.mView.tvMedicalCallScheduleItem.text = "-"
            } else {
                holder.mView.tvMedicalCallScheduleItem.text =
                    item.userData.get(0).medicalCondition
            }
            if (item.confirmStatus.equals("COMPLETED")) {
                holder.mView.tvCallTimeScheduleDate.text = "$req_convert_date"
            } else {
                holder.mView.textView111.visibility = View.GONE
                holder.mView.tvCallTimeScheduleDate.visibility = View.GONE
              //  holder.mView.tvCallTimeScheduleDate.text = "--"
            }
            /*if (item.userData.get(0).previousPediatrician.isNullOrEmpty()) {
                holder.mView.tvPrevoiusCallScheduleItem.text = "-"
            } else {
                holder.mView.tvPrevoiusCallScheduleItem.text =
                    item.userData.get(0).previousPediatrician
            }*/
            if (item.userData.get(0).previousPediatrician.isNullOrEmpty()) {
                // holder.mView.tvPrevoiusCallScheduleItem.text = "-"
                holder.mView.tvPrevoiusCallScheduleItem.visibility = View.GONE
                holder.mView.textView116.visibility = View.GONE
            } else {
                holder.mView.tvPrevoiusCallScheduleItem.visibility = View.VISIBLE
                holder.mView.textView116.visibility = View.VISIBLE
                holder.mView.tvPrevoiusCallScheduleItem.text =
                    item.userData.get(0).previousPediatrician
            }
//            if (item.userData.get(0).anyReaction.isNullOrEmpty()) {
//                holder.mView.tvAnyReaction.text = "-"
//            } else {
//                holder.mView.tvAnyReaction.text =
//                    item.userData.get(0).anyReaction
//            }
            if (item.userData.get(0).anyReaction.isNullOrEmpty()) {
                holder.mView.textView30.visibility = View.GONE
                holder.mView.tvAnyReaction.visibility = View.GONE
                // holder.mView.tvAnyReaction.text = "-"

            } else {
                holder.mView.textView30.visibility = View.VISIBLE
                holder.mView.tvAnyReaction.visibility = View.VISIBLE
                holder.mView.tvAnyReaction.text =
                    item.userData.get(0).anyReaction
            }
        }
    }

    override fun getItemCount(): Int {
        return call_list.size
    }
}