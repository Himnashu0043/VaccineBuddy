package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Address

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.MainAddressItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.AddressList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainAddressAdapter(
    val con: Context,
    val main_addres_list: ArrayList<AskedData>,
    val onPress: Radio
) :
    RecyclerView.Adapter<MainAddressAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: MainAddressItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(MainAddressItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = main_addres_list.get(position)
        val id = main_addres_list[position]._id
        if (main_addres_list[position].addressType.equals("Office")) {
            holder.mView.imageView15.setImageResource(R.drawable.ic_buildings)
            holder.mView.textView32.text = "Office Address"
        } else {
            holder.mView.imageView15.setImageResource(R.drawable.ic_noun_home)
            holder.mView.textView32.text = "Home Address"
        }
        if (item.defaultAddress) {
            holder.mView.radioButton.setChecked(true)
        } else {
            holder.mView.radioButton.setChecked(false)
        }
        holder.mView.tvMainAddressTowerFlat.text =
            "${main_addres_list[position].flat_no ?: ""} ${main_addres_list[position].tower ?: ""}"
        holder.mView.tvMainAddressItem.text =
            "${main_addres_list[position].streetAddress} ${main_addres_list[position].city} ${main_addres_list[position].state} ${main_addres_list[position].zipCode}"
        holder.mView.radioButton.setOnClickListener {
            updatelist(position)
            onPress.onRadioCheck(id, main_addres_list[position])


        }

    }

    override fun getItemCount(): Int {
        return main_addres_list.size
    }

    private fun updatelist(pos: Int) {
        for (i in main_addres_list.indices) {
            if (i == pos)
                main_addres_list.get(i).defaultAddress = true
            else
                main_addres_list.get(i).defaultAddress = false
        }
        notifyDataSetChanged()
    }

    interface Radio {
        fun onRadioCheck(id: String, msg: AskedData)
    }
}