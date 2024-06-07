package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.MycartItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.setFormatDate
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Cart.CartList.AskedData
import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyCartAdapter(
    val con: Context,
    val cartList: ArrayList<AskedData>,
    private val onPress: OnClickItem
) :
    RecyclerView.Adapter<MyCartAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: MycartItemBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MycartItemBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (cartList[position].cartType.equals("VACCINE")) {
            val img: String = cartList[position].vaccineImage
            println("---img${img}")
            if (img != null && img.length > 0) {
                Picasso.get().load(img).into(holder.mView.imageView53)
                holder.mView.lottie.visibility = View.GONE
            } else {
                holder.mView.lottie.visibility = View.VISIBLE

            }
            val from_to = cartList[position].slot.let {
                if (it.isNotEmpty())
                    "${it[0].from} - ${it[0].to}"
                else {
                    ""
                }
            }

            println("----frm$from_to")
            val date = cartList[position].slotDate
            val convert_date = setFormatDate(date)
            holder.mView.tvNameCart.text = cartList[position].vaccineName
            holder.mView.tvDesCart.text = cartList[position].vaccineDescription
            holder.mView.tvPriceCart.text = "₹" + cartList[position].price
            holder.mView.tvVaccineNameCart.text = cartList[position].userData.get(0).fullName
            holder.mView.tvDOBVaccineCart.text = cartList[position].userData.get(0).dob
            holder.mView.tvRelationVaccineCart.text = cartList[position].userData.get(0).relation
            holder.mView.divider18.visibility = View.GONE
            holder.mView.tvOfferPriceCart.visibility = View.GONE
            holder.mView.tvIncludeVaccine.text = cartList[position].noOfDose
            holder.mView.dayTimeDate.text =
                "${convert_date} , ${cartList[position].slotDay} ,${from_to}"
        } else {
            val img: String = cartList[position].packageImage
            println("---img${img}")
            if (img != null && img.length > 0) {
                Picasso.get().load(img).into(holder.mView.imageView53)
                holder.mView.lottie.visibility = View.GONE
            } else {
                holder.mView.lottie.visibility = View.VISIBLE
            }
            val from_to = cartList[position].slot.let {
                if (it.isNotEmpty())
                    "${it[0].from} - ${it[0].to}"
                else {
                    ""
                }
            }
            val date = cartList[position].slotDate
            val convert_date = setFormatDate(date)
            holder.mView.tvNameCart.text = cartList[position].packageName
            holder.mView.tvDesCart.text = cartList[position].packageDescription
            holder.mView.tvIncludeVaccine.text = cartList[position].inclusiveVaccine
            holder.mView.tvVaccineNameCart.text = cartList[position].userData.get(0).fullName
            holder.mView.tvDOBVaccineCart.text = cartList[position].userData.get(0).dob
            holder.mView.tvRelationVaccineCart.text = cartList[position].userData.get(0).relation
            holder.mView.dayTimeDate.text =
                "${convert_date} , ${cartList[position].slotDay} ,${from_to}"
            if (cartList[position].offerPrice.isNullOrEmpty()) {
                holder.mView.tvPriceCart.text = "₹" + cartList[position].totalCost
                holder.mView.divider18.visibility = View.GONE
                holder.mView.tvOfferPriceCart.visibility = View.GONE
            } else {
                holder.mView.tvPriceCart.text = "₹" + cartList[position].totalCost
                holder.mView.tvOfferPriceCart.text = "₹" + cartList[position].offerPrice

            }
        }
        holder.mView.imageView54.setOnClickListener {
            onPress.onDeleteCart(cartList[position], position)
        }
    }

    interface OnClickItem {
        fun onDeleteCart(msg: AskedData, position: Int)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}