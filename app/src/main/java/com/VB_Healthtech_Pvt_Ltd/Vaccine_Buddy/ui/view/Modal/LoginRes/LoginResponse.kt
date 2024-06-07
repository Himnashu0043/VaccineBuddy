package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.LoginRes

data class LoginResponse(
    val activeUser: ActiveUser,
    val code: Int,
    val deviceTokenLogin: Boolean,
    val message: String,
    val token: String
)