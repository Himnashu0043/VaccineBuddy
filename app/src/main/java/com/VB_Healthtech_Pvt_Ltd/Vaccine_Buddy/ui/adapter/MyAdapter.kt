package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ListItemBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getLocalTime
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.timeWithCurrentTime
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.NoOfDoesInfoModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.AskedNotification
import com.thekhaeng.pushdownanim.PushDownAnim

class MyAdapter(
    val con: Context,
    val list_noti: ArrayList<AskedNotification>,
    val onPress: ClickId
) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val mvView: ListItemBinding) : RecyclerView.ViewHolder(mvView.root) {
        init {
            PushDownAnim.setPushDownAnimTo(mvView.notiLay)
                .setScale(PushDownAnim.MODE_SCALE, 0.55f)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val date = setFormatDate(list_noti[position].createdAt)
        val time = getLocalTime(list_noti[position].createdAt)
        holder.mvView.tvtitle.text = list_noti[position].title
        holder.mvView.tvcontent.text = list_noti[position].content
        holder.mvView.tvTimeDate.text = "${date}, ${time}"
        holder.mvView.notiLay.setOnClickListener {
            if (list_noti[position].notificationType.equals("Custom Package")) {
                onPress.onClickId(
                    list_noti[position].packageId,
                    list_noti[position].notificationType,
                    list_noti[position]
                )
            } else {
                onPress.onClickId(
                    list_noti[position].vaccineId,
                    list_noti[position].notificationType,
                    list_noti[position]
                )
            }

        }


    }

    override fun getItemCount(): Int {
        return list_noti.size
    }


    interface ClickId {
        fun onClickId(
            id: String,
            notiType: String,
            msg:AskedNotification
        )
    }


}