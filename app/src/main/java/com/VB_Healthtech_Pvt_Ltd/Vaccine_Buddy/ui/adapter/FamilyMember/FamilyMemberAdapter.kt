package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.FamilyMember

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ScheduleForFamilyMemeberRoundedLayBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.getAge
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddFamilyMember
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bountybunch.helper.startDownload
import com.google.gson.Gson
import kotlin.collections.ArrayList

class FamilyMemberAdapter(
    val con: Context,
    val list: ArrayList<AskedData>,
    val onPress: OnClickItem
) :
    RecyclerView.Adapter<FamilyMemberAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: ScheduleForFamilyMemeberRoundedLayBinding) :
        RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            ScheduleForFamilyMemeberRoundedLayBinding.inflate(
                LayoutInflater.from(
                    con
                ), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (list[position].memberType.equals("KIDS")) {
            if (list[position].relation == "Myself") {
                holder.mView.imageView64.visibility = View.INVISIBLE
            } else {
                holder.mView.imageView64.visibility = View.VISIBLE
            }
//            holder.mView.tvAge.text = "${diff_year} years"
            holder.mView.tvAge.text = "${getAge(list[position].dob)} years"
            holder.mView.tvName.text = list[position].fullName
            holder.mView.dobtext.text = list[position].dob
            holder.mView.tvgender.text = list[position].gender
            holder.mView.relationtext2.text = list[position].relation
            holder.mView.lactoseIntolerent.text = list[position].medicalCondition
            //holder.mView.tvAge.text = "0 years"
            if (list[position].anyReaction.isNullOrEmpty()) {
                holder.mView.na.text = "--"
            } else {
                holder.mView.na.text = list[position].anyReaction
            }
            holder.mView.tvdelivery.text = list[position].deliveryType
            holder.mView.yesview.text = list[position].seizuresAtBirth
            holder.mView.tvmedicalDisorder.text = list[position].medicalDisorder
            holder.mView.child.visibility = View.VISIBLE
            if (list[position].uploadVaccinationChart.isEmpty()) {
                holder.mView.uploadChatBtn.visibility = View.GONE
            } else {
                holder.mView.uploadChatBtn.visibility = View.VISIBLE
                holder.mView.uploadChatBtn.setOnClickListener {
                    Toast.makeText(con, "Downloading.....", Toast.LENGTH_SHORT).show()
                    startDownload(
                        list[position].uploadVaccinationChart,
                        con,
                        "Vaccination chat",
                        "Vaccination chat"
                    )
                }
            }
            if (list[position].nicuDetails.isEmpty()) {
                holder.mView.uploadChatBtn2.visibility = View.GONE
            } else {
                holder.mView.uploadChatBtn2.visibility = View.VISIBLE
                holder.mView.uploadChatBtn2.setOnClickListener {

                    Toast.makeText(con, "Downloading.....", Toast.LENGTH_SHORT).show()
                    startDownload(
                        list[position].nicuDetails,
                        con,
                        "NICU Records Chat",
                        "NICU Records Chat"
                    )


                }
            }

            if (list[position].bithHistory.isEmpty()) {
                holder.mView.uploadChatBtn3.visibility = View.GONE
            } else {
                holder.mView.uploadChatBtn3.visibility = View.VISIBLE
                holder.mView.uploadChatBtn3.setOnClickListener {
                    Toast.makeText(con, "Downloading.....", Toast.LENGTH_SHORT).show()
                    startDownload(
                        list[position].bithHistory,
                        con,
                        "Birth History Chat",
                        "Birth History Chat"
                    )

                }
            }
        } else {
            if (list[position].relation == "Myself") {
                holder.mView.imageView64.visibility = View.INVISIBLE
            } else {
                holder.mView.imageView64.visibility = View.VISIBLE
            }
            holder.mView.tvName.text = list[position].fullName
            holder.mView.dobtext.text = list[position].dob
            holder.mView.tvgender.text = list[position].gender
            holder.mView.relationtext2.text = list[position].relation
            holder.mView.lactoseIntolerent.text = list[position].medicalCondition
            holder.mView.child.visibility = View.GONE
//            holder.mView.tvAge.text = "${diff_year} years"
            holder.mView.tvAge.text = "${getAge(list[position].dob)} years"
            if (list[position].anyReaction.isEmpty()) {
                holder.mView.na.text = "--"
            } else {
                holder.mView.na.text = list[position].anyReaction
            }
            if (list[position].uploadVaccinationChart.isEmpty()) {
                holder.mView.uploadChatBtn.visibility = View.GONE
            } else {
                holder.mView.uploadChatBtn.visibility = View.VISIBLE
                holder.mView.uploadChatBtn.setOnClickListener {
                    Toast.makeText(con, "Downloading.....", Toast.LENGTH_SHORT).show()
                    startDownload(
                        list[position].uploadVaccinationChart,
                        con,
                        "Vaccination chat",
                        "Vaccination chat"
                    )
                }
            }
        }
        holder.mView.imageView64.setOnClickListener {
            onPress.onDelete(list[position], position)
        }
        holder.mView.ivEdit.setOnClickListener {
            val intent = Intent(con, AddFamilyMember::class.java)
            intent.putExtra("Obj", Gson().toJson(list[position]))
            intent.putExtra("fag", true)
            intent.putExtra("redirection", "update")
            con.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /* fun getAge(date: String?, holder: MyViewHolder)
     {
         var age = 0
         var year2: Int? = null
         var month2: Int? = null
         var month1: Int?
         var diff: Int? = null
         try {
             val formatter = SimpleDateFormat("yyyy/MM/dd")
             val date1 = formatter.parse(date)
             val now = Calendar.getInstance()
             val dob = Calendar.getInstance()
             dob.time = date1
             require(!dob.after(now)) { "Can't be born in the future" }
             val year1 = now[Calendar.YEAR]
             year2 = dob[Calendar.YEAR]
             age = year1 - year2
             month1 = now[Calendar.MONTH]
             month2 = dob[Calendar.MONTH]
             diff = month1 - month2
             if (month2 > month1) {
                 age--
             } else if (month1 == month2) {
                 val day1 = now[Calendar.DAY_OF_MONTH]
                 val day2 = dob[Calendar.DAY_OF_MONTH]
                 if (day2 > day1) {
                     age--
                 }
             }
         } catch (e: ParseException) {
             e.printStackTrace()
         }
         if (age != 0) {
             holder.mView.tvAge.setText(diff.toString() + "Months")
         } else {
             holder.mView.tvAge.setText("$age Years")
         }


         Log.d("TAG", "getAge: AGE=> $age")

     }
 */
    interface OnClickItem {
        fun onDelete(msgItem: AskedData, position: Int)

    }
}
