package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.ChooseVaccineAdapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ChooseItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.Grouped
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.ModifyItemData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PriceModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil

class ChooseVaccineAdapter(
    val context: Context,
    val temp: ArrayList<Grouped>,
    val onPress: ChooseChildAdapter.Child,
    val onParent: Parent

) :
    RecyclerView.Adapter<ChooseVaccineAdapter.MyViewHolder>() {
    private lateinit var notificationsAdapterItem: ChooseChildAdapter
    private val viewPool = RecyclerView.RecycledViewPool()

    class MyViewHolder(val mView: ChooseItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(ChooseItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val age: Int = temp[position].age.toInt()

        val sendAge = convertDate(age.toString())
        holder.mView.textView181.text = convertDate(age.toString())
        notificationsAdapterItem =
            ChooseChildAdapter(
                context,
                getModifyList(temp[position].items),
                onPress,
                position,
                holder.mView.textView181.text.toString(),
                temp[position].age
            )
        holder.mView.rcychildChoose.apply {
            adapter = notificationsAdapterItem
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setRecycledViewPool(viewPool)
        }


        holder.mView.checkBox1.setOnClickListener {
            if (holder.mView.rcychildChoose.visibility == View.GONE) {
                holder.mView.rcychildChoose.visibility = View.VISIBLE
                holder.mView.checkBox1.isChecked = true
                temp[position].isSelectStatus = true
                onParent.onParentClick(sendAge, temp[position].age, true, position)

            } else {
                holder.mView.rcychildChoose.visibility = View.GONE
                holder.mView.checkBox1.isChecked = false
                temp[position].isSelectStatus = false
                onParent.onParentClick(sendAge, temp[position].age, false, position)
            }


        }

    }

    fun convertDate(dose: String): String {
        val dosage = dose.toIntOrNull() ?: 0
        if (dosage < 30) return "$dosage days"
        if (dosage < 120) return "${dosage / 7} weeks"
        if (dosage < 365) return "${dosage / 30} months"
        val years = dosage / 365
        val months = (dosage % 365) / 30
        return if (months != 0) "$years years $months months" else "$years years"
    }

    private fun getModifyList(items: ArrayList<Item>): ArrayList<ModifyItemData> {
        val modifyList = ArrayList<ModifyItemData>()
        val hasMap = HashMap<String, ArrayList<Item>>()
        for (items1 in items) {
            if (hasMap.containsKey(items1.name)) {
                val temp = ArrayList<Item>()
                hasMap[items1.name]?.let { temp.addAll(it) }
                temp.add(items1)
                hasMap[items1.name] = temp
            } else {
                val list = ArrayList<Item>()
                list.add(items1)
                hasMap[items1.name] = list
            }

        }

        for ((key, value) in hasMap.entries) {
            modifyList.add(ModifyItemData(value))
        }

        return modifyList

    }

    override fun getItemCount(): Int {
        return temp.size

    }

    interface Parent {
        fun onParentClick(age: String, normalAge: String, checkType: Boolean, position: Int)
    }
}