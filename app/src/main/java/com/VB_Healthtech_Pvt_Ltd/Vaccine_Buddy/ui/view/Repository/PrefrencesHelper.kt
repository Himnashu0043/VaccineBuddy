package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants.*
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage


object PrefrencesHelper {

    fun writePrefrencesValue(context: Context): SharedPreferences.Editor {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
    }


    fun getPrefrenceStringValue(context: Context, KEY: String): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString(KEY, "")
            .toString()
    }

/*
    fun getLanguage(context: Context?): String {
        val shred = context?.getSharedPreferences("language", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("language", "") ?: ""
    }*/

    fun setLanguage(context: Context, language: String) {
        writePrefrencesValue(context).apply() {
            putString(languageType, language)
        }.apply()
    }

    fun getSaveDeviceID(context: Context?, deviceID: String?) {
        val shred = context?.getSharedPreferences("deviceID", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("deviceID", deviceID)?.apply()
    }

    fun setSaveDeviceID(context: Context?): String {
        val shred = context?.getSharedPreferences("deviceID", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("deviceID", "") ?: ""
    }

    fun getType(context: Context?, type: String?) {
        val shred = context?.getSharedPreferences("type", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("type", type)?.apply()
    }

    fun setType(context: Context?): String {
        val shred = context?.getSharedPreferences("type", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("type", "") ?: ""
    }

    fun getSaveToken(context: Context?, token: String) {
        val shred = context?.getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("token", token)?.apply()
    }

    fun setSaveToken(context: Context?): String {
        val shred = context?.getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("token", "") ?: ""
    }

    fun getKey(context: Context?, key: String) {
        val shred = context?.getSharedPreferences("key", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("key", key)?.apply()
    }

    fun setKey(context: Context?): String {
        val shred = context?.getSharedPreferences("key", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("key", "") ?: ""
    }
    fun getEmailKey(context: Context?, emailkey: String) {
        val shred = context?.getSharedPreferences("emailkey", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("emailkey", emailkey)?.apply()
    }

    fun setEmailKey(context: Context?): String {
        val shred = context?.getSharedPreferences("emailkey", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("emailkey", "") ?: ""
    }




    fun saveLoginStatus(context: Context?, key: Boolean) {
        val shred = context?.getSharedPreferences(kisloginStatus, AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putBoolean(kisloginStatus, key)?.apply()
    }

    fun getLoginStatus(context: Context?): Boolean {
        val shred = context?.getSharedPreferences(kisloginStatus, AppCompatActivity.MODE_PRIVATE)
        return shred?.getBoolean(kisloginStatus, false) ?: false
    }

    fun saveTutorialStatus(context: Context?, key: Boolean) {
        val shred = context?.getSharedPreferences(kisTutorialStatus, AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putBoolean(kisTutorialStatus, key)?.apply()
    }

    fun getTutorialStatus(context: Context?): Boolean {
        val shred = context?.getSharedPreferences(kisTutorialStatus, AppCompatActivity.MODE_PRIVATE)
        return shred?.getBoolean(kisTutorialStatus, true) ?: true
    }

    ///cart_id_list_start
    fun saveCartIDList(context: Context?, cart: String) {
        val shred = context?.getSharedPreferences("cartIDList", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("cartIDList", cart)?.apply()

    }

    fun getCartIDList(context: Context?): String {
        val shred = context?.getSharedPreferences("cartIDList", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("cartIDList", "") ?: ""
    }

    fun saveMedicalCondtions(context: Context?, medical: String) {
        val shred = context?.getSharedPreferences("medical", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("medical", medical)?.apply()
    }

    fun getMedicalCondtions(context: Context?): String {
        val shred = context?.getSharedPreferences("medical", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("medical", "") ?: ""
    }

    fun saveAnyReaction(context: Context?, anyReaction: String) {
        val shred = context?.getSharedPreferences("anyReaction", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("anyReaction", anyReaction)?.apply()
    }

    fun getAnyReactionm(context: Context?): String {
        val shred = context?.getSharedPreferences("anyReaction", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("anyReaction", "") ?: ""
    }


    ///cart_id_list_End
    ////package
    fun savePackageId(context: Context?, packageId: String) {
        val shred = context?.getSharedPreferences("packageId", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("packageId", packageId)?.apply()
    }

    fun getPackageId(context: Context?): String {
        val shred = context?.getSharedPreferences("packageId", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("packageId", "") ?: ""
    }

    fun savePrice(context: Context?, price: String) {
        val shred = context?.getSharedPreferences("price", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("price", price)?.apply()
    }

    fun getPrice(context: Context?): String {
        val shred = context?.getSharedPreferences("price", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("price", "") ?: ""
    }
    fun saveNoDose(context: Context?, noDose: String) {
        val shred = context?.getSharedPreferences("noDose", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("noDose", noDose)?.apply()
    }

    fun getNoDose(context: Context?): String {
        val shred = context?.getSharedPreferences("noDose", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("noDose", "") ?: ""
    }
    fun saveAge(context: Context?, age: String) {
        val shred = context?.getSharedPreferences("age", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("noDose", age)?.apply()
    }

    fun getAge(context: Context?): String {
        val shred = context?.getSharedPreferences("age", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("age", "") ?: ""
    }
    fun saveFagStatus(context: Context?, key: Boolean) {
        val shred = context?.getSharedPreferences(kisFagStatus, AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putBoolean(kisFagStatus, key)?.apply()
    }

    fun getFagStatus(context: Context?): Boolean {
        val shred = context?.getSharedPreferences(kisFagStatus, AppCompatActivity.MODE_PRIVATE)
        return shred?.getBoolean(kisFagStatus, true) ?: true
    }
    fun savePackageList(context: Context?, cart: AskedPackage) {
        val shred = context?.getSharedPreferences("packageList", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("packageList", cart.toString())?.apply()
    }

    fun getPackageList(context: Context?): String {
        val shred = context?.getSharedPreferences("packageList", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("packageList", "") ?: ""
    }

    ////package
    //payment
    fun getOrderId(context: Context?, orderId: String) {
        val shred = context?.getSharedPreferences("orderId", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("orderId", orderId)?.apply()
    }

    fun setOrderId(context: Context?): String {
        val shred = context?.getSharedPreferences("orderId", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("orderId", "") ?: ""
    }
    fun getAddressId(context: Context?, addressId: String) {
        val shred = context?.getSharedPreferences("addressId", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("addressId", addressId)?.apply()
    }

    fun setAddressId(context: Context?): String {
        val shred = context?.getSharedPreferences("addressId", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("addressId", "") ?: ""
    }

    fun getCartPriceStatus(context: Context?, cartPrice: String) {
        val shred = context?.getSharedPreferences("cartPrice", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("cartPrice", cartPrice)?.apply()
    }

    fun setCartPriceStatus(context: Context?): String {
        val shred = context?.getSharedPreferences("cartPrice", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("cartPrice", "") ?: ""
    }
    fun getDiscountPercentageStatus(context: Context?, discountPercentage: String) {
        val shred = context?.getSharedPreferences("discountPercentage", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("discountPercentage", discountPercentage)?.apply()
    }

    fun setDiscountPercentageStatus(context: Context?): String {
        val shred = context?.getSharedPreferences("discountPercentage", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("discountPercentage", "") ?: ""
    }
    fun getWalletAmountStatus(context: Context?, walletAmount: String) {
        val shred = context?.getSharedPreferences("walletAmount", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("walletAmount", walletAmount)?.apply()
    }

    fun setWalletAmountStatus(context: Context?): String {
        val shred = context?.getSharedPreferences("walletAmount", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("walletAmount", "") ?: ""
    }
    fun getDiscamountStatus(context: Context?, discamount: String) {
        val shred = context?.getSharedPreferences("discamount", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("discamount", discamount)?.apply()
    }

    fun setDiscamountStatus(context: Context?): String {
        val shred = context?.getSharedPreferences("discamount", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("discamount", "") ?: ""
    }
    //payment

    ////login
    fun saveEmail_Phone(context: Context?, email_phone: String) {
        val shred = context?.getSharedPreferences("email_phone", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("email_phone", email_phone)?.apply()
    }

    fun getEmail_Phone(context: Context?): String {
        val shred = context?.getSharedPreferences("email_phone", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("email_phone", "") ?: ""
    }

    fun saveLogin_pass(context: Context?, pass: String) {
        val shred = context?.getSharedPreferences("pass", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("pass", pass)?.apply()
    }

    fun getLogin_pass(context: Context?): String {
        val shred = context?.getSharedPreferences("pass", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("pass", "") ?: ""
    }
////login_end

    /////////account Details
    fun saveFullName(context: Context?, fullName: String) {
        val shred = context?.getSharedPreferences("fullName", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("fullName", fullName)?.apply()
    }

    fun getFullName(context: Context?): String {
        val shred = context?.getSharedPreferences("fullName", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("fullName", "") ?: ""
    }

    fun saveEmail(context: Context?, fullName: String) {
        val shred = context?.getSharedPreferences("email", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("email", fullName)?.apply()
    }

    fun getEmail(context: Context?): String {
        val shred = context?.getSharedPreferences("email", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("email", "") ?: ""
    }

    fun saveDate(context: Context?, date: String) {
        val shred = context?.getSharedPreferences("date", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("date", date)?.apply()
    }

    fun getDate(context: Context?): String {
        val shred = context?.getSharedPreferences("date", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("date", "") ?: ""
    }

    fun saveGender(context: Context?, gender: String) {
        val shred = context?.getSharedPreferences("gender", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("gender", gender)?.apply()
    }

    fun getGender(context: Context?): String {
        val shred = context?.getSharedPreferences("gender", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("gender", "") ?: ""
    }

    fun saveRefCode(context: Context?, refcode: String) {
        val shred = context?.getSharedPreferences("refcode", AppCompatActivity.MODE_PRIVATE)
        shred?.edit()?.putString("refcode", refcode)?.apply()
    }

    fun getRefCode(context: Context?): String {
        val shred = context?.getSharedPreferences("refcode", AppCompatActivity.MODE_PRIVATE)
        return shred?.getString("refcode", "") ?: ""
    }

}
