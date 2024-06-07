package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityMyProfileBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.ViewProfile.User
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.EditProfile.EditProfileActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MyProfile.MainProfileActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.catalyist.helper.ErrorUtil
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MyProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var viewModel: ViewModalLogin
    lateinit var userlist: User
    var age = 0
    var referenceCode = ""
    var websiteLink: String = "https://play.google.com/store/apps/details?id=com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
    }

    private fun initView() {
        CommonUtil.themeSet(this, window)
        binding.personaltoobar.tvTittle.text = "My Profile"
        onViewProfileAPI()
        /* try {
             val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 LocalDateTime.now()
             } else {
                 TODO("VERSION.SDK_INT < O")
             }
             val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
             val formatted = current.format(formatter)
             currentdate = formatted
             println("---------->${currentdate}")
             current_year = current.getYear()
             val month = current.month
             println("-----month$month")
             println("---current_year$current_year")
         } catch (e: Exception) {
             e.toString()
         }*/


    }

    private fun lstnr() {
        binding.editButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("Obj", userlist)
            intent.putExtra("fag", true)
            startActivity(intent)
        }
        binding.personaltoobar.ivBack.setOnClickListener {
            startActivity(Intent(this, MainProfileActivity::class.java))
            finish()
        }
        binding.tvcode.setOnClickListener {
            val sendData =
                "Application link : Android users ${websiteLink}\nReference Code :${referenceCode} , IOS users ${"http://vaccinebuddy.app.link/rTTm37MjMvb"}\nIOS Reference Code :${referenceCode}"
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT, sendData
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)

        viewModel.responseViewProfile.observe(this) {
            if (it?.code == 200) {
                userlist = it.user
                referenceCode = it.user.myRefferral
                println("---o")
                binding.tvName.text = it.user.name
                binding.tvEmail.text = it.user.email
                binding.tvNumber.text = it.user.phoneNumber
                if (it.user.profilePic.isNullOrEmpty()) {
                    binding.dummy1Img.visibility = View.VISIBLE
                    binding.imageView38.visibility = View.INVISIBLE
                } else {
                    Picasso.get().load(it.user.profilePic).centerCrop().resize(100, 100)
                        .into(binding.imageView38)
                    binding.imageView38.visibility = View.VISIBLE
                    binding.dummy1Img.visibility = View.GONE

                }
                if (it.user.medicalCondition.isNullOrEmpty()) {
                    binding.textView84.text = "NA"
                } else {
                    binding.textView84.text = it.user.medicalCondition
                }
                if (it.user.dob.isNullOrEmpty() && it.user.dob == "Invalid Date") {
                    binding.tvDate.text = "NA"
                } else {
                    binding.tvDate.text = it.user.dob

                }
                /*val currentDate = LocalDate.parse(it.user.dob)
                year = currentDate.year
                val months_dd = currentDate.month
                println("-----months_add$months_dd")
                println("-----uuuuuuu$year")
                diff_year = current_year - year*/
                var t = getAge(it.user.dob)
                println("---t$t")
                binding.tvAge.text = "${t} years"
                binding.tvGender.text = it.user.gender
                binding.tvcode.text = it.user.myRefferral
                // Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            } else if (it?.code == 401) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    fun onViewProfileAPI() {
        viewModel.onViewProfile(this)
    }

    override fun onResume() {
        super.onResume()
        onViewProfileAPI()

    }

    /*    fun getAge(date: String?): Int {
            var age = 0
            try {
                val date1: Date = dateFormat.parse(date)
                val now = Calendar.getInstance()
                val dob = Calendar.getInstance()
                dob.time = date1
                require(!dob.after(now)) { "Can't be born in the future" }
                val year1 = now[Calendar.YEAR]
                val year2 = dob[Calendar.YEAR]
                age = year1 - year2
                val month1 = now[Calendar.MONTH]
                val month2 = dob[Calendar.MONTH]
                if (month2 > month1) {
                    age--
                } else if (month1 == month2) {
                    val day1 = now[Calendar.DAY_OF_MONTH]
                    val day2 = dob[Calendar.DAY_OF_MONTH]
                    if (day2 > day1) {
                        age--
                    }
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return age
        }*/
    fun getAge(date: String?): Int {
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date1 = formatter.parse(date)
            val now = Calendar.getInstance()
            val dob = Calendar.getInstance()
            dob.time = date1
            require(!dob.after(now)) { "Can't be born in the future" }
            val year1 = now[Calendar.YEAR]
            val year2 = dob[Calendar.YEAR]
            age = year1 - year2
            val month1 = now[Calendar.MONTH]
            val month2 = dob[Calendar.MONTH]
            if (month2 > month1) {
                age--
            } else if (month1 == month2) {
                val day1 = now[Calendar.DAY_OF_MONTH]
                val day2 = dob[Calendar.DAY_OF_MONTH]
                if (day2 > day1) {
                    age--
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
//        if (age < 1) {
//            binding.addBirth.visibility = View.VISIBLE
//        } else {
//            binding.addBirth.visibility = View.GONE
//        }

        Log.d("TAG", "getAge: AGE=> $age")
        return age
    }
    /*private fun getAge(dobString: String): Int {
        var date: Date? = null
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        try {
            date = sdf.parse(dobString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (date == null) return 0
        val dob: Calendar = Calendar.getInstance()
        val today: Calendar = Calendar.getInstance()
        println("-----today$today")
        dob.setTime(date)
        val year: Int = dob.get(Calendar.YEAR)
        val month: Int = dob.get(Calendar.MONTH)
        val day: Int = dob.get(Calendar.DAY_OF_MONTH)
        dob.set(year, month + 1, day)
        var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        println("-----agr$age")
        return age
    }*/
    /* fun getAge(dateTime: String?, currentFormat: String?): Int {
         val dateFormat = SimpleDateFormat(currentFormat)
         try {
             val date = dateFormat.parse(dateTime)
             val calendar = Calendar.getInstance()
             calendar.time = date
             val year = calendar[Calendar.YEAR]
             val month = calendar[Calendar.MONTH]
             val day = calendar[Calendar.DAY_OF_MONTH]
             val currentDate = Date()
             val currentCalendar = Calendar.getInstance()
             currentCalendar.time = currentDate
             val currentYear = currentCalendar[Calendar.YEAR]
             val currentMonth = currentCalendar[Calendar.MONTH]
             val currentDay = currentCalendar[Calendar.DAY_OF_MONTH]
             var deltaYear = currentYear - year
             val deltaMonth = currentMonth - month
             val deltaDay = currentDay - day
             if (deltaYear > 0) {
                 if (deltaMonth < 0) {
                     deltaYear--
                     println("----dddd$deltaYear")
                     println("----dddmmmd$deltaMonth")
                 } else if (deltaDay < 0) {
                     deltaYear--
                 }
                 return deltaYear
             }
         } catch (e: ParseException) {
             e.printStackTrace()
         }
         return 0
     }*/

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}

