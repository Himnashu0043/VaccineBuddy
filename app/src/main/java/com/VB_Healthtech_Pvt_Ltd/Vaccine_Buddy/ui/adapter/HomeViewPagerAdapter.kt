package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.HomeViewpagerItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.BannerList.AskedData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso

class HomeViewPagerAdapter(val con: Context, val list: ArrayList<AskedData>, val onPress: OnClick) :
    PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: HomeViewpagerItemBinding = HomeViewpagerItemBinding.inflate(
            LayoutInflater.from(
                con
            ), container, false
        )
        val old = list[position].image.replace(" ", "%20")
        if (old != null && old.length > 0) {
            Picasso.get().load(old).into(binding.imageView52)
            binding.lottie.visibility = View.GONE
        } else {
            binding.lottie.visibility = View.VISIBLE
        }
        binding.tvBannerName.text = list[position].bannerTitle
        if (list[position].categoryName != null) {
            binding.tvBannerDes.text = list[position].categoryName
        } else {
            binding.tvBannerDes.text = list[position].bannerName
        }

        binding.mianBannerLay.setOnClickListener {
            onPress.onBannerClick(position, list[position].bannerType, list[position].bannerId)
        }


        container.addView(binding.root, 0)
        return binding.root
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    interface OnClick {
        fun onBannerClick(position: Int, bannerType: String, id: String)
    }
}