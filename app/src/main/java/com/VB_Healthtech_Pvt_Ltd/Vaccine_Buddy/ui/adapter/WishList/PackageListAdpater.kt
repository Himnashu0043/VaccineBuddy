package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.WishList

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.PackageListsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishPackageList.AskedRequest
import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim

class PackageListAdpater(
    val con: Context,
    val wishPackageList: ArrayList<AskedRequest>,
    val onRemove: OnClickWishList,
    val onPress:OnClickWishList
) :
    RecyclerView.Adapter<PackageListAdpater.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PackageListsBinding.inflate(LayoutInflater.from(con), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        if (position == 0) {
//            holder.mView.tvoutofstock.visibility = View.VISIBLE
//        }

        val img: String = wishPackageList!![position].packageData.packageImage
        if (img != null && img.length > 0) {
            Picasso.get().load(img).into(holder.mView.imageView18)
            holder.mView.lottie.visibility = View.GONE
        } else {
            holder.mView.lottie.visibility = View.VISIBLE
        }
        /*if (wishPackageList[position].packageData.price.isNullOrEmpty()) {
            holder.mView.tvWishPackagePrice.text = "$3000"
        } else {
            holder.mView.tvWishPackagePrice.text = "$" + wishPackageList[position].packageData.price
        }*/
        if (wishPackageList[position].packageData.offerPrice.isNullOrEmpty()) {
            holder.mView.divider13.visibility = View.GONE
            holder.mView.tvwishlistOfferPrice.visibility = View.GONE
            holder.mView.tvWishPackagePrice.visibility = View.VISIBLE
            holder.mView.tvWishPackagePrice.text = "₹" + wishPackageList[position].packageData.price

        } else {
            holder.mView.tvWishPackagePrice.text = "₹" + wishPackageList[position].packageData.price
            holder.mView.tvwishlistOfferPrice.text =
                "₹" + wishPackageList[position].packageData.offerPrice
        }
        holder.mView.tvWishPackageName.text = wishPackageList[position].packageData.packageName
        holder.mView.tvWishPackageDes.text = wishPackageList[position].packageData.description
        holder.mView.tvIncludeVaccine.text = wishPackageList[position].packageData.doseInfo.size.toString()

        holder.mView.main.setOnClickListener {
//            con.startActivity(Intent(con, PackageDetailsActivity::class.java))
            onPress.onPackageId(wishPackageList[position])

        }
        holder.mView.ivDelete.setOnClickListener {
            onRemove.onRemoveWishlist(wishPackageList[position].packageData._id)
            /* val dialog = this.let { Dialog(con) }
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

    }

    override fun getItemCount(): Int {
        return wishPackageList.size
    }

    interface OnClickWishList {
        fun onRemoveWishlist(removeId: String)
        fun onPackageId(msg:AskedRequest)
    }

    class MyViewHolder(val mView: PackageListsBinding) : RecyclerView.ViewHolder(mView.root) {
        init {

            PushDownAnim.setPushDownAnimTo(mView.main)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f)
        }
    }
}


