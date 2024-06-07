package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.PopupVaccine

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.VaccineTotalPricePopupItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Cart.CartList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TotalVaccinePriceAdapter(val con: Context, val vaccone_price_list: ArrayList<AskedData>) :
    RecyclerView.Adapter<TotalVaccinePriceAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: VaccineTotalPricePopupItemBinding) :
        RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            VaccineTotalPricePopupItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (vaccone_price_list[position].cartType.equals("VACCINE")) {
            holder.mView.tvVaccineName.text = vaccone_price_list[position].vaccineName
            val noofDose = vaccone_price_list[position].noOfDose
            val vaccine_price = vaccone_price_list[position].vaccinePrice
            holder.mView.tvCostVaccine.text = "(${noofDose}*${vaccine_price})"
            val total_vaccine_price = noofDose.toInt() * vaccine_price.toInt()
            holder.mView.tvCostVaccinePrice.text = "₹$total_vaccine_price"
            val home_vaccination_free_price = vaccone_price_list[position].homeVaccinationFee
            holder.mView.tvHomeVaccinationFreeVaccine.text =
                "($noofDose*$home_vaccination_free_price)"
            val total_home_vaccination_free_price =
                noofDose.toInt() * home_vaccination_free_price.toInt()
            holder.mView.tvHomeVaccinationPrice.text = "₹$total_home_vaccination_free_price"
            println("---vaccone_price_list$vaccone_price_list")
            if (vaccone_price_list[position].offerPrice != null){
                holder.mView.tvDiscountPrice.text ="- ₹${vaccone_price_list[position].offerPrice}"
            }else{
                holder.mView.tvDiscountPrice.text ="- ₹0"
            }
        }
    }

    override fun getItemCount(): Int {
       return vaccone_price_list.size
    }
}
