package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.Address

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.AddressItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.AddressList.AskedData

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddNewAddress
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class AddressAdapter(val con: Context, val list: ArrayList<AskedData>, val onPress: OnClickItem) :
    RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: AddressItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AddressItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item = list[position]
        holder.mView.tvName.text = list[position].contactPersonName
        holder.mView.tvAddress.text =
            "${list[position].streetAddress} ${list[position].city} ${list[position].state} ${list[position].zipCode}"
        holder.mView.tvphone.text = list[position].phoneNumber
        holder.mView.tvAddressType.text = list[position].addressType
        holder.mView.tvtowerFlat.text =
            "${list[position].flat_no ?: ""} ${list[position].tower ?: ""}"
        if (list[position].addressType.equals("Office")) {
            holder.mView.imageView58.setImageResource(R.drawable.ic_buildings)
        } else {
            holder.mView.imageView58.setImageResource(R.drawable.ic_noun_home)
        }
        if (list[position].defaultAddress) {
            holder.mView.tvDefaultAddress.text = "Default Address"
            holder.mView.tvDefaultAddress.setTextColor(ContextCompat.getColor(con, R.color.black))
        } else {
            holder.mView.tvDefaultAddress.text = "Mark Address"
            holder.mView.tvDefaultAddress.setTextColor(ContextCompat.getColor(con, R.color.orange))
        }
        holder.mView.tvDefaultAddress.setOnClickListener {
            onPress.onDefaultAddress(list[position], position)
        }

        holder.mView.ivDelete.setOnClickListener {
            onPress.onDelete(list[position], position)
        }
        holder.mView.ivEdit.setOnClickListener {
            val intent = Intent(con, AddNewAddress::class.java)
            intent.putExtra("Obj", Gson().toJson(list[position]))
            intent.putExtra("fag", true)
            con.startActivity(intent)
            //con.startActivity(Intent(con, EditAddressActivity::class.java))
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickItem {
        fun onDelete(msgItem: AskedData, position: Int)
        fun onDefaultAddress(msgItem: AskedData, position: Int)


    }
}