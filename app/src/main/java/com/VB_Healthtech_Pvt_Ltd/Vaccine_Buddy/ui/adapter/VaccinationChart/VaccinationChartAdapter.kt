package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.VaccinationChart

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.DoesDetailsItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.DoseForAge.Grouped
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.newSchedule.NewScheduleList
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VaccinationChartAdapter(
    val con: Context,
    val list: ArrayList<NewScheduleList.Result>,
    var memberDOB: String
) :
    RecyclerView.Adapter<VaccinationChartAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: DoesDetailsItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            DoesDetailsItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    private fun convertDate(dose: String): String {
        var intConvert = dose.toInt()
        if (intConvert < 730) {
            return "${intConvert / 7} Week"
        } else {
            return "${intConvert / 30}  Month"
        }
    }

    private fun convertDate1(dose: String): String {
        var intConvert = dose.toInt()
        if (intConvert < 30) {
            return "${intConvert} Day"
        } else if (intConvert < 120) {
            return "${intConvert / 7} Week"
        } else if (intConvert < 365) {
            return "${intConvert / 30} Month"
        } else {
            var dos = intConvert / 30
            var i = 0
            while (dos > 12) {
                i += 1
                dos = dos - 12
            }
            if (dos == 0) {
                return "${i} Year"
            } else {
                return "${i} Year ${dos} Month"
            }

        }
    }


    private fun addDaysInDOB(dob: String, days: String): String {
        val dt = dob

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c: Calendar = Calendar.getInstance()
        try {
            c.setTime(sdf.parse(dt))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c.add(
            Calendar.DATE,
            days.toInt()
        ) // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val output: String = sdf1.format(c.getTime())
        return output
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedNameList: MutableList<String?> = java.util.ArrayList()
        var item = list[position].vaccine
        var age = list[position].ageGroup
        for (itemm in item) {
            selectedNameList.add(itemm.vaccineName)
        }
        holder.mView.tvInsuions.setText(TextUtils.join(", ", selectedNameList))
        holder.mView.tvAgeOfBirth.text = age
        holder.mView.tvStatus.text = addDaysInDOB(memberDOB,list[position].toDaysAge.toString())
        /* holder.mView.tvAgeOfBirth.text = list[position].ageofbaby
 //        holder.mView.tvInsuions.text =
 //            list[position].statusObj!!.vaccineName!!.replace("[", "").replace("]", "")
 //        if (list[position].statusObj!!.status.equals("COMPLETE")) {
 //            holder.mView.tvStatus.text =
 //                "${list[position].statusObj!!.status} on ${list[position].statusObj!!.completeDate} by ${list[position].statusObj!!.nurseName} (nurse)"
 //
 //        } else {
 //            holder.mView.tvStatus.text = "Pending"
 //        }*/
    }

    override fun getItemCount(): Int {
        return list.size
    }
}