package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.TourItemBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.TourModal
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class TourAdapter(val con: Context, val datalist: ArrayList<TourModal>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: TourItemBinding = TourItemBinding.inflate(
            LayoutInflater.from(
                con
            ), container, false
        )


        binding.ivImg.setImageResource(datalist[position].img)


        container.addView(binding.root, 0)
        return binding.root
    }

    override fun getCount(): Int {
        return  datalist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View)
    }
}