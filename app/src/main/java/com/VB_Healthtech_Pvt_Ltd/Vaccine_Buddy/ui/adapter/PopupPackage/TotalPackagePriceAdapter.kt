package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.PopupPackage

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.TotalPackagePopupItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Cart.CartList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TotalPackagePriceAdapter(val con: Context,val package_price_list:ArrayList<AskedData>) :
    RecyclerView.Adapter<TotalPackagePriceAdapter.MyViewHolder>() {
    class MyViewHolder (val mView:TotalPackagePopupItemBinding):RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(TotalPackagePopupItemBinding.inflate(LayoutInflater.from(con),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (package_price_list[position].cartType.equals("PACKAGE")){
            holder.mView.tvpackageNameItem.text = package_price_list[position].packageName
            holder.mView.tvPackagePriceItem.text = "₹"+package_price_list[position].offerPrice
        }

    }

    override fun getItemCount(): Int {
       return package_price_list.size
    }
}