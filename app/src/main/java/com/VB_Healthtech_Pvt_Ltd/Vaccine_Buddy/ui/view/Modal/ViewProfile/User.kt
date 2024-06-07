package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.ViewProfile

import java.io.Serializable

data class User(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val customer_number: String,
    val deviceToken: String,
    val deviceTokenLogin: Boolean,
    val dob: String,
    val email: String,
    val gender: String,
    val jwtToken: String,
    val medicalCondition: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val profilePic: String,
    val myRefferral: String,
    val updatedAt: String,
    val userStatus: String
) : Serializable