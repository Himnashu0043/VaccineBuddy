package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun setFormatDate(originalDate: String?): String? {
    if (originalDate !="null"){
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = inputFormat.parse(originalDate)
        val formattedDate = outputFormat.format(date)
        return formattedDate
    }


    return ""

}

fun setnewFormatDate(originalDate: String?): String? {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = inputFormat.parse(originalDate)
    val formattedDate = outputFormat.format(date)

    return formattedDate
}


fun setFormatDateByStatics(originalDate: String?): String? {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("dd-MMM-yy")
    val date = inputFormat.parse(originalDate)
    val formattedDate = outputFormat.format(date)

    return formattedDate
}


fun formatDate(originalDate: String?): String? {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = inputFormat.parse(originalDate)
    val formattedDate = outputFormat.format(date)

    return formattedDate
}


fun formatDate1(originalDate: String?): String? {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = inputFormat.parse(originalDate)
    val formattedDate = outputFormat.format(date)

    return formattedDate
}


@RequiresApi(Build.VERSION_CODES.O)
fun currentTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatterForTime = DateTimeFormatter.ofPattern("HH:mm")
    val formatted = current.format(formatterForTime)
    println("Current Date and Time is: $formatted")
    return formatted
}


fun setFormatTime(time: String?): String {
    val input = SimpleDateFormat("HH:mm")
    val output = SimpleDateFormat("hh:mm a")
    val originalTime: Date = input.parse(time)
    val parsetime = output.format(originalTime)

    return parsetime
}


fun convert24To12hrs(time: String?): String? {
    var convertedTime = ""
    try {
        val displayFormat = SimpleDateFormat("hh:mm a")
        val parseFormat = SimpleDateFormat("HH:mm")
        val date: Date = parseFormat.parse(time)
        convertedTime = displayFormat.format(date)
        println("convertedTime : $convertedTime")
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return convertedTime
    //output 10:23PM
}

fun parseTimeFormat(time: String?): String? {
    val inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    "2022-05-06T 09:25:59.629Z"
    val outputPattern = "HH:mm"
    val inputFormat = SimpleDateFormat(inputPattern)
    val outputFormat = SimpleDateFormat(outputPattern)
    var date: Date? = null
    var str: String? = null
    try {
        date = inputFormat.parse(time)
        str = outputFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return str
}

fun getCurrentDate(): String {
    val date = Calendar.getInstance().time
    val Dateformat = SimpleDateFormat("yyyy-MM-dd")
    val currentDate: String = Dateformat.format(date)

    return currentDate
}

fun getNewFormateCurrentDate(): String {
    val date = Calendar.getInstance().time
    val Dateformat = SimpleDateFormat("dd-MM-yyyy")
    val currentDate: String = Dateformat.format(date)

    return currentDate
}
fun getweekDay(): String {
    val date = Calendar.getInstance().time
    val Dateformat = SimpleDateFormat("EEE")
    val currentDate: String = Dateformat.format(date)

    return currentDate
}


fun getCurrentMonth(): String {
    val date = Calendar.getInstance().time
    val Dateformat = SimpleDateFormat("MM")
    val currentMonth: String = Dateformat.format(date)

    return currentMonth

}

fun timeWithCurrentTime(time: String?): String {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val output = SimpleDateFormat("hh:mm a")
    val originalTime: Date = input.parse(time)
    val parsetime = output.format(originalTime)

    return parsetime
}


fun getMonthOfDate(date: String): String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputFormat = SimpleDateFormat("dd")
    val date = inputFormat.parse(date)
    val formatMonth = outputFormat.format(date)

    return formatMonth
}

fun getCurrentDateAnotherFormate(): String {
    val date = Calendar.getInstance().time
    val Dateformat = SimpleDateFormat("dd-MMM-yyyy")
    val currentDate: String = Dateformat.format(date)

    return currentDate
}

fun convertLongToDate(time: Long): String {
    val date = Date(time)
    val formate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())//2022-04-27
    return formate.format(date)
}


fun convertDateTomonth(date1: Long): String? {
    val date = Date(date1)
    val month = SimpleDateFormat("MM", Locale.getDefault())
    return month.format(date)
}


fun showDateAndTime(context: Context) {
    val c = Calendar.getInstance()
    val dialog = DatePickerDialog(
        context,
        { view, year, month, dayOfMonth ->
            val _year = year.toString()
            val _month = if (month + 1 < 10) "0" + (month + 1) else (month + 1).toString()
            val _date = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
            val pickedDate = "$year-$_month-$_date"

            Log.e("PickedDate: ", "Date: $pickedDate") //2022-04-27

            println(">$pickedDate")

        }, c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.MONTH]
    )
    dialog.datePicker.minDate = System.currentTimeMillis() - 1000
    dialog.show()
}
fun getLocalTime(dateString: String): String {
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = inputFormatter.parse(dateString)
    date.time += ((5 * 60 * 60) + 1800) * 1000
    val outputFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return outputFormatter.format(date)
}
fun getAge(date: String?): Int {
    var age = 0
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
    Log.d("TAG", "getAge: AGE=> $age")
    return age
}









