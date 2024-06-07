package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SlotAdapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.SlotTimeItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SlotList.Slot
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getNewFormateCurrentDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getweekDay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SlotTimeAdapter(
    val con: Context,
    val listTime: ArrayList<Slot>,
    val onPress: Time,
    var back_selected_time: String,
    var today: Boolean
) :
    RecyclerView.Adapter<SlotTimeAdapter.MyViewHolder>() {
    var selectedPosition = -1
    var getWeek: String = ""



    class MyViewHolder(val mView: SlotTimeItemBinding) : RecyclerView.ViewHolder(mView.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        return MyViewHolder(SlotTimeItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        println("-----back_seleted_time$back_selected_time")
        val item = listTime.get(position)
        val selectedtimeId = item._id
        val timeSelected = "${listTime[position].from} - " + listTime[position].to
        var tt = compareTwoTime(listTime[position].to)
        println("========today$today")
        /* println("========by$byDefaultDate")*/
        getWeek = getweekDay()
        println("=$$$getWeek")
        if (selectedtimeId.equals(back_selected_time)) {
            selectedPosition = position
            item.isSelected = true
        }
        if (today){
            if (tt){
                holder.mView.timelay.setBackgroundResource(R.drawable.light_stroke_black_bg)
                holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.white))
            }else{
                if (item.slotStatus.equals("INACTIVE")) {
                    holder.mView.timelay.setBackgroundResource(R.drawable.red_stroke_black_bg)
                    holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.white))
                } else {
                    if (selectedPosition == position) {
                        holder.mView.root.isSelected = true
                        holder.mView.timelay.setBackgroundResource(R.drawable.black_stroke_blue_bg)
                        holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.white))
                    }else{
                        holder.mView.root.isSelected = false
                        holder.mView.timelay.setBackgroundResource(R.drawable.black_stroke_white_bg)
                        holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.black))
                    }

                }
            }
        } else {
            if (item.slotStatus.equals("INACTIVE")) {
                holder.mView.timelay.setBackgroundResource(R.drawable.red_stroke_black_bg)
                holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.white))
            } else {
                if (selectedPosition == position) {
                    holder.mView.root.isSelected = true
                    holder.mView.timelay.setBackgroundResource(R.drawable.black_stroke_blue_bg)
                    holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.white))
                }else{
                    holder.mView.root.isSelected = false
                    holder.mView.timelay.setBackgroundResource(R.drawable.black_stroke_white_bg)
                    holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.black))
                }
            }
        }

//        if (item.slotStatus.equals("INACTIVE")) {
//            holder.mView.timelay.setBackgroundResource(R.drawable.red_stroke_black_bg)
//            holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.white))
//        } else {
//            if (tt) {
//                if (byDefaultweek == getWeek) {
//
//                } else {
//                    holder.mView.timelay.setBackgroundResource(R.drawable.black_stroke_white_bg)
//                    holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.black))
//                }
//
//            } else if (selectedPosition == position) {
//                holder.mView.root.isSelected = true
//                holder.mView.timelay.setBackgroundResource(R.drawable.black_stroke_blue_bg)
//                holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.white))
//            } else {
//                holder.mView.root.isSelected = false
//                holder.mView.timelay.setBackgroundResource(R.drawable.black_stroke_white_bg)
//                holder.mView.tvSlotTime.setTextColor(con.getColor(R.color.black))
//
//            }
//        }



        holder.mView.timelay.setOnClickListener {
            var tt = compareTwoTime(item.to)
            if (today){
                if (tt){
                    Toast.makeText(con, "This slot not available!", Toast.LENGTH_SHORT).show()
                }else{
                    if (item.slotStatus.equals("INACTIVE")) {
                        Toast.makeText(con, "This Slot is IN Active!", Toast.LENGTH_SHORT).show()
                    } else {
                        if (selectedPosition != position) {
                            selectedPosition = holder.layoutPosition
                            back_selected_time = "${System.currentTimeMillis()}himansu"
                            onPress.onTimeSelect(selectedtimeId, timeSelected)
                        }
                    }
                }
            } else {
                if (item.slotStatus.equals("INACTIVE")) {
                    Toast.makeText(con, "This Slot is IN Active!", Toast.LENGTH_SHORT).show()
                } else {
                    if (selectedPosition != position) {
                        selectedPosition = holder.layoutPosition
                        back_selected_time = "${System.currentTimeMillis()}himansu"
                        onPress.onTimeSelect(selectedtimeId, timeSelected)
                    }
                }
            }


//            /* Log.d("isByDefaultttt", " $byDefaultDate  || $currentDate")
//             if (byDefaultDate == currentDate) {
//                 if (tt) {
//                     isByDefaultttt = true
//                     Toast.makeText(con, "This Slot is IN Active!", Toast.LENGTH_SHORT).show()
//                 } else {
//                     isByDefaultttt = false
//                 }
//
//             } else*/ if (item.slotStatus.equals("INACTIVE")) {
//            Toast.makeText(con, "This Slot is IN Active!", Toast.LENGTH_SHORT).show()
//        } else {
//
//            // updatelist(position)
//
//        }

            notifyDataSetChanged()
        }
        holder.mView.tvSlotTime.text = "${listTime[position].from} - " + listTime[position].to
    }

    override fun getItemCount(): Int {
        return listTime.size
    }

    private fun updatelist(pos: Int) {
        for (i in listTime.indices) {
            if (i == pos)
                listTime.get(i).isSelected = true
            else
                listTime.get(i).isSelected = false
        }
        notifyDataSetChanged()
    }

    interface Time {
        fun onTimeSelect(id: String, time: String)
    }

    fun compareTwoTime(startTime: String): Boolean {
        val calendar = Calendar.getInstance()

        // Define the first time (extracted from startTime argument)
        val startTimeComponents = startTime.split(":")
        val startHour = startTimeComponents[0].toInt()
        val startMinute = startTimeComponents[1].toInt()

        // Get the current time as a string
        val dateFormat = SimpleDateFormat("HH:mm")
        val currentTime = dateFormat.format(Date())

        // Define the second time (extracted from currentTime)
        val currentTimeComponents = currentTime.split(":")
        val currentHour = currentTimeComponents[0].toInt()
        val currentMinute = currentTimeComponents[1].toInt()

        // Combine the current date with the first time
        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        calendar.set(Calendar.MINUTE, startMinute)
        calendar.set(Calendar.SECOND, 0)
        val time1 = calendar.time

        // Combine the current date with the current time
        calendar.set(Calendar.HOUR_OF_DAY, currentHour)
        calendar.set(Calendar.MINUTE, currentMinute)
        val time2 = calendar.time

        // Compare the two times
        return when {
            time1.before(time2) -> {
                println("Time from input is before the current time")
                true
            }
            time1 == time2 -> {
                println("Time from input is the same as the current time")
                false
            }
            else -> {
                println("Time from input is after the current time")
                false
            }
        }
    }
}