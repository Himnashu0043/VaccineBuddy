package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.WishList

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.PackageListsBinding
import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim

class VaccineAdapter(
    val con: Context,
    val wishvaccinelist: ArrayList<com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishVaccineList.AskedRequest>,
    val onRemove: OnClickWishList,
    val onPress:OnClickWishList
) :
    RecyclerView.Adapter<VaccineAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PackageListsBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (wishvaccinelist[position].vaccineData.totalStock.equals("0")) {
            holder.mView.tvoutofstock.visibility = View.VISIBLE
        } else {
            holder.mView.tvoutofstock.visibility = View.GONE
        }
        val img: String = wishvaccinelist!![position].vaccineData.vaccineImage
        if (img != null && img.length > 0) {
            Picasso.get().load(img).into(holder.mView.imageView18)
            holder.mView.lottie.visibility = View.GONE
        } else {
            holder.mView.lottie.visibility = View.VISIBLE
        }


        holder.mView.divider13.visibility = View.GONE
        holder.mView.tvwishlistOfferPrice.visibility = View.GONE
        holder.mView.tvWishPackagePrice.text = "₹" + wishvaccinelist[position].vaccineData.price
        holder.mView.tvIncludeVaccine.text = wishvaccinelist[position].vaccineData.noOfVaccination
        holder.mView.tvWishPackageName.text = wishvaccinelist[position].vaccineData.vaccineName
        holder.mView.tvWishPackageDes.text = wishvaccinelist[position].vaccineData.description
        holder.mView.ivDelete.setOnClickListener {
            onRemove.onRemoveWishlist(wishvaccinelist[position].vaccineData._id)
            /*val dialog = this.let { Dialog(con) }
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_cancel)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)

            val btDismiss = dialog.findViewById<TextView>(R.id.dialog_newcancel)
            val btSubmit = dialog.findViewById<TextView>(R.id.dialog_ok)
            btSubmit.setOnClickListener {
                dialog.dismiss()
            }
            btDismiss.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

            val window = dialog.window
            if (window != null) {
                window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }*/
        }
        holder.mView.main.setOnClickListener {
            onPress.onVaccineId(wishvaccinelist[position])
        }
    }

    override fun getItemCount(): Int {
        return wishvaccinelist.size
    }

    interface OnClickWishList {
        fun onRemoveWishlist(removeId: String)
        fun onVaccineId(msg:com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishVaccineList.AskedRequest)
    }

    class MyViewHolder(val mView: PackageListsBinding) : RecyclerView.ViewHolder(mView.root) {
        init {

            PushDownAnim.setPushDownAnimTo(mView.main)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f)
        }
    }


}