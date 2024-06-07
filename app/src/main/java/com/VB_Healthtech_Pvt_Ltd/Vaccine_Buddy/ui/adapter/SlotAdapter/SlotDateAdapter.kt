package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SlotAdapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.SlotDateItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SlotList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getCurrentDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getNewFormateCurrentDate

class SlotDateAdapter(
    val con: Context,
    val listDate: ArrayList<AskedData>,
    val onPress: Date,
    var back_selected_date_day: String
) :
    RecyclerView.Adapter<SlotDateAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: SlotDateItemBinding) : RecyclerView.ViewHolder(mView.root)

    var currentDate: String = ""
    var isByDefault: Boolean = true
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(SlotDateItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listDate.get(position)
        val dayId = item._id
        val day = item.day
        val date = listDate[position].date
        val dd = setFormatDate(date)
        println("----datte$dd")
        holder.mView.tvSlotDate.text = "${dd} , ${listDate[position].day}"
        val date_day = "${dd} , ${listDate[position].day}"
        println("------back_selected_date_day$back_selected_date_day")
        currentDate = getNewFormateCurrentDate()
        /*currentDate = getNewFormateCurrentDate()
        if (currentDate == dd) {
            item.isSelected = true
        }*/
        /*for (cuu in listDate) {
            if (dd == currentDate) {
                item.isSelected = true
            }else {

            }
        }*/
        if (back_selected_date_day == "null") {
            if (isByDefault) {
                if (position == 0) {
                    item.isSelected = true
                }

            }
        }


        //notifyDataSetChanged()


        if (dayId.equals(back_selected_date_day)) {
            item.isSelected = true
        }

        if (item.isSelected) {
            holder.mView.datelay.setBackgroundResource(R.drawable.black_stroke_blue_bg)
            holder.mView.tvSlotDate.setTextColor(con.getColor(R.color.white))
        } else {
            holder.mView.datelay.setBackgroundResource(R.drawable.black_stroke_white_bg)
            holder.mView.tvSlotDate.setTextColor(con.getColor(R.color.black))
        }


        holder.mView.datelay.setOnClickListener {
            if (dd == currentDate) {
                isByDefault = true
            } else {
                isByDefault = false
            }
            back_selected_date_day = "ggggggglasssassass"
            updatelist(position)
            onPress.onDateSeleect(
                listDate[position],
                position,
                dayId,
                day,
                dd.toString()
            )
        }
    }

    private fun updatelist(pos: Int) {
        for (i in listDate.indices) {
            if (i == pos)
                listDate.get(i).isSelected = true
            else
                listDate.get(i).isSelected = false
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listDate.size
    }

    interface Date {
        fun onDateSeleect(
            msg: AskedData,
            position: Int,
            dayId: String,
            day: String,
            date: String
        )
    }
}