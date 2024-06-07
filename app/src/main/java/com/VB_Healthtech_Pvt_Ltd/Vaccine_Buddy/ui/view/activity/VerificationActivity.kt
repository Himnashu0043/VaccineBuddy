package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityVerificationBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class VerificationActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        lstnr()
    }

    private fun initView() {
        binding.btn.tv.text ="Next"
//        val mString = "Enter the 6-digit code you received in Email\nnor SMS on +01(760) 653-5300  Edit"
//        val  mSpannableString = SpannableString(mString)
//        val mBoldSpan = StyleSpan(Typeface.BOLD)
//         mSpannableString.setSpan(mBoldSpan, 71, 75, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        binding.textView14.text=mSpannableString

    }

    private fun lstnr() {
        binding.textView229.setOnClickListener {
            startActivity(Intent(this, forgotPassword::class.java))
        }
        binding.btn.tv.setOnClickListener {
            val intent = Intent(this,ChangePasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}